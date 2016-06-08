package com.jakespringer.qlearning.neuralmazegame;

import com.jakespringer.qlearning.QAction;
import com.jakespringer.qlearning.QFunction;
import com.jakespringer.qlearning.QLearner;
import com.jakespringer.qlearning.QState;

public class NeuralLearner<S extends QState, A extends QAction<S>> implements QLearner<S, A> {
	private QFunction<S, A> function;
	
	protected final double learningRate = 1.0;
	protected final double discountFactor = 1.0;
	
	public NeuralLearner(QFunction<S, A> function) {
		this.function = function;
	}
	
	public S train(S current) {
		A action = function.probableAction(current);
		if (action == null) return null;
		
		S next = action.act(current);
		double reward = function.getReward(next);
		A actionPrime = function.probableAction(next);
		
		double rewardPrime = actionPrime != null ? function.get(next, actionPrime) : 0.0;
		
		function.set(current, action, reward + rewardPrime);
		return next;
	}
}
