/*
package it.unifi.ing.stlab.empedocle.services.mpi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.transaction.UserTransaction;

import org.apache.log4j.Logger;
import org.junit.Test;

import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.MergeInformation;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.MergeObservableEntity;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.ObjectFactory;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.ObservableEntityIdentification;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.ObservableEntityIdentifiers;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.UpdatePersonInformation;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityMergeAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityModifyAction;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.factory.UserFactory;

public class MasterObservableEntityIndexServiceTest extends PersistenceTest {
	
	private MasterObservableEntityIndexServiceImpl mpiService;
	
	protected ObservableEntityDao observableEntityDao;
	protected UserDao userDao;
	protected Logger logger;
	protected UserTransaction utx;
	
	private UpdatePersonInformation updatePersonInformation;
	private MergeObservableEntity mergeObservableEntity;
	
	@Override 
	protected void insertData() throws Exception {
		mpiService = new MasterObservableEntityIndexServiceImpl();
		
		observableEntityDao = mock( ObservableEntityDao.class );
		userDao = mock( UserDao.class );
		logger = Logger.getLogger( MasterObservableEntityIndexServiceImpl.class );
		utx = mock( UserTransaction.class );
		
		FieldUtils.assignField( mpiService, "entityManager", entityManager);
		FieldUtils.assignField( mpiService, "observableEntityDao", observableEntityDao );
		FieldUtils.assignField( mpiService, "userDao", userDao );
		FieldUtils.assignField( mpiService, "logger", logger );
		FieldUtils.assignField( mpiService, "utx", utx );
		
		updatePersonInformation = initUpdatePersonInformation();
		mergeObservableEntity = initMergeObservableEntity();

		when( userDao.findByUsername( "administrator" )).thenReturn( UserFactory.createUser() );
		
		doNothing().when( utx ).begin();
		doNothing().when( utx ).commit();
//		doNothing().when( logger ).info( anyString() );
	}

	@Test
	public void testUpdate_observable_entityNotFound() {
		when( observableEntityDao.findByIdentifier( updatePersonInformation.getObservableEntityIdentification()
				.getObservableEntityIdentifiers().getIdAce() ) ).thenReturn( null );
		mpiService.update( updatePersonInformation );
	}
	
	@Test
	public void testUpdate_observableEntityFound() {
		ObservableEntity observableEntity = ObservableEntityFactory.createObservableEntity();
		
		assertNull( observableEntity.getDestination() );

		when( observableEntityDao.findByIdentifier( updatePersonInformation.getObservableEntityIdentification()
				.getObservableEntityIdentifiers().getIdAce() ) ).thenReturn( observable_entity );
		mpiService.update( updatePersonInformation );
		
		// a new ObservableEntityModifyAction is created
		assertNotNull( observableEntity.getDestination() );
		assertTrue( observableEntity.getDestination() instanceof ObservableEntityModifyAction);
		
		// the ObservableEntityModifyAction's source is observable_entity
		assertEquals( observable_entity, ((ObservableEntityModifyAction) observableEntity.getDestination()).getSource() );
		
		// the ObservableEntityModifyAction's target is a new observable_entity with the data collected in updatePersonInformation
		assertNotEquals( observable_entity, ((ObservableEntityModifyAction) observableEntity.getDestination()).getTarget() );
		assertEquals( updatePersonInformation.getObservableEntityIdentification().getObservableEntityIdentifiers().getIdAce(),
				((ObservableEntityModifyAction) observableEntity.getDestination()).getTarget().getIdentifier().getCode() );
		assertEquals( updatePersonInformation.getObservableEntityIdentification().getCognome(),
				((ObservableEntityModifyAction) observableEntity.getDestination()).getTarget().getSurname() );
		assertEquals( updatePersonInformation.getObservableEntityIdentification().getNome(),
				((ObservableEntityModifyAction) observableEntity.getDestination()).getTarget().getName() );
	}
	
	@Test
	public void testMerge_masterAndSlaveNotFound() {
		when( observableEntityDao.findByIdentifier(
				mergeObservableEntity.getObservableEntityIdentification().getObservableEntityIdentifiers().getIdAce() ) )
						.thenReturn( null );
		mpiService.merge( mergeObservableEntity );
	}
	
	@Test
	public void testMerge_masterFoundSlaveNotFound() {
		ObservableEntity master = ObservableEntityFactory.createObservableEntity();
		
		assertNull( master.getDestination() );

		when( observableEntityDao.findByIdentifier( mergeObservableEntity.getObservableEntityIdentification()
				.getObservableEntityIdentifiers().getIdAce() ) ).thenReturn( master );
		when( observableEntityDao.findByIdentifier( mergeObservableEntity.getMergeInformation()
				.getObservableEntityIdentifiers().getIdAce() ) ).thenReturn( null );
		mpiService.merge( mergeObservableEntity );
		
		// a new ObservableEntityModifyAction is created
		assertNotNull( master.getDestination() );
		assertTrue( master.getDestination() instanceof ObservableEntityModifyAction);
		
		// the ObservableEntityModifyAction's source is the master observable_entity
		assertEquals( master, ((ObservableEntityModifyAction) master.getDestination()).getSource() );
		
		// the ObservableEntityModifyAction's target is a new observable_entity with the data collected in mergeObservableEntity
		assertNotEquals( master, ((ObservableEntityModifyAction) master.getDestination()).getTarget() );
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getObservableEntityIdentifiers().getIdAce(),
				((ObservableEntityModifyAction) master.getDestination()).getTarget().getIdentifier().getCode());
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getCognome(),
				((ObservableEntityModifyAction) master.getDestination()).getTarget().getSurname() );
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getNome(),
				((ObservableEntityModifyAction) master.getDestination()).getTarget().getName() );
		assertNull(((ObservableEntityModifyAction) master.getDestination()).getTarget().getDestination());
	}
	
	@Test
	public void testMerge_masterNotFoundSlaveFound() {
		ObservableEntity slave = ObservableEntityFactory.createObservableEntity();
		
		assertNull( slave.getDestination() );

		when( observableEntityDao.findByIdentifier( mergeObservableEntity.getObservableEntityIdentification()
				.getObservableEntityIdentifiers().getIdAce() ) ).thenReturn( null );
		when( observableEntityDao.findByIdentifier( mergeObservableEntity.getMergeInformation()
				.getObservableEntityIdentifiers().getIdAce() ) ).thenReturn( slave );
		mpiService.merge( mergeObservableEntity );
		
		// a new ObservableEntityMergeAction is created to merge the slave observable_entity and a new master observable_entity generated ex-novo
		assertNotNull( slave.getDestination() );
		assertTrue( slave.getDestination() instanceof ObservableEntityMergeAction);
		
		// the ObservableEntityMergeAction's source2 is the slave observable_entity
		assertEquals( slave, ((ObservableEntityMergeAction) slave.getDestination()).getSource2() );
		
		// the ObservableEntityMergeAction's source1 is the new master observable_entity generated ex-novo starting from 
		// the new data collected in mergeObservableEntity
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getObservableEntityIdentifiers().getIdAce(), 
				((ObservableEntityMergeAction) slave.getDestination()).getSource1().getIdentifier().getCode() );
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getNome(), 
				((ObservableEntityMergeAction) slave.getDestination()).getSource1().getName() );
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getCognome(), 
				((ObservableEntityMergeAction) slave.getDestination()).getSource1().getSurname() );
		
		// the ObservableEntityMergeAction's target is a copy of the master observable_entity
		assertNotEquals( ((ObservableEntityMergeAction) slave.getDestination()).getSource1(), 
				((ObservableEntityMergeAction) slave.getDestination()).getTarget() );
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getObservableEntityIdentifiers().getIdAce(), 
				((ObservableEntityMergeAction) slave.getDestination()).getTarget().getIdentifier().getCode() );
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getNome(), 
				((ObservableEntityMergeAction) slave.getDestination()).getTarget().getName() );
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getCognome(), 
				((ObservableEntityMergeAction) slave.getDestination()).getTarget().getSurname() );	
	}
	
	@Test
	public void testMerge_masterFoundSlaveFound() {
		ObservableEntity master = ObservableEntityFactory.createObservableEntity();
		ObservableEntity slave = ObservableEntityFactory.createObservableEntity();
		
		assertNull( master.getDestination() );
		assertNull( slave.getDestination() );

		when( observableEntityDao.findByIdentifier( mergeObservableEntity.getObservableEntityIdentification()
				.getObservableEntityIdentifiers().getIdAce() ) ).thenReturn( master );
		when( observableEntityDao.findByIdentifier( mergeObservableEntity.getMergeInformation()
				.getObservableEntityIdentifiers().getIdAce() ) ).thenReturn( slave );
		mpiService.merge( mergeObservableEntity );
		
		assertNotNull( master.getDestination() );
		assertNotNull( slave.getDestination() );
		
		// a new ObservableEntityModifyAction is created to update the master observable_entity
		assertTrue( master.getDestination() instanceof ObservableEntityModifyAction );
		assertEquals( master, ((ObservableEntityModifyAction) master.getDestination()).getSource() );
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getObservableEntityIdentifiers().getIdAce(), 
				((ObservableEntityModifyAction) master.getDestination()).getTarget().getIdentifier().getCode() );
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getNome(), 
				((ObservableEntityModifyAction) master.getDestination()).getTarget().getName() );		
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getCognome(), 
				((ObservableEntityModifyAction) master.getDestination()).getTarget().getSurname() );				

		// a new ObservableEntityMergeAction is created to merge the updated version of master observable_entity and the slave observable_entity
		assertTrue( slave.getDestination() instanceof ObservableEntityMergeAction );
		// the ObservableEntityMergeAction's source1 is the copy of the master observable_entity after the ObservableEntityModifyAction
		assertEquals( ((ObservableEntityModifyAction) master.getDestination()).getTarget(), 
				((ObservableEntityMergeAction) slave.getDestination()).getSource1() );
		// the ObservableEntityMergeAction's source2 is the slave observable_entity
		assertEquals( slave, ((ObservableEntityMergeAction) slave.getDestination()).getSource2() );
		// the ObservableEntityMergeAction's target is a copy of the master observable_entity
		assertNotEquals( ((ObservableEntityMergeAction) slave.getDestination()).getSource1(), 
				((ObservableEntityMergeAction) slave.getDestination()).getTarget() );
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getObservableEntityIdentifiers().getIdAce(), 
				((ObservableEntityMergeAction) slave.getDestination()).getTarget().getIdentifier().getCode() );
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getNome(), 
				((ObservableEntityMergeAction) slave.getDestination()).getTarget().getName() );
		assertEquals( mergeObservableEntity.getObservableEntityIdentification().getCognome(), 
				((ObservableEntityMergeAction) slave.getDestination()).getTarget().getSurname() );	
	}
	
	private UpdatePersonInformation initUpdatePersonInformation() {
		ObjectFactory of = new ObjectFactory();
		UpdatePersonInformation upi = of.createUpdatePersonInformation();
		
		// create observable_entity information with id ACE and some personal data
		ObservableEntityIdentification identification = of.createObservableEntityIdentification();
		ObservableEntityIdentifiers identifiers = of.createObservableEntityIdentifiers();
		identifiers.setIdAce( "p001" );
		identification.setObservableEntityIdentifiers( identifiers );
		identification.setCognome( "Rossi" );
		identification.setNome( "Luca" );

		// add observable_entity information to upi
		upi.setObservableEntityIdentification( identification );
		
		return upi;
	}
	
	private MergeObservableEntity initMergeObservableEntity() {
		ObjectFactory of = new ObjectFactory();
		MergeObservableEntity mp = of.createMergeObservableEntity();
		
		// create master observable_entity information with id ACE and some personal data
		ObservableEntityIdentification identification = of.createObservableEntityIdentification();
		ObservableEntityIdentifiers masterIdentifiers = of.createObservableEntityIdentifiers();
		masterIdentifiers.setIdAce( "master" );
		identification.setObservableEntityIdentifiers( masterIdentifiers );
		identification.setCognome( "Rossi" );
		identification.setNome( "Luca" );
		
		// create slave observable_entity information with id ACE
		MergeInformation mergeInfo = of.createMergeInformation();
		ObservableEntityIdentifiers slaveIdentifiers = of.createObservableEntityIdentifiers();
		slaveIdentifiers.setIdAce( "slave" );
		mergeInfo.setObservableEntityIdentifiers( slaveIdentifiers );
		
		// add master information to mp
		mp.setObservableEntityIdentification( identification );
		// add slave information to mp
		mp.setMergeInformation( mergeInfo );
		
		return mp;
	}

}
*/
