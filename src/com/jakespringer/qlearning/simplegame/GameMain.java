package com.jakespringer.qlearning.simplegame;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.jakespringer.qlearning.QLearner;
import com.jakespringer.qlearning.SimpleLearner;
import com.jakespringer.qlearning.util.Con;

public class GameMain {
	public static void main(String[] args) throws InterruptedException {
		int SIZE = 8;
		
		Con<Boolean> toggle = new Con<>(true);
		
		Random random = new Random();
		
		GameBoard board = new GameBoard(SIZE, SIZE, random.nextInt(SIZE), random.nextInt(SIZE), random.nextInt(SIZE), random.nextInt(SIZE));
		GameTable table = new GameTable(SIZE, SIZE);
		QLearner<GameBoard, GameMove> learner = new SimpleLearner<>(table);
		
		GameComponent gc = new GameComponent(GameBoard.identity(SIZE, SIZE));
		
		JFrame frame = new JFrame();
		JPanel mainPanel = new JPanel();
		frame.setSize(600, 480);
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(gc, BorderLayout.CENTER);
		frame.getContentPane().add(mainPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
				
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
				toggle.contained = !toggle.contained;
			}
		});
		
		DecimalFormat df = new DecimalFormat("0.00"); 
		
		long iterations = 0;
		Queue<Long> lastWins = new LinkedList<>();
		long lastWin = 0L;
		while (true) {
			if (board.hasWon() || board == null) {
				++iterations;
				lastWins.add(lastWin);
				lastWin = 0L;
				board = new GameBoard(SIZE, SIZE, random.nextInt(SIZE), random.nextInt(SIZE), random.nextInt(SIZE), random.nextInt(SIZE));
				while (lastWins.size() > 100) lastWins.remove();
			}
			board = learner.train(board);
			gc.setBoard(board);
			gc.setMessage("Average turns = " + df.format(((lastWins.size() != 0) ? ((double)lastWins.parallelStream().reduce(0L, (x, y) -> x + y) / (double)lastWins.size()) : 0.0)) + " Iterations = " + iterations);
			
			double total = table.getTableTotal(board);
			
			Arrays.fill(gc.boardValues, 0.0f);
			gc.boardValues[((board.currentX+0+board.width)%board.width) + ((board.currentY+0+board.height)%board.height)*board.width] =
					(float) (Math.exp(table.table[board.currentX+board.currentY*table.width+board.targetX*table.width*table.height+board.targetY*table.width*table.height*table.width+0*table.width*table.height*table.width*table.height]/GameTable.kT)/total);
			gc.boardValues[((board.currentX+1+board.width)%board.width) + ((board.currentY+0+board.height)%board.height)*board.width] =
					(float) (Math.exp(table.table[board.currentX+board.currentY*table.width+board.targetX*table.width*table.height+board.targetY*table.width*table.height*table.width+1*table.width*table.height*table.width*table.height]/GameTable.kT)/total);
			gc.boardValues[((board.currentX-1+board.width)%board.width) + ((board.currentY+0+board.height)%board.height)*board.width] =
					(float) (Math.exp(table.table[board.currentX+board.currentY*table.width+board.targetX*table.width*table.height+board.targetY*table.width*table.height*table.width+2*table.width*table.height*table.width*table.height]/GameTable.kT)/total);
			gc.boardValues[((board.currentX+0+board.width)%board.width) + ((board.currentY+1+board.height)%board.height)*board.width] =
					(float) (Math.exp(table.table[board.currentX+board.currentY*table.width+board.targetX*table.width*table.height+board.targetY*table.width*table.height*table.width+3*table.width*table.height*table.width*table.height]/GameTable.kT)/total);
			gc.boardValues[((board.currentX+0+board.width)%board.width) + ((board.currentY-1+board.height)%board.height)*board.width] =
					(float) (Math.exp(table.table[board.currentX+board.currentY*table.width+board.targetX*table.width*table.height+board.targetY*table.width*table.height*table.width+4*table.width*table.height*table.width*table.height]/GameTable.kT)/total);
			
			++lastWin;
			if (toggle.contained) Thread.sleep(200);
		}
	}
}
