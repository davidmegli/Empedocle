package it.unifi.ing.stlab.observableentities.dao;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.observableentities.manager.ObservableEntityManager;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Date;
import java.util.List;

import org.junit.Test;

public class ObservableEntityDaoTest extends PersistenceTest {

	protected ObservableEntityDao dao;
	
	protected ObservableEntity p1, p2, p3;
	protected User author;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		dao = new ObservableEntityDaoBean();
		FieldUtils.assignField( dao, "entityManager", entityManager );
	}
	
	@Override
	public void insertData() {
		p1 = ObservableEntityFactory.createObservableEntity();
		p1.setIdentifier( ObservableEntityFactory.createObservableEntityIdentifier() );
		p1.getIdentifier().setCode( "1234" );
		entityManager.persist( p1 );
		
		p2 = ObservableEntityFactory.createObservableEntity();
		p2.setIdentifier( ObservableEntityFactory.createObservableEntityIdentifier() );
		p2.getIdentifier().setCode( "5678" );
		entityManager.persist( p2 );
		
		author = UserFactory.createUser();
		entityManager.persist( author );
		
		ObservableEntityManager manager = new ObservableEntityManager();
		p3 = manager.merge( author, new Time( new Date() ), p1, p2 );
		entityManager.persist( p3 );
	}
	
	@Test
	public void testFindLastVersionById() {
		ObservableEntity result1 = dao.findLastVersionById( p1.getId() );
		assertEquals( p3, result1 );
		
		ObservableEntity result2 = dao.findLastVersionById( p2.getId() );
		assertEquals( p3, result2 );

		ObservableEntity result3 = dao.findLastVersionById( p3.getId() );
		assertEquals( p3, result3 );
	}
	
	@Test
	public void testFindByIdentifier() {
		ObservableEntity result = dao.findByIdentifier( "1234" );
		
		assertEquals( p3, result );
	}
}
