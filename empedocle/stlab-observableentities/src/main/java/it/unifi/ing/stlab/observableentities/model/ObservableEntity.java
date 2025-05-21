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
@Table( name = "observable_entities" )
public class ObservableEntity 
	implements TracedEntity<ObservableEntity,ObservableEntityAction>,
				TimedEntity<TimeRange,Time>, Persistable {

	private PersistableImpl persistable;
	private TracedEntityImpl<ObservableEntity,ObservableEntityAction> tracedEntity;
	private TimedEntityImpl<TimeRange,Time> timedEntity;
	
	private ObservableEntityIdentifier identifier;


	//FIXME DPB da cancellare
	private String oldIdentifier;

	//FIXME: old patient attributes, delete
	private String name;
	private String surname;
	private Sex sex;
	private Date birthDate;
	private String birthPlace;
	private String taxCode;
	private String ssnCode;
	private Address residence;
	private Address domicile;
	private String region;
	private String homePhone;
	private String workPhone;
	private String nationality;
	private String asl;

	public enum ObservableEntityType {Tree, Stem, Log, Pole, Sawn_Timber, Reclaimed_Wood}
	//ObservableEntity attributes
	private String externalElementId;
	private ObservableEntityType type;
	private String specie;
	private String placeOfOrigin;
	private int age;
	private String note;


	public ObservableEntity( String uuid ) {
		persistable = new PersistableImpl( uuid );
		timedEntity = new TimedEntityImpl<TimeRange, Time>();
		tracedEntity = new TracedEntityImpl<ObservableEntity,ObservableEntityAction>();
		tracedEntity.setDelegator( this );
	}
	protected ObservableEntity() {
		persistable = new PersistableImpl();
		timedEntity = new TimedEntityImpl<TimeRange, Time>();
		tracedEntity = new TracedEntityImpl<ObservableEntity,ObservableEntityAction>();
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
	public ObservableEntityAction getOrigin() {
		return tracedEntity.getOrigin();
	}
	protected void setOrigin(ObservableEntityAction origin) {
		tracedEntity.setOrigin(origin);
	}

	
	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.PERSIST )
	@JoinColumn( name = "dest_id" )
	public ObservableEntityAction getDestination() {
		return tracedEntity.getDestination();
	}
	protected void setDestination(ObservableEntityAction destination) {
		tracedEntity.setDestination(destination);
	}


	
	//FIXME DPB da cancellare
//	@Column( unique = true )
	@Column( name="identifier" ) 
	public String getOldIdentifier() {
		return oldIdentifier;
	}
	public void setOldIdentifier(String oldIdentifier) {
		this.oldIdentifier = oldIdentifier;
	}

	// Identifier
	@ManyToOne( cascade=CascadeType.PERSIST )
	@JoinColumn( name="identifier_id", nullable=true )
	public ObservableEntityIdentifier getIdentifier() {
		return identifier;
	}
	public void setIdentifier(ObservableEntityIdentifier identifier) {
		this.identifier = identifier;
	}
	

	// Name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	// Surname
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	
	@Enumerated( EnumType.STRING )
	public Sex getSex() {
		return sex; 
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
	
	@Temporal( TemporalType.TIMESTAMP )
	@Column( name = "birth_date" )
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	
	@Column( name = "birth_place" )
	public String getBirthPlace() {
		return birthPlace;
	}
	public void setBirthPlace(String birthPlace) {
		this.birthPlace = birthPlace;
	}
	
	
	@Column( name = "tax_code" )
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	
	
	@Column( name = "ssn_code" )
	public String getSsnCode() {
		return ssnCode;
	}
	public void setSsnCode(String ssnCode) {
		this.ssnCode = ssnCode;
	}
	
	
	@AttributeOverrides({
		@AttributeOverride( name="place",column=@Column(name="residence_place"))
	 })	
	@Embedded
	public Address getResidence() {
		return residence;
	}
	public void setResidence(Address residence) {
		this.residence = residence;
	}
	
	@AttributeOverrides({
		@AttributeOverride( name="place",column=@Column(name="domicile_place"))
	 })	
	@Embedded
	public Address getDomicile() {
		return domicile;
	}
	public void setDomicile(Address domicile) {
		this.domicile = domicile;
	}
	
	
	// Region
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	
	@Column( name = "home_phone" )
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	
	
	@Column( name = "work_phone" )
	public String getWorkPhone() {
		return workPhone;
	}
	public void setWorkPhone(String workPhone) {
		this.workPhone = workPhone;
	}
	
	
	// Nationality
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	
	// Asl
	public String getAsl() {
		return asl;
	}
	public void setAsl(String asl) {
		this.asl = asl;
	}

	@Column( name = "external_element_id" )
	public String getExternalElementId() {
		return externalElementId;
	}
	public void setExternalElementId(String externalElementId) {
		this.externalElementId = externalElementId;
	}

	@Enumerated( EnumType.STRING )
	public ObservableEntityType getType() {
		return type;
	}
	public void setType(ObservableEntityType type) {
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

	private String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
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
			identifier == null &&
			isEmpty( name ) &&
			isEmpty( surname ) &&
			isEmpty( birthPlace ) &&
			isEmpty( taxCode ) &&
			isEmpty( ssnCode ) &&
			isEmpty( region ) &&
			isEmpty( homePhone ) &&
			isEmpty( workPhone ) &&
			isEmpty( nationality ) &&
			isEmpty( asl ) &&
			( birthDate == null ) &&
			( sex == null ) &&
			( residence == null ) &&
			( domicile == null );
	}
	
	@Override
	public boolean sameAs(ObservableEntity entity) {
		return 
			( identifier == null && entity.getIdentifier() == null || identifier != null && identifier.equals( entity.getIdentifier() ) ) &&
			( isEmpty( name ) && isEmpty( entity.getName() ) || name != null && name.equals( entity.getName() )) &&
			( isEmpty( surname ) && isEmpty( entity.getSurname() ) || surname != null && surname.equals( entity.getSurname() )) &&
			( sex == null && entity.getSex() == null || sex != null && sex.equals( entity.getSex() )) &&
			( birthDate == null && entity.getBirthDate() == null || birthDate != null && birthDate.compareTo( entity.getBirthDate() ) == 0 ) &&
			( isEmpty( birthPlace ) && isEmpty( entity.getBirthPlace() ) || birthPlace != null && birthPlace.equals( entity.getBirthPlace() )) &&
			( isEmpty( taxCode ) && isEmpty( entity.getTaxCode() ) || taxCode != null && taxCode.equals( entity.getTaxCode() )) &&
			( isEmpty( ssnCode ) && isEmpty( entity.getSsnCode() ) || ssnCode != null && ssnCode.equals( entity.getSsnCode() )) &&
			( residence == null && entity.getResidence() == null || residence != null && residence.equals( entity.getResidence() )) &&
			( domicile == null && entity.getDomicile() == null || domicile != null && domicile.equals( entity.getDomicile() )) &&
			( isEmpty( region ) && isEmpty( entity.getRegion() ) || region != null && region.equals( entity.getRegion() )) &&
			( isEmpty( homePhone ) && isEmpty( entity.getHomePhone() ) || homePhone != null && homePhone.equals( entity.getHomePhone() )) &&
			( isEmpty( workPhone ) && isEmpty( entity.getWorkPhone() ) || workPhone != null && workPhone.equals( entity.getWorkPhone() )) &&
			( isEmpty( nationality ) && isEmpty( entity.getNationality() ) || nationality != null && nationality.equals( entity.getNationality() )) &&
			( isEmpty( asl ) && isEmpty( entity.getAsl() ) || asl != null && asl.equals( entity.getAsl() ));
	}
	
	
	@Override
	public ObservableEntity copy() {
		ObservableEntity result = ObservableEntityFactory.createObservableEntity();
		result.setIdentifier( getIdentifier() );
		result.setName( getName() );
		result.setSurname( getSurname() );
		result.setSex( getSex() );
		result.setBirthDate( getBirthDate() );
		result.setBirthPlace( getBirthPlace() );
		result.setTaxCode( getTaxCode() );
		result.setSsnCode( getSsnCode() );

		if ( getResidence() != null ) {
			Address residence = new Address();
			residence.setPlace( getResidence().getPlace() );
			result.setResidence( residence );
		}

		if ( getDomicile() != null ) {
			Address domicile = new Address();
			domicile.setPlace( getDomicile().getPlace() );
			result.setDomicile( domicile );
		}

		result.setRegion( getRegion() );
		result.setHomePhone( getHomePhone() );
		result.setWorkPhone( getWorkPhone() );
		result.setNationality( getNationality() );
		result.setAsl( getAsl() );

		return result;
	}
	
	private boolean isEmpty( String s ) {
        return s == null || "".equals(s.trim());
    }
}

