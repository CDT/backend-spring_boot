package backend.patients.entity;

public class InpatientVisit {
	
	private String visitId;
	private String currentPaymentMethod;
	
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
