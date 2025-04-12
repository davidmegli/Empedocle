package it.unifi.ing.stlab.woodelements.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementMergeAction;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class WoodElementManagerTest {

	protected WoodElementManager manager;
	protected WoodElement woodElement1;
	protected WoodElement woodElement2;
	
	@Before
	public void setUp() {
		manager = new WoodElementManager();
		
		woodElement1 = WoodElementFactory.createWoodElement();
		woodElement1.setName( "Prova1" );
		
		woodElement2 = WoodElementFactory.createWoodElement();
		woodElement2.setName( "Prova2" );
	}
	
	@Test
	public void testMergeWoodElements() {
		WoodElement merged = manager.merge( UserFactory.createUser(), 
												new Time( new Date() ),
				woodElement1, woodElement2);
		
		assertNotNull( merged );
		assertNotNull( merged.getName() );
		assertEquals( woodElement1.getName(), merged.getName() );
		assertTrue( merged.listBefore().contains(woodElement1) );
		assertTrue( merged.listBefore().contains(woodElement2) );
		assertEquals( WoodElementMergeAction.class, merged.getOrigin().getClass() );
		assertEquals(woodElement1, ((WoodElementMergeAction)merged.getOrigin()).getSource1() );
		assertEquals(woodElement2, ((WoodElementMergeAction)merged.getOrigin()).getSource2() );
	}
	
	@Test
	public void testMergeWoodElements_Null() {
		WoodElement merged = manager.merge( UserFactory.createUser(), 
												new Time( new Date() ), 
												null, woodElement2);
		
		assertNotNull( merged );
		assertNotNull( merged.getName() );
		assertEquals( woodElement1.getName(), merged.getName() );
		assertTrue( merged.listBefore().contains(woodElement1) );
		assertTrue( merged.listBefore().contains(woodElement2) );
		assertEquals( WoodElementMergeAction.class, merged.getOrigin().getClass() );
		assertEquals(woodElement1, ((WoodElementMergeAction)merged.getOrigin()).getSource1() );
		assertEquals(woodElement2, ((WoodElementMergeAction)merged.getOrigin()).getSource2() );
	}
	
	
}
