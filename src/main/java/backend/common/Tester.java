package backend.common;

public class Tester {
	
	public Tester() {
		System.out.println("Tester");
	}
	
	public static void main(String[] args) {
	    Dog aDog = new Dog("Max");
	    // we pass the object to foo
	    foo(aDog);
	    // aDog variable is still pointing to the "Max" dog when foo(...) returns
	    aDog.getName().equals("Max"); // true, java passes by value
	    aDog.getName().equals("Fifi"); // false 
	}

	public static void foo(Dog d) {
	    d.setName("rub");
	}
}


class Dog {
	private String name;
	
	public Dog(String name) { this.name = name; }
	
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
}
