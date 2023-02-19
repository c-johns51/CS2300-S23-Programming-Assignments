import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class JohnsonCameronAssignment2_CS2300 {
	

	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		File inputFile = new File("p2-1.txt");
		Scanner readFile = new Scanner(inputFile);
		
		char[][] gameBoard = createMatrix(inputFile, readFile);
		ArrayList<Integer> playedCoordinates = new ArrayList<>();
		// int[] invalidCoordinates = new int[4];
		
		
		int invalidTimeout = readFile.nextInt();
		
		int startRow = 0;
		int startCol = 0;
		int endRow = 0;
		int endCol = 0;
		
		while(readFile.hasNext()) {
			
			for(int player = 1; player <= 2; player++) {
				
				startRow = readFile.nextInt();
				startCol = readFile.nextInt();
				endRow = readFile.nextInt();
				endCol = readFile.nextInt();
			
				boolean isTurnValid = validateTurn(startRow, startCol, endRow, endCol, playedCoordinates);
			
				if(isTurnValid) {
				
					processPlay(startRow, startCol, endRow, endCol, player, gameBoard);
				
				}
				else {
				
					// idk yet
					System.out.println("Not valid");
			
				}
			
			}
			
		}
			
	}
	
	public static char[][] createMatrix(File inputFile, Scanner readFile) throws IOException {
		
		int boardSize = readFile.nextInt();
		
		char[][] gameBoard = new char[boardSize][boardSize];
		
		return gameBoard;
 		
	}
	
	public static boolean validateTurn(int sr, int sc, int er, int ec, ArrayList<Integer> previousPlays) {
		
		boolean result = false;
		
		int[] currentPoints = {sr, sc, er, ec};
		
		if(previousPlays.size() != 0) {
			for(int i = 0; i == previousPlays.size(); i++) {
				for(int j = 0; j < currentPoints.length; j++) {
				
					if(currentPoints[j] != previousPlays.get(i)) {
					
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
	
	public static void processPlay(int sr, int sc, int er, int ec, int player, char[][] gameBoard) {
		
		double boardSize = gameBoard.length;
		
		double[] start = {sr, sc};
		double[] end = {er, ec};
		
		double[] q = {end[0] - start[0], end[1] - start[1]};
		
		// A 2x5 matrix holding x and y values of the line at midpoints of each cell
		double[][] line = new double[2][(int)boardSize];
		
		for(int i = 0; i < (int)boardSize; i++) {
			
			double divideByBoardSize = i / (boardSize - 1);
			
			line[0][i] = start[0] + (divideByBoardSize * q[0]);
			line[1][i] = start[1] + (divideByBoardSize * q[1]); 
			
			
		}
		
	}

}

