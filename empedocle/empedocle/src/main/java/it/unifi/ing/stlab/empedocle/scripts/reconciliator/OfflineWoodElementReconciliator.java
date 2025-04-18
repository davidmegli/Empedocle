package it.unifi.ing.stlab.empedocle.scripts.reconciliator;

import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;
import it.unifi.ing.stlab.woodelements.model.Address;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.WoodElementIdentifier;
import it.unifi.ing.stlab.woodelements.model.Sex;
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
public class OfflineWoodElementReconciliator {
	
	@PersistenceContext
	private EntityManager entityManager;	
	
	@Resource
	private UserTransaction utx;	
	
	@EJB
	private WoodElementDao woodElementDao;
	
	@EJB
	private UserDao userDao;
	
	@Inject
	private Logger logger;
	
	private MySQLConnector connector;
	
	private static final String DB_ADDRESS = "jdbc:mysql://localhost/empdb_dupl_wood_elements";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "root";
	
	@PostConstruct
	@Remove
	public void init() {
        logger.info( "*** OfflineWoodElementReconciliator started ***" );
        initConnection();
        doJob();
	}
	
	@PreDestroy //XXX non viene distrutto
	public void destroy() {
		closeConnection();
		logger.info("*** OfflineWoodElementReconciliator stopped ***");
	}
	
	private void initConnection() {
        connector = new MySQLConnector( DB_ADDRESS, DB_USER, DB_PASS );
	}
	
	private void doJob() {
		try {
			ResultSet rs = connector.executeQuery( "SELECT * from wood_elements WHERE NOT master" );
			int size = connector.count( rs );
			logger.info( "Detected " + size + " wood_elements to be merged!" );
			
			int current = 1;
			int merged = 0;
			while ( rs.next() ) {
				logger.info( "Started " + current + "/" + size + " wood_elements" );
	
				utx.begin();
			
				String slaveIdentifier = rs.getString( "id_ACE" );
				String masterIdentifier = rs.getString( "master_id_ACE" );
	
				WoodElement slave = woodElementDao.findByIdentifier( slaveIdentifier );
				WoodElement master = woodElementDao.findByIdentifier( masterIdentifier );
	
				WoodElementManager woodElementManager = new WoodElementManager();
				User author = userDao.findByUsername( "administrator" );
				Time time = new Time( new Date() );
				
				if ( master == null ) {
					// master not found
					if ( slave == null ) {
						// master and slave not found: wood_element not managed by empedocle
						logger.error( "Cannot find the wood_elements you wish to merge" );
					} else {
						// slave found, master not found
	
						// 1. a new wood_element with role master is created
						master = woodElementManager.createWoodElement( author, time );
						update( master, rs );
						entityManager.persist( master );
	
						// 2. master and slave are merged
						WoodElement result = woodElementManager.merge( author, time, master, slave );
						entityManager.persist( result );
						logger.info( "Merged slave " + slaveIdentifier + " with master " + masterIdentifier );
						merged++;
					}
				} else {
					// master found
					
					// 1. master is updated
					WoodElement copy = woodElementManager.modify( author, time, master );
					update( copy, rs );
					
					WoodElement purged = woodElementManager.purge( copy );
					if ( purged != null ) {
						master = purged;
						entityManager.persist( master );
						
						//XXX per ora i riferimenti agli survey_schedule rimangono sempre sullo stesso paziente
						// updateSurveySchedulesReferences();
					}
					
					if ( slave != null ) {
						// 2. master and slave are merged
						WoodElement result = woodElementManager.merge( author, time, master, slave );
						entityManager.persist( result );
						logger.info( "Merged slave " + slaveIdentifier + " with master " + masterIdentifier );
						merged++;
					}
				}
				utx.commit();
				logger.info( "Ended " + current + "/" + size + " wood_elements" );
				current++;
			}
			rs.close();
			logger.info( "Merged  " + merged + "/" + size + " wood_elements" );
		} catch ( Exception e ) {
			logger.error( e );
			
			try {
				utx.rollback();
			} catch (Exception ute) {
				logger.error( ute );
			}
		}
	}
	
	private void update( WoodElement woodElement, ResultSet rs ) throws SQLException {
		
		WoodElementIdentifier identifier = retrieveWoodElementIdentifier( rs.getString( "master_id_ACE" ) );
		woodElement.setIdentifier( identifier );
		
		woodElement.setTaxCode( check( rs.getString( "master_tax_code" ) ) );
		woodElement.setSsnCode( check( rs.getString( "master_ssn_code" ) ) );
		woodElement.setName( check( rs.getString( "master_name" ) ) );
		woodElement.setSurname( check( rs.getString( "master_surname" ) ) );
		woodElement.setSex( Sex.valueOf( check( rs.getString( "master_sex" ) ) ) );
		woodElement.setBirthDate( rs.getDate( "master_birth_date" ) ) ;
		woodElement.setBirthPlace( check( rs.getString( "master_birth_place" ) ) );

		if ( check( rs.getString( "master_residence_place" ) ) != null ) {
			woodElement.setResidence( new Address() );
			woodElement.getResidence().setPlace( rs.getString( "master_residence_place" ) );
		}
		
		if ( check( rs.getString( "master_domicile_place" ) )  != null ) {
			woodElement.setDomicile( new Address() );
			woodElement.getDomicile().setPlace( rs.getString( "master_domicile_place" ) );
		}
		
		woodElement.setHomePhone( check( rs.getString( "master_home_phone" ) ) );
		woodElement.setWorkPhone( check( rs.getString( "master_work_phone" ) ) );
		woodElement.setNationality( check( rs.getString( "master_nationality" ) ) );
	}
	
	private String check( String value ) {
		return ( value != null && !value.isEmpty() ) ? value : null;
	}

	private WoodElementIdentifier retrieveWoodElementIdentifier( String code ){
		WoodElementIdentifier identifier = null;
		
		if ( code == null ) 
			throw new IllegalArgumentException( "code is null" );
		
		List<WoodElementIdentifier> results =
				entityManager.createQuery( " select pi " +
									" from WoodElementIdentifier pi " +
									" where pi.code = :code ", 
									WoodElementIdentifier.class )
							.setParameter( "code", code )
							.setFlushMode( FlushModeType.COMMIT )
							.setMaxResults( 1 )
							.getResultList();
		
		if ( results.size() == 0 ) {
			identifier = WoodElementFactory.createWoodElementIdentifier();
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
}
