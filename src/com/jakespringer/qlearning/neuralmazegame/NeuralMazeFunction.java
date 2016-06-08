package com.jakespringer.qlearning.neuralmazegame;

import java.util.Arrays;

import com.jakespringer.qlearning.QFunction;
import com.jakespringer.qlearning.neural.TwoLayerNetwork;

public class NeuralMazeFunction implements QFunction<NeuralMazeBoard, NeuralMazeMove> {

	public final int width;
	public final int height;

	public static final double kT = 1.0;

	public final double[] inputs;
	public final double[] targets;
	public final double[] outputs;
	
	private final TwoLayerNetwork neuralNetwork;

	public NeuralMazeFunction(int width, int height, int numHiddenNodes) {
		this.width = width;
		this.height = height;
		inputs = new double[(width*height) + (width*height) + 5];
		targets = new double[1];
		outputs = new double[1];
		
		neuralNetwork = new TwoLayerNetwork(inputs.length, numHiddenNodes, outputs.length, 0.8, 0.1, x -> x, x -> 1.0);
	}

	@Override
	public double get(NeuralMazeBoard state, NeuralMazeMove action) {
		Arrays.fill(inputs, 0.0);
		switch (action.move) {
		case IDENTITY:
			return neuralNetwork.evaluate(getSetInputs(state.currentX, state.currentY, state.targetX, state.targetY, 0), outputs)[0];
		case RIGHT:
			return neuralNetwork.evaluate(getSetInputs(state.currentX, state.currentY, state.targetX, state.targetY, 1), outputs)[0];
		case LEFT:
			return neuralNetwork.evaluate(getSetInputs(state.currentX, state.currentY, state.targetX, state.targetY, 2), outputs)[0];
		case UP:
			return neuralNetwork.evaluate(getSetInputs(state.currentX, state.currentY, state.targetX, state.targetY, 3), outputs)[0];
		case DOWN:
			return neuralNetwork.evaluate(getSetInputs(state.currentX, state.currentY, state.targetX, state.targetY, 4), outputs)[0];
		default:
			return 0;
		}
	}

	@Override
	public double getReward(NeuralMazeBoard state) {
		return state.hasWon() ? 1.0 : 0.0;
	}

	@Override
	public double set(NeuralMazeBoard state, NeuralMazeMove action, double value) {
//		System.out.println("setting to value = " + value);
		
		Arrays.fill(inputs, 1.0);
		targets[0] = value;
		switch (action.move) {
		case IDENTITY:
			return neuralNetwork.train(getSetInputs(state.currentX, state.currentY, state.targetX, state.targetY, 0), targets, outputs)[0];
		case RIGHT:
			return neuralNetwork.train(getSetInputs(state.currentX, state.currentY, state.targetX, state.targetY, 1), targets, outputs)[0];
		case LEFT:
			return neuralNetwork.train(getSetInputs(state.currentX, state.currentY, state.targetX, state.targetY, 2), targets, outputs)[0];
		case UP:
			return neuralNetwork.train(getSetInputs(state.currentX, state.currentY, state.targetX, state.targetY, 3), targets, outputs)[0];
		case DOWN:
			return neuralNetwork.train(getSetInputs(state.currentX, state.currentY, state.targetX, state.targetY, 4), targets, outputs)[0];
		default:
			return 0;
		}
	}

	@Override
	public NeuralMazeMove probableAction(NeuralMazeBoard state) {
		double total = getTableTotal(state);
		
		double rand = Math.random();
		double current = 0.0;
		
//		System.out.println("total = " + total + " rand = " + rand);
		
		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			current += Math.exp(get(state, new NeuralMazeMove(NeuralMazeMove.Move.IDENTITY)) / kT);
//			System.out.println("current = " + current);
			
			if (rand < current / total)
				return new NeuralMazeMove(NeuralMazeMove.Move.fromInteger(0));
		}

		if (!state.walls[((state.width + state.currentX + 1) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			current += Math.exp(get(state, new NeuralMazeMove(NeuralMazeMove.Move.RIGHT)) / kT);
//			System.out.println("current = " + current);

			if (rand < current / total)
				return new NeuralMazeMove(NeuralMazeMove.Move.fromInteger(1));
		}

		if (!state.walls[((state.width + state.currentX - 1) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			current += Math.exp(get(state, new NeuralMazeMove(NeuralMazeMove.Move.LEFT)) / kT);
//			System.out.println("current = " + current);

			if (rand < current / total)
				return new NeuralMazeMove(NeuralMazeMove.Move.fromInteger(2));
		}

		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY + 1) % state.height) * state.width]) {
			current += Math.exp(get(state, new NeuralMazeMove(NeuralMazeMove.Move.UP)) / kT);
//			System.out.println("current = " + current);

			if (rand < current / total)
				return new NeuralMazeMove(NeuralMazeMove.Move.fromInteger(3));
		}

		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY - 1) % state.height) * state.width]) {
			current += Math.exp(get(state, new NeuralMazeMove(NeuralMazeMove.Move.DOWN)) / kT);
//			System.out.println("current = " + current);

			if (rand < current / total)
				return new NeuralMazeMove(NeuralMazeMove.Move.fromInteger(4));
		}
		
		return null;
	}

	public double getTableTotal(NeuralMazeBoard state) {
		double total = 0;
		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			total += Math.exp(get(state, new NeuralMazeMove(NeuralMazeMove.Move.IDENTITY)) / kT);
		}
		if (!state.walls[((state.width + state.currentX + 1) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			total += Math.exp(get(state, new NeuralMazeMove(NeuralMazeMove.Move.RIGHT)) / kT);
		}
		if (!state.walls[((state.width + state.currentX - 1) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			total += Math.exp(get(state, new NeuralMazeMove(NeuralMazeMove.Move.LEFT)) / kT);
		}
		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY + 1) % state.height) * state.width]) {
			total += Math.exp(get(state, new NeuralMazeMove(NeuralMazeMove.Move.UP)) / kT);
		}
		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY - 1) % state.height) * state.width]) {
			total += Math.exp(get(state, new NeuralMazeMove(NeuralMazeMove.Move.DOWN)) / kT);
		}
		return total;
	}

	public double getProbability(NeuralMazeBoard state, NeuralMazeMove action) {
		return Math.exp(get(state, action) / kT) / getTableTotal(state);
	}
	
	public double[] getSetInputs(int x, int y, int tx, int ty, int move) {
		Arrays.fill(inputs, 0.0);
		inputs[x+y*width] = 1.0;
		inputs[width*height + tx+ty*width] = 1.0;
		inputs[width*height + width*height + move] = 1.0;
		return inputs;
	}
	
	public void printNetwork() {
		neuralNetwork.printArrays();
	}
}
