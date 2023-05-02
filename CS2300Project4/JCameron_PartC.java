import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class JCameron_PartC {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File inputFile = new File("test_input.txt");
		
		partCSubPart1(inputFile);
		
		partCSubPart2(inputFile);
		
	}
	
	// Method that carries out the first part of part C
	// Input: file to be read
	// Output: no return, prints distances between line and plane to file
	public static void partCSubPart1(File inputFile) throws IOException {
		
		Scanner readInput = new Scanner(inputFile);
		
		int numRows = readLines(inputFile);
		
		double[] distances = new double[numRows];
		int index = 0;
		
		while(readInput.hasNext()) {
			
			double[] pointOnPlane = new double[3];
			double[] planeNormal = new double[3];
			double[] pointCoordinates = new double[3];
			
			
			// For loop that reads in the first three doubles for the eye location
			for(int i = 0; i < pointOnPlane.length; i++) {
				
				pointOnPlane[i] = readInput.nextDouble();
				
			}
			
			// For loop that reads in the second three doubles for the light direction
			for(int i = 0; i < planeNormal.length; i++) {
				
				planeNormal[i] = readInput.nextDouble();
				
			}
			
			// For loop that reads in the second three doubles for the light direction
			for(int i = 0; i < pointCoordinates.length; i++) {
						
				pointCoordinates[i] = readInput.nextDouble();
						
			}
			
			double planeNormalLength = normalizePlane(planeNormal);
			
			distances[index] = distancePointPlane(pointOnPlane, planeNormal, pointCoordinates, planeNormalLength);
			index++;
			
		}
		
		readInput.close();
		PrintWriter print2File = new PrintWriter("JCameron_output_partC_sub1.txt");
		
		for(int i = 0; i < distances.length; i++) {
			
			print2File.printf("%.2f ", distances[i]);
			
		}
		
		print2File.close();
		
	}
	
	// Method that carries out the second part of part C
	// Input: file to be read
	// Output: no return, prints intersect point/"Does not intersect." to file
	public static void partCSubPart2(File inputFile) throws IOException {
		
		Scanner readInput = new Scanner(inputFile);
		PrintWriter print2File = new PrintWriter("JCameron_output_partC_sub2.txt");
		
		double[] pointX = new double[3];
		double[] pointY = new double[3];
		double[][] matrixG = {{1,0,0},
							  {0,1,0},		// Identity matrix
							  {0,0,1}};
		
		
		// For loop that reads in the first three doubles for the eye location
		for(int i = 0; i < pointX.length; i++) {
						
			pointX[i] = readInput.nextDouble();
						
		}
					
		// For loop that reads in the second three doubles for the light direction
		for(int i = 0; i < pointY.length; i++) {
						
			pointY[i] = readInput.nextDouble();
						
		}
		
		/*double[] vectorV = {pointY[0] - pointX[0], pointY[1] - pointX[1], pointY[2] - pointX[2]};*/
		double[] vectorV = {0, 2, 4};
		
		readInput.nextLine();
		
		while(readInput.hasNext()) {
			
			double[] triPoint1 = new double[3];
			double[] triPoint2 = new double[3];
			double[] triPoint3 = new double[3];
			
			// For loop that reads in the first three doubles for the eye location
			for(int i = 0; i < triPoint1.length; i++) {
							
				triPoint1[i] = readInput.nextDouble();
							
			}
			
			// For loop that reads in the first three doubles for the eye location
			for(int i = 0; i < triPoint2.length; i++) {
										
				triPoint2[i] = readInput.nextDouble();
										
			}
			
			// For loop that reads in the first three doubles for the eye location
			for(int i = 0; i < triPoint3.length; i++) {
										
				triPoint3[i] = readInput.nextDouble();
										
			}
			
			double[] pointW = {triPoint2[0] - triPoint1[0], triPoint2[1] - triPoint1[1], triPoint2[2] - triPoint1[2]};
			double[] pointZ = {triPoint3[0] - triPoint1[0], triPoint3[1] - triPoint1[1], triPoint3[2] - triPoint1[2]};
			
			
			double[][] matrixA = {{pointW[0], pointZ[0], -vectorV[0]},
								  {pointW[1], pointZ[1], -vectorV[1]},		// Creating a matrix to perform gauss elim on
								  {pointW[2], pointZ[2], -vectorV[2]}};
			
			double[] vectorB = {pointX[0] - triPoint1[0], pointX[1] - triPoint1[1], pointX[2] - triPoint1[2]};
			double[] vectorU = new double[3];
			
			
			// Find element in largest absolute value in column j
			for(int j = 0; j < 2; j++) {
				double largestValue = 0;
				int indexLargestValue = 0;
				
				// Pivoting step, exchanges rows for largest absolute value to be on diagonal of column
				for(int r = j; r < 3; r++) {
					
					if(Math.abs(matrixA[r][j]) > largestValue) {
						
						indexLargestValue = r;
						largestValue = Math.abs(matrixA[r][j]);
						
					}
					
				}
				
				// Row pivot
				if(indexLargestValue > j) {
					
					double[] tempRow = {matrixA[indexLargestValue][0], matrixA[indexLargestValue][1], matrixA[indexLargestValue][2]};
					double tempB = vectorB[indexLargestValue];
					
					matrixA[indexLargestValue][0] = matrixA[j][0];
					matrixA[indexLargestValue][1] = matrixA[j][1];
					matrixA[indexLargestValue][2] = matrixA[j][2];
					
					matrixA[j][0] = tempRow[0];
					matrixA[j][1] = tempRow[1];
					matrixA[j][2] = tempRow[2];
					
					vectorB[indexLargestValue] = vectorB[j];
					vectorB[j] = tempB;
					
					
				}
				
				// If there's not a zero on the diagonal, continue with forward elim
				if(matrixA[j][j] != 0) {
					
					for(int i = j + 1; i < 3; i++) {
						
						matrixG[i][j] = matrixA[i][j]/matrixA[j][j];
						matrixA[i][j] = 0;
						
						for(int k = j+1; k < 3; k++) {
							
							matrixA[i][k] = matrixA[i][k] - (matrixG[i][j] * matrixA[j][k]);
							
						}
						
						vectorB[i] = vectorB[i] - (matrixG[i][j] * vectorB[j]);
						
						
					}
							
				}
				
			}
			
			// Perform back substitution
			vectorU[2] = vectorB[2]/matrixA[2][2];
			vectorU[1] = (1/matrixA[1][1]) * (vectorB[1] - (matrixA[1][2] * vectorU[2]));
			vectorU[0] = (1/matrixA[0][0]) * (vectorB[0] - (matrixA[0][1] * vectorU[1]) - (matrixA[0][2] * vectorU[2]));
			
			// if u1 and u2 are between 0 and 1, and they both add up to be less than 1,
			// then the line and bounded plane intersect
			if((vectorU[0] > 0 && vectorU[0] < 1) && (vectorU[1] > 0 && vectorU[1] < 1) && (vectorU[0] + vectorU[1] < 1)) {
				
				double[] pointOfIntersection = new double[3];
				
				
				for(int i = 0; i < pointOfIntersection.length; i++) {
					
					pointOfIntersection[i] = pointX[i] + (vectorV[i] * vectorU[2]);
					print2File.print(pointOfIntersection[i] + " ");
					
				}
				
				print2File.println();
				
			}
			else {
				
				print2File.println("Does not intersect.");
				
			}
			
		}
		
		readInput.close();
		print2File.close();
		
		
	}
	
	// Reads how many lines are in the file
	// Input: file to be read
	// Output: number of lines in file
	public static int readLines(File inputFile) throws IOException {
		
		Scanner readLines = new Scanner(inputFile);
		
		int numRows = 0;
		
		while(readLines.hasNext()) {
			
			readLines.nextLine();
			numRows++;
			
		}
		
		readLines.close();
		return numRows;
		
	}
	
	// Method that finds the length of the normal plane (used to normalize the plane
	// as well, hence the method name, but now doesn't)
	// Input: plane's normal vector
	// Output: length of the normal vector
	public static double normalizePlane(double[] planeNormal) {
		
		double planeNormalLength = Math.sqrt(Math.pow(planeNormal[0], 2) + Math.pow(planeNormal[1], 2) + Math.pow(planeNormal[2], 2));
		
		return planeNormalLength;
		
	}
	
	// Method that calculates the distance between the point and plane
	// Input: point on the plane, normal of the plane, current point, and the plane normal length
	// Output: the distance between the point and the plane
	public static double distancePointPlane(double[] pointOnPlane, double[] planeNormal, double[] currentPoint, double planeNormalLength) {
		
		// d = t||n||
		
		double[] negPlaneNormal = {planeNormal[0] * -1, planeNormal[1] * -1, planeNormal[2] * -1}; 
		
		double t = (dotProduct(negPlaneNormal, pointOnPlane) + dotProduct(planeNormal, currentPoint)) / dotProduct(planeNormal, planeNormal);
		
		double distance = t * planeNormalLength;
		
		return distance;
		
	}
	
	// Method that carries out the dot product between two vectors
	// Input: the two vectors to be "dotted together"
	// Output: dot product of the two vectors
	public static double dotProduct(double[] vector1, double[] vector2) {
		
		double dotProduct = 0;
		
		if(vector1.length == vector2.length) {
			
			for(int i = 0; i < vector1.length; i++) {
				
				dotProduct += vector1[i] * vector2[i];
				
			}
			
		}
		
		return dotProduct;
			
	}
	
	
}
