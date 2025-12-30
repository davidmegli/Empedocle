/*package it.unifi.ing.stlab.empedocle.scripts.reconciliator;

import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDaoBean;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.observableentities.manager.ObservableEntityManager;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntityIdentifier;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.sql.*;
import java.util.Date;
import java.util.List;

//XXX to be tested
//@Startup
@Singleton
@TransactionManagement( TransactionManagementType.BEAN )
public class OfflineObservableEntityReconciliator {
	
	@PersistenceContext
	private EntityManager entityManager;	
	
	@Resource
	private UserTransaction utx;

	@EJB(beanName = "ObservableEntityDaoBean")
	private ObservableEntityDao observableEntityDao;
	
	@EJB
	private UserDao userDao;
	
	@Inject
	private Logger logger;
	
	private MySQLConnector connector;
	
	private static final String DB_ADDRESS = "jdbc:mysql://localhost/empdb_dupl_observable_entities";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "root";
	
	@PostConstruct
	@Remove
	public void init() {
        logger.info( "*** OfflineObservableEntityReconciliator started ***" );
        initConnection();
        doJob();
	}
	
	@PreDestroy //XXX non viene distrutto
	public void destroy() {
		closeConnection();
		logger.info("*** OfflineObservableEntityReconciliator stopped ***");
	}
	
	private void initConnection() {
        connector = new MySQLConnector( DB_ADDRESS, DB_USER, DB_PASS );
	}
	
	private void doJob() {
		try {
			ResultSet rs = connector.executeQuery( "SELECT * from observable_entities WHERE NOT master" );
			int size = connector.count( rs );
			logger.info( "Detected " + size + " observable_entities to be merged!" );
			
			int current = 1;
			int merged = 0;
			while ( rs.next() ) {
				logger.info( "Started " + current + "/" + size + " observable_entities" );
	
				utx.begin();
			
				String slaveIdentifier = rs.getString( "id_ACE" );
				String masterIdentifier = rs.getString( "master_id_ACE" );
	
				ObservableEntity slave = observableEntityDao.findByIdentifier( slaveIdentifier );
				ObservableEntity master = observableEntityDao.findByIdentifier( masterIdentifier );
	
				//ObservableEntityManager observableEntityManager = new ObservableEntityManager();
				ObservableEntityManager observableEntityManager = observableEntityDao.getManager();
				User author = userDao.findByUsername( "administrator" );
				Time time = new Time( new Date() );
				
				if ( master == null ) {
					// master not found
					if ( slave == null ) {
						// master and slave not found: observable_entity not managed by empedocle
						logger.error( "Cannot find the observable_entities you wish to merge" );
					} else {
						// slave found, master not found
	
						// 1. a new observable_entity with role master is created
						master = observableEntityManager.create( author, time );
						update( master, rs );
						entityManager.persist( master );
	
						// 2. master and slave are merged
						ObservableEntity result = observableEntityManager.merge( author, time, master, slave );
						entityManager.persist( result );
						logger.info( "Merged slave " + slaveIdentifier + " with master " + masterIdentifier );
						merged++;
					}
				} else {
					// master found
					
					// 1. master is updated
					ObservableEntity copy = (ObservableEntity) observableEntityManager.modify( author, time, master );
					update( copy, rs );
					
					ObservableEntity purged = (ObservableEntity) observableEntityManager.purge( copy );
					if ( purged != null ) {
						master = purged;
						entityManager.persist( master );
						
						//XXX per ora i riferimenti agli survey_schedule rimangono sempre sullo stesso observable entity
						// updateSurveySchedulesReferences();
					}
					
					if ( slave != null ) {
						// 2. master and slave are merged
						ObservableEntity result = observableEntityManager.merge( author, time, master, slave );
						entityManager.persist( result );
						logger.info( "Merged slave " + slaveIdentifier + " with master " + masterIdentifier );
						merged++;
					}
				}
				utx.commit();
				logger.info( "Ended " + current + "/" + size + " observable_entities" );
				current++;
			}
			rs.close();
			logger.info( "Merged  " + merged + "/" + size + " observable_entities" );
		} catch ( Exception e ) {
			logger.error( e );
			
			try {
				utx.rollback();
			} catch (Exception ute) {
				logger.error( ute );
			}
		}
	}
	
	private void update( ObservableEntity observableEntity, ResultSet rs ) throws SQLException {
		
		ObservableEntityIdentifier identifier = retrieveObservableEntityIdentifier( rs.getString( "master_id_ACE" ) );
		observableEntity.setIdentifier( identifier );

		//TODO: aggiungere attributi woodelement???

	}
	
	private String check( String value ) {
		return ( value != null && !value.isEmpty() ) ? value : null;
	}

	private ObservableEntityIdentifier retrieveObservableEntityIdentifier( String code ){
		ObservableEntityIdentifier identifier = null;
		
		if ( code == null ) 
			throw new IllegalArgumentException( "code is null" );
		
		List<ObservableEntityIdentifier> results =
				entityManager.createQuery( " select pi " +
									" from ObservableEntityIdentifier pi " +
									" where pi.code = :code ", 
									ObservableEntityIdentifier.class )
							.setParameter( "code", code )
							.setFlushMode( FlushModeType.COMMIT )
							.setMaxResults( 1 )
							.getResultList();
		
		if ( results.size() == 0 ) {
			identifier = observableEntityDao.getManager().getFactory().createIdentifier();
			identifier.setCode( code );
			
		} else {
			identifier = results.get( 0 );
			entityManager.lock( identifier, LockModeType.OPTIMISTIC_FORCE_INCREMENT );
		}
			
		return identifier;
	}

	private void closeConnection() {
		connector.close();
	}
	
	class MySQLConnector {
		
		private final Connection connection;
		
		MySQLConnector( String url,  String user, String password ) {
			try {
				connection = DriverManager.getConnection( url, user, password );
				logger.info( "Successfully Connected!" );
			} catch (SQLException e) {
				logger.error( "Error", e );
				throw new RuntimeException( e );
			}
		}
		
		int count( ResultSet rs ) {
			int size = 0;
			try {
				rs.last();
			    size = rs.getRow();
			    rs.beforeFirst();
			}
			catch(Exception ex) {
			    return 0;
			}
			return size;
		}

		ResultSet executeQuery( String query ) {
			try {
				Statement stmt = connection.createStatement();

				logger.info( query );

				return stmt.executeQuery( query );
			} catch (SQLException e) {
				logger.error( "Error", e );
				throw new RuntimeException( e );
			}
		}
		
		int executeUpdate( String query ) {
			int result = 0;
			Statement stmt = null;

			try {
				stmt = connection.createStatement();
				
				logger.info( query );
				
				result = stmt.executeUpdate( query );

				logger.info( result + " records changed" );
				
				return result;
				
			} catch (SQLException e) {
				logger.error( "Error!", e );
				throw new RuntimeException( e );			
			}
		}
		
		void close() {
			try {
				connection.close();
			} catch ( SQLException e ) {
				logger.error( "Error!", e );
			}
		}
	}
}*/
