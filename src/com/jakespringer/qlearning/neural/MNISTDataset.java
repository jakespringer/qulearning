package com.jakespringer.qlearning.neural;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class MNISTDataset {
	public double[][] imageData;
	public int[] imageLabels;

	public MNISTDataset(String imageFilename, String labelFilename) throws IOException {
		load(imageFilename, labelFilename);
	}
	
	public void load(String imageFilename, String labelFilename) throws IOException {
		byte[] imageBytes = Files.readAllBytes(Paths.get(imageFilename));
		byte[] labelBytes = Files.readAllBytes(Paths.get(labelFilename));
		
		ByteBuffer images = ByteBuffer.wrap(imageBytes);
		ByteBuffer labels = ByteBuffer.wrap(labelBytes);
				
		if (!(images.getInt() == 2051 && labels.getInt() == 2049)) throw new RuntimeException("Invalid image or label file");
		int numImageItems = images.getInt();
		int imageLength = images.getInt() * images.getInt();
		
		imageData = new double[numImageItems][imageLength];
		
		for (int i=0; i<numImageItems*imageLength; ++i) {
			if (!images.hasRemaining()) throw new RuntimeException("Invalid image file");
			imageData[i/imageLength][i%imageLength] = ((double)images.get()) * 1.0/255.0;
		}
		
		int numLabelItems = labels.getInt();
		
		imageLabels = new int[numLabelItems];
		
		for (int i=0; i<numLabelItems; ++i) {
			if (!labels.hasRemaining()) throw new RuntimeException("Invalid label file");
			imageLabels[i] = (int) labels.get();
		}
	}
}
