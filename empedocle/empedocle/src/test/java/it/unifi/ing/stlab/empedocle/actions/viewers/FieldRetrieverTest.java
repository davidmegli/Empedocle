package it.unifi.ing.stlab.empedocle.actions.viewers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyLong;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import it.unifi.ing.stlab.empedocle.actions.viewer.FieldRetrieverBean;
import it.unifi.ing.stlab.empedocle.factory.health.SurveyScheduleFactory;
import it.unifi.ing.stlab.empedocle.factory.health.ExaminationFactory;
import it.unifi.ing.stlab.empedocle.model.health.SurveySchedule;
import it.unifi.ing.stlab.empedocle.model.health.Examination;
import it.unifi.ing.stlab.woodelements.dao.WoodElementDao;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.woodelements.model.WoodElement;
import it.unifi.ing.stlab.reflection.impl.factory.FactFactory;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.users.factory.QualificationFactory;
import it.unifi.ing.stlab.users.factory.UserFactory;
import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.view.controllers.FieldRetriever;

public class FieldRetrieverTest {

	protected FieldRetriever retriever;
	protected Fact root;
	protected String path;

	@Before
	public void setUp() throws Exception {
		retriever = new FieldRetrieverBean();

		root = FactFactory.createTextual();

		Examination examination = ExaminationFactory.createExamination();

		Calendar c = Calendar.getInstance();
		c.set( 2013, 04, 02 );
		SurveySchedule surveySchedule = SurveyScheduleFactory.createSurveySchedule();
		surveySchedule.setAcceptanceCode( "1234" );
		surveySchedule.setDate( c.getTime() );

		WoodElement wood_element = WoodElementFactory.createWoodElement();
		wood_element.setName( "Gino" );

		User user = UserFactory.createUser();
		user.setName( "Fabio" );
		Qualification qualification1 = QualificationFactory.createQualification();
		qualification1.setName( "medico" );
		// Qualification qualification2 =
		// QualificationFactory.createQualification();
		// qualification2.setName("specializzando");
		user.addQualification( qualification1 );
		// user.addQualification(qualification2);

		examination.setSurveySchedule( surveySchedule );
		surveySchedule.setWoodElement( wood_element );
		examination.setAuthor( user );

		root.setContext( examination );

		WoodElementDao wood_elementDao = mock( WoodElementDao.class );

		when( wood_elementDao.findLastVersionById( anyLong() ) ).thenReturn( wood_element );

		FieldUtils.assignField( retriever, "wood_elementDao", wood_elementDao );
	}

	@Test
	public void testObtainFieldPaziente() {
		path = "WoodElement.Name";
		String result = retriever.retrieve( root, path );

		assertEquals( "Gino", result );
	}

	@Test
	public void testObtainFieldVisita() {
		path = "SurveySchedule.AcceptanceCode";

		String result = retriever.retrieve( root, path );

		assertEquals( "1234", result );
	}

	@Test
	public void testObtainDateFormatted() {
		path = "SurveySchedule.Date";

		String result = retriever.retrieve( root, path );

		assertEquals( "02/05/2013", result );
	}

	@Test
	public void testObtainUser() {
		path = "User.Name";

		String result = retriever.retrieve( root, path );

		assertEquals( "Fabio", result );
	}

	@Test
	public void testObtainList() {
		path = "User.Qualifications";

		String result = retriever.retrieve( root, path );

		assertEquals( "medico", result );
	}

	@Test
	public void testObtainFieldNonExistent() {
		path = "Visita.foo";

		assertNull( retriever.retrieve( root, path ) );
	}

}
