package com.jakespringer.qlearning;

public interface QAction<S extends QState> {
	public S act(S state);
}
