package com.jakespringer.qlearning.util;

public class Util {
	@SafeVarargs
	public static <T> boolean equals(T... objects) {
		if (objects.length <= 1)
			return true;
		else {
			T previous = objects[0];
			for (int i = 1; i < objects.length; ++i) {
				if (previous.equals(objects[i])) {
					previous = objects[i];
				} else {
					return false;
				}
			}
		}

		return true;
	}

	public static String toBinary(int n) {
		if (n == 0) {
			return "0";
		}
		String binary = "";
		while (n > 0) {
			int rem = n % 2;
			binary = rem + binary;
			n = n / 2;
		}
		return binary;
	}
	
	public static double bound(double x, double lower, double upper) {
		if (x < lower) return lower;
		if (x > upper) return upper;
		return x;
	}
}
