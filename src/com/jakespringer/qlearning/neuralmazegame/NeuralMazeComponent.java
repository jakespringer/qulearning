package com.jakespringer.qlearning.neuralmazegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Arrays;

import javax.swing.JPanel;

public class NeuralMazeComponent extends JPanel {

	private NeuralMazeBoard board;
	private String message = "";
	
	public float[] boardValues;
	
	private static final long serialVersionUID = 8597900936372482076L;

	public NeuralMazeComponent(NeuralMazeBoard board) {
		this.board = board;
		boardValues = new float[board.width*board.height];
		Arrays.fill(boardValues, 0.5f);
		setVisible(true);
	}
	
	public void setBoard(NeuralMazeBoard b) {
		this.board = b;
		repaint();
	}

	public void setMessage(String msg) {
		message = msg;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (int i=0; i<board.width; ++i) {
			for (int j=0; j<board.height; ++j) {
				Color fill;
				Color outline = Color.BLACK;
				if (board.currentX == i && board.currentY == j) {
					fill = Color.BLUE;
				} else if (board.targetX == i && board.targetY == j) {
					fill = Color.GREEN;
				} else {
					fill = Color.WHITE;
				}
				
				g2.setColor(new Color(1.0f, 1.0f-boardValues[i+board.width*j], 1.0f-boardValues[i+board.width*j]));
				g2.fillRect((int)(((double)i*getWidth())/((double)board.width)), (int)(((double)j*getHeight())/((double)board.height)), (int)((double)getWidth()/(double)board.width), (int)((double)getHeight()/(double)board.height));
				if (!fill.equals(Color.WHITE)) {
					g2.setColor(fill);
					g2.fillRect((int)(((double)i*getWidth())/((double)board.width))+8, (int)(((double)j*getHeight())/((double)board.height))+8, (int)((double)getWidth()/(double)board.width)-16, (int)((double)getHeight()/(double)board.height)-16);
				}
				g2.setColor(outline);
				g2.drawRect((int)(((double)i*getWidth())/((double)board.width)), (int)(((double)j*getHeight())/((double)board.height)), (int)((double)getWidth()/(double)board.width), (int)((double)getHeight()/(double)board.height));
				if (board.walls[i+j*board.width]) {
					g2.setColor(Color.GRAY);
					g2.fillRect((int)(((double)i*getWidth())/((double)board.width)), (int)(((double)j*getHeight())/((double)board.height)), (int)((double)getWidth()/(double)board.width), (int)((double)getHeight()/(double)board.height));
				}
			}
		}
		
		g.setColor(Color.BLACK);
		g.drawString(message, 10, 20);
	}
}
