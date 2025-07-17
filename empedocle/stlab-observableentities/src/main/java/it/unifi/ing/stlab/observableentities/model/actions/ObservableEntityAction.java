package it.unifi.ing.stlab.observableentities.model.actions;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.implementation.traced.ActionImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.model.traced.Action;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.entities.model.traced.Actor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

@Entity
@Table( name="observable_entity_actions" )
@Inheritance( strategy=InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( 
	name= "from_class", 
	discriminatorType=DiscriminatorType.STRING )
public abstract class ObservableEntityAction<
		T extends ObservableEntity<T, A, ?, ?>,
		A extends ObservableEntityAction<T, A, U, H>,
		U extends Actor,
		H extends Time>
		implements Action<T, A, U, H>, Persistable{

	protected PersistableImpl persistable;
	protected ActionImpl<T,A,U,H> delegate;

	public ObservableEntityAction( String uuid ) {
		persistable = new PersistableImpl( uuid );
	}
	protected ObservableEntityAction() {
		persistable = new PersistableImpl();
	}

	
	
	@Transient
	protected ActionImpl<T, A, U, H> getDelegate() {
		return delegate;
	}
	protected void setDelegate(ActionImpl<T, A,U, H> delegate) {
		this.delegate = delegate;
	}
	
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="observable_entity_action",
		allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_gen")	
	public Long getId() {
		return persistable.getId();
	}
	public void setId(Long id) {
		persistable.setId(id);
	}

	
	// UUID
	public String getUuid() {
		return persistable.getUuid();
	}
	public void setUuid(String uuid) {
		persistable.setUuid(uuid);
	}

	
	@ManyToOne( fetch = FetchType.LAZY )
	@JoinColumn( name = "author_id" )
	public U getAuthor() {
		return delegate.getAuthor();
	}
	public void setAuthor(U author) {
		delegate.setAuthor( author );
	}

	@AttributeOverrides({
		@AttributeOverride( name="date",column=@Column(name="action_time"))
	 })	
	@Embedded
	public H getTime() {
		if ( delegate.getTime() == null ) {
			return (H) new Time( null );
		}
		return delegate.getTime();
	}
	public void setTime(H time) {
		delegate.setTime( time );
	}
	
	
	
	//
	// HashCode & Equals
	//
	
	@Transient
	public boolean isTerminal() {
		return getDelegate().isTerminal();
	}

	public void delete() {
		getDelegate().delete();
	}
	
	public int hashCode() {
		return persistable.hashCode();
	}

	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
}
