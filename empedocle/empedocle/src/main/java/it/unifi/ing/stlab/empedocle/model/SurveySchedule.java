package it.unifi.ing.stlab.empedocle.model;

import it.unifi.ing.stlab.empedocle.model.Agenda;
import it.unifi.ing.stlab.entities.implementation.persistable.PersistableImpl;
import it.unifi.ing.stlab.entities.model.persistable.Persistable;
import it.unifi.ing.stlab.observableentities.model.ObservableEntity;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;


@Entity
@Table(name = "survey_schedules", uniqueConstraints = @UniqueConstraint(columnNames = {
		"booking_code", "acceptance_code" }))
public class SurveySchedule implements Persistable {

	private PersistableImpl persistable;
	
	private Date date; 
	private String number;
	private String bookingCode;
	private String acceptanceCode;
	private Set<Service> services;
	private Agenda agenda;
	private ObservableEntity observableEntity;
	private SurveyScheduleStatus status;


	public SurveySchedule( String uuid ) {
		persistable = new PersistableImpl( uuid );
		services = new HashSet<Service>();
	}
	protected SurveySchedule() {
		persistable = new PersistableImpl();
		services = new HashSet<Service>();
	}
	
	@Id
	@TableGenerator( 
		name="table_gen", 
		table="sequence_table", 
		pkColumnName="seq_name",
		valueColumnName="seq_count", 
		pkColumnValue="survey_schedule", 
		allocationSize = 50 )
	@GeneratedValue(strategy=GenerationType.TABLE, generator="table_gen" )	
	public Long getId() {
		return persistable.getId();
	}
	protected void setId(Long id) {
		persistable.setId(id);
	}

	
	public String getUuid() {
		return persistable.getUuid();
	}
	protected void setUuid(String uuid) {
		persistable.setUuid(uuid);
	}

	
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	
	@Column( name = "booking_code", unique = true )
	public String getBookingCode() {
		return bookingCode;
	}
	public void setBookingCode(String bookingCode) {
		this.bookingCode = bookingCode;
	}
	
	
	@Column( name = "acceptance_code" )
	public String getAcceptanceCode() {
		return acceptanceCode;
	}
	public void setAcceptanceCode(String acceptanceCode) {
		this.acceptanceCode = acceptanceCode;
	}
	
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.PERSIST } )
	@JoinTable(
		name = "survey_schedule_services",
	    joinColumns = { @JoinColumn( name = "survey_schedule_id", referencedColumnName="id" ) },
	    inverseJoinColumns = { @JoinColumn( name = "service_id", referencedColumnName = "id") } )
	protected Set<Service> getServices() {
		return services;
	}
	protected void setServices(Set<Service> services) {
		this.services = services;
	}
	public Set<Service> listServices() {
		return Collections.unmodifiableSet( services );
	}
	public void addService( Service service ) {
		if ( service == null ) return;
		
		services.add( service );
	}
	public void removeService( Service service ) {
		services.remove( service );
	}
	public void clearServices() {
		services.clear();
	}
	
	@ManyToOne
	@JoinColumn( name = "agenda_id" )	
	public Agenda getAgenda() {
		return agenda;
	}
	public void setAgenda(Agenda agenda) {
		this.agenda = agenda;
	}	
	
	
	@ManyToOne( fetch = FetchType.EAGER )
	@JoinColumn( name = "observable_entity_id" )
	public ObservableEntity getObservableEntity() {
		return observableEntity;
	}
	public void setObservableEntity(ObservableEntity observableEntity) {
		this.observableEntity = observableEntity;
	}

	
	@Enumerated( EnumType.STRING )
	public SurveyScheduleStatus getStatus() {
		return status;
	}
	public void setStatus(SurveyScheduleStatus status) {
		this.status = status;
	}
	
	
	//
	// HashCode & Equals
	//
	public int hashCode() {
		return persistable.hashCode();
	}
	public boolean equals(Object obj) {
		return persistable.equals(obj);
	}
}
