package it.unifi.ing.stlab.empedocle.dao.health;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.empedocle.factory.health.MeasurementSessionFactory;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionStatus;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.observableentities.manager.ObservableEntityManager;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.observableentities.dao.ObservableEntityDao;
import it.unifi.ing.stlab.woodelements.manager.WoodElementManager;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDaoBean;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDaoBean;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Calendar;

import org.junit.Test;

public class MeasurementSessionDaoResumeFactTest extends PersistenceTest {
	
	protected MeasurementSessionDao measurementSessionDao;
	protected FactDao factDao;
	
	protected CompositeFact compositeFact;
	protected TextualFact textualFact;
	
	protected CompositeFact newCompositeFact;
	protected TextualFact newTextualFact;
	protected WoodElement p;
	protected User author;
	protected MeasurementSession measurementSession;
	protected WoodElementDaoBean observableEntityDao;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		
		measurementSessionDao = new MeasurementSessionDaoBean();
		FieldUtils.assignField( measurementSessionDao, "entityManager", entityManager );
		
		factDao = new FactDaoBean();
		FieldUtils.assignField( factDao, "entityManager", entityManager );

		observableEntityDao = new WoodElementDaoBean();
		
		setUpFacts();
		
	}
	
	protected void setUpFacts() {
		Time time = new Time(Calendar.getInstance().getTime());
		
		author = UserFactory.createUser();
		author.setUserid("usr");
		entityManager.persist(author);
		
		p = observableEntityDao.getManager().getFactory().create();
		entityManager.persist( p );
		
		measurementSession = MeasurementSessionFactory.createMeasurementSession();
		measurementSession.setSurveySchedule(SurveyScheduleFactory.createSurveySchedule());
		measurementSession.getSurveySchedule().setObservableEntity(p);
		measurementSession.setStatus(MeasurementSessionStatus.DONE);
		entityManager.persist( measurementSession );
		
		TextualType textualType = TypeFactory.createTextualType();
		textualType.setName( "Textual type" );
		textualType.setRecurrent(false);
		entityManager.persist( textualType );
		
		FactManager fManager = new FactManager();
		
		textualFact = fManager.createTextual(author, time);
		textualFact.assignType( textualType );
		textualFact.setContext( measurementSession );
		textualFact.setStatus(FactStatus.DRAFT);
		textualFact.setText("testo");
		factDao.save( textualFact );
		
		// nuovo fact txt appena creato
		newTextualFact = fManager.createTextual(author, time);
		newTextualFact.assignType( textualType );
		newTextualFact.setContext( measurementSession );
		
		CompositeType compositeType = TypeFactory.createCompositeType();
		compositeType.setName( "Composite type" );
		compositeType.setRecurrent(true);
		entityManager.persist( compositeType );
		
		TypeLink typeLink = TypeLinkFactory.createLink();
		typeLink.assignSource( compositeType );
		typeLink.assignTarget( textualType );
		entityManager.persist( typeLink );
		
		compositeFact = fManager.createComposite(author, time);
		compositeFact.assignType( compositeType );
		compositeFact.setContext( measurementSession );
		compositeFact.setStatus(FactStatus.DRAFT);
		factDao.save(compositeFact);
		
		FactLinkFactory factLinkFactory = new FactLinkFactory();
		FactLink factLink = factLinkFactory.insertLink((FactImpl)compositeFact, (FactImpl)textualFact);
		entityManager.persist( factLink );
		
//		// nuovo fact cmp appena creato
		newCompositeFact = fManager.createComposite(author, time);
		newCompositeFact.assignType( compositeType );
		newCompositeFact.setContext( measurementSession );
	}
	
	@Test
	public void testResumeNothing() {
		measurementSession.setStatus(MeasurementSessionStatus.RUNNING);
		measurementSession = entityManager.merge(measurementSession);
		
		Fact resumed = measurementSessionDao.resume(newTextualFact, p);
		
		assertNull(resumed);
		
	}
	
	@Test
	public void testResumeUpdatedObservableEntity() {
		WoodElementManager observableEntityManager = observableEntityDao.getManager();
		Time time = new Time(Calendar.getInstance().getTime());
		WoodElement pNew = (WoodElement) observableEntityManager.modify(author, time, p);
		entityManager.persist(pNew);

		Fact resumed = measurementSessionDao.resume(newTextualFact, pNew);

		assertNotNull(resumed);
		assertEquals("Textual type", resumed.getType().getName());
		assertEquals(FactStatus.DRAFT, resumed.getStatus());
		assertEquals(textualFact.getContext(), resumed.getContext());
		assertEquals(textualFact.getText(), ((TextualFact)resumed).getText());
		assertTrue(((FactImpl)textualFact).sameAs((FactImpl)resumed));
	}
	
	@Test
	public void testResumeComposite() {
		Fact resumed = measurementSessionDao.resume(newCompositeFact, p);
		
		assertNotNull(resumed);
		assertEquals("Composite type", resumed.getType().getName());
		assertEquals(FactStatus.DRAFT, resumed.getStatus());
		assertEquals(compositeFact.getContext(), resumed.getContext());
		assertTrue(((FactImpl)compositeFact).sameAs((FactImpl)resumed));
		assertEquals(1, resumed.listChildren().size());
		assertTrue(((FactImpl)textualFact).sameAs((FactImpl)resumed.listChildren().iterator().next().getTarget()));
	}
	
	@Test
	public void testResumeSimple() {
		Fact resumed = measurementSessionDao.resume(newTextualFact, p);
		
		assertNotNull(resumed);
		assertEquals("Textual type", resumed.getType().getName());
		assertEquals(FactStatus.DRAFT, resumed.getStatus());
		assertEquals(textualFact.getContext(), resumed.getContext());
		assertEquals(textualFact.getText(), ((TextualFact)resumed).getText());
		assertTrue(((FactImpl)textualFact).sameAs((FactImpl)resumed));
		
	}

	
}
