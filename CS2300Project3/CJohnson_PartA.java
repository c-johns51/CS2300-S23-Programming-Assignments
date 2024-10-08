import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class CJohnson_PartA {
	
	public static void main(String[] args) throws IOException {
		
		// Replace both this String and the output file String in printOutput() with
		// the input file name and desired output file name respectively.
		File inputFile = new File("test_input_4.txt");
		
		// 2x3 matrix that stores the values read from file
		double[][] valueMatrix = readFile(inputFile);
		
		// Empty 2x2 matrix A to be written to and used later
		double[][] matrixA = new double[2][2];
		// Empty vector b to be written to and used later
		double[] vectorB = new double[2];
		
		// Sends matrixA and vectorB to be updated using the values from valueMatrix
		createMatrixAndVector(valueMatrix, matrixA, vectorB);
		
		
		double detA = calculateDeterminant(matrixA);
		// If the determinant of matrix A is 0:
		
		double[] vectorX = new double[2];
		String systemIs = "";
		
		if(detA == 0) {
			
			// Determine if system is inconsistent or underdetermined
			systemIs = isInconsistentOrUnderdetermined(matrixA, vectorB);
				
		}

		solveForX(matrixA, vectorB, vectorX, detA);
		printOutput(systemIs, vectorX);
		
	}
	
	// This method reads the file for a 2x3 matrix
	// This matrix is then used to create matrix A and vector B later
	// Input: file to be read - Output: 2x3 matrix with values from file
	public static double[][] readFile(File inputFile) throws IOException {

		// Creates a 2x3 matrix to be written to
		double[][] returnMatrix = new double[2][3];
		
		Scanner readFile = new Scanner(inputFile);
		
		// Reads the file as long as there's more to be read
		while(readFile.hasNext()) {
			
			// Nested for loops that reads the values in file and places them in a matrix in program
			for (int i = 0; i < returnMatrix.length; i++) {
				for(int j = 0; j < returnMatrix[0].length; j++) {
					
					returnMatrix[i][j] = readFile.nextDouble();
					
				}
			}
		}
		
		readFile.close();
		return returnMatrix;
		
	}
	
	// Method that "splits" the valueMatrix into matrix A and vector B
	// Updates matrix A and vector B passed in from main
	// Input: 2x3 matrix from file, 2x2 matrix A, 2x1 vector B - Output: changes to matrix A and vector B
	public static void createMatrixAndVector(double[][] valueMatrix, double[][] matA, double[] vecB) {
		
		// Iterates through valueMatrix, places the first two columns into matA and
		// the last column into vecB
		for (int i = 0; i < valueMatrix.length; i++) {
			for(int j = 0; j < valueMatrix[0].length; j++) {
				
				matA[i][0] = valueMatrix[i][0];
				matA[i][1] = valueMatrix[i][1];
				
				vecB[i] = valueMatrix[i][2];
				
			}	
		}
	}
	
	// Calculates the determinant of matrix A, returns determinant value
	// Input: 2x2 matrix A - Output: determinant of matrix
	public static double calculateDeterminant(double[][] matA) {
		
		// Variable initialization
		// a, b, c, and d are not necessary but make the determinant equation easier to read
		double determinantA = 0;
		double a = matA[0][0];
		double b = matA[0][1];
		double c = matA[1][0];
		double d = matA[1][1];
		
		determinantA = (a * d) - (b * c);
		
		return determinantA;
		
	}
	
	// Method to update empty vector X passed by main
	// Inputs: 2x2 mat A, 2xa vec B, 2x1 empty vec X, determinant of mat A - Output: changes to vector X in main
	public static void solveForX(double[][] matA, double[] vecB, double[] vecX, double detA) {
		
		// Variable initialization for matA, vecB, and x1 and x2
		double a = matA[0][0];
		double b = matA[0][1];
		double c = matA[1][0];
		double d = matA[1][1];
		
		double b1 = vecB[0];
		double b2 = vecB[1];
		
		double x1 = 0;
		double x2 = 0;
		
		// If the determinant of matrix A wasn't 0:
		if(detA != 0) {
			
			// Values of inverse matrix: |e f|
			//							 |g h|
			double e = d/detA;
			double f = (-b)/detA;
			double g = (-c)/detA;
			double h = a/detA;
			
			// Equations to solve for x1 and x2
			x1 = (e*b1) + (f*b2);
			x2 = (g*b1) + (h*b2);
			
		}
		// If the determinant of matrix A is 0:
		else {
			
			// If value b in matrix A isn't 0:
			if(b != 0) {
				
				// Let x1 = 1, solve for x2
				x1 = 1;
				x2 = (b1 - a) / b;
				
			}
			// If value b in matrix A is 0 but value a in matrix A isn't:
			else if(b == 0 && a != 0) {
				
				// Let x2 = 1, solve for x1
				x2 = 1;
				x1 = b1/a;
				
			}
			
		}
		
		// Set vecX to x1 and x2
		// This updates vector X in main, no return
		vecX[0] = x1;
		vecX[1] = x2;
		
	}
 	
	// Method that determines if the system is inconsistent or underdetermined
	// Input: 2x2 mat A, 2x1 vec B - Output: "Inconsistent" or "Underdetermined"
	public static String isInconsistentOrUnderdetermined(double[][] matA, double[] vecB) {
		
		double a = matA[0][0];
		double b = matA[0][1];
		
		double b1 = vecB[0];
		
		String result = "";
		
		if(b == 0 && a == 0 && b1 != 0) {
			
			result = "Inconsistent";
			
		}
		else if(a == 0 && b == 0 && b1 == 0) {
			
			result = "Underdetermined";
			
		}
		else {
			
			result = "Can Solve for X";
			
		}
		
		return result;
		
	}
	
	// Method that prints to file/console
	// Inputs: state of system, vector X - Output: "System inconsistent" or "System underdetermined" or the values of vector X
	public static void printOutput(String systemIs, double[] vecX) throws IOException {
		
		// Change file output name to desired file name before running program
		PrintWriter print2File = new PrintWriter("JCameron_outputFile4.txt");
		
		if(systemIs.equals("Inconsistent") || systemIs.equals("Underdetermined")) {
			
			print2File.println("System " + systemIs);
			System.out.println("System " + systemIs);
			
		}
		else {
			
			print2File.printf("%.4f\n", vecX[0]);
			System.out.printf("%.4f\n", vecX[0]);
			print2File.printf("%.4f\n", vecX[1]);
			System.out.printf("%.4f\n", vecX[1]);
			
		}
		
		print2File.close();
		
	}

}


