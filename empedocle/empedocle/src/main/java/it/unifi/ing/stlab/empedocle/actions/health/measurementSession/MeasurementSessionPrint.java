package it.unifi.ing.stlab.empedocle.actions.health.measurementSession;

import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionTypeDao;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSessionTypeContext;
import it.unifi.ing.stlab.empedocle.security.LoggedUser;
import it.unifi.ing.stlab.view.model.Viewer;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Named
@Dependent
public class MeasurementSessionPrint implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<Viewer> reports;
	
	@Inject
	private LoggedUser loggedUser;
	
	@Inject
	private MeasurementSessionTypeDao measurementSessionTypeDao;
	
	public Boolean moreThanOneReport(MeasurementSession measurementSession) {
		return getReports(measurementSession).size() > 1;
	}
	
	public List<Viewer> getReports(MeasurementSession measurementSession){
		if(reports == null)
			initSelectedReports(measurementSession);
		
		return this.reports;
	}
	
	public List<Viewer> initSelectedReports(MeasurementSession measurementSession) {
		reports = measurementSessionTypeDao.findAssociatedViewer(measurementSession.getType().getId(),
							loggedUser.getUserQualification().getId(),
							MeasurementSessionTypeContext.REPORT);
		
		return reports;

	}
	
	public void clear(){
		reports = null;
	}

}
