package com.jakespringer.qlearning.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.jakespringer.qlearning.QLearner;
import com.jakespringer.qlearning.SimpleLearner;

public class TTTMain {
	public static void main(String[] args) throws InterruptedException {
		TTTState currentState = new TTTState(TTTMark.X, TTTState.IDENTITY_MARKS);
		TTTTable table = new TTTTable();
		QLearner<TTTState, TTTAction> learner = new SimpleLearner<>(table);
		int numRoundsPassed = 0;
		long totalWins = 0;
		long totalXWins = 0;
		while (true) {
			Thread.sleep(1);
			if (currentState.next == TTTMark.X) {
				currentState = learner.train(currentState);
				++numRoundsPassed;
				if (currentState.isTie() || currentState.getWinner() != TTTMark.NONE) {
					currentState = new TTTState(currentState.next, TTTState.IDENTITY_MARKS);
					System.out.println(((double)totalXWins / (double) totalWins) + " Win[X] -> " + numRoundsPassed);
					numRoundsPassed = 0;
					totalXWins++;
					totalWins++;
				}
			} else if (currentState.next == TTTMark.O) {
				TTTMark[] markPrime = Arrays.copyOf(currentState.marks, currentState.marks.length);
				
				List<Integer> possibleValues = new ArrayList<>(9);
				for (int i=0; i<9; ++i) {
					if (markPrime[i].compareTo(TTTMark.NONE) == 0) possibleValues.add(i);
				}
				
				markPrime[possibleValues.get((int)(Math.random()*possibleValues.size()))] = TTTMark.O;
				currentState = new TTTState(TTTMark.X, markPrime);
				++numRoundsPassed;
				if (currentState.isTie() || currentState.getWinner() != TTTMark.NONE) {
					currentState = new TTTState(currentState.next, TTTState.IDENTITY_MARKS);
					System.out.println(((double)totalXWins / (double) totalWins) + " Win[O] -> " + numRoundsPassed);
					numRoundsPassed = 0;
					totalWins++;
				}
			} else {
				currentState = new TTTState(currentState.next, TTTState.IDENTITY_MARKS);
				System.out.println(numRoundsPassed);
				numRoundsPassed = 0;
			}
		}
	}
}
