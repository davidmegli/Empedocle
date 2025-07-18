package it.unifi.ing.stlab.woodelements.model;

import static org.junit.Assert.assertEquals;
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
		WoodElement wood_element1 = factory.createConcreteEntity();
		WoodElementIdentifier identifier = factory.createConcreteIdentifier();
		identifier.setCode( "id" );
		wood_element1.setIdentifier( identifier );

		WoodElement wood_element2 = factory.createConcreteEntity();

		assertEquals( false, wood_element1.sameAs( wood_element2 ) );
		assertEquals( false, wood_element2.sameAs( wood_element1 ) );
	}
	
	@Test
	public void testSameAs2() {
		WoodElement wood_element1 = factory.createConcreteEntity();
		WoodElement wood_element2 = factory.createConcreteEntity();

		assertEquals( true, wood_element1.sameAs( wood_element2 ) );
		assertEquals( true, wood_element2.sameAs( wood_element1 ) );
	}
	
	@Test
	public void testSameAs3() {
		WoodElement wood_element1 = factory.createConcreteEntity();
		WoodElementIdentifier identifier = factory.createConcreteIdentifier();
		identifier.setCode( "id" );
		wood_element1.setIdentifier( identifier );

		WoodElement wood_element2 = factory.createConcreteEntity();
		wood_element2.setIdentifier( identifier );

		assertEquals( true, wood_element1.sameAs( wood_element2 ) );
		assertEquals( true, wood_element2.sameAs( wood_element1 ) );
	}
	
	@Test
	public void testSameAs4() {
		WoodElement wood_element1 = factory.createConcreteEntity();
		WoodElementIdentifier identifier1 = factory.createConcreteIdentifier();
		identifier1.setCode( "id1" );
		wood_element1.setIdentifier( identifier1 );

		WoodElement wood_element2 = factory.createConcreteEntity();
		WoodElementIdentifier identifier2 = factory.createConcreteIdentifier();
		identifier2.setCode( "id2" );
		wood_element2.setIdentifier( identifier2 );

		assertEquals( false, wood_element1.sameAs( wood_element2 ) );
		assertEquals( false, wood_element2.sameAs( wood_element1 ) );
	}	

}
