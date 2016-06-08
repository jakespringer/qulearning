package com.jakespringer.qlearning;

public interface QFunction<S extends QState, A extends QAction<S>> {
	public double get(S state, A action);
	public double getReward(S state);
	public double set(S state, A action, double value);
	public A probableAction(S state);
}
