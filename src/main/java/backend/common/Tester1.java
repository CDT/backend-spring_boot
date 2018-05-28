package backend.common;

public class Tester1 extends Tester {
	public Tester1() {
		System.out.println("No argument Tester1");
	}
	
	public Tester1(int i) {
		System.out.println("Int arg Tester1");
	}
	
	public static void main(String[] args) {
		Tester1 t1 = new Tester1(1);
	}
}