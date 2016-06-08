package com.jakespringer.qlearning.mazegame;

import com.jakespringer.qlearning.QFunction;

public class MazeTable implements QFunction<MazeBoard, MazeMove> {

	public final int width;
	public final int height;

	public static final double kT = 0.14;

	public final double[] table;

	public MazeTable(int width, int height) {
		this.width = width;
		this.height = height;
		table = new double[(width * height) * (width * height) * 5];
	}

	@Override
	public double get(MazeBoard state, MazeMove action) {
		switch (action.move) {
		case IDENTITY:
			return table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 0 * width * height * width * height];
		case RIGHT:
			return table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 1 * width * height * width * height];
		case LEFT:
			return table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 2 * width * height * width * height];
		case UP:
			return table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 3 * width * height * width * height];
		case DOWN:
			return table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 4 * width * height * width * height];
		default:
			return 0;
		}
	}

	@Override
	public double getReward(MazeBoard state) {
		return state.hasWon() ? 1.0 : 0.0;
	}

	@Override
	public double set(MazeBoard state, MazeMove action, double value) {
		switch (action.move) {
		case IDENTITY:
			return (table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 0 * width * height * width * height] = value);
		case RIGHT:
			return (table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 1 * width * height * width * height] = value);
		case LEFT:
			return (table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 2 * width * height * width * height] = value);
		case UP:
			return (table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 3 * width * height * width * height] = value);
		case DOWN:
			return (table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 4 * width * height * width * height] = value);
		default:
			return 0;
		}
	}

	@Override
	public MazeMove probableAction(MazeBoard state) {
		double total = 0;

		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			total += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 0 * width * height * width * height] / kT);
		}

		if (!state.walls[((state.width + state.currentX + 1) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			total += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 1 * width * height * width * height] / kT);
		}

		if (!state.walls[((state.width + state.currentX - 1) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			total += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 2 * width * height * width * height] / kT);
		}

		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY + 1) % state.height) * state.width]) {
			total += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 3 * width * height * width * height] / kT);
		}

		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY - 1) % state.height) * state.width]) {
			total += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 4 * width * height * width * height] / kT);
		}

		double rand = Math.random();
		double current = 0.0;

		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			current += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 0 * width * height * width * height] / kT);
			if (rand < current / total)
				return new MazeMove(MazeMove.Move.fromInteger(0));
		}

		if (!state.walls[((state.width + state.currentX + 1) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			current += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 1 * width * height * width * height] / kT);
			if (rand < current / total)
				return new MazeMove(MazeMove.Move.fromInteger(1));
		}

		if (!state.walls[((state.width + state.currentX - 1) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			current += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 2 * width * height * width * height] / kT);
			if (rand < current / total)
				return new MazeMove(MazeMove.Move.fromInteger(2));
		}

		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY + 1) % state.height) * state.width]) {
			current += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 3 * width * height * width * height] / kT);
			if (rand < current / total)
				return new MazeMove(MazeMove.Move.fromInteger(3));
		}

		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY - 1) % state.height) * state.width]) {
			current += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 4 * width * height * width * height] / kT);
			if (rand < current / total)
				return new MazeMove(MazeMove.Move.fromInteger(4));
		}

		return null;
	}

	public double getTableTotal(MazeBoard state) {
		double total = 0;
		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			total += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 0 * width * height * width * height] / kT);
		}

		if (!state.walls[((state.width + state.currentX + 1) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			total += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 1 * width * height * width * height] / kT);
		}

		if (!state.walls[((state.width + state.currentX - 1) % state.width)
				+ ((state.height + state.currentY + 0) % state.height) * state.width]) {
			total += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 2 * width * height * width * height] / kT);
		}

		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY + 1) % state.height) * state.width]) {
			total += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 3 * width * height * width * height] / kT);
		}

		if (!state.walls[((state.width + state.currentX + 0) % state.width)
				+ ((state.height + state.currentY - 1) % state.height) * state.width]) {
			total += Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + 4 * width * height * width * height] / kT);
		}

		return total;
	}

	public double getProbability(MazeBoard state, MazeMove action) {
		double total = getTableTotal(state);
		int x = 0, y = 0;
		if (action.move.equals(MazeMove.Move.RIGHT)) {
			x = 1;
		} else if (action.move.equals(MazeMove.Move.LEFT)) {
			x = -1;
		} else if (action.move.equals(MazeMove.Move.UP)) {
			y = 1;
		} else if (action.move.equals(MazeMove.Move.DOWN)) {
			y = -1;
		}
		
		if (!state.walls[((state.width + state.currentX + x) % state.width)
				+ ((state.height + state.currentY + y) % state.height) * state.width]) {
			return Math.exp(table[state.currentX + state.currentY * width + state.targetX * width * height
					+ state.targetY * width * height * width + action.move.toInteger() * width * height * width * height] / kT) / total;
		} else {
			return 0.0;
		}
	}
}
