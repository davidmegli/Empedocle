package it.unifi.ing.stlab.empedocle.actions.health.services;

import javax.ejb.Local;
import javax.ejb.Timer;

@Local
public interface CloseMeasurementSession {

	void startService();
	void stopService();
	void timeout(Timer timer);
	
}
