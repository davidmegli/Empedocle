package it.unifi.ing.stlab.empedocle.actions.woodelements.view;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.commons.cdi.ViewScoped;
import it.unifi.ing.stlab.empedocle.actions.health.measurementSession.MeasurementSessionRowStyleHelper;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionDetails;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionTypeContext;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;

@Named
@ViewScoped
public class MeasurementSessionView implements Serializable {

	private static final long serialVersionUID = -1363640151085653007L;

	//
	// HttpParam injections
	//
	@Inject
	@HttpParam( "id" )
	private String id;
	
	@Inject
	@HttpParam( "pid" )
	private String pid;
	
	@Inject
	@HttpParam( "eid" )
	private String eid;
	
	@Inject @HttpParam("from")
	private String from;
	
	@Inject
	private LoggedUser loggedUser;

	//
	// EJB injections
	//
	@Inject
	private MeasurementSessionDao measurementSessionDao;

	//
	// Local attributes
	//
	private MeasurementSessionDetails current;

	@PostConstruct
	public void init() {
		current = measurementSessionDao.fetchById( 
				Long.parseLong( id ),
				loggedUser.getUserQualification().getId(), 
				MeasurementSessionTypeContext.VIEW );
	}
	
	public String getStyleClass( MeasurementSession measurementSession ) {
		return MeasurementSessionRowStyleHelper.getRowStyleClass( measurementSession );
	}
	
	//
	// navigation methods
	//
	public String close() {
		return "close";
	}

	//
	// get methods
	//
	public String getId() {
		return id;
	}
	
	public String getFrom() {
		return from;
	}
	
	public MeasurementSessionDetails getCurrent() {
		return current;
	}
}
