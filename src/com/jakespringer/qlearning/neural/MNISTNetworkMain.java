package com.jakespringer.qlearning.neural;

import java.io.IOException;
import java.util.Arrays;

public class MNISTNetworkMain {
	public static void main(String[] s) throws ClassNotFoundException, IOException {
		TwoLayerNetwork mnist = new TwoLayerNetwork(28*28, 64, 10, 0.7, 0.4);
		MNISTDataset test = new MNISTDataset("t10k-images-idx3-ubyte", "t10k-labels-idx1-ubyte");
		MNISTDataset train = new MNISTDataset("train-images-idx3-ubyte", "train-labels-idx1-ubyte");
		
		double[] output = new double[10];
		double[] target = new double[10];
		while (true) {
			for (int n=0; n<2; ++n) {
				for (int i=0; i<train.imageLabels.length; ++i) {
					Arrays.fill(target, 0.1);
					target[train.imageLabels[i]] = 0.9;
					output = mnist.train(train.imageData[i], target, output);
				}
			}
			
			int gotRight = 0;
			for (int i=0; i<test.imageLabels.length; ++i) {
				output = mnist.evaluate(test.imageData[i], output);
				double highest = Double.MIN_VALUE;
				int number = 0;
				for (int j=0; j<10; ++j) {
					if (output[j] > highest) {
						number = j;
						highest = output[j];
					}
				}
				if (number == test.imageLabels[i]) ++gotRight;
			}
			System.out.println("Correct ["+gotRight+"/"+test.imageLabels.length+"]; Ratio ["+((double)gotRight/(double)test.imageLabels.length)+"]");
		}
	}
}
