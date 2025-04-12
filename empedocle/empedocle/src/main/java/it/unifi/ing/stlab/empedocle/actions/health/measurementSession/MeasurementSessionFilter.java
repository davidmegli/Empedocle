package it.unifi.ing.stlab.empedocle.actions.health.measurementSession;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionQueryBuilder;
import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.model.Staff;
import it.unifi.ing.stlab.empedocle.model.health.SurveyScheduleStatus;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionStatus;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.entities.utils.DateHelper;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterBean;
import it.unifi.ing.stlab.filters.FilterType;
import it.unifi.ing.stlab.filters.SelectItemBuilder;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.model.User;

@Named
@SessionScoped
public class MeasurementSessionFilter extends FilterBean implements MeasurementSessionQueryBuilder { 

	private static final long serialVersionUID = 5593366033833521811L;
	
	private MeasurementSessionListType selectedList;
	private Map<MeasurementSessionListType, MeasurementSessionQueryBuilder> queryBuilders;
	
	//
	// EJB injection
	//
	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	private AgendaDao agendaDao;
	
	@Inject
	private UserDao userDao;	
	
	@Inject
	private StaffDao staffDao;		
	
	public MeasurementSessionFilter() {
		setPageSize( 10 );
	}
	
	@PostConstruct
	public void init() {
		// filters available
		initFilters();
		// sorting available
		initSorting();
		// query builders
		initQueryBuilders();
		
		selectedList = MeasurementSessionListType.ALL;
	}
	
	private void initFilters() {
		initFilterDefs();
		initDefaultFilters();		
	}
	
	private void initSorting() {
		addSort( "Date", "e.survey_schedule.date asc, e.survey_schedule.wood_element.taxCode asc", "e.survey_schedule.date desc, e.survey_schedule.wood_element.taxCode desc" );
		toggle( "Date" );
	}

	@Override
	public void resetFilters() {
		super.resetFilters();
		selectedList = MeasurementSessionListType.ALL;
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
		return queryBuilders.get( selectedList ).getCountQuery( entityManager );
	} 

	@Override
	public Query getListQuery( EntityManager entityManager ) {
		return queryBuilders.get(selectedList).getListQuery( entityManager );
	}
	
	@Override
	protected void initDefaultFilters() {
		Filter agendaFilter = new Filter();
		agendaFilter.setDefinition( findFilterDefByName( "Agenda" ) );

		Filter fromDateFilter = new Filter();
		fromDateFilter.setDefinition( findFilterDefByName( "Date - from" ) );
		fromDateFilter.setValue( DateHelper.startOfToday( Calendar.getInstance().getTime() ) );

		Filter toDateFilter = new Filter();
		toDateFilter.setDefinition( findFilterDefByName( "Date - to" ) );
		toDateFilter.setValue( DateHelper.startOfTomorrow( Calendar.getInstance().getTime() ) );

		getFilters().add( agendaFilter );
		getFilters().add( fromDateFilter );
		getFilters().add( toDateFilter );
	}

