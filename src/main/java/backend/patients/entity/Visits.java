package backend.patients.entity;

import java.util.List;

public class Visits {
	
	private String patientId;
	private String type;
	private int numberOfVisit;
	private List<? extends Object> visits;
	
	public Visits(String patientId, String type, int numberOfVisit, List<? extends Object> visits) {
		this.patientId = patientId;
		this.type = type;
		this.numberOfVisit = numberOfVisit;
		this.visits = visits;
	}
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<? extends Object> getVisits() {
		return visits;
	}
	public int getNumberOfVisit() {
		return numberOfVisit;
	}

	public void setNumberOfVisit(int numberOfVisit) {
		this.numberOfVisit = numberOfVisit;
	}

	public void setVisits(List<? extends Object> visits) {
		this.visits = visits;
	}	

}
