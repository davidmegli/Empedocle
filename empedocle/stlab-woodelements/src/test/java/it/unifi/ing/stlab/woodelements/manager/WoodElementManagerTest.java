package it.unifi.ing.stlab.woodelements.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementMergeAction;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class WoodElementManagerTest {

	protected WoodElementManager manager;
	protected WoodElement wood_element1;
	protected WoodElement wood_element2;
	protected WoodElementFactory factory;
	
	@Before
	public void setUp() {
		manager = new WoodElementManager();
		factory = manager.getFactory();
		
		wood_element1 = factory.createConcreteEntity();
		wood_element1.setSpecie( "Naple" );
		
		wood_element2 = factory.createConcreteEntity();
		wood_element2.setSpecie( "Pine" );
	}
	
	@Test
	public void testmergeWoodElements() {
		WoodElement merged = manager.merge( UserFactory.createUser(), 
												new Time( new Date() ), 
												wood_element1, wood_element2 );
		
		assertNotNull( merged );
		assertTrue( merged.listBefore().contains( wood_element1 ) );
		assertTrue( merged.listBefore().contains( wood_element2 ) );
		assertEquals( WoodElementMergeAction.class, merged.getOrigin().getClass() );
		assertEquals( wood_element1, ((WoodElementMergeAction)merged.getOrigin()).getSource1() );
		assertEquals( wood_element2, ((WoodElementMergeAction)merged.getOrigin()).getSource2() );
	}
	
	@Test
	public void testmergeWoodElements_Null() {
		WoodElement merged = manager.merge( UserFactory.createUser(), 
												new Time( new Date() ), 
												null, wood_element2 );
		
		assertNotNull( merged );
		assertTrue( merged.listBefore().contains( wood_element1 ) );
		assertTrue( merged.listBefore().contains( wood_element2 ) );
		assertEquals( WoodElementMergeAction.class, merged.getOrigin().getClass() );
		assertEquals( wood_element1, ((WoodElementMergeAction)merged.getOrigin()).getSource1() );
		assertEquals( wood_element2, ((WoodElementMergeAction)merged.getOrigin()).getSource2() );
	}
	
	
}
