package it.unifi.ing.stlab.empedocle.actions.health.measurementSession;

import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.empedocle.factory.health.MeasurementSessionFactory;
import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.factory.types.TypeLinkFactory;
import it.unifi.ing.stlab.reflection.impl.factory.FactLinkFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.model.facts.links.FactLinkImpl;
import it.unifi.ing.stlab.reflection.model.facts.CompositeFact;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactContext;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.reflection.model.facts.TextualFact;
import it.unifi.ing.stlab.reflection.model.types.CompositeType;
import it.unifi.ing.stlab.reflection.model.types.TextualType;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Calendar;

public class BasicFactTest {

	protected FactContext context;
	protected SurveySchedule surveySchedule;
	protected ObservableEntity observableEntity;
	protected Fact root;
	
	// Composite with two children txt
	protected CompositeFact cmp;
	protected TextualFact child_txt1;
	protected TextualFact child_txt2;
	protected TextualType ttxt1;
	protected TextualType ttxt2;
	protected CompositeType tCmp;
	protected CompositeType tCmp2;
	protected Time time;
	protected User author;
	protected FactManager mng;
	
	public void setUp() {
		time = new Time(Calendar.getInstance().getTime());
		author = UserFactory.createUser();
		author.setUserid("usr");
		MeasurementSession ex = MeasurementSessionFactory.createMeasurementSession();
		surveySchedule = SurveyScheduleFactory.createSurveySchedule();
		observableEntity = ObservableEntityFactory.createObservableEntity();
		ex.setSurveySchedule(surveySchedule);
		surveySchedule.setObservableEntity(observableEntity);
		context = ex;
		mng = new FactManager();
		
		tCmp = TypeFactory.createCompositeType();
		tCmp.setRecurrent(false);
		tCmp.setName("tCmp");
		root = mng.createComposite(author, time);
		root.setContext(context);
		root.setStatus(FactStatus.DRAFT);
		root.assignType(tCmp);
		
		tCmp2 = TypeFactory.createCompositeType();
		tCmp2.setRecurrent(false);
		tCmp2.setName("tCmp2");
		cmp = mng.createComposite(author, time);
		cmp.setContext(context);
		cmp.setStatus(FactStatus.DRAFT);
		cmp.assignType(tCmp2);
		
		ttxt1 = TypeFactory.createTextualType();
		ttxt1.setRecurrent(false);
		ttxt1.setName("ttxt1");
		child_txt1 = mng.createTextual(author, time);
		child_txt1.setStatus(FactStatus.DRAFT);
		child_txt1.setContext(context);
		child_txt1.assignType(ttxt1);
		child_txt1.setText("testo1");
		
		ttxt2 = TypeFactory.createTextualType();
		ttxt2.setRecurrent(true);
		ttxt2.setName("ttxt2");
		child_txt2 = mng.createTextual(author, time);
		child_txt2.setStatus(FactStatus.DRAFT);
		child_txt2.setContext(context);
		child_txt2.assignType(ttxt2);
		child_txt2.setText("testo2");
		
		FactLinkFactory flFactory = new FactLinkFactory();
		FactLinkImpl fl1 = flFactory.insertLink((FactImpl)cmp, (FactImpl)child_txt1);
		FactLinkImpl fl2 = flFactory.insertLink((FactImpl)cmp, (FactImpl)child_txt2);
		FactLinkImpl fl0 = flFactory.insertLink((FactImpl)root, (FactImpl)cmp);
		
		TypeLink tl0 = TypeLinkFactory.createLink();
		TypeLink tl1 = TypeLinkFactory.createLink();
		TypeLink tl2 = TypeLinkFactory.createLink();
		
		fl0.setType(tl0);
		fl0.setPriority(2L);
		fl1.setType(tl1);
		fl1.setPriority(10L);
		fl2.setType(tl2);
		fl2.setPriority(5L);
		
		tl0.assignSource(tCmp);
		tl0.assignSource(tCmp2);
		tl0.setName("tCmp->tCmp2");
		tl0.setMin(1);
		tl0.setMax(0);
		tl0.setPriority(2L);
		
		tl1.assignSource(tCmp);
		tl1.assignTarget(ttxt1);
		tl1.setName("tcmp2->ttxt1");
		tl1.setMin(1);
		tl1.setMax(3);
		tl1.setPriority(10L);
		
		tl2.assignSource(tCmp);
		tl2.assignTarget(ttxt2);
		tl1.setName("tcmp2->ttxt2");
		tl2.setMin(2);
		tl2.setMax(5);
		tl2.setPriority(5L);
	}

}
