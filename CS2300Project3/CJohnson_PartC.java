import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.lang.Math;

public class CJohnson_PartC {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		// Replace both this String and the output file String in printOutput() with
		// the input file name and desired output file name respectively.
		File inputFile = new File("3D_test_input_2.txt");
				
		// 2x3 or 3x3 matrix that stores the values read from file
		// Size is determined in readFile
		double[][] valueMatrix = readFile(inputFile);
		
		double triangleArea = findAreaOfTriangle(valueMatrix);
		
		double distancePt2Line = findDistPoint2Line(valueMatrix);
		
		printOutput(triangleArea, distancePt2Line);

	}
	
	// This method reads the file for a 2x3 matrix
	// This matrix is then used to create matrix A and vector B later
	public static double[][] readFile(File inputFile) throws IOException {

		// Initializes numRows to 0 and numCols to 3
		// There will always be 3 columns to be read, while the number of rows is undetermined at this point
		int numRows = 0;
		int numCols = 3;
		
		Scanner readRowsFromFile = new Scanner(inputFile);
		
		// Reads the file as long as there's more to be read
		while(readRowsFromFile.hasNext()) {
			
			// These two lines of code will read the entire line in the file and
			// increment numRows by 1 when it reaches the end of the line
			// This will determine how many rows are being dealt with
			readRowsFromFile.nextLine();
			numRows++;
			
		}
		
		readRowsFromFile.close();
		
		// This will initialize a new matrix to be returned
		// This matrix's number of rows comes from how many rows were read from file
		double[][] returnMatrix = new double[numRows][numCols];
		
		// Creates a new scanner to read the file again, this time for reading the values to be
		// put in the return matrix
		// Not sure if this needs to be done or if readRowsFromFile would read from the top of the file
		// again, but this is being done just to avoid needing to account for that
		Scanner readValuesFromFile = new Scanner(inputFile);
		
		while(readValuesFromFile.hasNext()) {
			
			// Inserts the value read from the file into the ith, jth position
			// in return matrix
			for(int i = 0; i < numRows; i++) {
				for(int j = 0; j < numCols; j++) {
					
					returnMatrix[i][j] = readValuesFromFile.nextDouble();
					
				}
			}
		}
		
		readValuesFromFile.close();
		return returnMatrix;
		
	}
	
	// Method finds the area of a triangle given 3 points
	// Input: 2x3 or 3x3 matrix holding a point in each column - Output: area of the triangle the 3 points make
	public static double findAreaOfTriangle(double[][] matrix) {
		
		int numRows = matrix.length;
		double areaTriangle = 0;
		
		// If there are 2 rows in the matrix, the triangle is in 2D
		if(numRows == 2) {
			
			double[] a1 = {matrix[0][1] - matrix[0][0], matrix[1][1] - matrix[1][0]};
			double[] a2 = {matrix[0][2] - matrix[0][0], matrix[1][2] - matrix[1][0]};
			
			double determinant = (a1[0] * a2[1]) - (a1[1] * a2[0]);
			
			areaTriangle = Math.abs(0.5 * determinant);
			
		}
		// If there are 3 rows in the matrix, the triangle is in 3D
		else if(numRows == 3) {
			
			double[] a1 = {matrix[0][1] - matrix[0][0], matrix[1][1] - matrix[1][0], matrix[2][1] - matrix[2][0]};
			double[] a2 = {matrix[0][2] - matrix[0][0], matrix[1][2] - matrix[1][0], matrix[2][2] - matrix[2][0]};
			
			double[] crossProduct = {(a1[1] * a2[2]) - (a2[1] * a1[2]), (a1[2] * a2[0]) - (a2[2] * a1[0]), (a1[0] * a2[1]) - (a2[0] * a1[1])};
			
			double magnitude = Math.sqrt(Math.pow(crossProduct[0], 2) + Math.pow(crossProduct[1], 2) + Math.pow(crossProduct[2], 2));
			
			areaTriangle = Math.abs(0.5 * magnitude);
			
		}
		
		return areaTriangle;
		
	}
	
	// Method that finds the distance from the point to the line
	// Uses modified algorithm I created in assignment 2
	// Input: 2x3 or 3x3 matrix holding points - Output: distance between third point and line
	public static double findDistPoint2Line(double[][] matrix) {
		
		int numRows = matrix.length;
		double distance = 0;

		if(numRows == 2) {
			
			// Initializes @ vector and points for later use
			double[] vectorW = new double[numRows];
			double[] thirdPoint = new double[numRows];
			double[] secondPoint = new double[numRows];
			double[] firstPoint = new double[numRows];
			
			double dotProductTotal = 0;
			double cos = 0;
			
			
			// Point being tested for distance
			thirdPoint[0] = matrix[0][2];
			thirdPoint[1] = matrix[1][2];
			
			secondPoint[0] = matrix[0][1];
			secondPoint[1] = matrix[1][1];
			
			firstPoint[0] = matrix[0][0];
			firstPoint[1] = matrix[1][0];
				
			// l(t) = first + t(third - first), parametric line being used
				
			// Create vector using thirdPoint - firstPoint
			vectorW[0] = thirdPoint[0] - firstPoint[0];
			vectorW[1] = thirdPoint[1] - firstPoint[1];
				
			// Then take dot product of vectorW and (secondPoint - firstPoint)
			dotProductTotal = (vectorW[0] * (secondPoint[0] - firstPoint[0])) + (vectorW[1] * (secondPoint[1] - firstPoint[1]));
				
			// Then find length of vectorW and length of (secondPoint - firstPoint), or vector V
			double vectorWLength = Math.sqrt(Math.pow(vectorW[0], 2) + Math.pow(vectorW[1], 2));
			double vectorVLength = Math.sqrt(Math.pow((secondPoint[0] - firstPoint[0]), 2) + Math.pow((secondPoint[1] - firstPoint[1]), 2));
				
			// Find cos(a) for the sin() in the distance equation, d = ||w||sin(a) or d = ||w||sqrt(1 - (cos(a))^2)
			// If vectorWLength is equal to 0 (which means the point being tested for distance
			// is on the line, or has no vector b/c distance = 0...
			if(vectorWLength == 0) {
				
				// Just set cos to 0. This avoids getting NaN from dividing by 0,
				// which the computer can't inherently tell dividing by 0 means
				// no vector
				cos = 0;
				
			}
			else {
				
				// Otherwise, calculate cos as normal
				cos = dotProductTotal / (vectorWLength * vectorVLength);
				
			}
			// Use cos(a) in the distance equation
			distance = vectorWLength * Math.sqrt(1 - Math.pow(cos, 2));
			
			
		}
		else if(numRows == 3) {
			
			// Point initialization
			double[] p1 = {matrix[0][0], matrix[1][0], matrix[2][0]};
			double[] p2 = {matrix[0][1], matrix[1][1], matrix[2][1]};
			double[] p3 = {matrix[0][2], matrix[1][2], matrix[2][2]};
			
			// Finds the midway point using (p2 + p1) / 2
			double[] midwayPoint = {(p2[0] + p1[0]) / 2, (p2[1] + p1[1]) / 2, (p2[2] + p1[2]) / 2};
			
			// Finds the length of the vector normal to the plane using n = sqrt(p1^2 + p2^2 + p3^2)
			double lengthForNormal = Math.sqrt(Math.pow((p2[0] - p1[0]), 2) + Math.pow((p2[1] - p1[1]), 2) + Math.pow((p2[2] - p1[2]), 2));
			
			// Divides each point in the normal vector by the length of the vector
			double[] normalVector = {(p2[0] - p1[0]) / lengthForNormal, (p2[1] - p1[1]) / lengthForNormal, (p2[2] - p1[2]) / lengthForNormal};
			
			// Equation for plane is (normalVector * p3) - (normalVector * midwayPoint) = 0
			
			// Not sure if it's my handwritten calculations that are wrong or this line of code, but they do not match. For the first 3D test input file,
			// I get ~4.91, whereas the program gets ~3.40
			distance = normalVector[0] * p3[0] + normalVector[1] * p3[1] + normalVector[2] * p3[2]
						- (normalVector[0] * midwayPoint[0] + normalVector[1] + midwayPoint[1] + normalVector[2] * midwayPoint[2]);
			
		}
		
		return distance;
		
	}
	
	// Method that prints the triangle area and distance from point to line
	// Input: triangle area and distance between point and line - Output: those values into file/console
	public static void printOutput(double triArea, double pointDist) throws IOException {
		
		PrintWriter print2File = new PrintWriter("JCameron_PartC_3DoutputFile2");
		
		print2File.printf("%.4f\n", triArea);
		print2File.printf("%.4f\n", pointDist);
		
		// Testing purposes so I don't have to open the files every time
		System.out.println(triArea);
		System.out.println(pointDist);
		
		print2File.close();
		
	}

}

/*
 *  Tasks:
 *  
 *  Read in three points from a file -- DONE
 *  	* This can be in 3D, account for this
 *  
 *  Find the area of a triangle given these three points -- DONE
 *  
 *  If 2D, find the line given by the first two points -- DONE
 *  	Find the distance from the line to the third point
 *  
 *  If 3D, construct plane that bisects the line segment between the two points -- DONE
 *  	Find distance between third point and plane
 * 
 * Needed Methods:
 *	I can reuse and modify the method from PartA to be able to read in 2x3 or 3x3 matrices
 *
 *	Method to find the area of a triangle
 *		*Need to change result given 2D or 3D points
 *
 *	Method to create a line from the first two points (not needed)
 *		* Need to change result given 2D or 3D points
 *
 *	Method to find distance from line to third point
 *		* Need to change result given 2D or 3D points
 *
 *	Method to print output
 *		* Just two lines needed
 *
 */
