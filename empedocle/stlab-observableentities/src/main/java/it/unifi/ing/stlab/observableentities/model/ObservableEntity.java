package it.unifi.ing.stlab.observableentities.model;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.implementation.timed.TimedEntityImpl;
import it.unifi.ing.stlab.entities.implementation.traced.TracedEntityImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.model.timed.TimedEntity;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.users.model.User;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;


import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table( name = "observable_entity" )
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name = "entity_type",
		discriminatorType = DiscriminatorType.STRING
)
public abstract class ObservableEntity
			<T extends ObservableEntity<T, A, I, F>,
			A extends ObservableEntityAction<T, A, ?,?>,
			I extends ObservableEntityIdentifier,
			F extends ObservableEntityFactory>
	implements TracedEntity<T,A>,
				TimedEntity<TimeRange,Time>, Persistable {

	//Attributs
	protected PersistableImpl persistable;
	protected TracedEntityImpl<T,A> tracedEntity;
	protected TimedEntityImpl<TimeRange,Time> timedEntity;
	protected I identifier;

	//Constructors
	public ObservableEntity( String uuid ) {
		persistable = new PersistableImpl( uuid );
		timedEntity = new TimedEntityImpl<TimeRange, Time>();
		tracedEntity = new TracedEntityImpl<T,A>();

		tracedEntity.setDelegator( (T) this );
	}
	protected ObservableEntity() {
		persistable = new PersistableImpl();
		timedEntity = new TimedEntityImpl<TimeRange, Time>();
		tracedEntity = new TracedEntityImpl<T,A>();
		tracedEntity.setDelegator( (T) this );
	}

	//Setter and Getter
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="observable_entity", allocationSize = 1 )
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_gen")

	public Long getId() {
		return persistable.getId();
	}
	public void setId(Long id) {
		persistable.setId(id);
	}


	public String getUuid() {
		return persistable.getUuid();
	}
	public void setUuid(String uuid) {
		persistable.setUuid(uuid);
	}


	@Embedded
	public TimeRange getTimeRange() {
		return timedEntity.getTimeRange();
	}
	public void setTimeRange(TimeRange timeRange) {
		timedEntity.setTimeRange(timeRange);
	}


	@ManyToMany( fetch = FetchType.EAGER, targetEntity = ObservableEntity.class )
	@JoinTable(
		name = "observable_entity_before",
	    joinColumns = { @JoinColumn( name = "observable_entity_id", referencedColumnName="id" ) },
	    inverseJoinColumns = { @JoinColumn( name = "before_observable_entity_id", referencedColumnName = "id") } )
	public Set<T> getBefore() {
		return tracedEntity.getBefore();
	}
	public void setBefore(Set<T> before) {
		tracedEntity.setBefore(before);
	}
	public Set<T> listBefore() {
		return tracedEntity.listBefore();
	}

	
	@ManyToMany( mappedBy = "before", fetch = FetchType.EAGER, targetEntity = ObservableEntity.class )
	public Set<T> getAfter() {
		return tracedEntity.getAfter();
	}
	public void setAfter(Set<T> after) {
		tracedEntity.setAfter(after);
	}
	public Set<T> listAfter() {
		return tracedEntity.listAfter();
	}

	
	@ManyToOne( fetch = FetchType.LAZY , cascade = CascadeType.PERSIST)
	@JoinColumn( name = "origin_id" )
	public A getOrigin() {
		return tracedEntity.getOrigin();
	}
	public void setOrigin(A origin) {
		tracedEntity.setOrigin(origin);
	}

	
	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn( name = "dest_id" )
	public A getDestination() {
		return tracedEntity.getDestination();
	}
	public void setDestination(A destination) {
		tracedEntity.setDestination(destination);
	}


	// Identifier
	@ManyToOne( cascade=CascadeType.PERSIST)
	@JoinColumn( name="identifier_id", nullable=true )
	public I getIdentifier() {
		return identifier;
	}
	public void setIdentifier(I identifier) {
		this.identifier = identifier;
	}
	

	//
	// Methods
	//
	
	@Transient
	public boolean isActive() {
		return tracedEntity.isActive();
	}

	public boolean isValidReference(TimedEntity<?, ?> timedEntity) {
		return timedEntity.isValidReference(timedEntity);
	}

	public boolean isValidAt(Time time) {
		return timedEntity.isValidAt(time);
	}

	public void init() {
		tracedEntity.init();
	}

	public abstract void update(); // ogni sottoclasse implementa la sua logica

	public void delete() {
		tracedEntity.delete();
	}
	
	public int hashCode() {
		return persistable.hashCode();
	}

	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}

	@Override
	@Transient
	public boolean isEmpty() {
		return
			identifier == null;
	}
	
	@Override
	public boolean sameAs(T entity) {
		return 
			( identifier == null && entity.getIdentifier() == null || identifier != null && identifier.equals( entity.getIdentifier() ) );
	}


	@Override
	public abstract T copy();
	
	protected boolean isEmpty( String s ) {
        return s == null || "".equals(s.trim());
    }
}

