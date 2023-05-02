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
		
		if(isValidMatrix(inputMatrix)) {
			
			double[] eigenvector = powerAlgorithm(inputMatrix);
			
			for(int i = 0; i < eigenvector.length; i++) {
				
				System.out.print(eigenvector[i] + " ");
				
			}
			
			
		}
		else {
			
			System.out.println("Input is invalid, please use a valid input.");
			
		}
		
	}
	
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
	
	public static boolean isValidMatrix(double[][] matrixToCheck) {
		
		boolean result = true;
		double columnSum = 0;
		double rowSum = 0;
		
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
				
				if(Math.ceil(rowSum)!= 1) {
					if(Math.ceil(columnSum) != 1) {
						
						result = false;
						
					}
					
					
				}
				else if(Math.ceil(columnSum) != 1) {
					if(Math.ceil(rowSum) != 1) {
						
						result = false;
						
					}
					
					
				}

			}
		}
		else {
			
			result = false;
			
		}
		
		return result;
		
	}
	
	public static double[] powerAlgorithm(double[][] matrix) {
		
		double[] eigenvector = new double[matrix.length];
		
		for(int i = 0; i < eigenvector.length; i++) {
			
			eigenvector[i] = 1;
			
		}
		
		double eigenvalue = 0;
		double prevEigenvalue = 0;
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
			
			if(Math.abs(eigenvalue - prevEigenvalue) < tolerance) {
				
				k = maxIterations + 1;
				
			}
			
			if(k == maxIterations) {
				
				System.out.println("Max iteration exceeded.");
				
			}
			
		}
		
		return eigenvector;
		
	}
	
	public static double[] calculateY(double[][] A, double[] r) {
		
		double[] y = new double[A.length];
		
		for(int i = 0; i < y.length; i++) {
			
			y[i] = dotProduct(A[i], r);
			
		}
		
		return y;
		
	}
	
	public static double largestYValue(double[] y) {
		
		double largestValue = 0;
		
		for(int i = 0; i < y.length; i++) {
			
			if(y[i] > largestValue) {
				
				largestValue = y[i];
				
			}
		}
		
		return largestValue;
		
	}
	
	public static double dotProduct(double[] row, double[] r) {
		
		int size = row.length;
		double dotProduct = 0;
		
		for(int i = 0; i < size; i++) {
			
			dotProduct += row[i] * r[i];
			
			
		}
		
		return dotProduct;
		
	}

}
