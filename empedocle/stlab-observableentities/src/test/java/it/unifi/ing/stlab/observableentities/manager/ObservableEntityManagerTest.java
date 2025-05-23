package it.unifi.ing.stlab.observableentities.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityMergeAction;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class ObservableEntityManagerTest {

	protected ObservableEntityManager manager;
	protected ObservableEntity observable_entity1;
	protected ObservableEntity observable_entity2;
	
	@Before
	public void setUp() {
		manager = new ObservableEntityManager();
		
		observable_entity1 = ObservableEntityFactory.createObservableEntity();
		observable_entity1.setName( "Prova1" );
		
		observable_entity2 = ObservableEntityFactory.createObservableEntity();
		observable_entity2.setName( "Prova2" );
	}
	
	@Test
	public void testmergeObservableEntities() {
		ObservableEntity merged = manager.merge( UserFactory.createUser(), 
												new Time( new Date() ), 
												observable_entity1, observable_entity2 );
		
		assertNotNull( merged );
		assertTrue( merged.listBefore().contains( observable_entity1 ) );
		assertTrue( merged.listBefore().contains( observable_entity2 ) );
		assertEquals( ObservableEntityMergeAction.class, merged.getOrigin().getClass() );
		assertEquals( observable_entity1, ((ObservableEntityMergeAction)merged.getOrigin()).getSource1() );
		assertEquals( observable_entity2, ((ObservableEntityMergeAction)merged.getOrigin()).getSource2() );
	}
	
	@Test
	public void testmergeObservableEntities_Null() {
		ObservableEntity merged = manager.merge( UserFactory.createUser(), 
												new Time( new Date() ), 
												null, observable_entity2 );
		
		assertNotNull( merged );
		assertTrue( merged.listBefore().contains( observable_entity1 ) );
		assertTrue( merged.listBefore().contains( observable_entity2 ) );
		assertEquals( ObservableEntityMergeAction.class, merged.getOrigin().getClass() );
		assertEquals( observable_entity1, ((ObservableEntityMergeAction)merged.getOrigin()).getSource1() );
		assertEquals( observable_entity2, ((ObservableEntityMergeAction)merged.getOrigin()).getSource2() );
	}
	
	
}
