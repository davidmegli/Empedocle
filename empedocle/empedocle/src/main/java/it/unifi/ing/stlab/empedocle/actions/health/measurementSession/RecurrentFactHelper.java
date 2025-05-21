package it.unifi.ing.stlab.empedocle.actions.health.measurementSession;

import it.unifi.ing.stlab.empedocle.dao.health.MeasurementSessionDao;
import it.unifi.ing.stlab.empedocle.model.health.MeasurementSession;
import it.unifi.ing.stlab.empedocle.visitor.fact.FactResumeVisitor;
import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.reflection.model.facts.links.FactLink;

public class RecurrentFactHelper {
	
	private final MeasurementSessionDao measurementSessionDao;
	
	public RecurrentFactHelper(MeasurementSessionDao measurementSessionDao) {
		super();
		this.measurementSessionDao = measurementSessionDao;
	}
	
	public void resumeRecurrentFacts(Fact root) {
		
		for(FactLink child : root.listChildren()) {
			
			if(child.getTarget().getType().getRecurrent()) {
				
				Fact currFact = child.getTarget();
				Fact resumed = measurementSessionDao.resume(currFact, ((MeasurementSession)currFact.getContext()).getSurveySchedule().getObservableEntity());
				
				if(resumed != null) {
					FactResumeVisitor v = new FactResumeVisitor(currFact);
					resumed.accept(v);
				}
				
			}
			else {
				resumeRecurrentFacts(child.getTarget());
				
			}

		}
	}
	
}
