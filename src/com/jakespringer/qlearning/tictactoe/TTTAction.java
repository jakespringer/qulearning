package com.jakespringer.qlearning.tictactoe;

import java.util.Arrays;

import com.jakespringer.qlearning.QAction;

public class TTTAction implements QAction<TTTState> {

	public final int nextAction;
	
	public TTTAction(int nextAction) {
		this.nextAction = nextAction;
	}
	
	@Override
	public TTTState act(TTTState state) {
		TTTMark[] marks = Arrays.copyOf(state.marks, state.marks.length);
		marks[nextAction] = state.next;
		return new TTTState(state.next == TTTMark.X ? TTTMark.O : TTTMark.X, marks[0], marks[1], marks[2], 
				marks[3], marks[4], marks[5], 
				marks[6], marks[7], marks[8]);
	}
}
