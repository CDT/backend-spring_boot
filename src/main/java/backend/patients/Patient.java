package backend.patients;

import java.util.Date;

public class Patient {
	private String ID;
	private String name;
	private Date dateOfBirth;
	private String gender;
	private MedicalCard medicalCards;
	private OutpatientVisit outpatientVisits;
	private InpatientVisit inpatientVisit;
}
