import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.lang.Math;

public class CJohnson_PartB {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File inputFile = new File("test_input_4.txt");
		
		// 2x3 matrix that stores the values read from file
		double[][] matrixA = createMatrixAFromFile(inputFile);
		
		double[][] eigenMatrix = findEigenvalues(matrixA);
		
		double[][] eigenVectors = findEigenvectors(matrixA, eigenMatrix);
		
		double[][] eigenVecTranspose = createTranspose(eigenVectors);
		
		double[][] eigenDecomp = doEigenDecomp(eigenVectors, eigenMatrix, eigenVecTranspose);
		
		int areMatricesEqual = areMatricesEqual(matrixA, eigenDecomp);
		
		printOutput(areMatricesEqual, eigenMatrix, eigenVectors, eigenDecomp);
		
		
				

		
	}
	
	// This method reads the file for a 2x3 matrix
	// Matrix from here will then be used to make matrix A, last column is disregarded.
	public static double[][] createMatrixAFromFile(File inputFile) throws IOException {

		// Creates a 2x3 matrix to be written to
		double[][] returnMatrix = new double[2][2];
		
		Scanner readFile = new Scanner(inputFile);
		
		for(int i = 0; i < returnMatrix.length; i++) {
			for(int j = 0; j < returnMatrix[0].length; j++) {
				
				returnMatrix[i][j] = readFile.nextDouble();
				
			}
			
			readFile.nextLine();
			
		}
		
		readFile.close();
		return returnMatrix;
		
	}
	
	// Method that finds the eigenvalues for matrix A
	public static double[][] findEigenvalues(double[][] matA) {
		
		double[][] eigenMatrix = new double[2][2];
		double a = matA[0][0];
		double b = matA[0][1];
		double c = matA[1][0];
		double d = matA[1][1];
		
		// Skip to the quadratic formula to find both eigenvalues
		eigenMatrix[0][0] = ((a + d) + Math.sqrt(Math.pow((a + d), 2) - (4 * ((a * d) - (b * c))))) / 2;
		eigenMatrix[1][1] = ((a + d) - Math.sqrt(Math.pow((a + d), 2) - (4 * ((a * d) - (b * c))))) / 2;
		
		return eigenMatrix;
		
	}
	
	// Method that finds the eigenvectors given the eigenvalues and matrix A, returns them as a matrix
	// with each column being an eigenvector
	public static double[][] findEigenvectors(double[][] matA, double[][] eigenMat) {
		
		double[][] eigenVecMatrix = new double[2][2];
		double[] eigen = new double[2];
		double[][] matACopy = new double[2][2];
		
		double r1 = 0;
		double r2 = 0;
		double normR = 0;
		
		eigen[0] = eigenMat[0][0];
		eigen[1] = eigenMat[1][1];
		
		for(int i = 0; i < eigen.length; i++) {
			
			
			matACopy[0][0] = matA[0][0] - eigen[i];
			matACopy[0][1] = matA[0][1];
			matACopy[1][0] = matA[1][0];
			matACopy[1][1] = matA[1][1] - eigen[i];
			
			// gaussianElim will update matACopy to the new matrix that gaussian elimination was performed on,
			// and gaussianElim will return true or false if a column pivot occured, so that r1 and r2 can be swapped
			// when needed.
			boolean didColumnsPivot = gaussianElim(matACopy);
			
			// If the LL of the matrix is 0, continue
			if(matACopy[1][1] == 0) {
				
				// If the columns pivoted, swap r1 and r2
				// This just makes r1 = 1 and solve for r2 rather than vice versa as normal
				if(didColumnsPivot) {
					
					r1 = 1;
					r2 = -(matACopy[0][1]) / matACopy[0][0];
					
				}
				else {
					
					r2 = 1;
					r1 = -(matACopy[0][1]) / matACopy[0][0];
					
				}
				
			}
			// If the LL != 0, then there are no trivial eigenvalues
			else {
				
				// I want to redo this later
				// If I don't, it still looks like the output works, but there's unnecessary output afterwards
				// I want this to be the only thing printed out, so I could probably make this a method that returns a string
				// But then this method would also have to return a string, hmm...
				System.out.println("There are no non-trivial eigenvectors.");
				
			}
			
			normR = Math.sqrt(Math.pow(r1, 2) + Math.pow(r2, 2));
			r1 = r1/normR;
			r2 = r2/normR;
			
			eigenVecMatrix[0][i] = r1;
			eigenVecMatrix[1][i] = r2;
			
		}
		
		return eigenVecMatrix;
		
	}
	
	// Method that performs gaussian elimination on the given matrix, will return true/false if a
	// column swap occured, so that r1 and r2 values can be swapped
	public static boolean gaussianElim(double[][] matrix) {
		
		double A = matrix[0][0];
		double b = matrix[0][1];
		double c = matrix[1][0];
		double D = matrix[1][1];
		
		double[][] matCopy = new double[2][2];
		
		boolean swapRValues = false;
		
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				
				matCopy[i][j] = matrix[i][j];
				
			}
			
			
		}
		
		if(A == 0 && c != 0) {
			
			// row pivot
			matrix[0][0] = matCopy[1][0];
			matrix[0][1] = matCopy[1][1];
			matrix[1][0] = matCopy[0][0];
			matrix[1][1] = matCopy[0][1];
			
			
		}
		else if(A == 0 && c == 0 && b != 0) {
			
			// column pivot, swap r1 and r2 in findEigenvectors
			matrix[0][0] = matCopy[0][1];
			matrix[1][0] = matCopy[1][1];
			matrix[0][1] = matCopy[0][0];
			matrix[1][1] = matCopy[1][0];
			
			swapRValues = true;
			
		}
		else if(A == 0 && c == 0 && b == 0) {
			
			// row pivot and column pivot, swap r1 and r2 in findEigenvectors
			matrix[0][0] = matCopy[1][0];
			matrix[0][1] = matCopy[1][1];
			matrix[1][0] = matCopy[0][0];
			matrix[1][1] = matCopy[0][1];
			
			double[][] columnPivot = matrix;
			
			matrix[0][0] = columnPivot[1][0];
			matrix[0][1] = columnPivot[1][1];
			matrix[1][0] = columnPivot[0][0];
			matrix[1][1] = columnPivot[0][1];
			
			swapRValues = true;
			
		}
		

		// Code below calculates the needed shear matrix for the LL corner of the matrix
		// to be 0
		double[][] shearMat = new double[2][2];
		
		// Shear identity matrix
		shearMat[0][0] = 1;
		shearMat[0][1] = 0;
		shearMat[1][0] = 0;
		shearMat[1][1] = 1;
		
		double x = 0;
		
		// Equation for finding x
		x = -(matrix[1][0]) / matrix[0][0];
		
		// Setting the LL of the shear matrix to x
		shearMat[1][0] = x;
		
		// Multiplication between shear and matrix
		A = (shearMat[0][0] * matrix[0][0]) + (shearMat[0][1] * matrix[1][0]);
		b = (shearMat[0][0] * matrix[0][1]) + (shearMat[0][1] * matrix[1][1]);
		c = (shearMat[1][0] * matrix[0][0]) + (shearMat[1][1] * matrix[1][0]);
		D = (shearMat[1][0] * matrix[0][1]) + (shearMat[1][1] * matrix[1][1]);
		
		// Sets above values to respective positions in matrix
		matrix[0][0] = A;
		matrix[0][1] = b;
		matrix[1][0] = c;
		matrix[1][1] = D;
		
		return swapRValues;
		
	}
	
	// Creates a transpose of the eigen vector matrix
	public static double[][] createTranspose(double[][] matrix){
		
		double[][] transposeMatrix = new double[2][2];
		
		for(int i = 0; i < matrix.length; i++) {
			for(int j = 0; j < matrix[0].length; j++) {
				
				transposeMatrix[i][j] = matrix[j][i];
				
			}
			
			
		}
		
		return transposeMatrix;
		
	}
	
	// Carries out the process of eigendecomposition, multiplying the eigenvector matrix and eigenvalue matrix together,
	// then multiplying the product matrix with the transposed eigenvector matrix
	// Method reuses an algorithm for matrix multiplication from assignment 1
	public static double[][] doEigenDecomp(double[][] eigenVecs, double[][] eigenVals, double[][] eigenVecsTranspose){
		
		double[][] eigenDecompMatrixFirstStep = new double[2][2];
		
		// Iterates through the product matrix, also helps iterate through the two given matrices
		for(int i = 0; i < eigenVecs[0].length; i++) {
			for(int j = 0; j < eigenVals.length; j++) {
				
				eigenDecompMatrixFirstStep[i][j] = 0;
				
				// This value is used to iterate through the i row of matrix A and the j column of matrix B
				for(int k = 0; k < eigenVals[0].length; k++) {
					
					// Updates the value of the index of the product matrix with the product of the two values in each position of the two matrices
					eigenDecompMatrixFirstStep[i][j] += eigenVecs[i][k] * eigenVals[k][j];
					
				}
				
			}
			
		}
		
		double[][] eigenDecompMatrixSecondStep = new double[2][2];
		
		// Iterates through the product matrix, also helps iterate through the two given matrices
		for(int i = 0; i < eigenDecompMatrixFirstStep[0].length; i++) {
			for(int j = 0; j < eigenVecsTranspose.length; j++) {
				
				eigenDecompMatrixSecondStep[i][j] = 0;
				
				// This value is used to iterate through the i row of matrix A and the j column of matrix B
				for(int k = 0; k < eigenVecsTranspose[0].length; k++) {
					
					// Updates the value of the index of the product matrix with the product of the two values in each position of the two matrices
					eigenDecompMatrixSecondStep[i][j] += eigenDecompMatrixFirstStep[i][k] * eigenVecsTranspose[k][j];
					
				}
				
			}
			
		}
		
		return eigenDecompMatrixSecondStep;
		
	}
	
	// Compares each value in the matrix with each other
	public static int areMatricesEqual(double[][] matA, double[][] decompMat) {
		
		int result = 1;
		double tolerance = 0.000001;
		
		for(int i = 0; i < matA.length; i++) {
			for(int j = 0; j < matA[0].length; j++) {
				
				// if the value in ith,jth position of each matrix aren't equal to each other
				// and the difference of the two values in said position are greater than the tolerance
				// Tolerance is needed just in case the values are incredibly close to each other, in
				// which case they might as well be equal (because comparing doubles is tricky)
				if((matA[i][j] != decompMat[i][j]) && (Math.abs(matA[i][j] - decompMat[i][j]) > tolerance)) {
					
					result = 0;
					
				}
				
			}
			
			
		}
		
		return result;
	}
	
	// Method that prints out the vectors and the integer indicating if the matrices are equal
	public static void printOutput(int matEquality, double[][] eigenMat, double[][] eigenVecs, double[][] eigenDecompMat) throws IOException {
		
		PrintWriter print2File = new PrintWriter("JCameron_PartB_outputFile4");
		
		for(int i = 0; i < eigenMat.length; i++) {
			for(int j = 0; j < eigenMat[0].length; j++) {
				
				print2File.printf("%.4f ", eigenMat[i][j]);
				System.out.printf("%.4f ", eigenMat[i][j]);
				
			}
			
			print2File.println();
			System.out.println();
			
		}
		
		for(int i = 0; i < eigenVecs.length; i++) {
			for(int j = 0; j < eigenVecs[0].length; j++) {
				
				print2File.printf("%.4f ", eigenVecs[i][j]);
				System.out.printf("%.4f ", eigenVecs[i][j]);
				
			}
			
			print2File.println();
			System.out.println();
			
		}
		
		for(int i = 0; i < eigenDecompMat.length; i++) {
			for(int j = 0; j < eigenDecompMat[0].length; j++) {
				
				print2File.printf("%.4f ", eigenDecompMat[i][j]);
				System.out.printf("%.4f ", eigenDecompMat[i][j]);
				
			}
			
			print2File.println();
			System.out.println();
			
		}
		
		print2File.println(matEquality);
		System.out.println(matEquality);
		
		print2File.close();
		
	}

}

