package backend.patients.entity;

import java.util.Date;
import java.util.HashMap;

public class PatientCard {

	// model列名和数据库列名对照
	public static HashMap<String, String> columnMapper =  new HashMap<String, String>();
	static {
		columnMapper.put("patientId", "patient_Id");
		columnMapper.put("gender", "sex_code");
		columnMapper.put("idNumber", "id_number");
		columnMapper.put("dateOfBirth", "date_of_birth");
		columnMapper.put("phone1", "phone_number");
		columnMapper.put("phone2", "phone_number");
		columnMapper.put("dateOfIssue", "create_time");
		columnMapper.put("isCancelled", "is_cancel");
		columnMapper.put("currentStatus", "current_status");
		columnMapper.put("currentWard", "current_ward");
	}
	
	public PatientCard(String patientId, String name, String gender, String currentStatus, String currentWard, String idNumber, Date dateOfBirth, String phone, Date dateOfIssue, Address address) {
		this.patientId = patientId;
		this.name = name;
		this.gender = gender;
		this.currentStatus = currentStatus;
		this.currentWard = currentWard;
		this.idNumber = idNumber;
		this.dateOfBirth = dateOfBirth;
		this.phone = phone;
		this.dateOfIssue = dateOfIssue;
		this.address = address;
	}
	
	private String patientId;
	private String name;
	private String gender;
	private String currentStatus;
	private String currentWard;
	private String idNumber;
	private Date dateOfBirth;
	private String phone;
	private Date dateOfIssue;
	private Address address;

	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public static HashMap<String, String> getColumnMapper() {
		return columnMapper;
	}
	public static void setColumnMapper(HashMap<String, String> columnMapper) {
		PatientCard.columnMapper = columnMapper;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getDateOfIssue() {
		return dateOfIssue;
	}
	public String getCurrentStatus() {
		return currentStatus;
	}
	public void setCurrentStatus(String currentStatus) {
		this.currentStatus = currentStatus;
	}
	public String getCurrentWard() {
		return currentWard;
	}
	public void setCurrentWard(String currentWard) {
		this.currentWard = currentWard;
	}
	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
}
