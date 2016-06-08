package com.jakespringer.qlearning.simplegame;

import com.jakespringer.qlearning.QFunction;

public class GameTable implements QFunction<GameBoard, GameMove> {

	public final int width;
	public final int height;
	
	public static final double kT = 0.1;
	
	public final double[] table;
	
	public GameTable(int width, int height) {
		this.width = width;
		this.height = height;
		table = new double[(width*height)*(width*height)*5];
	}
	
	@Override
	public double get(GameBoard state, GameMove action) {
		switch (action.move) {
		case IDENTITY:
			return table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+0*width*height*width*height];
		case RIGHT:
			return table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+1*width*height*width*height];
		case LEFT:
			return table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+2*width*height*width*height];
		case UP:
			return table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+3*width*height*width*height];
		case DOWN:
			return table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+4*width*height*width*height];
		default:
			return 0;
		}
	}

	@Override
	public double getReward(GameBoard state) {
		return state.hasWon() ? 1.0 : 0.0;
	}

	@Override
	public double set(GameBoard state, GameMove action, double value) {
		switch (action.move) {
		case IDENTITY:
			return (table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+0*width*height*width*height] = value);
		case RIGHT:
			return (table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+1*width*height*width*height] = value);
		case LEFT:
			return (table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+2*width*height*width*height] = value);
		case UP:
			return (table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+3*width*height*width*height] = value);
		case DOWN:
			return (table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+4*width*height*width*height] = value);
		default:
			return 0;
		}
	}

	@Override
	public GameMove probableAction(GameBoard state) {
		double total = 0;
		for (int i=0; i<5; ++i) {
			total += Math.exp(table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+i*width*height*width*height]/kT);
		}
		
		double rand = Math.random();
		double current = 0.0;
		for (int i=0; i<5; ++i) {
			current += Math.exp(table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+i*width*height*width*height]/kT);
			if (rand < current/total) return new GameMove(GameMove.Move.fromInteger(i));
		}
		
		return null;
	}
	
	public double getTableTotal(GameBoard state) {
		double total = 0;
		for (int i=0; i<5; ++i) {
			total += Math.exp(table[state.currentX+state.currentY*width+state.targetX*width*height+state.targetY*width*height*width+i*width*height*width*height]/kT);
		}
		return total;
	}
}
