package it.unifi.ing.stlab.empedocle;


import it.unifi.ing.stlab.empedocle.dao.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.dao.MeasurementSessionDaoBean;
import it.unifi.ing.stlab.empedocle.dao.MeasurementSessionTypeDao;
import it.unifi.ing.stlab.empedocle.dao.MeasurementSessionTypeDaoBean;
import it.unifi.ing.stlab.empedocle.model.*;
import it.unifi.ing.stlab.entities.implementation.GarbageCollector;
import it.unifi.ing.stlab.entities.implementation.JpaGarbageAction;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.dao.types.TypeDaoBean;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDaoBean;
import it.unifi.ing.stlab.reflection.impl.manager.FactManager;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.visitor.type.FactFactoryVisitor;
import it.unifi.ing.stlab.reflection.model.facts.*;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;
import it.unifi.ing.stlab.reflection.model.types.*;
import it.unifi.ing.stlab.reflection.model.types.links.TypeLink;
import it.unifi.ing.stlab.reflection.visitor.fact.AssignContextVisitor;
import it.unifi.ing.stlab.reflection.visitor.fact.FactDefaultInitializerVisitor;
import it.unifi.ing.stlab.test.FieldUtils;
import it.unifi.ing.stlab.test.JpaTest;
import it.unifi.ing.stlab.test.TimeTracker;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.dao.UserDaoBean;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.view.model.widgets.container.*;
import it.unifi.ing.stlab.view.model.widgets.input.*;
import it.unifi.ing.stlab.view.model.widgets.output.*;
import org.junit.BeforeClass;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

// TODO Eseguire i seguenti tests (1000 ripetizioni):
// 1. start-end su visita todo oculistica e cardio
// 2. resume-suspend su visita sospesa (vuota o con alcuni campi riempiti???) oculistica e cardio
// 3. search-view su visita done/suspended (vuota o con alcuni campi riempiti???) oculistica e cardio


//@RunWith(Parameterized.class)
public class ReflectionTest extends JpaTest {

	private UserDao userDao;
	private FactDao factDao;
	private TypeDao typeDao;
	private MeasurementSessionDao measurementSessionDao;
	private MeasurementSessionTypeDao measurementSessionTypeDao;
		
	@Parameters
	public static List<Object[]> data() {
	    return Arrays.asList(new Object[2][0]);
	}
	
	@BeforeClass
	public static void setUpClass() {
		initEntityManagerFactory( "test_reflection" );
	}
	
