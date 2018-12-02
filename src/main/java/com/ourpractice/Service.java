package com.ourpractice;

public class Service {

	public String reverseString(String str) {
		StringBuilder sb = new StringBuilder(str).reverse();
		return sb.toString();
	}
}
