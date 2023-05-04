import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;
import java.lang.Math;


public class JCameron_PartB {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File trainingFile = new File("trainingFile.txt");
		
		// If the training file is valid, continue execution. If not, error and exit.
		if(validateTrainingFile(trainingFile)) {
			
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
		else {
			
			System.out.println("Invalid input.");
			
		}
		
		
		
	}
	
	// Method to validate the training file. The training file is validated based on if the label column only contains
	// 1s and 0s, as that was the only way I could see a training file being tested for validation.
	// Input: training file
	// Output: true/false based on validity
	public static boolean validateTrainingFile(File inputFile) throws IOException {
		
		Scanner readFile = new Scanner(inputFile);
		boolean result = true;
		
		while(readFile.hasNext()) {
			
			int testLabel = readFile.nextInt();
			
			// If the current row of the label column doesn't contain a 1 "or" a 0, then the file is not valid.
			if(testLabel != 1 && testLabel != 0) {
				
				result = false;
				
				
			}

			readFile.nextLine();
			
		}
		
		readFile.close();
		return result;
		
	}
	
	// Method to find the number of samples in the training file. This counts the number of rows (samples) the file provides.
	// Input: training file
	// Output: number of rows in the file
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
	
	// Method that counts the number of features per sample in the training file.
	// Input: training file
	// Output: number of features per sample
	public static int countFeatsOfFile(File inputFile) throws IOException {
		
		Scanner readFile = new Scanner(inputFile);
		int numFeats = 0;
		int numLines = findNumSamples(inputFile);
		
		// This for loop goes to the final line of the training file, to make
		// reading how many features a sample has easier
		for(int i = 0; i < numLines - 1; i++) {
			
			readFile.nextLine();
				
		}
		
		// Skips the first integer in the row, as that integer would pertain to the labels column
		readFile.nextInt();
		
		while(readFile.hasNext()) {
			
			readFile.nextInt();
			numFeats++;
			
		}
		
		readFile.close();
		return numFeats;
		
	}
	
	// Method that counts the number of features provided in each sample of the unclassified file
	// Input: file with unclassified features
	// Output: number of unclassified features
	public static int countTestFeats(File inputFile) throws IOException {
		
		Scanner readFile = new Scanner(inputFile);
		int numFeats = 0;
		int numLines = findNumSamples(inputFile);
		
		// Moves to the final line of the file for easier reading
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
	
	// Method that reads the first column from the file and places the values into a labels array
	// Input: training file, labels array
	// Output: no return, updates labels array wherever called from
	public static void readLabels(File inputFile, double[] labels) throws IOException {
		
		Scanner readFile = new Scanner(inputFile);
		
		for(int i = 0; i < labels.length; i++) {
			
			labels[i] = readFile.nextInt();
			readFile.nextLine();
			
		}
		
		readFile.close();
		
	}
	
	// Method that reads the features provided in each sample of the file, given what sample the algorithm is currently on
	// Input: training file, features array, current sample number
	// Output: no return, updates features array wherever called from
	public static void readFile(File inputFile, double[] features, int sample) throws IOException {
		
		Scanner readFile = new Scanner(inputFile);
		
		// If the sample isn't the first sample, skip to the current sample (row) in the file
		if(sample != 0) {
			
			for(int i = 0; i < sample; i++) {
				
				readFile.nextLine();
				
			}
			
		}
		
		// Skips the labels column in file
		readFile.nextInt();
		
		for(int i = 0; i < features.length; i++) {
			
			features[i] = readFile.nextInt();
			
		}
		
		readFile.close();
		
	}
	
	// Method that initializes weights randomly, given a max and a min.
	// Input: size of the x vector
	// Output: weight vector
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
	
	// Method that carries out the dot product between two vectors. Returns a 1 or 0
	// if the dot product is greater than or equal to 0 or less than 0, respectively.
	// Input: two vectors
	// Output: 1 or 0
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
	
	// Method that carries out the "machine learning" called the Perceptron.
	// Input: training file, features array, labels array
	// Output: trained weight vector
	public static double[] perceptron(File inputFile, double[] features, double[] labels) throws IOException {
		
		int numSamples = findNumSamples(inputFile);
		
		double[] xVector = new double[features.length + 1];
		double[] weightVector = initializeWeights(xVector.length);
		double[] prevWeightVector = new double[weightVector.length];
		double[] difference = new double[weightVector.length];
		
		double error = 0;
		
		do {
			
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
					
					prevWeightVector[j] = weightVector[j];
					
					weightVector[j] = weightVector[j] + (error * xVector[j]);
					
				}
				
				for(int j = 0; j < weightVector.length; i++) {
					
					difference[j] = prevWeightVector[j] - weightVector[j];
					
						
				}
				
			}
			
			
		}while(!convergence(difference));
		
		return weightVector;
		
	}
	
	// Method that finds if the Perceptron has reached convergence.
	// Input: difference between current weight vector and the one before it
	// Output: true/false based on convergence
	public static boolean convergence(double[] difference) {
		
		int tolerance = 1;
		boolean result = true;
		
		for(int i = 0; i < difference.length; i++) {
			
			if(Math.abs(difference[i]) > tolerance) {
				
				result = false;
				
			}
			
		}
		
		return result;
		
	}
	
	// Method that classifies the features of the unclassified file using the trained weight vector
	// Input: file with unclassified features, trained weight vector
	// Output: no return, calls printToFile method
	public static void classifyFeatures(File inputFile, double[] weightVector) throws IOException {
		
		double[] newFeatures = new double[countTestFeats(inputFile)];
		double[] finalLabels = new double[findNumSamples(inputFile)];
		
		double[] xVector = new double[weightVector.length];
		
		Scanner readFile = new Scanner(inputFile);
		
		for(int i = 0; i < finalLabels.length; i++) {
			
			for(int k = 0; k < newFeatures.length; k++) {
				
				newFeatures[k] = readFile.nextInt();
				
			}
			
				for(int j = 0; j < xVector.length; j++) {
				
					if(j == 0) {
						
						xVector[j] = 1;
						
					}
					else {
						
						xVector[j] = newFeatures[j-1];
						
					}
				
				}
			
			finalLabels[i] = dotProduct(weightVector, xVector);
			
		}
		
		printToFile(finalLabels, weightVector);
		
		readFile.close();
		
	}
	
	// Method that prints the labels of the unwatched movies and the trained weight vector
	// Input: final labels of the unwatched movies, trained weight vector
	// Output: no return, prints both vectors to a file.
	public static void printToFile(double[] finalLabels, double[] weightVector) throws IOException {
		
		PrintWriter print2File = new PrintWriter("JCameron_PartB.txt");
		
		for(int i = 0; i < finalLabels.length; i++) {
			
			print2File.print(finalLabels[i] + " ");
			
		}
		
		print2File.println();
		
		for(int i = 0; i < weightVector.length; i++) {
			
			print2File.print(weightVector[i] + " ");
			
		}
		
		print2File.close();
		
	}

}
