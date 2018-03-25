package backend.employees;

public class Employee {
	
	public Employee() {};
	
	public Employee(String ID, String name) {
		this.ID = ID;
		this.name = name;
	};
	
	private String ID;
	
	private String name;
	
	
	public String getID() {
		return ID;
	}
	public void setID(String ID) {
		this.ID = ID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
        return String.format(
                "%s: %s",
                ID, name);
    }
	
}
