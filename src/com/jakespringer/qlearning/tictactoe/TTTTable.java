package com.jakespringer.qlearning.tictactoe;

import java.util.List;
import java.util.stream.Collectors;

import com.jakespringer.qlearning.QFunction;

public class TTTTable implements QFunction<TTTState, TTTAction> {

	private final double[] table = new double[3*3*3 * 3*3*3 * 3*3*3];
	private double t = 1.0;
	
	@Override
	public double get(TTTState state, TTTAction action) {
		if (action.nextAction == -1) return 0.0;
		
		TTTState next = action.act(state);		
		int current = 0;
		int three = 1;
		for (int i=0; i<9; ++i) {
			switch (next.marks[i]) {
			case NONE:
				current += 0.0*three;
				break;
			case X:
				current += 1.0*three;
				break;
			case O:
				current += 2.0*three;
				break;
			default:
				break;
			}
			three *= 3;
		}
		
		return table[current];
	}

	@Override
	public double set(TTTState state, TTTAction action, double value) {
		TTTState next = action.act(state);
		int current = 0;
		int three = 1;
		for (int i=0; i<9; ++i) {
			switch (next.marks[i]) {
			case NONE:
				current += 0.0*three;
				break;
			case X:
				current += 1.0*three;
				break;
			case O:
				current += 2.0*three;
				break;
			default:
				break;
			}
			three *= 3;
		}
		
		return (table[current] = value);
	}

	@Override
	public TTTAction probableAction(TTTState state) {
		t *= 0.9999999999999;
		
		List<Integer> possibleActions = state.getPossibleActions();
		if (possibleActions.size() == 0) return new TTTAction(-1);
		List<Double> possibleActionsValues = possibleActions.stream().map(x -> Math.exp(get(state, new TTTAction(x))) / t).collect(Collectors.toList());
		double totalValues = possibleActionsValues.stream().reduce(0.0, (x, y) -> x + y);
		double random = Math.random();
		double accumulatedValues = 0;
		for (int i=0; i<possibleActions.size(); ++i) {
			accumulatedValues += possibleActionsValues.get(i) / totalValues;
			if (random < accumulatedValues) return new TTTAction(possibleActions.get(i));
		}
		return new TTTAction(possibleActions.get((int)(Math.random()*possibleActions.size())));
	}

	@Override
	public double getReward(TTTState state) {
		if (state.isTie() || state.getWinner() != TTTMark.NONE) return 1.0;
		else return 0;
	}
}
