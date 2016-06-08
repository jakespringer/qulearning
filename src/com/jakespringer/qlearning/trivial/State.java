package com.jakespringer.qlearning.trivial;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jakespringer.qlearning.QState;

public class State implements QState {
	public final char id;
	
	public final List<State> possibleTransitions = new ArrayList<>();
	public static State start;
	public static State end;
	public static int numStates = 11;
	
	private static State a = new State('a'),
			  b = new State('b'), 
			  c = new State('c'), 
			  d = new State('d'), 
			  e = new State('e'), 
			  f = new State('f'), 
			  g = new State('g'), 
			  h = new State('h'), 
			  i = new State('i'), 
			  j = new State('j'),
			  k = new State('k');
	
	private State(char id) {
		this.id = id;
	}
	
	public static State getStateById(int id) {
		switch (id) {
		case 0: return a;
		case 1: return b;
		case 2: return c;
		case 3: return d;
		case 4: return e;
		case 5: return f;
		case 6: return g;
		case 7: return h;
		case 8: return i;
		case 9: return j;
		case 10: return k;
		default: return null;
		}
	}
	
	public static void setup() {
		start = a;
		end = k;
		
		a.possibleTransitions.addAll(Arrays.asList(b, c, d, e, f, g, h, i, j));
		b.possibleTransitions.addAll(Arrays.asList(k));
		c.possibleTransitions.addAll(Arrays.asList(d));
		d.possibleTransitions.addAll(Arrays.asList(e));
		e.possibleTransitions.addAll(Arrays.asList(f));
		f.possibleTransitions.addAll(Arrays.asList(g, k));
		g.possibleTransitions.addAll(Arrays.asList(h, d, e, k));
		h.possibleTransitions.addAll(Arrays.asList(i, h));
		i.possibleTransitions.addAll(Arrays.asList(j));
		j.possibleTransitions.addAll(Arrays.asList(k));		
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof State) {
			return ((State) other).id == id;
		}
		return false;
	}
}
