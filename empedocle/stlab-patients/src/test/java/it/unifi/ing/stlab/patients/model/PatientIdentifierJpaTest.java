package it.unifi.ing.stlab.wood-elements.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.wood-elements.factory.WoodElementFactory;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class WoodElementIdentifierJpaTest extends PersistenceTest {

	protected String uuid;
	
	@Override
	protected void insertData() throws Exception {
		WoodElementIdentifier identifier = WoodElementFactory.createWoodElementIdentifier();
		identifier.setCode( "IDENTIFIER001" );
		
		entityManager.persist( identifier );

		uuid = identifier.getUuid();
	}
	
	@Test
	public void testWoodElement() {
		WoodElementIdentifier identifier = (WoodElementIdentifier)
			entityManager
				.createQuery( 
					"select p " +
					" from WoodElementIdentifier p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( identifier );
		assertEquals( "IDENTIFIER001", identifier.getCode());

	}
	
}
