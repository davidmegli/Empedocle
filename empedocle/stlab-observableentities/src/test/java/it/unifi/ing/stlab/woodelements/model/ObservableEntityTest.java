package it.unifi.ing.stlab.observableentities.model;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;

import org.junit.Test;

public class ObservableEntityTest {

	@Test
	public void testSameAs1() {
		ObservableEntity observable_entity1 = ObservableEntityFactory.createObservableEntity();
		ObservableEntityIdentifier identifier = ObservableEntityFactory.createObservableEntityIdentifier();
		identifier.setCode( "id" );
		observable_entity1.setIdentifier( identifier );

		ObservableEntity observable_entity2 = ObservableEntityFactory.createObservableEntity();

		assertEquals( false, observable_entity1.sameAs( observable_entity2 ) );
		assertEquals( false, observable_entity2.sameAs( observable_entity1 ) );
	}
	
	@Test
	public void testSameAs2() {
		ObservableEntity observable_entity1 = ObservableEntityFactory.createObservableEntity();
		ObservableEntity observable_entity2 = ObservableEntityFactory.createObservableEntity();

		assertEquals( true, observable_entity1.sameAs( observable_entity2 ) );
		assertEquals( true, observable_entity2.sameAs( observable_entity1 ) );
	}
	
	@Test
	public void testSameAs3() {
		ObservableEntity observable_entity1 = ObservableEntityFactory.createObservableEntity();
		ObservableEntityIdentifier identifier = ObservableEntityFactory.createObservableEntityIdentifier();
		identifier.setCode( "id" );
		observable_entity1.setIdentifier( identifier );

		ObservableEntity observable_entity2 = ObservableEntityFactory.createObservableEntity();
		observable_entity2.setIdentifier( identifier );

		assertEquals( true, observable_entity1.sameAs( observable_entity2 ) );
		assertEquals( true, observable_entity2.sameAs( observable_entity1 ) );
	}
	
	@Test
	public void testSameAs4() {
		ObservableEntity observable_entity1 = ObservableEntityFactory.createObservableEntity();
		ObservableEntityIdentifier identifier1 = ObservableEntityFactory.createObservableEntityIdentifier();
		identifier1.setCode( "id1" );
		observable_entity1.setIdentifier( identifier1 );

		ObservableEntity observable_entity2 = ObservableEntityFactory.createObservableEntity();
		ObservableEntityIdentifier identifier2 = ObservableEntityFactory.createObservableEntityIdentifier();
		identifier2.setCode( "id2" );
		observable_entity2.setIdentifier( identifier2 );

		assertEquals( false, observable_entity1.sameAs( observable_entity2 ) );
		assertEquals( false, observable_entity2.sameAs( observable_entity1 ) );
	}	

}
