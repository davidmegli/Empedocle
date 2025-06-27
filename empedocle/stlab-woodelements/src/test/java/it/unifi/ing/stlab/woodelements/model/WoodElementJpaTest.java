package it.unifi.ing.stlab.woodelements.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import java.sql.Date;

import org.junit.Test;

public class WoodElementJpaTest extends PersistenceTest {

	protected String uuid;
	protected WoodElementIdentifier identifier;
	protected TimeRange period;
	
	@Override
	protected void insertData() throws Exception {
		period = new TimeRange( 
			new Time( DateHelper.createDate( "2013-03-01") ), 
			new Time( DateHelper.createDate( "2013-03-31")));

		WoodElement wood_element = WoodElementFactory.createWoodElement();
		
		identifier = WoodElementFactory.createWoodElementIdentifier();
		identifier.setCode( "id" );
		wood_element.setIdentifier( identifier );
		
		entityManager.persist( wood_element );

		uuid = wood_element.getUuid();
	}
	
	@Test
	public void testWoodElement() {
		WoodElement wood_element = (WoodElement)
			entityManager
				.createQuery( 
					"select p " +
					" from WoodElement p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( wood_element );
		
		assertEquals( identifier, wood_element.getIdentifier());
	}
	
}
