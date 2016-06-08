package com.jakespringer.qlearning.util;

public class Con<T> {
	public T contained;
	
	public Con(T item) {
		contained = item;
	}
	
	public Con() {
		contained = null;
	}
}
