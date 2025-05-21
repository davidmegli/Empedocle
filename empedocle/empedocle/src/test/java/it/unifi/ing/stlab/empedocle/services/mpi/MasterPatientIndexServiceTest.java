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
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.MergeWoodElement;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.ObjectFactory;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.WoodElementIdentification;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.WoodElementIdentifiers;
import it.unifi.ing.stlab.empedocle.services.mpi.jaxb.UpdatePersonInformation;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementMergeAction;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementModifyAction;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.factory.UserFactory;

public class MasterWoodElementIndexServiceTest extends PersistenceTest {
	
	private MasterWoodElementIndexServiceImpl mpiService;
	
	protected WoodElementDao woodElementDao;
	protected UserDao userDao;
	protected Logger logger;
	protected UserTransaction utx;
	
	private UpdatePersonInformation updatePersonInformation;
	private MergeWoodElement mergeWoodElement;
	
	@Override 
	protected void insertData() throws Exception {
		mpiService = new MasterWoodElementIndexServiceImpl();
		
		woodElementDao = mock( WoodElementDao.class );
		userDao = mock( UserDao.class );
		logger = Logger.getLogger( MasterWoodElementIndexServiceImpl.class );
		utx = mock( UserTransaction.class );
		
		FieldUtils.assignField( mpiService, "entityManager", entityManager);
		FieldUtils.assignField( mpiService, "woodElementDao", woodElementDao );
		FieldUtils.assignField( mpiService, "userDao", userDao );
		FieldUtils.assignField( mpiService, "logger", logger );
		FieldUtils.assignField( mpiService, "utx", utx );
		
		updatePersonInformation = initUpdatePersonInformation();
		mergeWoodElement = initMergeWoodElement();

		when( userDao.findByUsername( "administrator" )).thenReturn( UserFactory.createUser() );
		
		doNothing().when( utx ).begin();
		doNothing().when( utx ).commit();
//		doNothing().when( logger ).info( anyString() );
	}

	@Test
	public void testUpdate_wood_elementNotFound() {
		when( woodElementDao.findByIdentifier( updatePersonInformation.getWoodElementIdentification()
				.getWoodElementIdentifiers().getIdAce() ) ).thenReturn( null );
		mpiService.update( updatePersonInformation );
	}
	
	@Test
	public void testUpdate_woodElementFound() {
		WoodElement woodElement = WoodElementFactory.createWoodElement();
		
		assertNull( woodElement.getDestination() );

		when( woodElementDao.findByIdentifier( updatePersonInformation.getWoodElementIdentification()
				.getWoodElementIdentifiers().getIdAce() ) ).thenReturn( wood_element );
		mpiService.update( updatePersonInformation );
		
		// a new WoodElementModifyAction is created
		assertNotNull( woodElement.getDestination() );
		assertTrue( woodElement.getDestination() instanceof WoodElementModifyAction);
		
		// the WoodElementModifyAction's source is wood_element
		assertEquals( wood_element, ((WoodElementModifyAction) woodElement.getDestination()).getSource() );
		
		// the WoodElementModifyAction's target is a new wood_element with the data collected in updatePersonInformation
		assertNotEquals( wood_element, ((WoodElementModifyAction) woodElement.getDestination()).getTarget() );
		assertEquals( updatePersonInformation.getWoodElementIdentification().getWoodElementIdentifiers().getIdAce(),
				((WoodElementModifyAction) woodElement.getDestination()).getTarget().getIdentifier().getCode() );
		assertEquals( updatePersonInformation.getWoodElementIdentification().getCognome(),
				((WoodElementModifyAction) woodElement.getDestination()).getTarget().getSurname() );
		assertEquals( updatePersonInformation.getWoodElementIdentification().getNome(),
				((WoodElementModifyAction) woodElement.getDestination()).getTarget().getName() );
	}
	
	@Test
	public void testMerge_masterAndSlaveNotFound() {
		when( woodElementDao.findByIdentifier(
				mergeWoodElement.getWoodElementIdentification().getWoodElementIdentifiers().getIdAce() ) )
						.thenReturn( null );
		mpiService.merge( mergeWoodElement );
	}
	
