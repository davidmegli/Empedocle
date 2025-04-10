package it.unifi.ing.stlab.wood-elements.manager;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.wood-elements.factory.WoodElementFactory;
import it.unifi.ing.stlab.wood-elements.model.WoodElement;
import it.unifi.ing.stlab.wood-elements.model.actions.WoodElementMergeAction;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class WoodElementManagerTest {

	protected WoodElementManager manager;
	protected WoodElement wood_element1;
	protected WoodElement wood_element2;
	
	@Before
	public void setUp() {
		manager = new WoodElementManager();
		
		wood_element1 = WoodElementFactory.createWoodElement();
		wood_element1.setName( "Prova1" );
		
		wood_element2 = WoodElementFactory.createWoodElement();
		wood_element2.setName( "Prova2" );
	}
	
	@Test
	public void testMergeWoodElements() {
		WoodElement merged = manager.merge( UserFactory.createUser(), 
												new Time( new Date() ), 
												wood_element1, wood_element2 ); 
		
		assertNotNull( merged );
		assertNotNull( merged.getName() );
		assertEquals( wood_element1.getName(), merged.getName() );
		assertTrue( merged.listBefore().contains( wood_element1 ) );
		assertTrue( merged.listBefore().contains( wood_element2 ) );
		assertEquals( WoodElementMergeAction.class, merged.getOrigin().getClass() );
		assertEquals( wood_element1, ((WoodElementMergeAction)merged.getOrigin()).getSource1() );
		assertEquals( wood_element2, ((WoodElementMergeAction)merged.getOrigin()).getSource2() );
	}
	
	@Test
	public void testMergeWoodElements_Null() {
		WoodElement merged = manager.merge( UserFactory.createUser(), 
												new Time( new Date() ), 
												null, wood_element2 ); 
		
		assertNotNull( merged );
		assertNotNull( merged.getName() );
		assertEquals( wood_element1.getName(), merged.getName() );
		assertTrue( merged.listBefore().contains( wood_element1 ) );
		assertTrue( merged.listBefore().contains( wood_element2 ) );
		assertEquals( WoodElementMergeAction.class, merged.getOrigin().getClass() );
		assertEquals( wood_element1, ((WoodElementMergeAction)merged.getOrigin()).getSource1() );
		assertEquals( wood_element2, ((WoodElementMergeAction)merged.getOrigin()).getSource2() );
	}
	
	
}