/*
 * Tasks:
 * 
 * Given a 2x2 matrix A and 2x1 vector b, find the 2x1 vector x where Ax=b
 * 
 * 1. Calculate the determinant of matrix A
 * 			det|A| = ad - bc, where A = |a b|
 * 										|c d|
 * 
 * If det|A| not 0:
 * 		The inverse of A (A^-1) is 1/det|A| * |d -b|
 * 											  |-c a|
 * 		So: |d/(ad-bc) -b/(ad-bc)| = |e f|
 * 			|-c(ad-bc)  a/(ad-bc)|	 |g h|	(Amazing matrix notations)
 * 
 * 		Then x = (A^-1)b
 * 		In my matrix notation: |x1| = |e f||b1|
 *							   |x2| = |g h||b2|
 *
 *			Then: x1 = e*b1 + f*b2
 *				  x2 = g*b1 + h*b2
 * 
 * If det|A| is 0, then must find if inconsistent or underdetermined:
 * 
 * 		Ax = b => |a b||x1| = |b1|
 * 				  |c d||x2| = |b2|
 * 		
 * 		And: a*x1 + b*x2 = b1
 * 			 c*x1 + d*x2 = b2
 * 
 * 		Then:
 * 			If b (in A mat) != 0, let x1 = 1, then x2 = (b1 - a)/b
 * 
 * 			If b = 0 and a != 0, let x2 = 1 then x1 = b1/a
 * 
 * 			If b = 0, a = 0, and b1 != 0, then **inconsistent**
 * 
 * 			If a, b, and b1 = 0, then **underdetermined**
 * 
 *  -- Methods --
 *  
 *  Method to calculate determinant (return boolean true/false if determinant = 0/!= 0?)
 *  Method to determine if inconsistent or underdetermined (if determinant = 0)
 *  
 */
