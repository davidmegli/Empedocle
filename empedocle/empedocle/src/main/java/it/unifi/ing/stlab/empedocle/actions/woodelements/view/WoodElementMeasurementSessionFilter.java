package it.unifi.ing.stlab.empedocle.actions.observableentities.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.unifi.ing.stlab.empedocle.actions.health.measurementSession.MeasurementSessionListType;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionQueryBuilder;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.health.SurveyScheduleStatus;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionStatus;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterBean;
import it.unifi.ing.stlab.filters.FilterType;
import it.unifi.ing.stlab.filters.SelectItemBuilder;

@Named
@SessionScoped
public class ObservableEntityMeasurementSessionFilter extends FilterBean implements MeasurementSessionQueryBuilder { 

	private static final long serialVersionUID = -4636045233963106991L;

	private MeasurementSessionListType selectedList;
	
	private Long observableEntityId;

	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	private AgendaDao agendaDao;
	
	public ObservableEntityMeasurementSessionFilter() {
		setPageSize( 10 );
		
		// filters available
		initFilters();
		// sorting available
		initSorting();
		
		selectedList = MeasurementSessionListType.ALL;
	}
	
	private void initFilters() {
		initFilterDefs();
		initDefaultFilters();		
	}
	
	private void initFilterDefs() {
		addFilterDef( "Agenda", FilterType.SUGGESTION, "e.surveySchedule.agenda.uuid = :pagenda", "pagenda", new SelectItemBuilder() {
			
			@Override
			public List<SelectItem> getSelectItems(Object param, int offset, int limit) {
				List<SelectItem> agendaItems = new ArrayList<SelectItem>();
				if (param == null)
					param = "";
					
				List<Agenda> availableAgendas = agendaDao.findBySuggestion( param.toString(), loggedUser.getUsername(), limit);
				List<Agenda> favoriteAgendas = agendaDao.findFavoriteAgendasByUsername( loggedUser.getUsername() );
	
				SelectItemGroup favorite_group = new SelectItemGroup("Agende preferite");
				SelectItemGroup other_group = new SelectItemGroup("Altre agende");
	
				List<SelectItem> favorite_items = new ArrayList<SelectItem>();
				List<SelectItem> other_items = new ArrayList<SelectItem>();			
				
				for( Agenda a : availableAgendas ) {
					SelectItem selectItem = new SelectItem( a.getUuid(), a.toString() );
					
					if ( favoriteAgendas.contains( a ) ) {
			        	favorite_items.add( selectItem );
					} else {
			        	other_items.add( selectItem );
					}
				}
								
				if( !favorite_items.isEmpty() ) {
					favorite_group.setSelectItems(favorite_items.toArray(new SelectItem[1]));
					agendaItems.add(favorite_group);
				}
				if( !other_items.isEmpty() ) {
					other_group.setSelectItems(other_items.toArray(new SelectItem[1]));
					agendaItems.add(other_group);
				}
				
				return agendaItems;
			}
	
		} );
		
		addFilterDef( "Visited from", FilterType.DATE, "e.surveySchedule.date >= :pamin", "pamin" );
		addFilterDef( "Visited until", FilterType.DATE, "e.surveySchedule.date <= :pamax", "pamax" );
		
		setFilterDefsOrder( FilterDefsOrder.INSERTION );		
	}
	
	private void initSorting() {
		addSort( "Date", "e.surveySchedule.date asc", "e.surveySchedule.date desc" );
		
		toggle( "Date" ); // ordinamento asc
		toggle( "Date" ); // ordinamento desc
	}

	public void selectList( String listType ) {
		for ( MeasurementSessionListType type : MeasurementSessionListType.values()) {
			if ( type.toString().equalsIgnoreCase( listType )) {
				selectedList = type;
				clear();
			}
		}
	}
	public boolean isSelectedList( String listType ) {
		return selectedList.toString().equalsIgnoreCase( listType );
	}
	
