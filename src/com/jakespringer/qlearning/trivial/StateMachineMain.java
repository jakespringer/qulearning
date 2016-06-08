package com.jakespringer.qlearning.trivial;

import java.util.Scanner;

import com.jakespringer.qlearning.QLearner;
import com.jakespringer.qlearning.SimpleLearner;

public class StateMachineMain {
	public static void main(String[] a) {
		Scanner scan = new Scanner(System.in);
		
		State.setup();
		Table table = new Table();
		State current = State.start;
		QLearner<State, Transition> learner = new SimpleLearner<>(table);
		
		while (true) {
			for (int i=0; i<500; ++i) {
				if (current == State.end || current == null) current = State.start;
				System.out.println(table.getTableString() + "Currently in state \'"+current.id+"\', received reward "+table.getReward(current)+"\n\n");
				current = learner.train(current);
			}
			scan.nextLine();
		}
	}
}
