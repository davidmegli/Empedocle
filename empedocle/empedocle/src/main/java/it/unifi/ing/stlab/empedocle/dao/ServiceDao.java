package it.unifi.ing.stlab.empedocle.dao;

import it.unifi.ing.stlab.empedocle.model.Service;

import javax.ejb.Local;

@Local
public interface ServiceDao {
	
	Service findById(Long id);
	Service find(String code, String description, String agendaCode);
	void save(Service service);
	void update(Service service);
	void delete(Long id);

}
