import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.lang.Math;


public class JCameron_PartB {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File trainingFile = new File("trainingFile.txt");
		
		int numFeats = countFeatsOfFile(trainingFile);
		int numSamples = findNumSamples(trainingFile);
		
		double[] features = new double[numFeats];
		double[] labels = new double[numSamples];
		
		readLabels(trainingFile, labels);
		readFile(trainingFile, features, 0);
		
		double[] weightVector = perceptron(trainingFile, features, labels);
		
		File inputFile = new File("testFile.txt");
		
		classifyFeatures(inputFile, weightVector);
		
		
	}
	
	public static int findNumSamples(File inputFile) throws IOException {
		
		Scanner readFile = new Scanner(inputFile);
		int numLines = 0;
		
		while(readFile.hasNext()) {
			
			readFile.nextLine();
			numLines++;
			
		}
		
		readFile.close();
		return numLines;
		
	}
	
	public static int countFeatsOfFile(File inputFile) throws IOException {
		
		Scanner readFile = new Scanner(inputFile);
		int numFeats = 0;
		int numLines = findNumSamples(inputFile);
		
		for(int i = 0; i < numLines - 1; i++) {
			
			readFile.nextLine();
				
		}
		
		readFile.nextInt();
		
		while(readFile.hasNext()) {
			
			readFile.nextInt();
			numFeats++;
			
		}
		
		readFile.close();
		return numFeats;
		
	}
	
	public static int countTestFeats(File inputFile) throws IOException {
		
		Scanner readFile = new Scanner(inputFile);
		int numFeats = 0;
		int numLines = findNumSamples(inputFile);
		
		for(int i = 0; i < numLines - 1; i++) {
			
			readFile.nextLine();
				
		}
		
		while(readFile.hasNext()) {
			
			readFile.nextInt();
			numFeats++;
			
		}
		
		readFile.close();
		return numFeats;
		
	}
	
	public static void readLabels(File inputFile, double[] labels) throws IOException {
		
		Scanner readFile = new Scanner(inputFile);
		
		for(int i = 0; i < labels.length; i++) {
			
			labels[i] = readFile.nextInt();
			readFile.nextLine();
			
		}
		
		readFile.close();
		
	}
	
	public static void readFile(File inputFile, double[] features, int sample) throws IOException {
		
		Scanner readFile = new Scanner(inputFile);
		
		if(sample != 0) {
			
			for(int i = 0; i < sample; i++) {
				
				readFile.nextLine();
				
			}
			
		}
		
		readFile.nextInt();
		
		for(int i = 0; i < features.length; i++) {
			
			features[i] = readFile.nextInt();
			
		}
		
		readFile.close();
		
	}
	
	public static double[] initializeWeights(int size) {
		
		double[] weightVector = new double[size];
		
		int max = 5;
		int min = -5;
		int range = max - min + 1;
		
		for(int i = 0; i < weightVector.length; i++) {
			
			weightVector[i] = (int)(Math.random() * range) + min;
			
		}
		
		return weightVector;
		
	}
	
	public static int dotProduct(double[] vector1, double[] vector2) {
		
		double dotProduct = 0;
		
		if(vector1.length == vector2.length) {
			
			for(int i = 0; i < vector1.length; i++) {
				
				dotProduct += vector1[i] * vector2[i];
				
			}
			
		}
		
		if(dotProduct >= 0) {
			
			return 1;
			
		}
		else {
			
			return 0;
			
		}
		
	}
	
	public static double[] perceptron(File inputFile, double[] features, double[] labels) throws IOException {
		
		int numSamples = findNumSamples(inputFile);
		
		double[] xVector = new double[features.length + 1];
		double[] weightVector = initializeWeights(xVector.length);
		
		double error = 0;
		
		for(int i = 0; i < numSamples; i++) {
			
			readFile(inputFile, features, i);
			
			for(int j = 0; j < xVector.length; j++) {
				
				if(j == 0) {
					
					xVector[j] = 1;
					
				}
				else {
					
					xVector[j] = features[j-1];
					
				}
				
			}
			
			double dotProduct = dotProduct(weightVector, xVector);
			
			error = labels[i] - dotProduct;
			
			for(int j = 0; j < weightVector.length; j++) {
				
				weightVector[j] = weightVector[j] + (error * xVector[j]);
				
			}
			
		}
		
		return weightVector;
		
	}
	
	public static void classifyFeatures(File inputFile, double[] weightVector) throws IOException {
		
		double[] newFeatures = new double[countTestFeats(inputFile)];
		double[] finalLabels = new double[findNumSamples(inputFile)];
		
		Scanner readFile = new Scanner(inputFile);
		
		for(int i = 0; i < finalLabels.length; i++) {
			
				for(int j = 0; j < newFeatures.length; j++) {
				
					newFeatures[j] = readFile.nextInt();
				
				}
			
			finalLabels[i] = dotProduct(weightVector, newFeatures);
			
		}
		
		for(int i = 0; i < finalLabels.length; i++) {
			
			System.out.print(finalLabels[i] + " ");
			
		}
		
		System.out.println();
		
		for(int i = 0; i < weightVector.length; i++) {
			
			System.out.print(weightVector[i] + " ");
			
		}
		
		readFile.close();
		
	}

}
