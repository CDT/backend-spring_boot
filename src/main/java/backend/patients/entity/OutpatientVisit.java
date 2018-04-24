package backend.patients.entity;

import java.util.Date;

public class OutpatientVisit {
	private String id;
	
	// 挂号单上的挂号票据号，格式例如“02-LEIP-10015482”，02代表[TODO]，LEIP为操作员工号，10015482为[TODO]
	private String regNumber;  
	
	// 挂号日期
	private Date regDate;
	
	// 挂号专科
	private String regDepartmentName;
	
	// 诊区地址
	private String departmentLocation;
	
	// 挂号操作员
	private String operator;
	
	// 挂号费别(TJ0005费别)
	private String paymentType;
	
	// 院区编码
	private String branchCode;
	
	// 医生接诊时间
	private Date interviewTime;
	
	// 接诊医生工号
	private String doctorOfInterview;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRegNumber() {
		return regNumber;
	}

	public void setRegNumber(String regNumber) {
		this.regNumber = regNumber;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getRegDepartmentName() {
		return regDepartmentName;
	}

	public void setRegDepartmentName(String regDepartmentName) {
		this.regDepartmentName = regDepartmentName;
	}

	public String getDepartmentLocation() {
		return departmentLocation;
	}

	public void setDepartmentLocation(String departmentLocation) {
		this.departmentLocation = departmentLocation;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public Date getInterviewTime() {
		return interviewTime;
	}

	public void setInterviewTime(Date interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getDoctorOfInterview() {
		return doctorOfInterview;
	}

	public void setDoctorOfInterview(String doctorOfInterview) {
		this.doctorOfInterview = doctorOfInterview;
	}
}
