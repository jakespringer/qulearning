package com.jakespringer.qlearning.neural;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UCILetterDataset {
	public double[][] testData;
	public double[][] trainData;
	public int[] testLetter;
	public int[] trainLetter;
	
	public UCILetterDataset(String filename) throws IOException {
		load(filename);
	}
	
	public void load(String dataFilename) throws IOException {
		List<String> lines = Files.readAllLines(Paths.get(dataFilename));
		
		testData = new double[2000][16];
		trainData = new double[18000][16];
		testLetter = new int[2000];
		trainLetter = new int[18000];
		
		int i=0;
		int j=0;
		int k=0;
		for (String line : lines) {
			String[] token = line.split(",");
			if (i % 20 == 13 || i % 20 == 16) {
				testLetter[j] = token[0].charAt(0) - 'A';
				for (int l=0; l<16; ++l) {
					testData[j][l] = Integer.parseInt(token[l+1]);
				}
				++j;
			} else {
				trainLetter[k] = token[0].charAt(0) - 'A';
				for (int l=0; l<16; ++l) {
					trainData[k][l] = Integer.parseInt(token[l+1]);
				}
				++k;
			}
			
			++i;
		}
	}
}
