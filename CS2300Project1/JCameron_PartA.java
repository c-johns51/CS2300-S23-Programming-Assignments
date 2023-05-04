/*
 * Author: Cameron Johnson
 * Programming Assignment 5
 * CS 2300 003
 * Last updated: May 2023
 * Updated by: Cameron Johnson
 * 
 * Description: This program takes values from a file and places them in a matrix, provided that the matrix is a stochastic matrix and
 * contains no negative numbers. The program then applies the power algorithm on this matrix, returning the dominant eigenvector of the matrix.
 * Each eigenvalue in the eigenvector represents a webpage. These webpages are then ranked based on their associated eigenvalue.
 * 
 */

import java.lang.Math;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.io.PrintWriter;

public class JCameron_PartA {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File inputFile = new File("input.txt");
		
		double[][] inputMatrix = valuesToMatrix(inputFile);
		
		// if the matrix is valid, continue execution. If not, exit program with error.
		if(isValidMatrix(inputMatrix)) {
			
			double[] eigenvector = new double[inputMatrix.length];
			
			// If the power algorithm was successful, continue execution.
			// If algorithm was unsuccessful (ex. largest value in y was 0 at any time), exit with error. Error printed in powerAlgorithm method.
			if(powerAlgorithm(inputMatrix, eigenvector)) {
				
				int[] rankedWebpages = rankPages(eigenvector);
				
				printToFile(eigenvector, rankedWebpages);
				
			}
		}
		else {
			
			System.out.println("Input is invalid, please use a valid input.");
			
		}	
	}
	
	// Method to print to a file
	// Inputs: eigenvector, vector of pages ranked
	// Output: File with printed outputs
	public static void printToFile(double[] eigenvector, int[] rankedPages) throws IOException {
		
		PrintWriter print2File = new PrintWriter("JCameron_PartA.txt");
		
		for(int i = 0; i < eigenvector.length; i++) {
			
			print2File.printf("%.2f ", eigenvector[i]);
			
		}
		
		print2File.println();
		
		for(int i = 0; i < rankedPages.length; i++) {
			
			print2File.print(rankedPages[i] + " ");
			
		}
		
		print2File.close();
		
	}
	
	// Method to rank the webpages
	// Input: eigenvector
	// Output: vector of the ranked pages
	public static int[] rankPages(double[] eigenvector) {
		
		int[] rankedPages = new int[eigenvector.length];
		double largestValue = eigenvector[0];
		int[] prevLargestIndex = new int[rankedPages.length];
		int largestValueIndex = 0;
		boolean alreadyUsed = false;
		
		for(int i = 0; i < rankedPages.length; i++) {
			
			for(int j = 0; j < rankedPages.length; j++) {
				
				// Resets already used to false
				alreadyUsed = false;
				
				// If the value in the jth position is greater than the current largest value
				if(eigenvector[j] > largestValue) {
					
					for(int k = 0; k < prevLargestIndex.length; k++) {
						
						// Check through the array of previous indexes where the largest number was found
						// If they match, then the value was already ranked
						if(j + 1 == prevLargestIndex[k]) {
							
							alreadyUsed = true;
							
						}

						
					}
					
					// If the value wasn't already ranked, set current value as largest value
					// and current index j as the index holding said largest value
					if(!alreadyUsed) {
						
						largestValue = eigenvector[j];
						largestValueIndex = j;
						
					}
					
				}
				
				
			}
			
			// Put the index of the current largest number (plus 1) into the ranked pages array
			// Reset the largest value for the next iteration
			// Store the index of the largest value in the array of previous largest indexes array for future iterations
			rankedPages[i] = largestValueIndex + 1;
			largestValue = 0;
			prevLargestIndex[i] = largestValueIndex + 1;
			
		}
		
		return rankedPages;
		
	}
	
	// Method to take the values from the file and put them in a matrix
	// Input: input file
	// Output: returns a matrix with values from file
	public static double[][] valuesToMatrix(File inputFile) throws IOException {
		
		Scanner readFile = new Scanner(inputFile);
		int numRows = 0;
		int numColumns = 0;
		
		while(readFile.hasNext()) {
			
			readFile.nextLine();
			numRows++;
			
		}
		
		readFile.close();
		readFile = new Scanner(inputFile);
		
		while(readFile.hasNext()) {
			
			readFile.nextDouble();
			numColumns++;
			
			
		}
		
		readFile.close();
		readFile = new Scanner(inputFile);
		
		// Finds the number of columns in the file.
		// This should always produce the same number of rows given a NxN matrix.
		numColumns /= numRows;
		
		double[][] matrix = new double[numRows][numColumns];
		
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				
				matrix[i][j] = readFile.nextDouble();
				
			}
		}
		
		readFile.close();
		return matrix;
		
		
	}
	
	// Method to validate the matrix
	// Inputs: matrix
	// Output: true/false based on validity
	public static boolean isValidMatrix(double[][] matrixToCheck) {
		
		boolean result = true;
		double columnSum = 0;
		double rowSum = 0;
		double tolerance = 0.1;
		
		// Check if matrix is a square matrix
		if(matrixToCheck.length == matrixToCheck[0].length) {
			
			for(int i = 0; i < matrixToCheck.length; i++) {
				
				rowSum = 0;
				columnSum = 0;
				
				for(int j = 0; j < matrixToCheck[0].length; j++) {
					
					// If any element in the matrix is negative, not valid
					if(matrixToCheck[i][j] < 0) {
						
						result = false;
						
					}
					
					rowSum += matrixToCheck[i][j];
					columnSum += matrixToCheck[j][i];
					
				}
				
				// If either the row sums to 1 or the column sums to 1 (checks every iteration)
				// 		Do nothing.
				// Otherwise, the matrix is not valid.
				// Tolerance is used because some values, like 1/3, don't properly add up to 1 in code.
				// Tolerance accepts 0.9 as having the tolerance set to 0.01 caused issues with a number like 0.99, for some reason.
				if(Math.abs(1 - rowSum) <= tolerance || Math.abs(1 - columnSum) <= tolerance) {
					
					
					
				}
				else{
					
					result = false;
					
				}

			}
		}
		else {
			
			result = false;
			
		}
		
		return result;
		
	}
	
	// Method to carry out the power algorithm on the matrix
	// Inputs: matrix, empty array for eigenvector
	// Output: updates eigenvector in main, returns a boolean if algoritm was successful
	public static boolean powerAlgorithm(double[][] matrix, double[] eigenvector) {
		
		boolean result = true;
		
		for(int i = 0; i < eigenvector.length; i++) {
			
			eigenvector[i] = 1;
			
		}
		
		double eigenvalue = 0;
		double prevEigenvalue = 0;
		
		// Maximum amount of iterations can be changed here if want more or less iterations.
		int maxIterations = 100;
		
		double tolerance = 0.000001;
		
		for(int k = 1; k <= maxIterations; k++) {
			
			double[] y = calculateY(matrix, eigenvector);
			prevEigenvalue = eigenvalue;
			eigenvalue = largestYValue(y);
			
			if(largestYValue(y) != 0) {
				
				for(int i = 0; i < eigenvector.length; i++) {
					
					eigenvector[i] = y[i]/largestYValue(y);
					
				}
			}
			// If the largest y value is 0, exit the algorithm.
			else {
				
				System.out.println("Eigenvalue zero. Select new r(1) and restart.");
				k = maxIterations + 1;
				result = false;
				
			}
			
			// Exits the algorithm if convergence is met
			if(Math.abs(eigenvalue - prevEigenvalue) < tolerance) {
				
				k = maxIterations + 1;
				
			}
			
			// If the max iterations was met before convergence was met, state so.
			if(k == maxIterations) {
				
				System.out.println("Max iteration exceeded.");
				
			}
			
		}
		
		return result;
		
	}
	
	// Method to calculate the y vector
	// Inputs: matrix A, vector r
	// Output: returns y vector
	public static double[] calculateY(double[][] A, double[] r) {
		
		double[] y = new double[A.length];
		
		for(int i = 0; i < y.length; i++) {
			
			y[i] = dotProduct(A[i], r);
			
		}
		
		return y;
		
	}
	
	// Method to find the largest value in the y vector
	// Inputs: y vector
	// Output: returns the largest y value
	public static double largestYValue(double[] y) {
		
		double largestValue = 0;
		
		for(int i = 0; i < y.length; i++) {
			
			if(y[i] > largestValue) {
				
				largestValue = y[i];
				
			}
		}
		
		return largestValue;
		
	}
	
	// Method to carry out the dot product
	// Inputs: two vectors
	// Output: the dot product of the two vectors
	public static double dotProduct(double[] row, double[] r) {
		
		int size = row.length;
		double dotProduct = 0;
		
		for(int i = 0; i < size; i++) {
			
			dotProduct += row[i] * r[i];
			
			
		}
		
		return dotProduct;
		
	}

}
