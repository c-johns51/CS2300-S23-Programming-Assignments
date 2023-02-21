import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;

public class JohnsonCameronAssignment2_CS2300 {
	

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File inputFile = new File("p2-1.txt");
		Scanner readFile = new Scanner(inputFile);
		
		char[][] gameBoard = createMatrix(inputFile, readFile);
		ArrayList<Integer> playedCoordinates = new ArrayList<>();
		// int[] invalidCoordinates = new int[4];
		
		
		int invalidTimeout = readFile.nextInt();
		int invalidTurnCounter = 0;
		
		int startRow = 0;
		int startCol = 0;
		int endRow = 0;
		int endCol = 0;
		
		while(readFile.hasNext()) {
			
			for(int player = 1; player <= 2; player++) {
				
				// A second check for if the file has more to be read. This is
				// redundant and I will try to find a better way to approach this,
				// but for now, this avoids the file running out when the for loop
				// still has to take player 2's turn.
				if(readFile.hasNext()) {
				
					startRow = readFile.nextInt();
					startCol = readFile.nextInt();
					endRow = readFile.nextInt();
					endCol = readFile.nextInt();
				
					boolean isTurnValid = validateTurn(startRow, startCol, endRow, endCol, playedCoordinates);
				
					if(isTurnValid) {
					
						processPlay(gameBoard, startRow, startCol, endRow, endCol, player);
						printPlay(gameBoard);
						
						playedCoordinates.add(startRow);
						playedCoordinates.add(startCol);
						playedCoordinates.add(endRow);
						playedCoordinates.add(endCol);
					
					}
					else {
					
						invalidTurnCounter = invalidTimeout;
				
					}
					
				}
				
			}
			
		}
			
	}
	
	public static char[][] createMatrix(File inputFile, Scanner readFile) throws IOException {
		
		int boardSize = readFile.nextInt();
		
		// The board is created with dimensions NxN, with N being the integer read
		// from the file. There is one row and one column added to this board size.
		// This is so that the coordinates work correctly, and the 0th row and 0th
		// column will be omitted entirely.
		char[][] gameBoard = new char[boardSize + 1][boardSize + 1];
		
		return gameBoard;
 		
	}
	
	public static boolean validateTurn(int sr, int sc, int er, int ec, ArrayList<Integer> previousPlays) {
		
		boolean result = false;
		
		int[] currentPoints = {sr, sc, er, ec};
		
		if(previousPlays.size() != 0) {
			for(int i = 0; i < previousPlays.size(); i++) {
				for(int j = 0; j < currentPoints.length; j++) {
				
					if(currentPoints[j] != previousPlays.get(j + (i * 2))) {
					
						result = true;
					
					}
				
				}
				
			}
			
		}
		else {
			
			result = true;
			
		}
		
		return result;
		
	}
	
	public static void processPlay(char[][] gameBoard, int sr, int sc, int er, int ec, int player) {
		
		// A boat load of variables
		int numRow = gameBoard.length;
		int numCol = gameBoard[0].length;
		
		int[] startPoint = {sr, sc};
		int[] endPoint = {er, ec};
		int[] testPoint = new int[2];
		int[] pointLineVector = new int[2];
		
		double dotProductTotal = 0;
		double pointLineVectorLength = 0;
		double endMinusStartVectorLength = 0;
		
		double cos = 0;
		double distance = 0;
		
		// Algorithm for finding the distance between the line and a point
		// The point, in this case, is the current index of the board
		for(int i = 1; i < numCol; i++) {
			for(int j = 1; j < numRow; j++) {
				
				// Current index being tested for distance
				testPoint[0] = i;
				testPoint[1] = j;
					
				// l(t) = start + t(end - start), parametric line being used
					
				// Create vector using testPoint - startPoint
				pointLineVector[0] = testPoint[0] - startPoint[0];
				pointLineVector[1] = testPoint[1] - startPoint[1];
					
				// Then take dot product of pointLineVector and (endPoint - startPoint)
				dotProductTotal = (pointLineVector[0] * (endPoint[0] - startPoint[0])) + (pointLineVector[1] * (endPoint[1] - startPoint[1]));
					
				// Then find length of pointLineVector and length of (endPoint - startPoint) vector
				// This is unnecessarily complicated but gets the job done
				pointLineVectorLength = Math.sqrt(Math.pow(pointLineVector[0], 2) + Math.pow(pointLineVector[1], 2));
				endMinusStartVectorLength = Math.sqrt(Math.pow((endPoint[0] - startPoint[0]), 2) + Math.pow((endPoint[1] - startPoint[1]), 2));
					
				// Find cos(a) for the sin() in the distance equation, d = ||w||sin(a) or d = ||w||sqrt(1 - (cos(a))^2)
				// If pointLineVectorLength is equal to 0 (which means the point being tested for distance
				// is on the line, or has no vector b/c distance = 0...
				if(pointLineVectorLength == 0) {
					
					// Just set cos to 0. This avoids getting NaN from dividing by 0,
					// which the computer can't inherently tell dividing by 0 means
					// no vector
					cos = 0;
					
				}
				else {
					
					// Otherwise, calculate cos as normal
					cos = dotProductTotal / (pointLineVectorLength * endMinusStartVectorLength);
					
				}
				// Use cos(a) in the distance equation
				distance = pointLineVectorLength * Math.sqrt(1 - Math.pow(cos, 2));
					
				// If the distance between the midpoint of cell (j,k) is less than .5
				// Considering the midpoint of the cell is (j,k), the edges of each cell are
				// j +/- .5 and k +/- .5
				if(distance >= 0 && distance <= .5) {
					
					// If else statement determining whether to put
					// and X or O depending on player
					if(player == 1) {
							
						gameBoard[i][j] = 'X';
							
					}
					else if(player == 2) {
							
						gameBoard[i][j] = 'O';
							
					}
						
				}
					
			}
				
		}
			
		
	}
	
	public static void printPlay(char[][] gameBoard) {
		
		System.out.print("  ");
		
		for(int i = 1; i < gameBoard.length; i++) {
			
			System.out.printf("%d ", i);
			
		}
		
		System.out.println();
		
		for(int i = gameBoard.length - 1; i > 0; i--) {
			
			System.out.print(i + " ");
			
			for( int j = 1; j < gameBoard[0].length; j++) {
				
				System.out.print(gameBoard[i][j] + " ");
				
			}
			
			System.out.println();
			
		}
		
		System.out.println();
		
	}

}

