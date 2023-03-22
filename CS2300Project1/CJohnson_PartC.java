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
		File inputFile = new File("test_input_1.txt");
				
		// 2x3 or 3x3 matrix that stores the values read from file
		// Size is determined in readFile
		double[][] valueMatrix = readFile(inputFile);
		
		double triangleArea = findAreaOfTriangle(valueMatrix);

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
	
	public static double findAreaOfTriangle(double[][] matrix) {
		
		int numRows = matrix.length;
		double areaTriangle = 0;
		
		if(numRows == 2) {
			
			System.out.println("Find area of triangle in 2D");
			
		}
		else if(numRows == 3) {
			
			System.out.println("Find area of triangle in 3D");
			
		}
		
		return areaTriangle;
		
	}

}

/*
 *  Tasks:
 *  
 *  Read in three points from a file -- DONE
 *  	* This can be in 3D, account for this
 *  
 *  Find the area of a triangle given these three points
 *  
 *  If 2D, find the line given by the first two points
 *  	Find the distance from the line to the third point
 *  
 *  If 3D, construct plane that bisects the line segment between the two points
 *  	Find distance between third point and plane
 * 
 * Needed Methods:
 *	I can reuse and modify the method from PartA to be able to read in 2x3 or 3x3 matrices
 *
 *	Method to find the area of a triangle
 *		*Need to change result given 2D or 3D points
 *
 *	Method to create a line from the first two points
 *		* Need to change result given 2D or 3D points
 *
 *	Method to find distance from line to third point
 *		* Need to change result given 2D or 3D points
 *
 *	Method to print output
 *		* Just two lines needed
 *
 */