	@Override
	public Query getCountQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select count( distinct e ) " )		
			  .append( " from MeasurementSession e " )
			  .append( " join e.surveySchedule.observableEntity.after aa " )
			  .append( " where aa.id = :id " )
			  .append( " and e.surveySchedule.agenda in :agendas " )
			  .append( " and e.status in :ex_status " )
			  .append( " and e.surveySchedule.status in :ap_status");
		
		writeFilters( buffer );

		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
	} 
	
	@Override
	public void resetFilters() {
		super.resetFilters();
		
		selectedList = MeasurementSessionListType.ALL;
	}

	@Override
	public Query getListQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select distinct e " )
			  .append( " from MeasurementSession e " )
			  .append( " join e.surveySchedule.observableEntity.after aa ")
			  .append( " where aa.id = :id " )
			  .append( " and e.surveySchedule.agenda in :agendas " )			  
			  .append( " and e.status in :ex_status " )
			  .append( " and e.surveySchedule.status in :ap_status");
		
		writeFilters( buffer );
		
		if ( getSorting() != null ) {
			buffer.append( " order by " )
				  .append( getSorting() );
		}

		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
	}


	private void writeFilters( StringBuffer buffer ) {
		for ( Filter filter : getFilters() ) {
			if ( filter.isUsed() ) {
				buffer.append( " and " )
					.append( filter.getDefinition().getExpression() );
			}
		}
	}
	
	@Override
	public void resolveParameters( Query query ) {
		super.resolveParameters( query );
		
		query.setParameter( "id", observableEntityId );
		query.setParameter( "agendas",  loggedUser.getAgendas());		
		
		switch ( selectedList ) {
		case ALL:
			query.setParameter( "ex_status", Arrays.asList(
					MeasurementSessionStatus.TODO,
					MeasurementSessionStatus.RUNNING,
					MeasurementSessionStatus.SUSPENDED, 
					MeasurementSessionStatus.DONE,
					MeasurementSessionStatus.CONCLUDED ));
			query.setParameter( "ap_status", Arrays.asList( 
					SurveyScheduleStatus.ACCEPTED, 
					SurveyScheduleStatus.BOOKED ));
			break;
		case BOOKED:
			query.setParameter( "ex_status", Arrays.asList(
					MeasurementSessionStatus.TODO ));
			query.setParameter( "ap_status", Arrays.asList( 
					SurveyScheduleStatus.BOOKED ));
			break;
		case ACCEPTED:
			query.setParameter( "ex_status", Arrays.asList(
					MeasurementSessionStatus.TODO ));
			query.setParameter( "ap_status", Arrays.asList( 
					SurveyScheduleStatus.ACCEPTED ));
			break;
		case SUSPENDED:
			query.setParameter( "ex_status", Arrays.asList(
					MeasurementSessionStatus.SUSPENDED ));
			query.setParameter( "ap_status", Arrays.asList( 
					SurveyScheduleStatus.ACCEPTED ));
			break;
		case RUNNING:
			query.setParameter( "ex_status", Arrays.asList(
					MeasurementSessionStatus.RUNNING ));
			query.setParameter( "ap_status", Arrays.asList( 
					SurveyScheduleStatus.ACCEPTED ));
			break;
		case DONE:
			query.setParameter( "ex_status", Arrays.asList(
					MeasurementSessionStatus.DONE ));
			query.setParameter( "ap_status", Arrays.asList( 
					SurveyScheduleStatus.ACCEPTED ));
			break;
		case CONCLUDED:
			query.setParameter( "ex_status", Arrays.asList(
					MeasurementSessionStatus.CONCLUDED ));
			query.setParameter( "ap_status", Arrays.asList( 
					SurveyScheduleStatus.ACCEPTED ));
			break;
		default:
			break;
		}
	}

	public Long getObservableEntityId() {
		return observableEntityId;
	}
	public void setObservableEntityId( Long observableEntityId ) {
		this.observableEntityId = observableEntityId;
	}
}
