package it.unifi.ing.stlab.empedocle.factory;

import it.unifi.ing.stlab.empedocle.model.Service;

import java.util.UUID;

public class ServiceFactory {

	public static Service createService() {
		return new Service( UUID.randomUUID().toString() );
	}
}
