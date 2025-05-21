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
		observable_entity.setName( "name" );
		observable_entity.setSurname( "surname" );
		observable_entity.setSex( Sex.M );
		observable_entity.setBirthDate( new Date( DateHelper.createDate( "2013-03-01").getTime() ));
		observable_entity.setBirthPlace( "birthPlace" );
		observable_entity.setTaxCode( "taxCode" );
		observable_entity.setSsnCode( "ssnCode" );

		Address residence = new Address();
		residence.setPlace( "residence" );
		observable_entity.setResidence( residence );

		Address domicile = new Address();
		domicile.setPlace( "domicile" );
		observable_entity.setDomicile( domicile );
		
		observable_entity.setRegion( "region" );
		observable_entity.setHomePhone( "homePhone" );
		observable_entity.setWorkPhone( "workPhone" );
		observable_entity.setNationality( "nationality" );
		observable_entity.setAsl( "asl" );
		
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
		assertEquals( "name", observable_entity.getName());
		assertEquals( "surname", observable_entity.getSurname());
		assertEquals( Sex.M, observable_entity.getSex());
		assertEquals( new Date( DateHelper.createDate( "2013-03-01").getTime() ), observable_entity.getBirthDate());
		assertEquals( "birthPlace", observable_entity.getBirthPlace());
		assertEquals( "taxCode", observable_entity.getTaxCode());
		assertEquals( "ssnCode", observable_entity.getSsnCode());
		assertEquals( "residence", observable_entity.getResidence().getPlace());
		assertEquals( "domicile", observable_entity.getDomicile().getPlace());
		assertEquals( "region", observable_entity.getRegion());
		assertEquals( "homePhone", observable_entity.getHomePhone());
		assertEquals( "workPhone", observable_entity.getWorkPhone());
		assertEquals( "nationality", observable_entity.getNationality());
		assertEquals( "asl", observable_entity.getAsl());
	}
	
}
