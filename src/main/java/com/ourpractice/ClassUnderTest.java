package com.ourpractice;

public class ClassUnderTest {

	private Service service;
	
	public void setService(Service service) {
		this.service = service;
	}
	
	public String letsDoSomething(String gimmeSomething) {
		String rev = service.reverseString(gimmeSomething);
		int length = MyStatic.getLength(rev);
		
		return getAToZOfLength(length);
	}

	private String getAToZOfLength(int length) {
		String str = "";
		for (int i=0; i<length; i++) {
			str+="a";
		}
		return str;
	}
}
