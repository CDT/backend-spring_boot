package backend.common;

public class Tester {
	
	public static void main(String[] args) {
		String s = "a$bc";
		System.out.println(s.replaceAll("\\$b", "d"));
	}
	
}
