package it.unifi.ing.stlab.woodelements.model;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;

import org.junit.Test;

public class WoodElementTest {

	@Test
	public void testSameAs1() {
		WoodElement woodElement1 = WoodElementFactory.createWoodElement();
		WoodElementIdentifier identifier = WoodElementFactory.createWoodElementIdentifier();
		identifier.setCode( "id" );
		woodElement1.setIdentifier( identifier );

		WoodElement woodElement2 = WoodElementFactory.createWoodElement();

		assertEquals( false, woodElement1.sameAs( woodElement2 ) );
		assertEquals( false, woodElement2.sameAs( woodElement1 ) );
	}
	
	@Test
	public void testSameAs2() {
		WoodElement woodElement1 = WoodElementFactory.createWoodElement();
		WoodElement woodElement2 = WoodElementFactory.createWoodElement();

		assertEquals( true, woodElement1.sameAs( woodElement2 ) );
		assertEquals( true, woodElement2.sameAs( woodElement1 ) );
	}
	
	@Test
	public void testSameAs3() {
		WoodElement woodElement1 = WoodElementFactory.createWoodElement();
		WoodElementIdentifier identifier = WoodElementFactory.createWoodElementIdentifier();
		identifier.setCode( "id" );
		woodElement1.setIdentifier( identifier );

		WoodElement woodElement2 = WoodElementFactory.createWoodElement();
		woodElement2.setIdentifier( identifier );

		assertEquals( true, woodElement1.sameAs( woodElement2 ) );
		assertEquals( true, woodElement2.sameAs( woodElement1 ) );
	}
	
	@Test
	public void testSameAs4() {
		WoodElement woodElement1 = WoodElementFactory.createWoodElement();
		WoodElementIdentifier identifier1 = WoodElementFactory.createWoodElementIdentifier();
		identifier1.setCode( "id1" );
		woodElement1.setIdentifier( identifier1 );

		WoodElement woodElement2 = WoodElementFactory.createWoodElement();
		WoodElementIdentifier identifier2 = WoodElementFactory.createWoodElementIdentifier();
		identifier2.setCode( "id2" );
		woodElement2.setIdentifier( identifier2 );

		assertEquals( false, woodElement1.sameAs( woodElement2 ) );
		assertEquals( false, woodElement2.sameAs( woodElement1 ) );
	}	

}
