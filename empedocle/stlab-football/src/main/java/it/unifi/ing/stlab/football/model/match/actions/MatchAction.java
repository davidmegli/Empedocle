package it.unifi.ing.stlab.football.model.match.actions;

import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.football.model.match.Match;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.entities.model.traced.Action;


@Entity
@Table(name = "match_actions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
        name = "action_type",
        discriminatorType = DiscriminatorType.STRING
)
public abstract class MatchAction
        extends ObservableEntityAction<Match, MatchAction, User, Time> implements Action<Match, MatchAction, User, Time>{

    public MatchAction(String uuid) {
        super(uuid);
    }

    protected MatchAction() {
        super();
    }
}
