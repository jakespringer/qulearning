package com.jakespringer.qlearning.neural;

public abstract class NeuralNetwork {
	public abstract double[] train(double[] inputs, double[] target, double[] optionalAllocatedArray);

	public abstract double[] evaluate(double[] inputs, double[] optionalAllocatedArray);
	
	public abstract double activate(double x);
		
	public double[] train(double[] inputs, double[] target) {
		return train(inputs, target, null);
	}

	public double[] evaluate(double[] inputs) {
		return evaluate(inputs, null);
	}
}
