package backend.patients.entity;

import java.util.Date;
import java.util.HashMap;

public class Patient {

	// model列名和数据库列名对照
	public static HashMap<String, String> columnMapper =  new HashMap<String, String>();
	static {
		columnMapper.put("patientId", "patient_Id");
		columnMapper.put("gender", "sex_code");
		columnMapper.put("dateOfBirth", "date_of_birth");
		columnMapper.put("phone1", "phone_number1");
		columnMapper.put("phone2", "phone_number2");
		columnMapper.put("dateOfIssue", "create_time");
		columnMapper.put("isCancelled", "is_cancel");
	}
	
	public Patient(String patientId, String name, String gender, Date dateOfBirth, String phone1, String phone2, Date dateOfIssue, Boolean isCancelled, String track) {
		this.patientId = patientId;
		this.name = name;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
		this.phone1 = phone1;
		this.phone2 = phone2;
		this.dateOfIssue = dateOfIssue;
		this.isCancelled = isCancelled;
		this.track = track;
	}
	
	// 实际上Patient就是患者的就诊卡，一个患者多张就诊卡的情况较多
	private String patientId;
	private String name;
	private String gender;
	private Date dateOfBirth;
	private String phone1;
	private String phone2;
	private Date dateOfIssue;
	private Boolean isCancelled;
	private String track;
	
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public static HashMap<String, String> getColumnMapper() {
		return columnMapper;
	}
	public static void setColumnMapper(HashMap<String, String> columnMapper) {
		Patient.columnMapper = columnMapper;
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
	public String getPhone1() {
		return phone1;
	}
	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}
	public String getPhone2() {
		return phone2;
	}
	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}
	public Date getDateOfIssue() {
		return dateOfIssue;
	}
	public void setDateOfIssue(Date dateOfIssue) {
		this.dateOfIssue = dateOfIssue;
	}
	public Boolean getIsCancelled() {
		return isCancelled;
	}
	public void setIsCancelled(Boolean isCancelled) {
		this.isCancelled = isCancelled;
	}
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	
}
