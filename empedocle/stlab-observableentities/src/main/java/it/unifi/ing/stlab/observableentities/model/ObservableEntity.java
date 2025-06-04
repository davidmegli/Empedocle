package it.unifi.ing.stlab.observableentities.model;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.implementation.timed.TimedEntityImpl;
import it.unifi.ing.stlab.entities.implementation.traced.TracedEntityImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.model.timed.TimedEntity;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.observableentities.factory.ObservableEntityFactory;
import it.unifi.ing.stlab.observableentities.model.actions.ObservableEntityAction;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ObservableEntity
	<T extends ObservableEntity, A extends ObservableEntityAction<T, A>, I extends ObservableEntityIdentifier,
			F extends ObservableEntityFactory>
	implements TracedEntity<T,A>,
				TimedEntity<TimeRange,Time>, Persistable {

	private PersistableImpl persistable;
	private TracedEntityImpl<T,A> tracedEntity;
	private TimedEntityImpl<TimeRange,Time> timedEntity;
	
	private I identifier;


	public ObservableEntity( String uuid ) {
		persistable = new PersistableImpl( uuid );
		timedEntity = new TimedEntityImpl<TimeRange, Time>();
		tracedEntity = new TracedEntityImpl<T,A>();
		tracedEntity.setDelegator( this );
	}
	protected ObservableEntity() {
		persistable = new PersistableImpl();
		timedEntity = new TimedEntityImpl<TimeRange, Time>();
		tracedEntity = new TracedEntityImpl<T,A>();
		tracedEntity.setDelegator( this );
	}

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
	protected void setId(Long id) {
		persistable.setId(id);
	}

	

	// UUID
	public String getUuid() {
		return persistable.getUuid();
	}
	protected void setUuid(String uuid) {
		persistable.setUuid(uuid);
	}

	@Embedded
	public TimeRange getTimeRange() {
		return timedEntity.getTimeRange();
	}
	public void setTimeRange(TimeRange timeRange) {
		timedEntity.setTimeRange(timeRange);
	}

	
	@ManyToMany( fetch = FetchType.LAZY )
	@JoinTable(
		name = "observable_entity_before",
	    joinColumns = { @JoinColumn( name = "observable_entity_id", referencedColumnName="id" ) },
	    inverseJoinColumns = { @JoinColumn( name = "before_observable_entity_id", referencedColumnName = "id") } )
	protected Set<ObservableEntity> getBefore() {
		return tracedEntity.getBefore();
	}
	protected void setBefore(Set<ObservableEntity> before) {
		tracedEntity.setBefore(before);
	}
	public Set<ObservableEntity> listBefore() {
		return tracedEntity.listBefore();
	}

	
	@ManyToMany( mappedBy = "before", fetch = FetchType.LAZY )
	protected Set<ObservableEntity> getAfter() {
		return tracedEntity.getAfter();
	}
	protected void setAfter(Set<ObservableEntity> after) {
		tracedEntity.setAfter(after);
	}
	public Set<ObservableEntity> listAfter() {
		return tracedEntity.listAfter();
	}

	
	@ManyToOne( fetch = FetchType.LAZY , cascade = CascadeType.PERSIST )
	@JoinColumn( name = "origin_id" )
	public A getOrigin() {
		return tracedEntity.getOrigin();
	}
	protected void setOrigin(A origin) {
		tracedEntity.setOrigin(origin);
	}

	
	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
	@JoinColumn( name = "dest_id" )
	public A getDestination() {
		return tracedEntity.getDestination();
	}
	protected void setDestination(A destination) {
		tracedEntity.setDestination(destination);
	}


	// Identifier
	@ManyToOne( cascade=CascadeType.PERSIST )
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
	public T copy() {
		T result = F.createObservableEntity();
		result.setIdentifier( getIdentifier() );
		return result;
	}
	
	private boolean isEmpty( String s ) {
        return s == null || "".equals(s.trim());
    }
}

