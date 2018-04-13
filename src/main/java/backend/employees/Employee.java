package backend.employees;

import java.util.HashMap;

public class Employee {
	
	// model列名和数据库列名对照
	public static HashMap<String, String> columnMapper = new HashMap<String, String>();
	static {
		columnMapper.put("job_NO", "people_identifier");
		columnMapper.put("name", "people_name");
	}	
	
	public Employee() {};
	
	public Employee(String JOB_NO, String name, String mobile, String ID_NO) {
		this.JOB_NO = JOB_NO;
		this.name = name;
		this.mobile = mobile;
		this.ID_NO = ID_NO;
	};
	
	private String JOB_NO;
	
	private String name;
	
	private String mobile;
	
	private String ID_NO;
	
	

	public String getJOB_NO() {
		return JOB_NO;
	}
	public void setID(String JOB_NO) {
		this.JOB_NO = JOB_NO;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getID_NO() {
		return ID_NO;
	}
	public void setID_NO(String iD_NO) {
		ID_NO = iD_NO;
	}
	
	@Override
	public String toString() {
        return String.format(
                "%s: %s",
                JOB_NO, name);
    }
	
}
