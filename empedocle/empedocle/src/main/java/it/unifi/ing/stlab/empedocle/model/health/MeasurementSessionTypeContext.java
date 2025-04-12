package it.unifi.ing.stlab.empedocle.model.health;

public enum MeasurementSessionTypeContext {
	
	EDIT ("edit", "Delivery"),
	VIEW ("view", "Summary"),
	REPORT ("report", "Report"),
	HIGHLIGHTS ("highlights", "Highlights");
	
	private String id;
	private String name;
	
	MeasurementSessionTypeContext(String id, String name){
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return id;
	}	
	
}
