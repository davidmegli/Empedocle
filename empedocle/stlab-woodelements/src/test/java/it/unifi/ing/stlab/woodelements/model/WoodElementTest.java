package it.unifi.ing.stlab.woodelements.model;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;

import org.junit.Test;

public class WoodElementTest {

	@Test
	public void testSameAs1() {
		WoodElement wood_element1 = WoodElementFactory.createWoodElement();
		WoodElementIdentifier identifier = WoodElementFactory.createWoodElementIdentifier();
		identifier.setCode( "id" );
		wood_element1.setIdentifier( identifier );

		WoodElement wood_element2 = WoodElementFactory.createWoodElement();

		assertEquals( false, wood_element1.sameAs( wood_element2 ) );
		assertEquals( false, wood_element2.sameAs( wood_element1 ) );
	}
	
	@Test
	public void testSameAs2() {
		WoodElement wood_element1 = WoodElementFactory.createWoodElement();		
		WoodElement wood_element2 = WoodElementFactory.createWoodElement();

		assertEquals( true, wood_element1.sameAs( wood_element2 ) );
		assertEquals( true, wood_element2.sameAs( wood_element1 ) );
	}
	
	@Test
	public void testSameAs3() {
		WoodElement wood_element1 = WoodElementFactory.createWoodElement();
		WoodElementIdentifier identifier = WoodElementFactory.createWoodElementIdentifier();
		identifier.setCode( "id" );
		wood_element1.setIdentifier( identifier );

		WoodElement wood_element2 = WoodElementFactory.createWoodElement();
		wood_element2.setIdentifier( identifier );

		assertEquals( true, wood_element1.sameAs( wood_element2 ) );
		assertEquals( true, wood_element2.sameAs( wood_element1 ) );
	}
	
	@Test
	public void testSameAs4() {
		WoodElement wood_element1 = WoodElementFactory.createWoodElement();
		WoodElementIdentifier identifier1 = WoodElementFactory.createWoodElementIdentifier();
		identifier1.setCode( "id1" );
		wood_element1.setIdentifier( identifier1 );

		WoodElement wood_element2 = WoodElementFactory.createWoodElement();
		WoodElementIdentifier identifier2 = WoodElementFactory.createWoodElementIdentifier();
		identifier2.setCode( "id2" );
		wood_element2.setIdentifier( identifier2 );

		assertEquals( false, wood_element1.sameAs( wood_element2 ) );
		assertEquals( false, wood_element2.sameAs( wood_element1 ) );
	}	

}
