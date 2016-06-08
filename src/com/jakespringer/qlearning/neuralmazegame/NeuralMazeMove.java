package com.jakespringer.qlearning.neuralmazegame;

import com.jakespringer.qlearning.QAction;

public class NeuralMazeMove implements QAction<NeuralMazeBoard> {

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
		
		public int toInteger() {
			switch (this) {
			case IDENTITY: return 0;
			case RIGHT: return 1;
			case LEFT: return 2;
			case UP: return 3;
			case DOWN: return 4;
			default: throw new RuntimeException("Invalid enum value");
			}
		}
	}
	
	public final Move move;
	
	public NeuralMazeMove(Move move) {
		this.move = move;
	}
	
	@Override
	public NeuralMazeBoard act(NeuralMazeBoard state) {
		switch (move) {
		case IDENTITY:
			return state;
		case RIGHT: 
			return new NeuralMazeBoard(state.width, state.height, state.targetX, state.targetY, state.currentX+1, state.currentY, state.walls);
		case LEFT:
			return new NeuralMazeBoard(state.width, state.height, state.targetX, state.targetY, state.currentX-1, state.currentY, state.walls);
		case UP:
			return new NeuralMazeBoard(state.width, state.height, state.targetX, state.targetY, state.currentX, state.currentY+1, state.walls);
		case DOWN:
			return new NeuralMazeBoard(state.width, state.height, state.targetX, state.targetY, state.currentX, state.currentY-1, state.walls);
		}
		return null;
	}
}
