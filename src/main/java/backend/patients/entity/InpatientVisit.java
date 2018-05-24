package backend.patients.entity;

public class InpatientVisit {
	
	private String visitId;
	private String currentPaymentMethod;
	private String bloodType;
	
	public String getBloodType() {
		return bloodType;
	}
	public void setBloodType(String bloodType) {
		this.bloodType = bloodType;
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
}
