package com.github.mdjc.commons;

public class IllegalArguments {
	public static String checkBlank(String s) {
		if (s == null || s.length() == 0) {
			throw new IllegalArgumentException();
		}
		return s;
	}

	public static <T> T checkNull(T obj) {
		if (obj == null) {
			throw new IllegalArgumentException();
		}
		return obj;
	}
}
