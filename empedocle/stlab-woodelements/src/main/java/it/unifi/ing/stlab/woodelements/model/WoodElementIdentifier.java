package it.unifi.ing.stlab.woodelements.model;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.observablenetities.model.ObservableEntityIdentifier;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;

//TODO: extend class

@Entity
@Table( name = "wood_element_identifiers" )
public class WoodElementIdentifier extends ObservableEntityIdentifier implements Persistable {

	public WoodElementIdentifier() {
		super();
	}

	public WoodElementIdentifier(String uuid) {
		super(uuid);
	}



}
