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

		WoodElement woodElement = WoodElementFactory.createWoodElement();
		
		identifier = WoodElementFactory.createWoodElementIdentifier();
		identifier.setCode( "id" );
		woodElement.setIdentifier( identifier );
		woodElement.setName( "name" );
		woodElement.setSurname( "surname" );
		woodElement.setSex( Sex.M );
		woodElement.setBirthDate( new Date( DateHelper.createDate( "2013-03-01").getTime() ));
		woodElement.setBirthPlace( "birthPlace" );
		woodElement.setTaxCode( "taxCode" );
		woodElement.setSsnCode( "ssnCode" );

		Address residence = new Address();
		residence.setPlace( "residence" );
		woodElement.setResidence( residence );

		Address domicile = new Address();
		domicile.setPlace( "domicile" );
		woodElement.setDomicile( domicile );
		
		woodElement.setRegion( "region" );
		woodElement.setHomePhone( "homePhone" );
		woodElement.setWorkPhone( "workPhone" );
		woodElement.setNationality( "nationality" );
		woodElement.setAsl( "asl" );
		
		entityManager.persist( woodElement );

		uuid = woodElement.getUuid();
	}
	
	@Test
	public void testWoodElement() {
		WoodElement woodElement = (WoodElement)
			entityManager
				.createQuery( 
					"select p " +
					" from WoodElement p " +
					" where p.uuid = :uuid" ).setParameter( "uuid", uuid ).getSingleResult();
	
		assertNotNull( woodElement );
		
		assertEquals( identifier, woodElement.getIdentifier());
		assertEquals( "name", woodElement.getName());
		assertEquals( "surname", woodElement.getSurname());
		assertEquals( Sex.M, woodElement.getSex());
		assertEquals( new Date( DateHelper.createDate( "2013-03-01").getTime() ), woodElement.getBirthDate());
		assertEquals( "birthPlace", woodElement.getBirthPlace());
		assertEquals( "taxCode", woodElement.getTaxCode());
		assertEquals( "ssnCode", woodElement.getSsnCode());
		assertEquals( "residence", woodElement.getResidence().getPlace());
		assertEquals( "domicile", woodElement.getDomicile().getPlace());
		assertEquals( "region", woodElement.getRegion());
		assertEquals( "homePhone", woodElement.getHomePhone());
		assertEquals( "workPhone", woodElement.getWorkPhone());
		assertEquals( "nationality", woodElement.getNationality());
		assertEquals( "asl", woodElement.getAsl());
	}
	
}
