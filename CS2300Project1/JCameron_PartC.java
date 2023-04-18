import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class JCameron_PartC {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File inputFile = new File("test_input.txt");
		
		double[] distances = partCSubPart1(inputFile);
		
		for(int i =0; i < distances.length; i++) {
			
			System.out.print(distances[i] + " ");
			
		}
		
		partCSubPart2(inputFile);
		
		

	}
	
	public static double[] partCSubPart1(File inputFile) throws IOException {
		
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
		return distances;
		
	}
	
	public static void partCSubPart2(File inputFile) throws IOException {
		
		Scanner readInput = new Scanner(inputFile);
		
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
			
			/*double[] pointW = {triPoint2[0] - triPoint1[0], triPoint2[1] - triPoint1[1], triPoint2[2] - triPoint1[2]};
			double[] pointZ = {triPoint3[0] - triPoint1[0], triPoint3[1] - triPoint1[1], triPoint3[2] - triPoint1[2]};*/
			
			double[] pointW = {2, 4, 4};
			double[] pointZ = {-2, 0, 2};
			
			double[][] matrixA = {{pointW[0], pointZ[0], -vectorV[0]},
								  {pointW[1], pointZ[1], -vectorV[1]},		// Creating a matrix to perform gauss elim on
								  {pointW[2], pointZ[2], -vectorV[2]}};
			
			/*double[] vectorB = {pointX[0] - triPoint1[0], pointX[1] - triPoint1[1], pointX[2] - triPoint1[2]};*/
			double[] vectorB = {4, -2, 0};
			double[] vectorU = new double[3];
			
			
			// Find element in largest absolute value in column j
			for(int j = 0; j < 2; j++) {
				double largestValue = 0;
				int indexLargestValue = 0;
				
				// Pivoting step???
				for(int r = j; r < 3; r++) {
					
					if(Math.abs(matrixA[r][j]) > largestValue) {
						
						indexLargestValue = r;
						largestValue = Math.abs(matrixA[r][j]);
						
					}
					
				}
				
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
			for(int k = 1; k >= 0; k--) {
				
				vectorU[k] = (1 / matrixA[k][k]) * (vectorB[k] - (matrixA[k][k+1] * vectorU[k + 1]));
				System.out.println(vectorU[k]);
				
			}
			
			/*if((vectorU[0] > 0 && vectorU[0] < 1) && (vectorU[1] > 0 && vectorU[1] < 1) && (vectorU[0] + vectorU[1] < 1)) {
				
				double[] pointOfIntersection = new double[3];
				
				for(int i = 0; i < pointOfIntersection.length; i++) {
					
					pointOfIntersection[i] = pointX[i] + (vectorV[i] * vectorU[2]);
					System.out.print(pointOfIntersection[i] + " ");
					
				}
				
				System.out.println();
				
			}
			else {
				
				System.out.println("Does not intersect.");
				
			}*/
			
		}
		
		
	}
	
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
	
	public static double normalizePlane(double[] planeNormal) {
		
		double planeNormalLength = Math.sqrt(Math.pow(planeNormal[0], 2) + Math.pow(planeNormal[1], 2) + Math.pow(planeNormal[2], 2));
		
		return planeNormalLength;
		
	}
	
	public static double distancePointPlane(double[] pointOnPlane, double[] planeNormal, double[] currentPoint, double planeNormalLength) {
		
		// d = t||n||
		
		double[] negPlaneNormal = {planeNormal[0] * -1, planeNormal[1] * -1, planeNormal[2] * -1}; 
		
		double t = (dotProduct(negPlaneNormal, pointOnPlane) + dotProduct(planeNormal, currentPoint)) / dotProduct(planeNormal, planeNormal);
		
		double distance = t * planeNormalLength;
		
		return distance;
		
	}
	
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
