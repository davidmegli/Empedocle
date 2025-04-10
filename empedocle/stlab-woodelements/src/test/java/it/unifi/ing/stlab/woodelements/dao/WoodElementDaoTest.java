package it.unifi.ing.stlab.woodelements.dao;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Date;
import java.util.List;

import org.junit.Test;

public class WoodElementDaoTest extends PersistenceTest {

	protected WoodElementDao dao;
	
	protected WoodElement p1, p2, p3;
	protected User author;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		dao = new WoodElementDaoBean();
		FieldUtils.assignField( dao, "entityManager", entityManager );
	}
	
	@Override
	public void insertData() {
		p1 = WoodElementFactory.createWoodElement();
		p1.setIdentifier( WoodElementFactory.createWoodElementIdentifier() );
		p1.getIdentifier().setCode( "1234" );
		p1.setName( "name" );
		p1.setSurname( "surname" );
		entityManager.persist( p1 );
		
		p2 = WoodElementFactory.createWoodElement();
		p2.setIdentifier( WoodElementFactory.createWoodElementIdentifier() );
		p2.getIdentifier().setCode( "5678" );
		entityManager.persist( p2 );
		
		author = UserFactory.createUser();
		entityManager.persist( author );
		
		WoodElementManager manager = new WoodElementManager();
		p3 = manager.merge( author, new Time( new Date() ), p1, p2 );
		entityManager.persist( p3 );
	}
	
	@Test
	public void testFindLastVersionById() {
		WoodElement result1 = dao.findLastVersionById( p1.getId() );
		assertEquals( p3, result1 );
		
		WoodElement result2 = dao.findLastVersionById( p2.getId() );
		assertEquals( p3, result2 );

		WoodElement result3 = dao.findLastVersionById( p3.getId() );
		assertEquals( p3, result3 );
	}
	
	@Test
	public void testFindByIdentifier() {
		WoodElement result = dao.findByIdentifier( "1234" );
		
		assertEquals( p3, result );
	}
	
	@Test
	public void testFindForMerge() {
		WoodElement p4 = WoodElementFactory.createWoodElement();
		p4.setName( "name" );
		p4.setSurname( "surname" );
		entityManager.persist( p4 );
		
		List<WoodElement> result = dao.findForMerge(p3.getName(), p3.getSurname(), p3.getId());
		
		assertEquals( 1, result.size() );
		assertEquals( p4, result.get(0) );
	}
	
	@Test
	public void testMergeWoodElements() {
		WoodElement p4 = WoodElementFactory.createWoodElement();
		entityManager.persist( p4 );
		
		WoodElement result = dao.mergeWoodElements( p4.getId(), p3.getId(), author );
		
		assertEquals( "1234", result.getIdentifier().getCode() );
		assertEquals( "name", result.getName() );
		assertEquals( "surname", result.getSurname() );
	}

	@Test
	public void testFindByName() {
		List<WoodElement> result = dao.findByName( "name", "surname" );
		
		assertEquals( 1, result.size() );
		assertEquals( p3, result.get( 0 ) );
	}
	
}
