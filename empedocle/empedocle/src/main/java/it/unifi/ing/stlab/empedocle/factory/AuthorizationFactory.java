package it.unifi.ing.stlab.empedocle.factory;

import it.unifi.ing.stlab.empedocle.model.Authorization;

import java.util.UUID;

public class AuthorizationFactory {

	public static Authorization createAuthorization() {
		return new Authorization( UUID.randomUUID().toString() );
	}
	
}