	@Override
	public void setUp() throws Exception {
		super.setUp();

		userDao = new UserDaoBean();
		FieldUtils.assignField( userDao, "entityManager", entityManager );
		
		factDao = new FactDaoBean();
		FieldUtils.assignField( factDao, "entityManager", entityManager );
		
		typeDao = new TypeDaoBean();
		FieldUtils.assignField( typeDao, "entityManager", entityManager );


		measurementSessionDao = new MeasurementSessionDaoBean();
		FieldUtils.assignField( measurementSessionDao, "entityManager", entityManager );
		FieldUtils.assignField( measurementSessionDao, "factDao", factDao );
		FieldUtils.assignField( measurementSessionDao, "typeDao", typeDao );
		measurementSessionTypeDao = new MeasurementSessionTypeDaoBean();
		FieldUtils.assignField( measurementSessionDao, "entityManager", entityManager );
		
		//garbageCollector = new GarbageCollectorHelperBean();
		FieldUtils.assignField( measurementSessionDao, "entityManager", entityManager );
	}

//	@Test
	public void testStartEnd() {
		TimeTracker timeTracker = new TimeTracker( "Start-End" );
		
		timeTracker.start();
		// Read
		MeasurementSession measurementSession = measurementSessionDao.findById( new Long( 174350  ));
		
		// Start
		MeasurementSessionType measurementSessionType = measurementSessionTypeDao.findByMeasurementSessionId( measurementSession.getId() );
		User user = userDao.findByUsername( "administrator" );
		
		Type type = typeDao.fetchById( measurementSessionType.getType().getId() );
		FactFactoryVisitor factFactory = new FactFactoryVisitor( user, new Time( new Date( System.currentTimeMillis())));
		type.accept( factFactory );
		
		Fact fact = factFactory.getResult();
		AssignContextVisitor assignContext = new AssignContextVisitor( measurementSession );
		fact.accept( assignContext );
		
		FactDefaultInitializerVisitor assignDefault = new FactDefaultInitializerVisitor();
		fact.accept( assignDefault );

		//RecurrentFactHelper recurrentHelper = new RecurrentFactHelper(measurementSessionDao);
		//recurrentHelper.resumeRecurrentFacts(fact);
		//garbageCollector.flush();

		measurementSession.setStatus( MeasurementSessionStatus.RUNNING );
		measurementSession.setType( measurementSessionType );
		measurementSession.setLastUpdate( new Date( System.currentTimeMillis() ));
		measurementSession.setAuthor( user );

		factDao.save( fact );
		entityManager.getTransaction().commit();

		// End
		entityManager.getTransaction().begin();
		
		Date date = new Date( System.currentTimeMillis() );
		
		measurementSession.setStatus( MeasurementSessionStatus.DONE );
		measurementSession.setWasDone( true );
		measurementSession.setLastUpdate( date );
		FactManager factManager = new FactManager();
		factManager.purge( (FactImpl)fact );
		//GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));

		entityManager.getTransaction().commit();
		
		timeTracker.stop();
		printResult( timeTracker );
	}	
	
//	@Test
	public void testResumeSuspend() {
		TimeTracker timeTracker = new TimeTracker( "Resume-Suspend" );
		
		timeTracker.start();
		// Read
		MeasurementSession measurementSession = measurementSessionDao.findById( new Long( 174350  ));
		User user = userDao.findByUsername( "administrator" );
		Date date = new Date( System.currentTimeMillis() );
		Time time = new Time( date );

		// Resume
		entityManager.getTransaction().begin();
		Fact source = factDao.findByContextId( measurementSession.getId(), measurementSession.getType().getType().getId() );
		factDao.fetchById( ((FactImpl)source).getId() );

		FactManager factManager = new FactManager();
		Fact dest = factManager.modify( user, time, (FactImpl)source );

		measurementSession.setStatus( MeasurementSessionStatus.RUNNING );
		measurementSession.setLastUpdate( date );
		measurementSession.setAuthor( user );

		factDao.save( dest );
		entityManager.getTransaction().commit();

		// Suspend
		entityManager.getTransaction().begin();

		measurementSession.setStatus( MeasurementSessionStatus.SUSPENDED );
		measurementSession.setLastUpdate( date );
		factManager.purge( (FactImpl)dest );
		//GarbageCollector.getInstance().flush( new JpaGarbageAction( entityManager ));
		entityManager.getTransaction().commit();
		
		timeTracker.stop();
		printResult( timeTracker );
	}

	
	private void printResult( TimeTracker t) {
		PrintWriter writer;
		try {
			writer = new PrintWriter( new FileOutputStream( new File( t.getMethod() + ".txt"), true ));
			writer.println(t.getDuration());
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
	}
}


class ReflectionFactVisitor implements FactVisitor {

	private final ReflectionTypeVisitor typeVisitor;
	
	
	public ReflectionFactVisitor(ReflectionTypeVisitor typeVisitor) {
		super();
		this.typeVisitor = typeVisitor;
	}

	@Override
	public void visitTextual(TextualFact fact) {
		visitType( fact );
		fact.getText();
		
	}

	@Override
	public void visitQuantitative(QuantitativeFact fact) {
		visitType( fact );
		if ( fact.getQuantity() != null ) {
			fact.getQuantity().getUnit();
		}
	}

	@Override
	public void visitQualitative(QualitativeFact fact) {
		visitType( fact );
		if ( fact.getPhenomenon() != null ) {
			fact.getPhenomenon().getName();
		}
	}

	@Override
	public void visitTemporal(TemporalFact fact) {
		visitType( fact );
		fact.getDate();
	}

	@Override
	public void visitComposite(CompositeFact fact) {
		visitType( fact );

		for ( FactLink link : fact.listActiveLinks() ) {
			if ( link.getTarget() != null ) {
				link.getTarget().accept( this );
			}
		}
	}
	
	private void visitType( Fact fact ) {
		if ( fact.getType() != null ) {
			fact.getType().accept( typeVisitor );
		}
	}
}

class ReflectionTypeVisitor implements TypeVisitor {

	@Override
	public void visitTextualType(TextualType type) {
		type.getName();
	}

	@Override
	public void visitEnumeratedType(EnumeratedType type) {
		type.getName();
		
		for( Phenomenon ph : type.listPhenomena()) {
			ph.getName();
		}
	}

	@Override
	public void visitQueriedType(QueriedType type) {
		type.getName();
		type.getQueryDef();
	}

	@Override
	public void visitQuantitativeType(QuantitativeType type) {
		type.getName();
		
		for ( UnitUse uu : type.listUnits()) {
			uu.getUnit();
		}
	}

	@Override
	public void visitTemporalType(TemporalType type) {
		type.getName();
		type.getType();
	}

	@Override
	public void visitCompositeType(CompositeType type) {
		type.getName();
		
		for ( TypeLink link : type.listChildren() ) {
			if ( link.getTarget() != null ) {
				link.getTarget().accept( this );
			}
		}
	}
	
}
