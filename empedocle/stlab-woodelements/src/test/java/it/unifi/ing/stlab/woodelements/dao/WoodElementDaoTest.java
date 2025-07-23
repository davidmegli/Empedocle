package it.unifi.ing.stlab.woodelements.dao;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class WoodElementDaoTest extends PersistenceTest {

	protected WoodElementDaoBean dao;
	
	protected WoodElement p1, p2, p3;
	protected User author;
	protected WoodElementManager manager;
	protected WoodElementFactory factory;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		dao = new WoodElementDaoBean();
		FieldUtils.assignField( dao, "entityManager", entityManager );
	}
	
	@Override
	public void insertData() {
		manager = new WoodElementManager();
		factory = manager.getFactory();
		p1 = factory.create();
		p1.setIdentifier( factory.createIdentifier() );
		p1.getIdentifier().setCode( "1234" );
		entityManager.persist( p1 );
		
		p2 = factory.create();
		p2.setIdentifier( factory.createIdentifier() );
		p2.getIdentifier().setCode( "5678" );
		entityManager.persist( p2 );
		
		author = UserFactory.createUser();
		entityManager.persist( author );

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
}
