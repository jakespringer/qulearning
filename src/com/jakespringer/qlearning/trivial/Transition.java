package com.jakespringer.qlearning.trivial;

import com.jakespringer.qlearning.QAction;

public class Transition implements QAction<State> {

	public final char transition;
	
	public Transition(char transition) {
		this.transition = transition;
	}
	
	@Override
	public State act(State state) {
		if (state.possibleTransitions.stream().anyMatch(x -> x.id == transition)) {
			return state.possibleTransitions.stream().filter(x -> x.id == transition).findAny().get();
		} else {
			return null;
		}
	}
}
