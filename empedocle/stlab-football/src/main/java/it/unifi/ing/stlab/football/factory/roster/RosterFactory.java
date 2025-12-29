package it.unifi.ing.stlab.football.factory.roster;

import it.unifi.ing.stlab.football.model.roster.Roster
import it.unifi.ing.stlab.football.model.roster.RosterIdentifier;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;

import javax.ejb.Stateless;

import java.util.UUID;

@Stateless
public class RosterFactory extends ObservableEntityFactory<Roster, RosterIdentifier> {

	@Override
	protected Roster createConcreteEntity() {
		return new Roster();
	}

	@Override
	protected RosterIdentifier createConcreteIdentifier() {
		return new RosterIdentifier();
	}

	@Override
	public Roster create() {
		Roster roster = super.create();
		return roster;
	}

	@Override
	public RosterIdentifier createIdentifier() {
		RosterIdentifier identifier = super.createIdentifier();
		return identifier;
	}
}

