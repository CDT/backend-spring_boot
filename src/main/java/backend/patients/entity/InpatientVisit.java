package backend.patients.entity;

public class InpatientVisit {
	
	private String patientId;
	private String numberOfVisit;
	private String visitId;
	private String currentPaymentMethod;
	private String currentDept;
	private String currentWard;
	
	public InpatientVisit(String patientId, String numberOfVisit, 
			String visitId, String currentPaymentMethod, 
			String currentDept, String currentWard) {
		this.patientId = patientId;
		this.numberOfVisit = numberOfVisit;
		this.visitId = visitId;
		this.currentDept = currentDept;
		this.currentWard = currentWard;
	}
	
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getNumberOfVisit() {
		return numberOfVisit;
	}
	public void setNumberOfVisit(String numberOfVisit) {
		this.numberOfVisit = numberOfVisit;
	}
	public String getVisitId() {
		return visitId;
	}
	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}
	public String getCurrentPaymentMethod() {
		return currentPaymentMethod;
	}
	public void setCurrentPaymentMethod(String currentPaymentMethod) {
		this.currentPaymentMethod = currentPaymentMethod;
	}
	public String getCurrentDept() {
		return currentDept;
	}
	public void setCurrentDept(String currentDept) {
		this.currentDept = currentDept;
	}
	public String getCurrentWard() {
		return currentWard;
	}
	public void setCurrentWard(String currentWard) {
		this.currentWard = currentWard;
	}	
	
}
