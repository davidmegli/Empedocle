package it.unifi.ing.stlab.empedocle.model.health;

import it.unifi.ing.stlab.reflection.model.facts.Fact;
import it.unifi.ing.stlab.view.model.Viewer;

public class MeasurementSessionDetails {

	private MeasurementSession measurementSession;
	private Fact fact;
	private Viewer viewer;

	public MeasurementSession getMeasurementSession() {
		return measurementSession;
	}
	public void setMeasurementSession(MeasurementSession measurementSession) {
		this.measurementSession = measurementSession;
	}
	public Fact getFact() {
		return fact;
	}
	public void setFact(Fact fact) {
		this.fact = fact;
	}
	public Viewer getViewer() {
		return viewer;
	}
	public void setViewer(Viewer viewer) {
		this.viewer = viewer;
	}
}
