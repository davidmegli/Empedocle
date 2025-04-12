package it.unifi.ing.stlab.reflection.dao.facts;

import static org.junit.Assert.assertEquals;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.empedocle.factory.health.MeasurementSessionFactory;
import it.unifi.ing.stlab.empedocle.factory.health.MeasurementSessionTypeFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ServiceFactory;
import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionStatus;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionType;
import it.unifi.ing.stlab.empedocle.model.health.Service;
import it.unifi.ing.stlab.reflection.factory.types.TypeFactory;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.model.types.Type;
import it.unifi.ing.stlab.test.PersistenceTest;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import java.util.Calendar;
import java.util.List;

import org.junit.Test;

public class SelectUserMeasurementSessionTest extends PersistenceTest {
	
	private MeasurementSession e1;
	private FactImpl root1;
	private User author;
	private Time time;
	
	@Override
	public void insertData() {
		FactManager mng = new FactManager();
		
		author = UserFactory.createUser();
		author.setUserid("admin");
		author.setSurname("admin");
		entityManager.persist(author);
		
		time = new Time(Calendar.getInstance().getTime());
		
		Service s = ServiceFactory.createService();
		entityManager.persist(s);
		
		SurveySchedule a = SurveyScheduleFactory.createSurveySchedule();
		a.addService(s);
		entityManager.persist(a);
		
		MeasurementSessionType et = MeasurementSessionTypeFactory.createMeasurementSessionType();
		entityManager.persist(et);
		
		Type t = TypeFactory.createCompositeType();
		entityManager.persist(t);
		
		
		e1 = MeasurementSessionFactory.createMeasurementSession();
		e1.setStatus(MeasurementSessionStatus.SUSPENDED);
		e1.setSurveySchedule(a);
		e1.setType(et);
		entityManager.persist(e1);
		
		root1 = mng.createComposite(author, time);
		root1.setContext(e1);
		entityManager.persist(root1.getOrigin());
		entityManager.persist(root1);
		
		root1.assignType(t);
		et.setType(t);
		
		MeasurementSession e2 = MeasurementSessionFactory.createMeasurementSession();
		e2.setStatus(MeasurementSessionStatus.CONCLUDED);
		e2.setSurveySchedule(a);
		e2.setType(et);
		entityManager.persist(e2);
		
		FactImpl root2 = mng.createComposite(author, time);
		root2.setContext(e2);
		entityManager.persist(root2.getOrigin());
		entityManager.persist(root2);
		
		root2.assignType(t);
		
		MeasurementSession e3 = MeasurementSessionFactory.createMeasurementSession();
		e3.setStatus(MeasurementSessionStatus.SUSPENDED);
		e3.setSurveySchedule(a);
		e3.setType(et);
		entityManager.persist(e3);
		
		User other = UserFactory.createUser();
		other.setUserid("other");
		other.setSurname("other");
		entityManager.persist(other);
		
		FactImpl root3 = mng.createComposite(other, time);
		root3.setContext(e3);
		entityManager.persist(root3.getOrigin());
		entityManager.persist(root3);
		
	}
	
	@Test
	public void testQuery() {
		
		List<?> result2 = entityManager.createQuery("from MeasurementSession e").getResultList();
		assertEquals(3, result2.size());
		
		String q = "select distinct(e)" +
				" from MeasurementSession e, FactImpl f" +
//				" join e.survey_schedule.services s" +   //XXX non serve
				" join f.context c" +
				" where c.id = e.id" +
				" and f.type = e.type.type" +
				" and f.destination is null" +
				" and f.origin.author.surname like 'admin'" +
				" and e.status = 'SUSPENDED'";
		
		List<?> result = entityManager.createQuery(q).getResultList();
		assertEquals(1, result.size());
		
	}

	
	
}