	@Test
	public void testMerge_masterFoundSlaveNotFound() {
		WoodElement master = WoodElementFactory.createWoodElement();
		
		assertNull( master.getDestination() );

		when( woodElementDao.findByIdentifier( mergeWoodElement.getWoodElementIdentification()
				.getWoodElementIdentifiers().getIdAce() ) ).thenReturn( master );
		when( woodElementDao.findByIdentifier( mergeWoodElement.getMergeInformation()
				.getWoodElementIdentifiers().getIdAce() ) ).thenReturn( null );
		mpiService.merge( mergeWoodElement );
		
		// a new WoodElementModifyAction is created
		assertNotNull( master.getDestination() );
		assertTrue( master.getDestination() instanceof WoodElementModifyAction);
		
		// the WoodElementModifyAction's source is the master wood_element
		assertEquals( master, ((WoodElementModifyAction) master.getDestination()).getSource() );
		
		// the WoodElementModifyAction's target is a new wood_element with the data collected in mergeWoodElement
		assertNotEquals( master, ((WoodElementModifyAction) master.getDestination()).getTarget() );
		assertEquals( mergeWoodElement.getWoodElementIdentification().getWoodElementIdentifiers().getIdAce(),
				((WoodElementModifyAction) master.getDestination()).getTarget().getIdentifier().getCode());
		assertEquals( mergeWoodElement.getWoodElementIdentification().getCognome(),
				((WoodElementModifyAction) master.getDestination()).getTarget().getSurname() );
		assertEquals( mergeWoodElement.getWoodElementIdentification().getNome(),
				((WoodElementModifyAction) master.getDestination()).getTarget().getName() );
		assertNull(((WoodElementModifyAction) master.getDestination()).getTarget().getDestination());
	}
	
	@Test
	public void testMerge_masterNotFoundSlaveFound() {
		WoodElement slave = WoodElementFactory.createWoodElement();
		
		assertNull( slave.getDestination() );

		when( woodElementDao.findByIdentifier( mergeWoodElement.getWoodElementIdentification()
				.getWoodElementIdentifiers().getIdAce() ) ).thenReturn( null );
		when( woodElementDao.findByIdentifier( mergeWoodElement.getMergeInformation()
				.getWoodElementIdentifiers().getIdAce() ) ).thenReturn( slave );
		mpiService.merge( mergeWoodElement );
		
		// a new WoodElementMergeAction is created to merge the slave wood_element and a new master wood_element generated ex-novo
		assertNotNull( slave.getDestination() );
		assertTrue( slave.getDestination() instanceof WoodElementMergeAction);
		
		// the WoodElementMergeAction's source2 is the slave wood_element
		assertEquals( slave, ((WoodElementMergeAction) slave.getDestination()).getSource2() );
		
		// the WoodElementMergeAction's source1 is the new master wood_element generated ex-novo starting from 
		// the new data collected in mergeWoodElement
		assertEquals( mergeWoodElement.getWoodElementIdentification().getWoodElementIdentifiers().getIdAce(), 
				((WoodElementMergeAction) slave.getDestination()).getSource1().getIdentifier().getCode() );
		assertEquals( mergeWoodElement.getWoodElementIdentification().getNome(), 
				((WoodElementMergeAction) slave.getDestination()).getSource1().getName() );
		assertEquals( mergeWoodElement.getWoodElementIdentification().getCognome(), 
				((WoodElementMergeAction) slave.getDestination()).getSource1().getSurname() );
		
		// the WoodElementMergeAction's target is a copy of the master wood_element
		assertNotEquals( ((WoodElementMergeAction) slave.getDestination()).getSource1(), 
				((WoodElementMergeAction) slave.getDestination()).getTarget() );
		assertEquals( mergeWoodElement.getWoodElementIdentification().getWoodElementIdentifiers().getIdAce(), 
				((WoodElementMergeAction) slave.getDestination()).getTarget().getIdentifier().getCode() );
		assertEquals( mergeWoodElement.getWoodElementIdentification().getNome(), 
				((WoodElementMergeAction) slave.getDestination()).getTarget().getName() );
		assertEquals( mergeWoodElement.getWoodElementIdentification().getCognome(), 
				((WoodElementMergeAction) slave.getDestination()).getTarget().getSurname() );	
	}
	
