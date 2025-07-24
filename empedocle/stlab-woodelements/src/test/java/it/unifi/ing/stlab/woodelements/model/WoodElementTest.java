package it.unifi.ing.stlab.woodelements.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;

import org.junit.Test;
import org.junit.Before;

public class WoodElementTest {

	protected WoodElementFactory factory;

	@Before
	public void setUp(){
		factory = new WoodElementFactory();
	}

	@Test
	public void testSameAs1() {
		WoodElement wood_element1 = factory.create();
		WoodElementIdentifier identifier = factory.createIdentifier();
		identifier.setCode( "id" );
		wood_element1.setIdentifier( identifier );

		WoodElement wood_element2 = factory.create();


		assertFalse(wood_element1.sameAs(wood_element2));
		assertFalse(wood_element2.sameAs(wood_element1));
	}
	
	@Test
	public void testSameAs2() {
		WoodElement wood_element1 = factory.create();
		WoodElement wood_element2 = factory.create();

		assertEquals( true, wood_element1.sameAs( wood_element2 ) );
		assertEquals( true, wood_element2.sameAs( wood_element1 ) );
	}
	
	@Test
	public void testSameAs3() {
		WoodElement wood_element1 = factory.create();
		WoodElementIdentifier identifier = factory.createIdentifier();
		identifier.setCode( "id" );
		wood_element1.setIdentifier( identifier );

		WoodElement wood_element2 = factory.create();
		wood_element2.setIdentifier( identifier );

		assertEquals( true, wood_element1.sameAs( wood_element2 ) );
		assertEquals( true, wood_element2.sameAs( wood_element1 ) );
	}
	
	@Test
	public void testSameAs4() {
		WoodElement wood_element1 = factory.create();
		WoodElementIdentifier identifier1 = factory.createIdentifier();
		identifier1.setCode( "id1" );
		wood_element1.setIdentifier( identifier1 );

		WoodElement wood_element2 = factory.create();
		WoodElementIdentifier identifier2 = factory.createIdentifier();
		identifier2.setCode( "id2" );
		wood_element2.setIdentifier( identifier2 );

		assertEquals( false, wood_element1.sameAs( wood_element2 ) );
		assertEquals( false, wood_element2.sameAs( wood_element1 ) );
	}	

}
