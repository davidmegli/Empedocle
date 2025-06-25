package it.unifi.ing.stlab.woodelements.model;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.implementation.timed.TimedEntityImpl;
import it.unifi.ing.stlab.entities.implementation.traced.TracedEntityImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.entities.model.timed.TimedEntity;
import it.unifi.ing.stlab.entities.model.traced.TracedEntity;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;
import it.unifi.ing.stlab.woodelements.factory.WoodElementFactory;
import it.unifi.ing.stlab.woodelements.model.actions.WoodElementAction;
import it.unifi.ing.stlab.users.model.time.Time;
import it.unifi.ing.stlab.users.model.time.TimeRange;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

//TODO: check class

@Entity
@Table( name = "wood_elements" )
public class WoodElement extends ObservableEntity {

	//attributs
	private WoodElementIdentifier identifier;
	public enum WoodElementType {Tree, Stem, Log, Pole, Sawn_Timber, Reclaimed_Wood}
	private String externalElementId;
	private WoodElementType type;
	private String specie;
	private String placeOfOrigin;
	private int age;
	private String note;

	//constructors
	public WoodElement( String uuid ) {
		super(uuid)
	}
	public WoodElement() {
		super()
	}

	// Identifier
	@ManyToOne( cascade=CascadeType.PERSIST )
	@JoinColumn( name="identifier_id", nullable=true )
	public WoodElementIdentifier getIdentifier() {
		return identifier;
	}
	public void setIdentifier(WoodElementIdentifier identifier) {
		this.identifier = identifier;
	}

	@Column( name = "external_element_id" )
	public String getExternalElementId() {
		return externalElementId;
	}
	public void setExternalElementId(String externalElementId) {
		this.externalElementId = externalElementId;
	}

	@Enumerated( EnumType.STRING )
	public WoodElementType getType() {
		return type;
	}
	public void setType(WoodElementType type) {
		this.type = type;
	}

	public String getSpecie() {
		return specie;
	}
	public void setSpecie(String specie) {
		this.specie = specie;
	}

	@Column( name = "place_of_origin" )
	public String getPlaceOfOrigin(){
		return placeOfOrigin;
	}
	public void setPlaceOfOrigin(String placeOfOrigin){
		this.placeOfOrigin = placeOfOrigin;
	}

	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	

	// Methods
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
		return identifier == null
	}
	
	@Override
	public boolean sameAs(WoodElement entity) {
		return 
			( identifier == null && entity.getIdentifier() == null || identifier != null && identifier.equals( entity.getIdentifier() ));
	}


	@Override
	public WoodElement copy() {
		WoodElement result = WoodElementFactory.createWoodElement();
		result.setIdentifier(getIdentifier());
		result.setExternalElementId(getExternalElementId());
		result.setType(getType());
		result.setSpecie(getSpecie());
		result.setPlaceOfOrigin(getPlaceOfOrigin());
		result.setAge(getAge());
		result.setNote(getNote());
		return result;
	}
	
	private boolean isEmpty( String s ) {
        return s == null || "".equals(s.trim());
    }
}

