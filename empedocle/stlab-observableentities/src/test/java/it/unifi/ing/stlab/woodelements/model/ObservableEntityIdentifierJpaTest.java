package it.unifi.ing.stlab.observableentities.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class ObservableEntityIdentifierJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		ObservableEntityIdentifier identifier = ObservableEntityFactory.createObservableEntityIdentifier();
		identifier.setCode( "IDENTIFIER001" );
		
		entityManager.persist( identifier );

		uuid = identifier.getUuid();
	}
	
	@Test
	public void testObservableEntity() {
		ObservableEntityIdentifier identifier = (ObservableEntityIdentifier)
			entityManager
				.createQuery( 
					"select p " +
					" from ObservableEntityIdentifier p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( identifier );
		assertEquals( "IDENTIFIER001", identifier.getCode());

	}
	
}
