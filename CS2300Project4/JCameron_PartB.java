import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.lang.Math;

public class JCameron_PartB {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File inputFile = new File("test_input.txt");
		
		double[] pointOnPlane = new double[3];
		double[] planeNormal = new double[3];
		double[] projectionDirection = new double[3];
		
		readFileValues(inputFile, pointOnPlane, planeNormal, projectionDirection);
		
		normalizePlane(planeNormal);
		
		double[][] parallelProjectionMatrix = parallelProjection(inputFile, pointOnPlane, planeNormal, projectionDirection);
		double[][] perspectiveProjectionMatrix = perspectiveProjection(inputFile, pointOnPlane, planeNormal);
		
		print2File(parallelProjectionMatrix, "JCameron_output_PartB_subA.txt");
		print2File(perspectiveProjectionMatrix, "JCameron_output_PartB_subB.txt");
		
		
		
		
	}
	
	// Method that reads the files and puts values in their vectors
	// Input: inputFile, pointOnPlane, planeNormal, projectionDirection
	// Output: no return, updates passed in vectors in main
	public static void readFileValues(File inputFile, double[] pointOnPlane, double[] planeNormal, double[] projectionDirection) throws IOException {
		
		Scanner readInput = new Scanner(inputFile);
		
		// For loop that reads in the first three doubles for the point on plane
		for(int i = 0; i < pointOnPlane.length; i++) {
			
			pointOnPlane[i] = readInput.nextDouble();
			
		}
		
		// For loop that reads in the second three doubles for the plane normal
		for(int i = 0; i < planeNormal.length; i++) {
			
			planeNormal[i] = readInput.nextDouble();
			
		}
		
		// For loop that reads in the last three doubles for the projection direction
		for(int i = 0; i < projectionDirection.length; i++) {
					
			projectionDirection[i] = readInput.nextDouble();
					
		}
		
		readInput.close();
		
	}
	
	// Method that normalizes the plane normal
	// Input: planeNormal vector
	// Output: no return, updates the normal to the plane in main
	public static void normalizePlane(double[] planeNormal) {
		
		double planeNormalLength = Math.sqrt(Math.pow(planeNormal[0], 2) + Math.pow(planeNormal[1], 2) + Math.pow(planeNormal[2], 2));
		
		for(int i = 0; i < planeNormal.length; i++) {
			
			planeNormal[i] = planeNormal[i] / planeNormalLength;
			
		}
		
	}
	
	// Method that reads the lines in the file, and skips the first line
	// Input: inputFile
	// Output: number of lines in the file minus the first line
	public static int readLines(File inputFile) throws IOException {
		
		Scanner readLines = new Scanner(inputFile);
		readLines.nextLine();
		
		int numRows = 0;
		
		while(readLines.hasNext()) {
			
			readLines.nextLine();
			numRows++;
			
		}
		
		readLines.close();
		return numRows;
		
	}
	
	// Method that prints the values to a file
	// Input: inputFile, name of file
	// Output: no return, writes to a new file
	public static void print2File(double[][] points, String nameOfFile) throws IOException {
		
		PrintWriter print2File = new PrintWriter(nameOfFile);
		
		for(int i = 0; i < points.length; i++) {
			for(int j = 0; j < points[0].length; j++) {
				
				print2File.print(points[i][j] + " ");
				System.out.print(points[i][j] + " ");
				
			}
			
			print2File.println();
			System.out.println();
			
		}
		
		print2File.close();
		
	}
	
	// Method that carries out parallel projection
	// Input: inputFile, point on plane, plane normal, projection direction
	// Output: matrix holding the three projected points per line, as many lines are there were lines in the file - 1
	public static double[][] parallelProjection(File inputFile, double[] pointOnPlane, double[] planeNormal, double[] projectionDirection) throws IOException {
		
		int numRows = readLines(inputFile);
		
		
		double[] currentPoint = new double[3];
		double[][] projectedPoints = new double[numRows][9];
		int currentRow = 0;
		int numPoint = 0;
		
		Scanner readPoints = new Scanner(inputFile);
		readPoints.nextLine();
		
		while(readPoints.hasNext()) {
			
			// Read the next three doubles in the file in and make it our current point for this iteration
			for(int i = 0; i < currentPoint.length; i++) {
				
				currentPoint[i] = readPoints.nextDouble();
				
			}
			
			// Creates the point as a result of pointOnPlane - currentPoint (q - x)
			double[] vectorQMinusX = new double[3];
			for(int i = 0; i < vectorQMinusX.length; i++) {
				
				vectorQMinusX[i] = pointOnPlane[i] - currentPoint[i];
				
			}
			
			// Calculates the projected point using x' = x + [([q-x] . n)/v . n]v
			for(int i = 0; i < currentPoint.length; i++) {
				
				// Carries out parallel projection on the current point and places it in the projected points matrix
				projectedPoints[currentRow][i + (3 * numPoint)] = currentPoint[i] + ((dotProduct(vectorQMinusX, planeNormal) / dotProduct(projectionDirection, planeNormal)) * projectionDirection[i]);
				
			}
			
			// If the point just read was the third point in the line
			// set the numPoint back to 0 and increment the current row to properly use matrix
			if(numPoint == 2) {
				
				numPoint = 0;
				currentRow++;
				
			}
			else {
				
				numPoint++;
				
			}
			
		}
		
		readPoints.close();
		return projectedPoints;
		
	}
	
	// Method that carries out perspective projection, very similar method compared to parallel projection but with a different equation
	// Input: inputFile, point on plane, plane normal
	// Output: matrix that holds all the projected points using perspective projection
	public static double[][] perspectiveProjection(File inputFile, double[] pointOnPlane, double[] planeNormal) throws IOException {
		
		int numRows = readLines(inputFile);
		
		double[] currentPoint = new double[3];
		double[][] projectedPoints = new double[numRows][9];
		int currentRow = 0;
		int numPoint = 0;
		
		Scanner readPoints = new Scanner(inputFile);
		readPoints.nextLine();
		
		while(readPoints.hasNext()) {
			
			// Read the next three doubles in the file in and make it our current point for this iteration
			for(int i = 0; i < currentPoint.length; i++) {
							
				currentPoint[i] = readPoints.nextDouble();
							
			}
			
			for(int i = 0; i < currentPoint.length; i++) {
				
				projectedPoints[currentRow][i + (3 * numPoint)] = (dotProduct(pointOnPlane, planeNormal) / dotProduct(currentPoint, planeNormal)) * currentPoint[i];
				
			}

			
			if(numPoint == 2) {
				
				numPoint = 0;
				currentRow++;
				
			}
			else {
				
				numPoint++;
				
			}
			
		}
		
		readPoints.close();
		return projectedPoints;
		
	}
	
	// Method that carries out the dot product between two vectors
	// Input: two vectors
	// Output: dot product of the two vectors
	public static double dotProduct(double[] vector1, double[] vector2) {
		
		double dotProduct = 0;
		
		// If the lengths of the vectors match, carry out the dot product process
		if(vector1.length == vector2.length) {
			
			for(int i = 0; i < vector1.length; i++) {
				
				dotProduct += vector1[i] * vector2[i];
				
			}
			
		}
		
		return dotProduct;
			
	}

}
