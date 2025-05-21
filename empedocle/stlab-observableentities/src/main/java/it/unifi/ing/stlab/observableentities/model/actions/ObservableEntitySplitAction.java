package it.unifi.ing.stlab.observableentities.model.actions;

import it.unifi.ing.stlab.entities.implementation.traced.actions.SplitActionImpl;
import it.unifi.ing.stlab.entities.model.traced.actions.SplitAction;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue( "SP" )
public class ObservableEntitySplitAction
	extends ObservableEntityAction
	implements SplitAction<ObservableEntity,ObservableEntityAction,User,Time> {

	public ObservableEntitySplitAction(String uuid) {
		super(uuid);
		setDelegate( new SplitActionImpl<ObservableEntity,ObservableEntityAction,User,Time>());
		getDelegate().setDelegator( this );
	}
	protected ObservableEntitySplitAction() {
		super();
		setDelegate( new SplitActionImpl<ObservableEntity,ObservableEntityAction,User,Time>());
		getDelegate().setDelegator( this );
	}
	
	@Transient
	protected SplitActionImpl<ObservableEntity,ObservableEntityAction,User,Time> getDelegate() {
		return (SplitActionImpl<ObservableEntity,ObservableEntityAction,User,Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source_id" )
	public ObservableEntity getSource() {
		return getDelegate().getSource();
	}
	protected void setSource(ObservableEntity source) {
		getDelegate().setSource(source);
	}
	public void assignSource(ObservableEntity newSource) {
		getDelegate().assignSource(newSource);
	}

	
	@ManyToOne
	@JoinColumn( name = "target1_id" )
	public ObservableEntity getTarget1() {
		return getDelegate().getTarget1();
	}
	protected void setTarget1(ObservableEntity target1) {
		getDelegate().setTarget1(target1);
	}
	public void assignTarget1(ObservableEntity newTarget1) {
		getDelegate().assignTarget1(newTarget1);
	}

	
	@ManyToOne
	@JoinColumn( name = "target2_id" )
	public ObservableEntity getTarget2() {
		return getDelegate().getTarget2();
	}
	protected void setTarget2(ObservableEntity target2) {
		getDelegate().setTarget2(target2);
	}
	public void assignTarget2(ObservableEntity newTarget2) {
		getDelegate().assignTarget2(newTarget2);
	}

}
