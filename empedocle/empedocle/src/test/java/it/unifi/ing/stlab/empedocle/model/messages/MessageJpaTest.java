package it.unifi.ing.stlab.empedocle.model.messages;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import it.unifi.ing.stlab.empedocle.factory.MessageFactory;
import it.unifi.ing.stlab.empedocle.model.messages.Message;
import it.unifi.ing.stlab.empedocle.model.messages.MessageLevel;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDaoBean;
import it.unifi.ing.stlab.test.PersistenceTest;

import org.junit.Test;

public class MessageJpaTest extends PersistenceTest {

	protected String uuid;
	private ObservableEntity observableEntity;
	protected ObservableEntityDao observableEntityDao;

	@Override
	protected void insertData() throws Exception {
		observableEntityDao = new WoodElementDaoBean();
		observableEntity = observableEntityDao.getManager().getFactory().create();

		Message message = MessageFactory.createMessage();
		message.setDate( new Date( 1000 ) );
		message.setSender( "me" );
		message.setLevel( MessageLevel.INFO );
		message.setSubject( "subject message" );
		message.setBody( "body message" );
		message.setObservableEntity(observableEntity);

		entityManager.persist(observableEntity);
		entityManager.persist( message );

		uuid = message.getUuid();
	}

	@Test
	public void testMessage() {
		Message message = (Message) entityManager
				.createQuery( "select p " + " from Message p " + " where p.uuid = :uuid" )
				.setParameter( "uuid", uuid ).getSingleResult();

		assertNotNull( message );

		assertEquals( "me", message.getSender() );
		assertEquals( "subject message", message.getSubject() );
		assertEquals( "body message", message.getBody() );
		assertEquals( MessageLevel.INFO, message.getLevel() );
		assertEquals( new Date( 1000 ), message.getDate() );
		assertEquals(observableEntity, message.getObservableEntity() );
	}

}
