package backend.patients.entity;

public class InpatientVisit {
	
	private String patientId;
	private Long numberOfVisit;
	private Long visitId;
	private String bedNumber;
	private Long medicalTeamId;
	private String currentPaymentMethod;
	private String currentDept;
	private String currentWard;
	private String currentStatus;
	
	public InpatientVisit(String patientId, Long numberOfVisit, Long visitId, 
			String bedNumber, Long medicalTeamId, 
			String currentPaymentMethod, String currentDept, String currentWard, String currentStatus) {
		this.patientId = patientId;
		this.numberOfVisit = numberOfVisit;
		this.visitId = visitId;
		this.bedNumber = bedNumber;
		this.medicalTeamId = medicalTeamId;
		this.currentPaymentMethod = currentPaymentMethod;
		this.currentDept = currentDept;
		this.currentWard = currentWard;
		this.currentStatus = currentStatus;
	}	

	public String getCurrentStatus() {
		return currentStatus;
	}

	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	public String getBedNumber() {
		return bedNumber;
	}

	public void setBedNumber(String bedNumber) {
		this.bedNumber = bedNumber;
	}

	public Long getMedicalTeamId() {
		return medicalTeamId;
	}

	public void setMedicalTeamId(Long medicalTeamId) {
		this.medicalTeamId = medicalTeamId;
	}

	public Long getNumberOfVisit() {
		return numberOfVisit;
	}
	public void setNumberOfVisit(Long numberOfVisit) {
		this.numberOfVisit = numberOfVisit;
	}
	public Long getVisitId() {
		return visitId;
	}
	public void setVisitId(Long visitId) {
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
