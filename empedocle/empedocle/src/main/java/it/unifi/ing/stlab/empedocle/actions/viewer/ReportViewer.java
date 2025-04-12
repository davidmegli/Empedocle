package it.unifi.ing.stlab.empedocle.actions.viewer;

import it.unifi.ing.stlab.commons.cdi.HttpParam;
import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionDetails;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.users.dao.UserDao;
import it.unifi.ing.stlab.users.model.Qualification;
import it.unifi.ing.stlab.view.model.Viewer;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@RequestScoped
public class ReportViewer {

	@Inject @HttpParam("eid")	
	private String measurementSessionId;
	
	@Inject @HttpParam("vid")	
	private String viewerId;
	
	@Inject
	private MeasurementSessionDao measurementSessionDao;
	
	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	private UserDao userDao;
	
	private MeasurementSessionDetails measurementSessionDetails;
	
	@PostConstruct
	public void init(){
		measurementSessionDetails = measurementSessionDao.fetchByMeasurementSessionViewer( Long.parseLong( measurementSessionId ), getQualificationId(), Long.parseLong( viewerId ));

	}
	
	public boolean isDisplayable(){
		return measurementSessionDetails != null;
	}
	
	public Fact getFact(){
		if(measurementSessionDetails == null)
			return null;
		
		return measurementSessionDetails.getFact();
	}
	
	public Viewer getViewer(){
		if(measurementSessionDetails == null)
			return null;
		
		return measurementSessionDetails.getViewer();
	}
	
	private Long getQualificationId() {
		Set<Qualification> result = userDao.listUserQualifications(loggedUser.getUser().getId());
		
		if ( result.size() != 1 ) 
			throw new IllegalStateException( "number of qualifications != 1" );
		
		return result.iterator().next().getId();
	}
	
}
