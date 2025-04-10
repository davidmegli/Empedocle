package it.unifi.ing.stlab.empedocle.dao.health;

import it.unifi.ing.stlab.empedocle.model.health.Appointment;
import it.unifi.ing.stlab.woodelements.model.WoodElement;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface AppointmentDao {
	
	List<Appointment> findByWoodElements(Set<WoodElement> woodElements);
	void update(Appointment a);
}
