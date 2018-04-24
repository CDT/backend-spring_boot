package backend.patients.entity;

import java.util.Date;
import java.util.List;

public class Patient {
	// 身份证号码，不是患者ID(一个患者可能办多张卡，从而对应多个患者ID)
	// 唯一标志一名患者
	// 部分情况下患者身份证为空
	private String ID;
	
	// 由于办卡时可能信息登记错误，下列信息可能存在不一致的情况，因此均用List表示
	// 正常情况下，下列List只有一个成员，除非信息登记错误
	private List<String> name;
	private List<Date> dateOfBirth;
	private List<String> gender;
	private List<String> mobile;
	
	// 下面的字段属于关联信息，可以为NULL，需要的时候才查出来
	private MedicalCard medicalCards;
	private List<OutpatientVisit> outpatientVisits;
	private List<InpatientVisit> inpatientVisit;
	
	public Patient(String ID, List<String> name, List<Date> dateOfBirth, List<String> gender, List<String> mobile) {
		this.ID = ID;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.mobile = mobile;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public List<String> getName() {
		return name;
	}

	public void setName(List<String> name) {
		this.name = name;
	}

	public List<Date> getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(List<Date> dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<String> getGender() {
		return gender;
	}

	public void setGender(List<String> gender) {
		this.gender = gender;
	}

	public List<String> getMobile() {
		return mobile;
	}

	public void setMobile(List<String> mobile) {
		this.mobile = mobile;
	}

	public MedicalCard getMedicalCards() {
		return medicalCards;
	}

	public void setMedicalCards(MedicalCard medicalCards) {
		this.medicalCards = medicalCards;
	}

	public List<OutpatientVisit> getOutpatientVisits() {
		return outpatientVisits;
	}

	public void setOutpatientVisits(List<OutpatientVisit> outpatientVisits) {
		this.outpatientVisits = outpatientVisits;
	}

	public List<InpatientVisit> getInpatientVisit() {
		return inpatientVisit;
	}

	public void setInpatientVisit(List<InpatientVisit> inpatientVisit) {
		this.inpatientVisit = inpatientVisit;
	}
	
}
