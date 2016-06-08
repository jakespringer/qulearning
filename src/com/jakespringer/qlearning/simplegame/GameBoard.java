package com.jakespringer.qlearning.simplegame;

import com.jakespringer.qlearning.QState;

public class GameBoard implements QState {
	public final int width;
	public final int height;
	public final int targetX;
	public final int targetY;
	public final int currentX;
	public final int currentY;
	
	public GameBoard(int width, int height, int targetX, int targetY, int currentX, int currentY) {
		this.width = width;
		this.height = height;
		this.targetX = (targetX + width*100) % width;
		this.targetY = (targetY + height*100) % height;
		this.currentX = (currentX + width*100) % width;
		this.currentY = (currentY + height*100) % height;
	}
	
	public boolean hasWon() {
		return targetX == currentX && targetY == currentY;
	}
	
	public static GameBoard identity(int width, int height) {
		return new GameBoard(width, height, 0, 0, width/2, height/2);
	}
}
