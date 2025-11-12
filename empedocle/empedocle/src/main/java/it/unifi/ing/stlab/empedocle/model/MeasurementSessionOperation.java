package it.unifi.ing.stlab.empedocle.model;

public enum MeasurementSessionOperation {

	END_EXAMINATION("end_measurementSession", "end measurementSession");

	private String id;
	private String name;
	
	MeasurementSessionOperation(String id, String name){
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
	
}
