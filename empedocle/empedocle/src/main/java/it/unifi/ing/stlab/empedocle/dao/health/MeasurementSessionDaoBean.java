package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.*;
import it.unifi.ing.stlab.entities.utils.ClassHelper;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.reflection.dao.types.TypeDao;
import it.unifi.ing.stlab.reflection.impl.dao.FactDao;
import it.unifi.ing.stlab.reflection.impl.model.facts.FactImpl;
import it.unifi.ing.stlab.reflection.impl.visitor.fact.RecursiveResolveLazyLoadVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.FactStatus;
import it.unifi.ing.stlab.view.dao.ViewerDao;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Stateless
@TransactionAttribute
public class MeasurementSessionDaoBean implements MeasurementSessionDao {

	@EJB
	private FactDao factDao;
	
	@EJB
	private TypeDao typeDao;
	
	@EJB 
	private ViewerDao viewerDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Fact resume(Fact f, ObservableEntity p) {
		String q = "SELECT f FROM FactImpl f" +
				" JOIN f.context.surveySchedule.observableEntity.after aa " +
				" WHERE f.type = :type" +
				" AND aa.id = :observable_entity" +
				" AND f.context.status != :notStatusMeasurementSession" +
				" AND f.status != :notStatusFact" +
				" AND f.destination is null" +
				" ORDER BY f.origin.time DESC";
		
		List<Fact> result = entityManager.createQuery(q)
			.setMaxResults(1)
			.setParameter("type", f.getType())
			.setParameter("observable_entity", p.getId())
			.setParameter("notStatusMeasurementSession", MeasurementSessionStatus.RUNNING)
			.setParameter("notStatusFact", FactStatus.REFUSED)
			// NB: f è ancora TRANSIENT !
//			.setParameter("currFact", f)
			.getResultList();
		
		if(result.size() < 1) {
			return null;
		}
		else {
			return result.iterator().next();
		}
	}
	
	@Override
	public Long countUserMeasurementSessionsByStatus(String userid, MeasurementSessionStatus status, Date start, Date end) {
		String q = "select count( distinct e )" +
				" from MeasurementSession e" +
				" where e.author.userid = :id" +
				" and e.status = :status " +
				" and e.surveySchedule.date >= :start " +
				" and e.surveySchedule.date <= :end";
		
		return (Long)entityManager.createQuery(q)
				.setParameter("id", userid)
				.setParameter("status", status)
				.setParameter("start", start)
				.setParameter("end", end)
				.getSingleResult();
	}
	
