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

public abstract class ObservableEntitySplitAction
	<T extends ObservableEntity<T, A, ?, ?>, A extends ObservableEntityAction<T, A,User,Time>>
	extends ObservableEntityAction<T,A,User,Time>
	implements SplitAction<T,A,User,Time> {

	public ObservableEntitySplitAction(String uuid) {
		super(uuid);
		setDelegate( new SplitActionImpl<T,A,User,Time>());
		getDelegate().setDelegator( (A)this );
	}
	protected ObservableEntitySplitAction() {
		super();
		setDelegate( new SplitActionImpl<T,A,User,Time>());
		getDelegate().setDelegator( (A)this );
	}
	
	@Transient
	protected SplitActionImpl<T,A,User,Time> getDelegate() {
		return (SplitActionImpl<T,A,User,Time>)super.getDelegate();
	}
	
	@ManyToOne
	@JoinColumn( name = "source_id" )
	public T getSource() {
		return getDelegate().getSource();
	}
	protected void setSource(T source) {
		getDelegate().setSource(source);
	}
	public void assignSource(T newSource) {
		getDelegate().assignSource(newSource);
	}

	
	@ManyToOne
	@JoinColumn( name = "target1_id" )
	public T getTarget1() {
		return getDelegate().getTarget1();
	}
	protected void setTarget1(T target1) {
		getDelegate().setTarget1(target1);
	}
	public void assignTarget1(T newTarget1) {
		getDelegate().assignTarget1(newTarget1);
	}

	
	@ManyToOne
	@JoinColumn( name = "target2_id" )
	public T getTarget2() {
		return getDelegate().getTarget2();
	}
	protected void setTarget2(T target2) {
		getDelegate().setTarget2(target2);
	}
	public void assignTarget2(T newTarget2) {
		getDelegate().assignTarget2(newTarget2);
	}

}
