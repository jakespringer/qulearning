package com.jakespringer.qlearning.tictactoe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.jakespringer.qlearning.QState;

public class TTTState implements QState {
	public final TTTMark[] marks = new TTTMark[9];
	public final TTTMark next;
	
	public static final TTTMark[] IDENTITY_MARKS = new TTTMark[9];
	static {
		Arrays.fill(IDENTITY_MARKS, TTTMark.NONE);
	}
	
	public TTTState(TTTMark next, TTTMark a, TTTMark b, TTTMark c, TTTMark d, TTTMark e, TTTMark f, TTTMark g, TTTMark h, TTTMark i) {
		this.next = next;
		this.marks[0] = a;
		this.marks[1] = b;
		this.marks[2] = c;
		this.marks[3] = d;
		this.marks[4] = e;
		this.marks[5] = f;
		this.marks[6] = g;
		this.marks[7] = h;
		this.marks[8] = i;
	}
	
	public TTTState(TTTMark next, TTTMark[] array) {
		if (array.length == 9) {
			this.next = next;
			for (int i=0; i<9; ++i) marks[i] = array[i];
		} else {
			throw new RuntimeException("Invalid array length");
		}
	}
	
	public List<Integer> getPossibleActions() {
		List<Integer> actions = new ArrayList<>();
		for (int i=0; i<9; ++i) {
			if (marks[i].equals(TTTMark.NONE)) actions.add(i);
		}
		return Collections.unmodifiableList(actions);
	}
	
	public TTTMark getWinner() {
		if (marks[0] == TTTMark.X && marks[1] == TTTMark.X && marks[2] == TTTMark.X) return TTTMark.X;
		if (marks[3] == TTTMark.X && marks[4] == TTTMark.X && marks[5] == TTTMark.X) return TTTMark.X;
		if (marks[6] == TTTMark.X && marks[7] == TTTMark.X && marks[8] == TTTMark.X) return TTTMark.X;
		if (marks[0] == TTTMark.X && marks[3] == TTTMark.X && marks[6] == TTTMark.X) return TTTMark.X;
		if (marks[1] == TTTMark.X && marks[4] == TTTMark.X && marks[7] == TTTMark.X) return TTTMark.X;
		if (marks[2] == TTTMark.X && marks[5] == TTTMark.X && marks[8] == TTTMark.X) return TTTMark.X;
		if (marks[0] == TTTMark.X && marks[4] == TTTMark.X && marks[8] == TTTMark.X) return TTTMark.X;
		if (marks[2] == TTTMark.X && marks[4] == TTTMark.X && marks[6] == TTTMark.X) return TTTMark.X;

		if (marks[0] == TTTMark.O && marks[1] == TTTMark.O && marks[2] == TTTMark.O) return TTTMark.O;
		if (marks[3] == TTTMark.O && marks[4] == TTTMark.O && marks[5] == TTTMark.O) return TTTMark.O;
		if (marks[6] == TTTMark.O && marks[7] == TTTMark.O && marks[8] == TTTMark.O) return TTTMark.O;
		if (marks[0] == TTTMark.O && marks[3] == TTTMark.O && marks[6] == TTTMark.O) return TTTMark.O;
		if (marks[1] == TTTMark.O && marks[4] == TTTMark.O && marks[7] == TTTMark.O) return TTTMark.O;
		if (marks[2] == TTTMark.O && marks[5] == TTTMark.O && marks[8] == TTTMark.O) return TTTMark.O;
		if (marks[0] == TTTMark.O && marks[4] == TTTMark.O && marks[8] == TTTMark.O) return TTTMark.O;
		if (marks[2] == TTTMark.O && marks[4] == TTTMark.O && marks[6] == TTTMark.O) return TTTMark.O;
		
		return TTTMark.NONE;
	}
	
	public boolean isTie() {
		for (int i=0; i<9; ++i) {
			if (marks[i] == TTTMark.NONE) return false;
		}
		return true;
	}
}
