package it.unifi.ing.stlab.football.model.participation.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.ModifyActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.ModifyAction;
import it.unifi.ing.stlab.football.model.participation.Participation;
import it.unifi.ing.stlab.football.model.participation.actions.ParticipationAction;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityModifyAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;

import javax.persistence.*;


@Entity
@DiscriminatorValue( "MD" )
public class ParticipationModifyAction
        extends ParticipationAction
        implements ModifyAction<Participation, ParticipationAction, User, Time> {

    public ParticipationModifyAction(String uuid) {
        super(uuid);
        setDelegate(new ModifyActionImpl<>());
        getDelegate().setDelegator(this);
    }
    public ParticipationModifyAction() {
        super();
        setDelegate(new ModifyActionImpl<>());
        getDelegate().setDelegator(this);
    }

    @Transient
    public ModifyActionImpl<Participation,ParticipationAction,User,Time> getDelegate() {
        return (ModifyActionImpl<Participation,ParticipationAction,User,Time>)super.getDelegate();
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


    @OneToOne(mappedBy = "origin", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    public Participation getTarget() {
        return (Participation) getDelegate().getTarget();
    }
    public void setTarget(Participation target) {
        getDelegate().setTarget(target);
    }
    public void assignTarget(Participation newTarget) {
        getDelegate().assignTarget(newTarget);
    }

}