	@Test
	public void testMerge_masterFoundSlaveFound() {
		WoodElement master = WoodElementFactory.createWoodElement();
		WoodElement slave = WoodElementFactory.createWoodElement();
		
		assertNull( master.getDestination() );
		assertNull( slave.getDestination() );

		when( woodElementDao.findByIdentifier( mergeWoodElement.getWoodElementIdentification()
				.getWoodElementIdentifiers().getIdAce() ) ).thenReturn( master );
		when( woodElementDao.findByIdentifier( mergeWoodElement.getMergeInformation()
				.getWoodElementIdentifiers().getIdAce() ) ).thenReturn( slave );
		mpiService.merge( mergeWoodElement );
		
		assertNotNull( master.getDestination() );
		assertNotNull( slave.getDestination() );
		
		// a new WoodElementModifyAction is created to update the master wood_element
		assertTrue( master.getDestination() instanceof WoodElementModifyAction );
		assertEquals( master, ((WoodElementModifyAction) master.getDestination()).getSource() );
		assertEquals( mergeWoodElement.getWoodElementIdentification().getWoodElementIdentifiers().getIdAce(), 
				((WoodElementModifyAction) master.getDestination()).getTarget().getIdentifier().getCode() );
		assertEquals( mergeWoodElement.getWoodElementIdentification().getNome(), 
				((WoodElementModifyAction) master.getDestination()).getTarget().getName() );		
		assertEquals( mergeWoodElement.getWoodElementIdentification().getCognome(), 
				((WoodElementModifyAction) master.getDestination()).getTarget().getSurname() );				

		// a new WoodElementMergeAction is created to merge the updated version of master wood_element and the slave wood_element
		assertTrue( slave.getDestination() instanceof WoodElementMergeAction );
		// the WoodElementMergeAction's source1 is the copy of the master wood_element after the WoodElementModifyAction
		assertEquals( ((WoodElementModifyAction) master.getDestination()).getTarget(), 
				((WoodElementMergeAction) slave.getDestination()).getSource1() );
		// the WoodElementMergeAction's source2 is the slave wood_element
		assertEquals( slave, ((WoodElementMergeAction) slave.getDestination()).getSource2() );
		// the WoodElementMergeAction's target is a copy of the master wood_element
		assertNotEquals( ((WoodElementMergeAction) slave.getDestination()).getSource1(), 
				((WoodElementMergeAction) slave.getDestination()).getTarget() );
		assertEquals( mergeWoodElement.getWoodElementIdentification().getWoodElementIdentifiers().getIdAce(), 
				((WoodElementMergeAction) slave.getDestination()).getTarget().getIdentifier().getCode() );
		assertEquals( mergeWoodElement.getWoodElementIdentification().getNome(), 
				((WoodElementMergeAction) slave.getDestination()).getTarget().getName() );
		assertEquals( mergeWoodElement.getWoodElementIdentification().getCognome(), 
				((WoodElementMergeAction) slave.getDestination()).getTarget().getSurname() );	
	}
	
	private UpdatePersonInformation initUpdatePersonInformation() {
		ObjectFactory of = new ObjectFactory();
		UpdatePersonInformation upi = of.createUpdatePersonInformation();
		
		// create wood_element information with id ACE and some personal data
		WoodElementIdentification identification = of.createWoodElementIdentification();
		WoodElementIdentifiers identifiers = of.createWoodElementIdentifiers();
		identifiers.setIdAce( "p001" );
		identification.setWoodElementIdentifiers( identifiers );
		identification.setCognome( "Rossi" );
		identification.setNome( "Luca" );

		// add wood_element information to upi
		upi.setWoodElementIdentification( identification );
		
		return upi;
	}
	
	private MergeWoodElement initMergeWoodElement() {
		ObjectFactory of = new ObjectFactory();
		MergeWoodElement mp = of.createMergeWoodElement();
		
		// create master wood_element information with id ACE and some personal data
		WoodElementIdentification identification = of.createWoodElementIdentification();
		WoodElementIdentifiers masterIdentifiers = of.createWoodElementIdentifiers();
		masterIdentifiers.setIdAce( "master" );
		identification.setWoodElementIdentifiers( masterIdentifiers );
		identification.setCognome( "Rossi" );
		identification.setNome( "Luca" );
		
		// create slave wood_element information with id ACE
		MergeInformation mergeInfo = of.createMergeInformation();
		WoodElementIdentifiers slaveIdentifiers = of.createWoodElementIdentifiers();
		slaveIdentifiers.setIdAce( "slave" );
		mergeInfo.setWoodElementIdentifiers( slaveIdentifiers );
		
		// add master information to mp
		mp.setWoodElementIdentification( identification );
		// add slave information to mp
		mp.setMergeInformation( mergeInfo );
		
		return mp;
	}

}
*/
