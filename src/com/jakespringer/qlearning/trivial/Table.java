package com.jakespringer.qlearning.trivial;

import java.text.DecimalFormat;
import java.util.function.Function;

import com.jakespringer.qlearning.QFunction;
import com.jakespringer.qlearning.util.Con;

public class Table implements QFunction<State, Transition> {

	public double table[] = new double[State.numStates * State.numStates];
	public static double kT = 0.4;
	
	@Override
	public double get(State state, Transition action) {
		if (action.transition == State.end.id) return 1.0;
		else return table[(action.transition-'a')+(State.numStates*(state.id-'a'))];
	}

	@Override
	public double set(State state, Transition action, double value) {
		return (table[(action.transition-'a')+(State.numStates*(state.id-'a'))] = value);
	}
	
	@Override
	public double getReward(State state) {
		if (state.id == 'k') return 1.0;
		else return 0.0;
	}

	@Override
	public Transition probableAction(State state) {
		double total = state.possibleTransitions.stream().mapToDouble(x -> Math.exp(table[(x.id-'a')+(State.numStates*(state.id-'a'))]/kT)).sum();
		final Con<Double> current = new Con<>(0.0);
		final double rand = Math.random();
		if (state.possibleTransitions.isEmpty()) return null;
		return new Transition(state.possibleTransitions.stream().filter(x -> rand < ((current.contained += Math.exp(table[(x.id-'a')+(State.numStates*(state.id-'a'))]/kT)) / total)).findFirst().get().id);
	}
	
	public String getTableString() {
		StringBuilder b = new StringBuilder();
		DecimalFormat df = new DecimalFormat("0.00"); 
		
		b.append("        a           b           c           d           e           f           g           h           i           j           k\n");
		
		Function<Double, Double> infToZero = x -> {
			if (x.isInfinite()) return 0.0;
			else return x;
		};
		
		for (int i=0; i<State.numStates; ++i) {
			b.append((char)('a' + i));
			final int state = i;
			for (int j=0; j<State.numStates; ++j) {
				double total = State.getStateById(i).possibleTransitions.stream().mapToDouble(x -> Math.exp(table[(x.id-'a')+(State.numStates*(State.getStateById(state).id-'a'))]/kT)).sum();
				if (table[j+State.numStates*i] != 0.0) b.append("..." + df.format(table[j+State.numStates*i]) + ":" 
						+ (State.getStateById(i).possibleTransitions.contains(State.getStateById(j)) ? df.format(infToZero.apply(Math.exp(table[(State.getStateById(j).id-'a')+(State.numStates*(State.getStateById(i).id-'a'))]/kT)/total)) : df.format(0.0)));
				else b.append("............");
			}
			b.append("\n");
		}
		return b.toString();
	}
}

// exp(Q(s, a) / kT) \over sum exp(Q(s, a) / kT)