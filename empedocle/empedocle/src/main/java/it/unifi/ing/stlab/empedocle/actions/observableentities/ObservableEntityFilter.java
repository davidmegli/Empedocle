package it.unifi.ing.stlab.empedocle.actions.observableentities;

import javax.enterprise.context.SessionScoped;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import it.unifi.ing.stlab.commons.query.QueryBuilder;
import it.unifi.ing.stlab.empedocle.dao.agendas.AgendaDao;
import it.unifi.ing.stlab.empedocle.dao.staff.StaffDao;
import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.filters.Filter;
import it.unifi.ing.stlab.filters.FilterBean;
import it.unifi.ing.stlab.filters.FilterType;
import it.unifi.ing.stlab.filters.SelectItemBuilder;

import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class ObservableEntityFilter extends FilterBean implements QueryBuilder { 

	private static final long serialVersionUID = -6187714669628170593L;

	//
	// EJB injection
	//
	@Inject
	private LoggedUser loggedUser;

	@Inject
	private StaffDao staffDao;

	@Inject
	private AgendaDao agendaDao;

	public ObservableEntityFilter() {
		setPageSize( 10 );
		
		// filters available
		initFilters();
		// default filters
		initDefaultFilters();
		// sorting available
		initSorting();
	}
	
	
	@Override
	public Query getCountQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select count( distinct p ) from ObservableEntity p where p.destination is null" );
		writeFilters( buffer );
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
	} 

	@Override
	public Query getListQuery( EntityManager entityManager ) {
		StringBuffer buffer = new StringBuffer();
		
		buffer.append( "select distinct p from ObservableEntity p where p.destination is null" );
		
		writeFilters( buffer );
		
		if ( getSorting() != null ) {
			buffer.append( " order by " )
				  .append( getSorting() );
		}
		
		Query result = entityManager.createQuery( buffer.toString() );
		resolveParameters( result );
		
		return result;
	}

	// specifically used to check if "Visita per Agenda:" has been set by the user
	// (this enables to run a new measurementSession in the specified Agenda
	public boolean isFilterSet( String filterName ) {
		List<Filter> filters = getFilters();
		if( filters.isEmpty() )
			return false;
		for( Filter f : filters ) {
			if( f.getDefinition() != null && f.getDefinition().getName().equals( filterName ) ) {
				return f.getValue() != null;
			}
		}
		return false;
	}


	public Filter getFilterByFilterDefName( String filterName ) {
		List<Filter> filters = getFilters();
		if( filters.isEmpty() )
			return null;
		for( Filter f : filters ) {
			if( f.getDefinition().getName().equals( filterName ) ) {
				return f;
			}
		}
		return null;
	}

	@Override
	public void resolveParameters( Query query ) {
		for ( Filter filter : getFilters() ) {
			if( filter.getDefinition() != null
					&& filter.getDefinition().getParam() != null )
				filter.resolveParameter( query );
		}
	}

	public boolean atLeastOneFilterSet() {
		for ( Filter filter : getFilters() ) {
			if ( filter.isUsed()
					&& filter.getDefinition().getExpression() != null ) {
				return true;
			}
		}
		return false;
	}

	private void initFilters(){
		addFilterDef( "Tax Code", FilterType.TEXT, "p.taxCode like :ptaxc", "ptaxc" );
		addFilterDef( "Surname", FilterType.TEXT, "p.surname like :psurname", "psurname" );
		addFilterDef( "Name", FilterType.TEXT, "p.name like :pname", "pname" );
		addFilterDef( "Sex", FilterType.TEXT, "p.sex like :psex", "psex" );
		addFilterDef( "Born after", FilterType.DATE, "p.birthDate >= :pbmin", "pbmin" );
		addFilterDef( "Born before", FilterType.DATE, "p.birthDate <= :pbmax", "pbmax" );
		addFilterDef( "Birthplace", FilterType.TEXT, "p.birthPlace like :pbplace", "pbplace" );
		addFilterDef( "Nationality", FilterType.TEXT, "p.nationality like :pnat", "pnat" );
		addFilterDef( "Region", FilterType.TEXT, "p.region like :preg", "preg" );
		addFilterDef( "Asl", FilterType.TEXT, "p.asl like :pasl", "pasl" );

		// this filter is declared in half because it is just used to store the clinical trial for enrollment, not
		// for real filtering
		addFilterDef( "Visit for Agenda:", FilterType.COMBO, null, null,
				new AgendaSelectItemBuilder() );
		
		setFilterDefsOrder( FilterDefsOrder.INSERTION );
	}
	
	private void initSorting() {
		addSort( "Surname", "p.surname asc, p.name asc", "p.surname desc, p.name asc" );
		addSort( "Sex", "p.sex asc, p.surname asc", "p.sex desc, p.surname asc" );
		addSort( "Birthdate", "p.birthDate asc, p.surname asc", "p.birthDate desc, p.surname asc" );
		addSort( "Birthplace", "p.birthPlace asc, p.surname asc", "p.birthPlace desc, p.surname asc" );

		// default sorting
		toggle( "Surname" );
	}
	
	@Override
	protected void initDefaultFilters() {
		Filter clinicalTrial = new Filter();
		clinicalTrial.setDefinition( findFilterDefByName( "Visit for Agenda:" ) );

		Filter surname = new Filter();
		surname.setDefinition( findFilterDefByName( "Surname" ) );
		
		Filter name = new Filter();
		name.setDefinition( findFilterDefByName( "Name" ) );

		getFilters().add( clinicalTrial );
		getFilters().add( surname );
		getFilters().add( name );
	}




	private void writeFilters( StringBuffer buffer ) {
		for ( Filter filter : getFilters() ) {
			if ( filter.isUsed()
					&& filter.getDefinition().getExpression() != null ) {
				buffer.append( " and " )
						.append( filter.getDefinition().getExpression() );
			}
		}
	}

	private class AgendaSelectItemBuilder implements SelectItemBuilder {

		@Override
		public List<SelectItem> getSelectItems(Object param, int offset, int limit) {
			List<SelectItem> agendaItems = new ArrayList<SelectItem>();

			List<Agenda> availableAgendas = agendaDao.findAll();
			List<Agenda> favoriteAgendas = agendaDao.findFavoriteAgendasByUsername(
					loggedUser.getUsername() );

			SelectItemGroup favorite_group = new SelectItemGroup( "" );
			SelectItemGroup other_group = new SelectItemGroup( "" );

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
				favorite_group.setSelectItems( favorite_items.toArray( new SelectItem[1] ) );
				agendaItems.add(favorite_group);
			}
			if( !other_items.isEmpty() ) {
				other_group.setSelectItems( other_items.toArray( new SelectItem[1] ) );
				agendaItems.add(other_group);
			}

			return agendaItems;
		}
	}

}
