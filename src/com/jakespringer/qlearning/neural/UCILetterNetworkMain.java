package com.jakespringer.qlearning.neural;

import java.io.IOException;
import java.util.Arrays;

import com.jakespringer.qlearning.util.Util;

public class UCILetterNetworkMain {
	
	public static void main(String[] s) throws ClassNotFoundException, IOException {
		TwoLayerNetwork letter = new TwoLayerNetwork(16, 128, 26, 0.007, 0.004, x -> x, x -> 1.0);
		UCILetterDataset data = new UCILetterDataset("letter-recognition.data");
//		test = train;
		
		double[] output = new double[26];
		double[] target = new double[26];
		while (true) {
			for (int n=0; n<2; ++n) {
				for (int i=0; i<data.trainLetter.length; ++i) {
					Arrays.fill(target, 0.1);
					target[data.trainLetter[i]] = 0.9;
					output = letter.train(data.trainData[i], target, output);
				}
			}
			
			int gotRight = 0;
			for (int i=0; i<data.testLetter.length; ++i) {
				output = letter.evaluate(data.testData[i], output);
				double highest = Double.MIN_VALUE;
				int number = 0;
				for (int j=0; j<26; ++j) {
					if (output[j] > highest) {
						number = j;
						highest = output[j];
					}
				}
				if (number == data.testLetter[i]) ++gotRight;
//				System.out.println(Arrays.toString(output));
			}
			System.out.println("Correct ["+gotRight+"]; Incorrect ["+(data.testLetter.length-gotRight)+"]; Percent ["+((double)gotRight/(double)data.testLetter.length)+"]");
		}
	}
}
