package com.jakespringer.qlearning.neuralmazegame;

import com.jakespringer.qlearning.QState;

public class NeuralMazeBoard implements QState {
	public final int width;
	public final int height;
	public final int targetX;
	public final int targetY;
	public final int currentX;
	public final int currentY;
	
	public final boolean walls[];
	
	public NeuralMazeBoard(int width, int height, int targetX, int targetY, int currentX, int currentY, boolean[] walls) {
		this.width = width;
		this.height = height;
		this.targetX = (targetX + width*100) % width;
		this.targetY = (targetY + height*100) % height;
		this.currentX = (currentX + width*100) % width;
		this.currentY = (currentY + height*100) % height;
		this.walls = walls;;
	}
	
	public boolean hasWon() {
		return targetX == currentX && targetY == currentY;
	}
	
	public static NeuralMazeBoard identity(int width, int height) {
		return new NeuralMazeBoard(width, height, 0, 0, width/2, height/2, new boolean[width*height]);
	}
}
