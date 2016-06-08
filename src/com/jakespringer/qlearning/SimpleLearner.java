package com.jakespringer.qlearning;

public class SimpleLearner<S extends QState, A extends QAction<S>> implements QLearner<S, A>{

	private QFunction<S, A> function;
	private A lastAction;
	private S lastState;
	
	protected final double learningRate = 1.0;
	protected final double discountFactor = 1.0;
	
	public SimpleLearner(QFunction<S, A> function) {
		this.function = function;
	}
	
	public S train(S current) {
		A action = function.probableAction(current);
		if (action == null) return null;
		
		S next = action.act(current);
		double reward = function.getReward(next);
		A actionPrime = function.probableAction(next);
		
		double rewardPrime = actionPrime != null ? function.get(next, actionPrime) : 0.0;
		
		double currentValue = function.get(current, action);
		function.set(current, action, currentValue + learningRate * (reward + discountFactor * rewardPrime - currentValue));
		
		lastAction = action;
		lastState = current;
		return next;
	}
}