/* Tasks:
 * 
 * Find eigenvalues of given matrix A and create a new matrix with found eigenvalues on matrix diagonals
 * 
 * Find the eigenvectors for the system:
 * 
 * 		To do this, solve: |a-l b||r1| = |0|
 * 						   |c d-l||r2|   |0|
 * 
 * 		|A b|	* If A = 0 and c != 0, row pivot
 * 		|c D|	* If A = 0, c = 0 and b!= 0, column pivot
 * 					** Swap r1 and r2
 * 				* If A = c = b = 0, do a row pivot and column pivot to get D in UL
 * 					** Swap r1 and r2
 * 
 * 		Gaussian elim to get 0 in place of c
 * 
 * 		|1 0||A b||r1| = |0||1 0|	* xA + c = 0 --> x = -c/A
 * 		|x 1||c D||r2|   |0||x 1|
 * 
 * 		Multiply matrices to get: |A b			  ||r1| = |0|
 * 								  |0 (-bc)/(A + D)||r2|	  |0|
 * 
 * 		For non-trivial eigenvectors: -bc/A + D = 0
 * 
 * 		If it is 0, let r2 = 1. Then: Ar1 + b(1) = 0
 * 								So, r1 = -b/A
 * 								And r = |-b/A|
 * 										|  1 |
 * 
 * 		Normalize vector r by dividing by length of r
 * 		Do for each eigenvalue to get r1 and r2
 * 
 * 		Then matrix R = |r1 r2| = |r1,1 r1,2|
 * 								  |r2,1 r2,2|
 * 
 * 		Transpose matrix R
 * 
 * 			R = |w x| --> Rt = |w y|
 * 				|y z|		   |x z|
 * 
 * 		Multiply all final matrices!
 * 
 * 			R(eigen mat)Rt = |w x|
 * 						 	 |y z|
 * 
 * 		Finally, find if matrix A and very final matrix are equal to each other
 */
