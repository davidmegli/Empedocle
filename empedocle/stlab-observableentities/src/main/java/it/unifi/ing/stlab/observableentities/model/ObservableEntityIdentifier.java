package it.unifi.ing.stlab.observableentities.model;

import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Version;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class ObservableEntityIdentifier implements Persistable {

	protected PersistableImpl persistable;
	
	protected String code;
	protected Long version;

	public ObservableEntityIdentifier(){
		persistable = new PersistableImpl();
		
	}
	public ObservableEntityIdentifier(String uuid){
		persistable = new PersistableImpl( uuid );
	}
	
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="observable_entity_identifier", allocationSize = 1 )
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
	
	
	// Identifier
	@Column( unique = true, nullable = false )
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	
	@Version
	public Long getVersion() {
		return version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
	
	public int hashCode() {
		return persistable.hashCode();
	}

	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
	
	
}
