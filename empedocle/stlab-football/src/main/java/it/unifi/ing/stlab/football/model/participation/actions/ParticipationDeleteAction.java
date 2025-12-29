package it.unifi.ing.stlab.football.model.participation.actions;s

import it.unifi.ing.stlab.entities.implementation.traced.actions.DeleteActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.DeleteAction;
import it.unifi.ing.stlab.woodelements.model.participation.Participation;
import it.unifi.ing.stlab.woodelements.model.participation.actions.ParticipationAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityDeleteAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;


@Entity
@DiscriminatorValue( "DL" )
public class ParticipationDeleteAction
        extends ParticipationAction
        implements DeleteAction<Participation, ParticipationAction, User, Time> {

    public ParticipationDeleteAction(String uuid) {
        super(uuid);
        setDelegate(new DeleteActionImpl<>());
        getDelegate().setDelegator(this);
    }
    protected ParticipationDeleteAction() {
        super();
        setDelegate(new DeleteActionImpl<>());
        getDelegate().setDelegator(this);
    }
    @Transient
    public DeleteActionImpl<Participation, ParticipationAction, User, Time> getDelegate() {
        return (DeleteActionImpl<Participation, ParticipationAction, User, Time>)super.getDelegate();
    }

    @ManyToOne
    @JoinColumn( name = "source_id" )
    public Participation getSource() {
        return (Participation) getDelegate().getSource();
    }
    public void setSource(Participation source) {
        getDelegate().setSource(source);
    }
    public void assignSource(Participation newSource) {
        getDelegate().assignSource(newSource);
    }

}