	@Override
	public int count(MeasurementSessionQueryBuilder builder) {
		return ((Long)builder.getCountQuery( entityManager ).getSingleResult()).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MeasurementSession> find(MeasurementSessionQueryBuilder builder, int offset, int limit) {
		Query query = builder
			.getListQuery( entityManager )
			.setFirstResult( offset );
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return (List<MeasurementSession>) query.getResultList();
	}

	@Override
	public MeasurementSession findById(Long id) {
		return entityManager.find(MeasurementSession.class, id);
	}
	
	@Override
	public int countObservableEntityHistory(Long observableEntityId, Long measurementSessionFromId,
			Set<MeasurementSessionStatus> statuses, Set<Agenda> agendas ) {
		if(observableEntityId == null || measurementSessionFromId == null)
			throw new IllegalArgumentException("id observable entity o id esame sono nulli");
		
		return ((Long)entityManager.createQuery("select count ( distinct e ) " +
						  						" from MeasurementSession e " +
						  						" join e.surveySchedule.observableEntity.after aa " +
						  						" where aa.id = :pid " +
												" and e.status in :statuses " +
												" and e.surveySchedule.agenda in :agendas " +
												" and e.id != :measurementSessionId ")						  						
						  				.setParameter("pid", observableEntityId)
						  				.setParameter("measurementSessionId", measurementSessionFromId)
						  				.setParameter("statuses", statuses)
						  				.setParameter("agendas", agendas)
				  						.getSingleResult()).intValue();
		
	}
	
	@Override
	public boolean hasObservableEntityHistory( Long observableEntityId ) {
		if( observableEntityId == null )
			throw new IllegalArgumentException("id observable entity è nullo");
		
		 return ((Long) entityManager.createQuery("select count ( distinct e ) " +
						 " from MeasurementSession e " +
						 " join e.surveySchedule.observableEntity.after aa " +
						 " where aa.id = :pid ")
				 .setParameter("pid", observableEntityId)
				 .getSingleResult()).intValue() > 0;
	}
	
	// FIXME ripulire
	@Override
	public boolean hasObservableEntityHistory(Long observableEntityId, Set<MeasurementSessionStatus> statuses, Set<Agenda> agendas ) {
		if(observableEntityId == null)
			throw new IllegalArgumentException("id observable entity è nullo");
		
		StringBuffer query = new StringBuffer();
		query.append("select e.id ")
			.append(" from MeasurementSession e ")
			.append(" join e.surveySchedule a ")
			.append(" join a.agenda ag ")
			.append(" join a.observableEntity.after aa ")
			.append(" where aa.id = :pid ");
		
		if(agendas != null && agendas.size() > 0) {
			query.append(" and (");
			
			int count_ag = 0;
			for(Agenda a : agendas) {
				query.append(" ag.id = '" + a.getId() +"' ");
				if(count_ag != agendas.size() - 1) {
					query.append(" or ");
				}
				count_ag++;
			}
			
			query.append(")");
		}
		
		if(statuses != null && statuses.size() > 0) {
			query.append(" and (");
			
			int count_st = 0;
			for(MeasurementSessionStatus e : statuses) {
				query.append(" e.status = '" + e.getId() +"' ");
				if(count_st != statuses.size() - 1) {
					query.append(" or ");
				}
				count_st++;
			}
			
			query.append(")");
		}
		
		return (entityManager.createQuery(query.toString())
			.setMaxResults(1)
			.setParameter("pid", observableEntityId)
			.getResultList().size() > 0);
	}
	
	@Override
	// FIXME eliminare IN?
	public List<MeasurementSession> findObservableEntityHistory( Long observableEntityId, Long measurementSessionFromId,
			Set<MeasurementSessionStatus> statuses, Set<Agenda> agendas, int offset, int limit ) {
		if ( observableEntityId == null || measurementSessionFromId == null )
			throw new IllegalArgumentException( "id observable entity o id esame sono nulli" );
		
		TypedQuery<MeasurementSession> query = entityManager.createQuery(
			" select distinct e from MeasurementSession e " 
					+ " join e.surveySchedule.observableEntity.after aa "
					+ " where e.status in :statuses " 
					+ " and e.surveySchedule.agenda in :agendas " 
					+ " and e.id != :measurementSessionId " 
					+ " and aa.id = :pid " 
					+ " order by e.surveySchedule.date DESC", MeasurementSession.class );
		
		query.setParameter( "pid", observableEntityId )
			.setParameter( "measurementSessionId", measurementSessionFromId )
			.setParameter( "statuses", statuses )
			.setParameter( "agendas", agendas );
		
		query.setFirstResult(offset);
		
		if ( limit > 0 ) {
			query.setMaxResults( limit );
		}
		
		return query.getResultList();
	}

	public List<MeasurementSession> findObservableEntityLastMeasurementSessions( Long observableEntityId, Long lastMeasurementSessionId,  int measurementSessionsNum){
		TypedQuery<MeasurementSession> query = entityManager.createQuery(
				" select distinct e from MeasurementSession e "
						+ " join e.surveySchedule.observableEntity.after aa"
						+ " where e.id != :measurementSessionId "
						+ " and aa.id = :pid "
						+ " order by e.surveySchedule.date DESC", MeasurementSession.class );

		query.setParameter( "pid", observableEntityId )
				.setParameter( "measurementSessionId", lastMeasurementSessionId );

		query.setMaxResults( measurementSessionsNum );

		return query.getResultList();

	}
	

	@Override
	public MeasurementSessionDetails fetchById(Long measurementSessionId, Long qualificationId, MeasurementSessionTypeContext context ) {
		long start = System.currentTimeMillis();
		
		if ( measurementSessionId == null || qualificationId == null ) return null;
		
		MeasurementSessionDetails result = new MeasurementSessionDetails();

		// init measurementSession
		result.setMeasurementSession( findById( measurementSessionId ));
		if ( result.getMeasurementSession() == null ) return null;

		
		// find type
		if ( result.getMeasurementSession().getType() == null || 
			 result.getMeasurementSession().getType().getType() == null ) return result;
			
		typeDao.fetchById( result.getMeasurementSession().getType().getType().getId() );
		
		
		// find fact
		Long typeId = result.getMeasurementSession().getType().getType().getId();
		result.setFact( factDao.findByContextId(measurementSessionId, typeId ));
		if ( result.getFact() == null )
			return result;
		
		factDao.fetchById( getId( result.getFact() ) );
		
		// FIXME trovare soluzione più elegante?
		// aggiunto per risolvere crash quando si stampa visita con storia passata
		result.getFact().accept(new RecursiveResolveLazyLoadVisitor());		

		// init viewer
		for ( ViewerUse viewerUse  : result.getMeasurementSession().getType().listViewers() ) {
			if ( viewerUse.getContext().equals( context ) &&
				 viewerUse.getQualification().getId().equals( qualificationId )) {
				
				result.setViewer( viewerDao.fetchById( viewerUse.getViewer().getId() ));
			}
		}
		
		long end = System.currentTimeMillis();
		
		// FIXME ripulire
		System.out.println( "TEMPO CARICAMENTO DATI:" + ( end - start ));
		
		
		return result;
	}
	
	@Override
	public MeasurementSessionDetails fetchByMeasurementSessionViewer( Long measurementSessionId, Long qualificationId, Long viewerId ) {
		MeasurementSessionDetails result = new MeasurementSessionDetails();

		// init measurementSession
		result.setMeasurementSession( findById( measurementSessionId ));
		if ( result.getMeasurementSession() == null ) return null;

		// prefetch qualifiche user
		entityManager.createQuery("select u from User u left join fetch u.qualifications q where u = :user")
				.setParameter("user", result.getMeasurementSession().getAuthor()).getResultList();
		
		// find type
		if ( result.getMeasurementSession().getType() == null || 
			 result.getMeasurementSession().getType().getType() == null ) return null;
			
		typeDao.fetchById( result.getMeasurementSession().getType().getType().getId() );
		
		// find fact
		Long typeId = result.getMeasurementSession().getType().getType().getId();
		result.setFact( factDao.findByContextId(measurementSessionId, typeId ));
		if ( result.getFact() == null )
			return null;
		
		factDao.fetchById( getId( result.getFact() ) );
		
		// FIXME trovare soluzione più elegante?
		// aggiunto per risolvere crash quando si stampa visita con storia passata
		result.getFact().accept(new RecursiveResolveLazyLoadVisitor());

		//FIXME da ricontrollare e testare
		// controllo che il viewer sia associato effettivamente con la mia qualifica
//		int size = ((Long)entityManager.createQuery(" select count( distinct v) from ViewerUse v " +
//									" where v.viewer.id = :viewerId " +
//									" and v.qualification.id = :qualificationId")
//								.setParameter("viewerId", viewerId)
//								.setParameter("qualificationId", qualificationId)
//								.getSingleResult()).intValue();
		
//		if(size != 1) return null;
		
		result.setViewer( viewerDao.fetchById( viewerId ));
		
		// pre-fetch dei services per poter leggere agenda
		// FIXME serve ancora? adesso l'agenda è associata anche alla visita!
		entityManager.createQuery("select a from SurveySchedule a, FactImpl f left join fetch a.services ss where f.context = a and f.id = :id")
			.setParameter("id", getId( result.getFact() ) ).getResultList();
		
		if ( result.getViewer() == null ) return null;
		
		return result;
	}

	@Override
	public void update( MeasurementSession e ) {
		entityManager.merge(e);
	}
	@Override
	public MeasurementSession save( MeasurementSession e ) {
		return entityManager.merge(e);
	}

	@Override
	public boolean hasPermission( Long measurementSessionId, Long qualificationId, MeasurementSessionOperation operation ) {
		MeasurementSessionType type = findById( measurementSessionId ).getType();
		
		for (Authorization auth : type.listAuthorizations()) {
			if( operation.equals( auth.getMeasurementSessionOperation() ) && 
					qualificationId.equals( auth.getQualification().getId() ) )
				return true;
		}
		
		return false;
		
	}
	
	//TODO test
	@Override
	public MeasurementSession findBySurveyScheduleCodes(String bookingCode, String acceptanceCode) {
		if ( bookingCode == null && acceptanceCode == null ) 
			throw new IllegalArgumentException( "bookingCode and acceptanceCode are null" );
		
		Query query;
		
		if ( acceptanceCode == null ) {
			query = entityManager.createQuery( "select e" +
					" from MeasurementSession e left join e.surveySchedule" +
					" where e.surveySchedule.bookingCode = :bookingCode" )
					.setParameter( "bookingCode", bookingCode );
			
		} else if ( bookingCode == null ) {
			query = entityManager.createQuery( "select e" +
					" from MeasurementSession e left join e.surveySchedule" +
					" where e.surveySchedule.acceptanceCode = :acceptanceCode" )
					.setParameter( "acceptanceCode", bookingCode );
			
		} else {
			query = entityManager.createQuery( "select e" +
					" from MeasurementSession e left join e.surveySchedule" +
					" where e.surveySchedule.bookingCode = :bookingCode" +
					" and e.surveySchedule.acceptanceCode = :acceptanceCode" )
					.setParameter( "bookingCode", bookingCode )
					.setParameter( "acceptanceCode", acceptanceCode );
		}
		
		List<?> results = query
				.setMaxResults(1)
				.getResultList();
		
		if ( results.size() == 0 ) 
			return null;
		else  if ( results.size() == 1 )
			return (MeasurementSession) results.get(0);
		else 
			throw new RuntimeException( "Error: More than one measurementSession returned" ); 
			// può capitare solo nel caso di cancellazione con id_accettazione multiplo 
			// usato per fare più accettazioni in blocco
	}

	@Override
	public Long countByType(MeasurementSessionType type) {
		return (Long) entityManager.createQuery("select count(e) from MeasurementSession e where e.type = :type").setParameter("type", type).getSingleResult();
	}
	
	
	private Long getId(Fact fact) {
		return ClassHelper.cast( fact, FactImpl.class ).getId();
	}

	@Override
	public void deleteById( Long id ) {
		if ( id != null ) {
			MeasurementSession measurementSession = findById(id);
			entityManager.remove( measurementSession.getSurveySchedule() );
			entityManager.remove( measurementSession );
		}

	}
	@Override
	public void delete(Long id){
		entityManager.remove( findById(id) );
	}
}
