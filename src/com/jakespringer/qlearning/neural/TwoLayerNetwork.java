package com.jakespringer.qlearning.neural;

import java.util.Arrays;
import java.util.function.Function;

public class TwoLayerNetwork extends NeuralNetwork {
	private double[][][] weights;
	private double[][][] momentums;
	private double[][] neuronValues;
	private double[][] errorSignals;
	private double learningRate;
	private double momentumFactor;
	
	private final int numInputNodes;
	private final int numHiddenNodes;
	private final int numOutputNodes;
	
	private final Function<Double, Double> activateFunction;
	private final Function<Double, Double> activateDerivativeFunction;
	
	public TwoLayerNetwork(int numInputNodes, int numHiddenNodes, int numOutputNodes, double learningRate, double momentumFactor, Function<Double, Double> activateFunction, Function<Double, Double> activateDerivativeFunction) {
		this.numInputNodes = numInputNodes;
		this.numHiddenNodes = numHiddenNodes;
		this.numOutputNodes = numOutputNodes;
		
		weights = new double[2][][];
		momentums = new double[2][][];
		neuronValues = new double[2][];
		errorSignals = new double[2][];
		weights[0] = new double[numHiddenNodes][];
		weights[1] = new double[numOutputNodes][];
		momentums[0] = new double[numHiddenNodes][];
		momentums[1] = new double[numOutputNodes][];
		neuronValues[0] = new double[numHiddenNodes];
		neuronValues[1] = new double[numOutputNodes];
		errorSignals[0] = new double[numHiddenNodes];
		errorSignals[1] = new double[numOutputNodes];
		
		for (int i=0; i<numHiddenNodes; ++i) {
			weights[0][i] = new double[numInputNodes+1];
			momentums[0][i] = new double[numInputNodes+1];			
			for (int j=0; j<numInputNodes+1; ++j) {
				weights[0][i][j] = ((Math.random() * 2.0) - 1.0) * 0.01;
			}
		}
		
		for (int i=0; i<numOutputNodes; ++i) {
			weights[1][i] = new double[numHiddenNodes+1];
			momentums[1][i] = new double[numHiddenNodes+1];
			for (int j=0; j<numHiddenNodes+1; ++j) {
				weights[1][i][j] = ((Math.random() * 2.0) - 1.0) * 0.01;
			}
		}
		
		this.activateFunction = activateFunction;
		this.activateDerivativeFunction = activateDerivativeFunction;
		
		this.learningRate = learningRate;
		this.momentumFactor = momentumFactor;		
	}
	
	public TwoLayerNetwork(int numInputNodes, int numHiddenNodes, int numOutputNodes, double learningRate, double momentumFactor) {
		this(numInputNodes, numHiddenNodes, numOutputNodes, learningRate, momentumFactor, x -> 1.0 / (1.0+Math.exp(-x)), x -> x * (1.0 - x));
	}
	
	public double[] train(double[] inputs, double[] target) {
		return train(inputs, target, null);
	}
	
	public double[] train(double[] inputs, double[] target, double[] optionalAllocatedArray) {
		if (inputs.length != numInputNodes) throw new RuntimeException("Invalid input size");
		if (target.length != numOutputNodes) throw new RuntimeException("Invalid target size");
		if (optionalAllocatedArray == null || optionalAllocatedArray.length != numOutputNodes) {
			optionalAllocatedArray = new double[numOutputNodes];
		}
		
		double[] actual = evaluate(inputs, optionalAllocatedArray);
		for (int i=0; i<numOutputNodes; ++i) {
			errorSignals[1][i] = (target[i] - actual[i]) * activateDerivative(actual[i]);
			for (int j=0; j<numHiddenNodes; ++j) {
				double oldWeight = momentums[1][i][j];
				momentums[1][i][j] = weights[1][i][j];
				weights[1][i][j] = weights[1][i][j] + errorSignals[1][i] * neuronValues[0][j] * learningRate + (momentums[1][i][j] - oldWeight) * momentumFactor;
			}
			double oldWeight = momentums[1][i][numHiddenNodes];
			momentums[1][i][numHiddenNodes] = weights[1][i][numHiddenNodes];
			weights[1][i][numHiddenNodes] = weights[1][i][numHiddenNodes] + errorSignals[1][i] * learningRate + (momentums[1][i][numHiddenNodes] - oldWeight) * momentumFactor;
		}
		
		for (int i=0; i<numHiddenNodes; ++i) {
			errorSignals[0][i] = 0.0;
			for (int j=0; j<numOutputNodes; ++j) {
				errorSignals[0][i] += errorSignals[1][j] * weights[1][j][i];
			}
			errorSignals[0][i] *= activateDerivativeHidden(neuronValues[0][i]);
			
			for (int j=0; j<numInputNodes; ++j) {
				double oldWeight = momentums[0][i][j];
				momentums[0][i][j] = weights[0][i][j];
				weights[0][i][j] = weights[0][i][j] + errorSignals[0][i] * inputs[j] * learningRate + (momentums[0][i][j] - oldWeight) * momentumFactor;
			}
			double oldWeight = momentums[0][i][numInputNodes];
			momentums[0][i][numInputNodes] = weights[0][i][numInputNodes];
			weights[0][i][numInputNodes] = weights[0][i][numInputNodes] + errorSignals[0][i] * learningRate + (momentums[0][i][numInputNodes] - oldWeight) * momentumFactor;
		}
		
		return actual;
	}
	
	public double[] evaluate(double[] inputs) {
		return evaluate(inputs, null);
	}
	
	public double[] evaluate(double[] inputs, double[] optionalAllocatedArray) {
		if (inputs.length != numInputNodes) throw new RuntimeException("Invalid input size");
		if (optionalAllocatedArray == null || optionalAllocatedArray.length != numOutputNodes) {
			optionalAllocatedArray = new double[numOutputNodes];
		}
		
		for (int i=0; i<neuronValues[0].length; ++i) {
			double sum = 0.0;
			for (int j=0; j<numInputNodes; ++j) {
				sum += inputs[j] * weights[0][i][j];
			}
			sum += weights[0][i][numInputNodes];
			neuronValues[0][i] = activateHidden(sum);
		}
		
		for (int i=0; i<neuronValues[1].length; ++i) {
			double sum = 0.0;
			for (int j=0; j<numHiddenNodes; ++j) {
				sum += neuronValues[0][j] * weights[1][i][j];
			}
			sum += weights[1][i][numHiddenNodes];
			neuronValues[1][i] = activate(sum);
		}
		
		System.arraycopy(neuronValues[1], 0, optionalAllocatedArray, 0, neuronValues[1].length);
		return optionalAllocatedArray;
	}
	
	public double activate(double x) {
		return activateFunction.apply(x);
	}
	
	public double activateDerivative(double x) {
		return activateDerivativeFunction.apply(x);
	}
	
	public double activateHidden(double x) {
		return 1.0 / (1.0 + Math.exp(-x));
	}
	
	public double activateDerivativeHidden(double x) {
		return x * (1.0 - x);
	}
	
	public void printArrays() {
		Arrays.stream(weights).flatMap(Arrays::stream).forEach(x -> System.out.println(Arrays.toString(x)));
	}
}
