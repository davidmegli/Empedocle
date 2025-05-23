package it.unifi.ing.stlab.empedocle.actions.health.measurementSession;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.visitor.fact.tools.EmptyFactVisitor;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.reflection.impl.visitor.fact.FactCopyVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignContextVisitor;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignStatusVisitor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RecurrentFactHelperTest extends BasicFactTest {

	private RecurrentFactHelper helper;
	
	@Mock private MeasurementSessionDao measurementSessionDao;
	@Mock private ObservableEntity fakeObservableEntity;
	
	@Before
	public void setUp() {
		super.setUp();
		helper = new RecurrentFactHelper(measurementSessionDao);
		when(fakeObservableEntity.getId()).thenReturn(1L);
		
	}
	
	// recupero di una figlia testuale
	@Test
	public void testResumeRecurrentFactSimple() {
		TextualFact fake_return = mng.createTextual(author, time);
		fake_return.setStatus(FactStatus.ACTIVE);
		fake_return.assignType(ttxt2);
		fake_return.setText("recuperata!");
		fake_return.setContext(context);
		
		when(measurementSessionDao.resume(any(Fact.class), any(ObservableEntity.class))).thenReturn(fake_return);
		
		helper.resumeRecurrentFacts(root);
		
		assertEquals(1, root.listChildren().size());
		Fact first = root.listChildren().iterator().next().getTarget();
		
		assertEquals(2, first.listChildren().size());
		
		assertEquals("recuperata!", ((TextualFact)first.listChildrenOrdered().get(0).getTarget()).getText());
		assertEquals(FactStatus.DRAFT, first.listChildrenOrdered().get(0).getTarget().getStatus());
		assertEquals(context, first.listChildrenOrdered().get(0).getTarget().getContext());
		assertTrue(child_txt2 == first.listChildrenOrdered().get(0).getTarget());
		
		assertEquals(context, first.listChildrenOrdered().get(1).getTarget().getContext());
		assertTrue(child_txt1 == first.listChildrenOrdered().get(1).getTarget());

	}
	
	@Test
	public void testResumeRecurrentFactsComposite() {
		cmp.getType().setRecurrent(true);
		child_txt2.getType().setRecurrent(false);
		
		Fact destination = createEmptyCopy(root);
		
		MeasurementSession e = (MeasurementSession)root.getContext();
		SurveySchedule a = SurveyScheduleFactory.createSurveySchedule();
		a.setObservableEntity(fakeObservableEntity);
		e.setSurveySchedule(a);
		
		AssignContextVisitor acv = new AssignContextVisitor(e);
		destination.accept(acv);
		AssignStatusVisitor asv = new AssignStatusVisitor(FactStatus.DRAFT);
		destination.accept(asv);
		
		when(measurementSessionDao.resume(any(Fact.class), any(ObservableEntity.class))).thenReturn(cmp);
		
		helper.resumeRecurrentFacts(destination);
		
		// copia di cmp
		Fact first = destination.listChildren().iterator().next().getTarget();
		
		assertEquals(2, first.listChildren().size());
		
		// copia di child_txt2
		assertEquals("testo2", ((TextualFact)first.listChildrenOrdered().get(0).getTarget()).getText());
		assertEquals(FactStatus.DRAFT, first.listChildrenOrdered().get(0).getTarget().getStatus());
		assertEquals(context, first.listChildrenOrdered().get(0).getTarget().getContext());
		assertTrue(child_txt2 != first.listChildrenOrdered().get(0).getTarget());
		
		assertEquals("testo1", ((TextualFact)first.listChildrenOrdered().get(1).getTarget()).getText());
		assertEquals(FactStatus.DRAFT, first.listChildrenOrdered().get(1).getTarget().getStatus());
		assertEquals(context, first.listChildrenOrdered().get(1).getTarget().getContext());
		assertTrue(child_txt1 != first.listChildrenOrdered().get(1).getTarget());
		
	}
	
	private Fact createEmptyCopy(Fact src) {
		FactCopyVisitor cv = new FactCopyVisitor();
		src.accept(cv);
		Fact result = cv.getResult();
		
		EmptyFactVisitor ev = new EmptyFactVisitor();
		result.accept(ev);
		
		for(FactLink fl : result.listChildren()) {
			assertTrue(fl.getTarget().isEmpty());
		}
		
		return result;
		
	}
	
}