	// se cerco le visite di una giornata, dovrò andare dalla mezzanotte di oggi alla mezzanotte di domani
	private void initFilterDefs() {
		
		addFilterDef( "Agenda", FilterType.SUGGESTION, "e.survey_schedule.agenda.uuid = :pagenda", "pagenda", new SelectItemBuilder() {
			
			@Override
			public List<SelectItem> getSelectItems(Object param, int offset, int limit) {
				List<SelectItem> agendaItems = new ArrayList<SelectItem>();
				if (param == null)
					param = "";
					
				List<Agenda> availableAgendas = agendaDao.findBySuggestion( param.toString(), loggedUser.getUsername(), limit);
				List<Agenda> favoriteAgendas = agendaDao.findFavoriteAgendasByUsername( loggedUser.getUsername() );
	
				SelectItemGroup favorite_group = new SelectItemGroup("Favorite agenda");
				SelectItemGroup other_group = new SelectItemGroup("Other agendas");
	
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
			
		addFilterDef( "Date - from", FilterType.DATE, "e.survey_schedule.date >= :pamin", "pamin" );
		addFilterDef( "Date - to", FilterType.DATE, "e.survey_schedule.date <= :pamax", "pamax" );
		
		addFilterDef( "Assigned to", FilterType.SUGGESTION, "e.author.uuid = :puser", "puser", new SelectItemBuilder() {
			@Override
			public List<SelectItem> getSelectItems(Object param, int offset, int limit) {
				List<SelectItem> userItems = new ArrayList<SelectItem>();
				
				Staff currStaff = staffDao.fetchByUsername( loggedUser.getUsername() );
				List<User> users = userDao.findBySuggestion( 
						param.toString(), limit );

				for ( User u : users ) {
					userItems.add( new SelectItem( u.getUuid(), u.getSurname() + " " + u.getName() ));
				}
				return userItems;
			}
		} );		
		
		addFilterDef( "Tax Code", FilterType.TEXT, "e.survey_schedule.wood_element.taxCode like :ptaxc", "ptaxc" );
		addFilterDef( "Surname", FilterType.TEXT, "e.survey_schedule.wood_element.surname like :psur", "psur" );
		addFilterDef( "Name", FilterType.TEXT, "e.survey_schedule.wood_element.name like :pname", "pname" );
		addFilterDef( "Birthplace", FilterType.TEXT, "e.survey_schedule.wood_element.birthPlace like :pbplace", "pbplace" );
		addFilterDef( "Birthdate - from", FilterType.DATE, "e.survey_schedule.wood_element.birthDate >= :pbmin", "pbmin" );
		addFilterDef( "Birthdate - to", FilterType.DATE, "e.survey_schedule.wood_element.birthDate <= :pbmax", "pbmax" );
		
		setFilterDefsOrder( FilterDefsOrder.INSERTION );
	}
	
	private void initQueryBuilders() {
		queryBuilders = new HashMap<MeasurementSessionListType, MeasurementSessionQueryBuilder>();
		final Set<Agenda> agendas = loggedUser.getAgendas();
		
		// FIXME si può evitare l'uso dell'operatore IN ?
		queryBuilders.put( MeasurementSessionListType.ALL, new MeasurementSessionQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.survey_schedule.agenda in :agendas", "agendas", agendas));			
		}});
		queryBuilders.put( MeasurementSessionListType.BOOKED, new MeasurementSessionQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.survey_schedule.agenda in :agendas", "agendas", agendas));			
			addPredefinedFilter( createFilter( "e.status = :pestatus", "pestatus", MeasurementSessionStatus.TODO ));
			addPredefinedFilter( createFilter( "e.survey_schedule.status = :pastatus", "pastatus", SurveyScheduleStatus.BOOKED ));
		}} );
		queryBuilders.put( MeasurementSessionListType.ACCEPTED, new MeasurementSessionQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.survey_schedule.agenda in :agendas", "agendas", agendas));			
			addPredefinedFilter( createFilter( "e.status = :pestatus", "pestatus", MeasurementSessionStatus.TODO ));
			addPredefinedFilter( createFilter( "e.survey_schedule.status = :pastatus", "pastatus", SurveyScheduleStatus.ACCEPTED ));
		}} );
		queryBuilders.put( MeasurementSessionListType.RUNNING, new MeasurementSessionQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.survey_schedule.agenda in :agendas", "agendas", agendas));			
			addPredefinedFilter( createFilter( "e.status = :pestatus", "pestatus", MeasurementSessionStatus.RUNNING ));
		}} );
		queryBuilders.put( MeasurementSessionListType.SUSPENDED, new MeasurementSessionQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.survey_schedule.agenda in :agendas", "agendas", agendas));			
			addPredefinedFilter( createFilter( "e.status = :pestatus", "pestatus", MeasurementSessionStatus.SUSPENDED ));
		}} );
		queryBuilders.put( MeasurementSessionListType.DONE, new MeasurementSessionQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.survey_schedule.agenda in :agendas", "agendas", agendas));			
			addPredefinedFilter( createFilter( "e.status = :pestatus", "pestatus", MeasurementSessionStatus.DONE ));
		}} );
		queryBuilders.put( MeasurementSessionListType.CONCLUDED, new MeasurementSessionQueryBuilderImpl( this ){{
			addPredefinedFilter( createFilter( "e.survey_schedule.agenda in :agendas", "agendas", agendas));			
			addPredefinedFilter( createFilter( "e.status = :pestatus", "pestatus", MeasurementSessionStatus.CONCLUDED ));
		}} );
	}
}
