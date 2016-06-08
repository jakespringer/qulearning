package com.jakespringer.qlearning.simplegame;

import com.jakespringer.qlearning.QAction;

public class GameMove implements QAction<GameBoard> {

	public static enum Move {
		IDENTITY, RIGHT, LEFT, UP, DOWN;
		
		public static Move fromInteger(int index) {
			switch (index) {
			case 0: return IDENTITY;
			case 1: return RIGHT;
			case 2: return LEFT;
			case 3: return UP;
			case 4: return DOWN;
			default: throw new RuntimeException("Invalid index");
			}
		}
	}
	
	public final Move move;
	
	public GameMove(Move move) {
		this.move = move;
	}
	
	@Override
	public GameBoard act(GameBoard state) {
		switch (move) {
		case IDENTITY:
			return state;
		case RIGHT: 
			return new GameBoard(state.width, state.height, state.targetX, state.targetY, state.currentX+1, state.currentY);
		case LEFT:
			return new GameBoard(state.width, state.height, state.targetX, state.targetY, state.currentX-1, state.currentY);
		case UP:
			return new GameBoard(state.width, state.height, state.targetX, state.targetY, state.currentX, state.currentY+1);
		case DOWN:
			return new GameBoard(state.width, state.height, state.targetX, state.targetY, state.currentX, state.currentY-1);
		}
		return null;
	}
}
