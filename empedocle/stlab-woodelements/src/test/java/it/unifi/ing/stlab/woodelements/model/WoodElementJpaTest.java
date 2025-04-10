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
		wood_element.setName( "name" );
		wood_element.setSurname( "surname" );
		wood_element.setSex( Sex.M );
		wood_element.setBirthDate( new Date( DateHelper.createDate( "2013-03-01").getTime() ));
		wood_element.setBirthPlace( "birthPlace" );
		wood_element.setTaxCode( "taxCode" );
		wood_element.setSsnCode( "ssnCode" );

		Address residence = new Address();
		residence.setPlace( "residence" );
		wood_element.setResidence( residence );

		Address domicile = new Address();
		domicile.setPlace( "domicile" );
		wood_element.setDomicile( domicile );
		
		wood_element.setRegion( "region" );
		wood_element.setHomePhone( "homePhone" );
		wood_element.setWorkPhone( "workPhone" );
		wood_element.setNationality( "nationality" );
		wood_element.setAsl( "asl" );
		
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
		assertEquals( "name", wood_element.getName());
		assertEquals( "surname", wood_element.getSurname());
		assertEquals( Sex.M, wood_element.getSex());
		assertEquals( new Date( DateHelper.createDate( "2013-03-01").getTime() ), wood_element.getBirthDate());
		assertEquals( "birthPlace", wood_element.getBirthPlace());
		assertEquals( "taxCode", wood_element.getTaxCode());
		assertEquals( "ssnCode", wood_element.getSsnCode());
		assertEquals( "residence", wood_element.getResidence().getPlace());
		assertEquals( "domicile", wood_element.getDomicile().getPlace());
		assertEquals( "region", wood_element.getRegion());
		assertEquals( "homePhone", wood_element.getHomePhone());
		assertEquals( "workPhone", wood_element.getWorkPhone());
		assertEquals( "nationality", wood_element.getNationality());
		assertEquals( "asl", wood_element.getAsl());
	}
	
}
