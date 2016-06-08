package com.jakespringer.qlearning.mazegame;

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

public class MazeMain {
	public static void main(String[] args) throws InterruptedException {
		int SIZE = 12;
		
		Con<Boolean> toggle = new Con<>(true);
		
		Random random = new Random();
		
		boolean[] walls = new boolean[SIZE*SIZE];
		walls[0+0*SIZE] = true;
		walls[1+0*SIZE] = true;
		walls[2+0*SIZE] = true;
		walls[3+0*SIZE] = true;
		walls[4+0*SIZE] = true;
		walls[5+0*SIZE] = true;
		walls[6+0*SIZE] = true;
		walls[7+0*SIZE] = true;
		walls[8+0*SIZE] = true;
		walls[9+0*SIZE] = true;
		walls[10+0*SIZE] = true;
		walls[11+0*SIZE] = true;
		walls[11+1*SIZE] = true;
		walls[11+2*SIZE] = true;
		walls[11+3*SIZE] = true;
		walls[11+4*SIZE] = true;
		walls[11+5*SIZE] = true;
		walls[11+6*SIZE] = true;
		walls[11+7*SIZE] = true;
		walls[11+8*SIZE] = true;
		walls[11+9*SIZE] = true;
		walls[11+10*SIZE] = true;
		walls[11+11*SIZE] = true;
		
		walls[2+1*SIZE] = true;
		walls[2+2*SIZE] = true;
		walls[2+3*SIZE] = true;
		walls[2+4*SIZE] = true;
		walls[2+5*SIZE] = true;
		walls[2+6*SIZE] = true;
		walls[2+7*SIZE] = true;
		walls[2+8*SIZE] = true;
		
		walls[3+8*SIZE] = true;
		walls[4+8*SIZE] = true;
		walls[5+8*SIZE] = true;
		walls[6+8*SIZE] = true;
		walls[7+8*SIZE] = true;
		
		walls[5+5*SIZE] = true;
		walls[6+5*SIZE] = true;
		walls[7+5*SIZE] = true;
		walls[8+5*SIZE] = true;
		walls[9+5*SIZE] = true;
		walls[10+5*SIZE] = true;

		walls[5+4*SIZE] = true;
		walls[5+3*SIZE] = true;
		walls[5+2*SIZE] = true;
				
		int aa, bb, cc, dd;
		do {
			aa = random.nextInt(SIZE);
			bb = random.nextInt(SIZE);
		} while (walls[aa+bb*SIZE]);
		do {
			cc = random.nextInt(SIZE);
			dd = random.nextInt(SIZE);
		} while (walls[cc+dd*SIZE]);	
		
		MazeBoard board = new MazeBoard(SIZE, SIZE, aa, bb, cc, dd, walls);
		MazeTable table = new MazeTable(SIZE, SIZE);
		QLearner<MazeBoard, MazeMove> learner = new SimpleLearner<>(table);
		
		MazeComponent gc = new MazeComponent(board);
		
		JFrame frame = new JFrame();
		frame.setTitle("Maze Main");
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
				int a, b, c, d;
				do {
					a = random.nextInt(SIZE);
					b = random.nextInt(SIZE);
				} while (walls[a+b*SIZE]);
				do {
					c = random.nextInt(SIZE);
					d = random.nextInt(SIZE);
				} while (walls[c+d*SIZE]);				
				
				board = new MazeBoard(SIZE, SIZE, a, b, c, d, walls);
				while (lastWins.size() > 100) lastWins.remove();
			}
			board = learner.train(board);
			if (toggle.contained) gc.setBoard(board);
			gc.setMessage("Average turns = " + df.format(((lastWins.size() != 0) ? ((double)lastWins.parallelStream().reduce(0L, (x, y) -> x + y) / (double)lastWins.size()) : 0.0)) + " Iterations = " + iterations);
			
			double total = table.getTableTotal(board);
			
			Arrays.fill(gc.boardValues, 0.0f);
			gc.boardValues[((board.currentX+0+board.width)%board.width) + ((board.currentY+0+board.height)%board.height)*board.width] =
					(float) (Math.exp(table.table[board.currentX+board.currentY*table.width+board.targetX*table.width*table.height+board.targetY*table.width*table.height*table.width+0*table.width*table.height*table.width*table.height]/MazeTable.kT)/total);
			gc.boardValues[((board.currentX+1+board.width)%board.width) + ((board.currentY+0+board.height)%board.height)*board.width] =
					(float) (Math.exp(table.table[board.currentX+board.currentY*table.width+board.targetX*table.width*table.height+board.targetY*table.width*table.height*table.width+1*table.width*table.height*table.width*table.height]/MazeTable.kT)/total);
			gc.boardValues[((board.currentX-1+board.width)%board.width) + ((board.currentY+0+board.height)%board.height)*board.width] =
					(float) (Math.exp(table.table[board.currentX+board.currentY*table.width+board.targetX*table.width*table.height+board.targetY*table.width*table.height*table.width+2*table.width*table.height*table.width*table.height]/MazeTable.kT)/total);
			gc.boardValues[((board.currentX+0+board.width)%board.width) + ((board.currentY+1+board.height)%board.height)*board.width] =
					(float) (Math.exp(table.table[board.currentX+board.currentY*table.width+board.targetX*table.width*table.height+board.targetY*table.width*table.height*table.width+3*table.width*table.height*table.width*table.height]/MazeTable.kT)/total);
			gc.boardValues[((board.currentX+0+board.width)%board.width) + ((board.currentY-1+board.height)%board.height)*board.width] =
					(float) (Math.exp(table.table[board.currentX+board.currentY*table.width+board.targetX*table.width*table.height+board.targetY*table.width*table.height*table.width+4*table.width*table.height*table.width*table.height]/MazeTable.kT)/total);
			
			++lastWin;
			if (toggle.contained) Thread.sleep(200);
		}
	}
}
