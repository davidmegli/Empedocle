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

@Entity
@Table( name = "wood_elements" )
public class WoodElement extends ObservableEntity<WoodElement, WoodElementAction, WoodElementIdentifier, WoodElementFactory> {

	//attributs
	private WoodElementIdentifier identifier;
	@Enumerated(EnumType.STRING)
	public enum WoodElementType {Tree, Stem, Log, Pole, Sawn_Timber, Reclaimed_Wood}
	private String externalElementId;
	private WoodElementType type;
	private String specie;
	private String placeOfOrigin;
	private int age;
	private String note;

	//constructors
	public WoodElement( String uuid ) {
		super(uuid);
	}
	public WoodElement() {
		super();
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
	@Override
	@Transient
	public boolean isEmpty() {
		return 	identifier == null &&
				isEmpty(externalElementId) &&
				type == null &&
				isEmpty(specie) &&
				isEmpty(placeOfOrigin) &&
				age == 0 &&
				isEmpty(note);
	}

	@Override
	public boolean sameAs(WoodElement entity) {
		if (entity == null) return false;

		return
				( identifier == null ? entity.getIdentifier() == null : identifier.equals(entity.getIdentifier()) ) &&
				( externalElementId == null ? entity.getExternalElementId() == null : externalElementId.equals(entity.getExternalElementId()) ) &&
				( type == null ? entity.getType() == null : type.equals(entity.getType()) ) &&
				( specie == null ? entity.getSpecie() == null : specie.equals(entity.getSpecie()) ) &&
				( placeOfOrigin == null ? entity.getPlaceOfOrigin() == null : placeOfOrigin.equals(entity.getPlaceOfOrigin()) ) &&
				age == entity.getAge() &&
				( note == null ? entity.getNote() == null : note.equals(entity.getNote()) );
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

