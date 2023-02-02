import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class JohnsonCameronAssignment1_CS2300 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		// --- PART 1 ---
		// This part creates every matrix using their own method, then stores the matrix its respective variable.
		// I've made every matrix of the double data type to make adding and multiplying of certain matrices easier.
		// Each matrix is then printed out into a text file using a print2File method I made.
		
		double[][] mat1 = mat1();
		double[][] mat2 = mat2();
		double[][] mat3 = mat3();
		double[][] mat4 = mat4();
		double[][] mat5 = mat5();
		
		print2File(mat1, "JohnsonCameron_mat1.txt");
		print2File(mat2, "JohnsonCameron_mat2.txt");
		print2File(mat3, "JohnsonCameron_mat3.txt");
		print2File(mat4, "JohnsonCameron_mat4.txt");
		print2File(mat5, "JohnsonCameron_mat5.txt");
		
		// --- PART 2 ---
		// This part calls the JohnsonCameron_p2 method on matrices that can be added together, and
		// stores the matrix they make in its respective variable.
		// Each matrix is then printed out into text files.
		
		double[][] mat12 = JohnsonCameron_p2(mat1, mat2);
		double[][] mat13 = JohnsonCameron_p2(mat1, mat3);
		double[][] mat23 = JohnsonCameron_p2(mat2, mat3);
		double[][] mat45 = JohnsonCameron_p2(mat4, mat5);
		
		print2File(mat12, "JohnsonCameron_mat12.txt");
		print2File(mat13, "JohnsonCameron_mat13.txt");
		print2File(mat23, "JohnsonCameron_mat23.txt");
		print2File(mat45, "JohnsonCameron_mat45.txt");
		
		
		// --- PART 3 ---
		// This part takes matrices that are capable of multiplying together and does so through
		// the JohnsonCameron_p3 method, and stores the new matrix that is made into its
		// respective variable.
		// Each new matrix is then printed out into text files.
		
		double[][] matMult12 = JohnsonCameron_p3(mat1, mat2);
		double[][] matMult13 = JohnsonCameron_p3(mat1, mat3);
		double[][] matMult23 = JohnsonCameron_p3(mat2, mat3);
		
		print2File(matMult12, "JohnsonCameron_matMult12.txt");
		print2File(matMult13, "JohnsonCameron_matMult13.txt");
		print2File(matMult23, "JohnsonCameron_matMult23.txt");
		
		
	}
	
	// Method used to add matrices together.
	public static double[][] JohnsonCameron_p2(double[][] matA, double[][] matB) {
		
		// Uses the number of rows and columns from one of the given matrices.
		// Only one matrix's rows and columns are needed, for the matrices must be of same
		// size to be added together.
		int numRows = matA.length;
		int numCols = matA[0].length;
		
		// Creates a new matrix to store the added numbers from the two matrices
		double[][] matAB = new double[numRows][numCols];
		
		// I coded this as if two matrices could be put in that were of equal dimension
		// Now I realize that I control that, and I fear that if I remove this I will
		// mess the code up, so I will not be removing this. Should function though
		if (matA.length == matB.length) {
			if(matA[0].length == matB[0].length) {
				
				// Nested for loop, iterating through columns first before rows
				for(int row = 0; row < numRows; row++) {
					for(int col = 0; col < numCols; col++) {
						
						// Adds the values from the indexed position in each matrix and puts the new value into the indexed
						// position of the new matrix
						matAB[row][col] = matA[row][col] + matB[row][col];
						
						
					}
					
				}
					
			}
				
		}
		
		// Returns the new matrix back to main
		return matAB;
			
	}
	
	// Method used to multiply matrices.
	public static double[][] JohnsonCameron_p3(double[][] matA, double[][] matB) {
		
		// Creates the matrix using the number of rows in the first matrix and columns in the second matrix
		double[][] matAB = new double[matA[0].length][matB.length];
		
		// Iterates through the product matrix, also helps iterate through the two given matrices
		for(int i = 0; i < matA[0].length; i++) {
			for(int j = 0; j < matB.length; j++) {
				
				matAB[i][j] = 0;
				
				// This value is used to iterate through the i row of matrix A and the j column of matrix B
				for(int k = 0; k < matB[0].length; k++) {
					
					// Updates the value of the index of the product matrix with the product of the two values in each position of the two matrices
					matAB[i][j] += matA[i][k] * matB[k][j];
					
				}
				
			}
			
		}
		
		// Returns product matrix to main
		return matAB;
		
	}

	
	
	// The mat(x) methods create the initial matrices, using row and column counters to iterate through the matrix,
	// whether it be rows first or columns first, and then increments a value to be stored in the indexed position of the
	// matrix wherever needed. I hope this comment serves as a general comment for all 5 methods creating the matrices, as they
	// all behave relatively similarly.
	public static double[][] mat1(){
	
		int numRows = 7;
		int numCols = 7;
	
		int numCount = 1;
	
		double[][] mat1 = new double[numRows][numCols];
	
		for(int row = 0; row < numRows; row++) {
			for(int col = 0; col < numCols; col++) {
			
				mat1[row][col] = numCount;
				numCount++;
			
			}
			
		}
	
		return mat1;
	}
	
	public static double[][] mat2(){
		
		int numRows = 7;
		int numCols = 7;
		
		int numCount = 3;
		
		double[][] mat2 = new double[numRows][numCols];
		
		for(int col = 0; col < numCols; col++) {
			for(int row = 0; row < numRows; row++) {
				
				mat2[row][col] = numCount;
				
				numCount = numCount + 3;
				
			}
			
		}
		
		return mat2;
		
	}
	
	public static double[][] mat3(){
		
		int numRows = 7;
		int numCols = 7;
		
		double numCount = 0.4;
		
		double[][] mat3 = new double[numRows][numCols];
		
		for(int col = 0; col < numCols; col++) {
			for(int row = 0; row < numRows; row++) {
				
				mat3[row][col] = numCount;
				
				numCount = numCount + 0.3;
				
			}
				
		}
		
		return mat3;
	
	}
	
	public static double[][] mat4(){
		
		int numRows = 6;
		int numCols = 13;
		
		int numCount = 2;
		
		double[][] mat4 = new double[numRows][numCols];
		
		for(int col = 0; col < numCols; col++) {
			for(int row = 0; row < numRows; row++) {
				
				mat4[row][col] = numCount;
				
				numCount = numCount + 2;
				
			}
			
		}
		
		return mat4;
		
	}
	
	public static double[][] mat5(){
		
		int numRows = 6;
		int numCols = 13;
		
		int numCount = -7;
		
		double[][] mat5 = new double[numRows][numCols];
		
		for(int row = 0; row < numRows; row++) {
			for( int col = 0; col < numCols; col++) {
				
				mat5[row][col] = numCount;
				
				numCount++;
				
			}

		}
		
		return mat5;
		
	}
	
	// Method to print out give matrices to their files.
	public static void print2File(double[][] mat2Print, String matName) throws IOException {
		
		// Creates numRows and numCols, using the number of rows and columns the given matrix has
		int numRows = mat2Print.length;
		int numCols = mat2Print[0].length;
		
		// Creates File object, with matName being what the file will be named when created.
		// matName is passed through when the method is called in main. I could not find a way
		// to not hard code the text file names because they would not be made properly when incorporating
		// the variable itself in the string.
		File fileName = new File(matName);
				
		// Creates the PrintWriter object outputFile, and where it will be printing to.
		PrintWriter outputFile = new PrintWriter(fileName);
				
		// Nested for loop that prints every value in the matrix in the file
		for(int i = 0; i < numRows; i++) {
					
			for(int j = 0; j < numCols; j++) {
				
				// Prints to the file
				outputFile.print(mat2Print[i][j] + " ");
						
			}
			
			// Creates a new line to print the next row.
			outputFile.println();
					
		}
		
		// Closes outputFile, and prints where the new file is located at.
		// Unfortunately this prints for every file made, and I've yet to know
		// how to get it to print the folder every file is stored.
		outputFile.close();
		System.out.println("File made will be located in: " + fileName.getAbsolutePath());
		
	}
}

