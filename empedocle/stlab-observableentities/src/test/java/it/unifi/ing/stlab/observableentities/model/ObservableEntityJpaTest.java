package it.unifi.ing.stlab.observableentities.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import java.sql.Date;

import org.junit.Test;

public class ObservableEntityJpaTest extends PersistenceTest {

	protected String uuid;
	protected ObservableEntityIdentifier identifier;
	protected TimeRange period;
	
	@Override
	protected void insertData() throws Exception {
		period = new TimeRange( 
			new Time( DateHelper.createDate( "2013-03-01") ), 
			new Time( DateHelper.createDate( "2013-03-31")));

		ObservableEntity observable_entity = ObservableEntityFactory.createObservableEntity();
		
		identifier = ObservableEntityFactory.createObservableEntityIdentifier();
		identifier.setCode( "id" );
		observable_entity.setIdentifier( identifier );
		
		entityManager.persist( observable_entity );

		uuid = observable_entity.getUuid();
	}
	
	@Test
	public void testObservableEntity() {
		ObservableEntity observable_entity = (ObservableEntity)
			entityManager
				.createQuery( 
					"select p " +
					" from ObservableEntity p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( observable_entity );
		
		assertEquals( identifier, observable_entity.getIdentifier());
	}
	
}
