package com.jakespringer.qlearning;

public interface QLearner<S extends QState, A extends QAction<S>> {
	public S train(S current);
}
