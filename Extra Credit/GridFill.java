import java.io.*;
import java.util.*;
public class GridFill
{
	// The row, col, and value of the cell the user selects to toggle
	static int currentRow = 0;
	static int currentCol = 0;
	static int currentNum = 0;
	
	// The size of the grid
	static int numRows = 0;
	static int numCols = 0;
	
	// The board
	static int [][] board;
	static boolean [][] visited;
	
	// When to stop
	static boolean play = true;
	
	public static void main (String [] args) throws IOException
	{	
		System.out.println("Welcome to the Fill Grid Simulation. ");
	
		// Ask the user for m and n
		// Make a m x n board randomly filled with 0s and 1s
		
		askDimensions();
		randomlyfill();
		
		// Until the user wishes to stop
		
		while (play)
		{		
			// Ask for the row and column of the cell to toggle
			inputChanges();
			
			// Begin a flood fill from their input with no cells visited
			visited = new boolean [numRows][numCols];
			toggleBoard(currentRow, currentCol);
			
			// Ask if they want to stop
			checkStop();
		}
	}
	
	// Set the dimensions of the board
	public static void askDimensions() throws IOException
	{
		// Ensure that the user inputs two numbers
		// The size of the grid should be between 1 x 1 and 15 x 15 for readability
		while ((numRows < 1) || (numRows > 15) || (numCols < 1) || (numCols > 15))
		{
			System.out.print("\nInput the # of rows (1 - 15) and # of columns (1 - 15) for the grid, separated by a space: ");
			
			BufferedReader lineReader = new BufferedReader (new InputStreamReader (System.in));
			StringTokenizer tokenReader = new StringTokenizer (lineReader.readLine());
			
			String input1;
			String input2;
			
			if (tokenReader.hasMoreTokens())
			{
				input1 = tokenReader.nextToken();
			}
			else
			{
				System.out.println("Please make sure you enter two integers, separated by a space.");
				continue;
			}
			
			if (tokenReader.hasMoreTokens())
			{
				input2 = tokenReader.nextToken();
			}
			else
			{
				System.out.println("Please make sure you enter two integers, separated by a space.");
				continue;
			}
		
			try
			{
				numRows = Integer.parseInt(input1);
				numCols = Integer.parseInt(input2);
				
				if ((numRows < 1) || (numRows > 15) || (numCols < 1) || (numCols > 15))
				{
					System.out.print("Sorry, your input was invalid. Please make sure that the row is between 1 and 15 ");
					System.out.println("and that the column is between 1 and 15."); 
				}
			}
			
			catch (Exception exception)
			{
				System.out.println("Sorry. Your input was invalid. Please try again.");
			}
		}
		
		board = new int [numRows][numCols];
	}
	
	// Randomly fill the m x n board with 0s and 1s
	public static void randomlyfill ()
	{
		// Assign each cell either 0 or 1
		for (int i = 0; i < numRows; i++)
		{
			for (int j = 0; j < numCols; j++)
			{
				int randNum = (int)(Math.random()*2);
				board[i][j] = randNum;
			}
		}
		
		System.out.println("\nA " + numRows + " x " + numCols + " grid has been randomly generated. It is shown below: ");
		printBoard();
	}
	
	// Print the Board
	public static void printBoard ()
	{
		System.out.println();
		for (int i = 0; i < numRows; i++)
		{
			for (int j = 0; j < numCols; j++)
			{
				System.out.print(board[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
	
	// Ask for a cell to toggle
	public static void inputChanges () throws IOException
	{
		// Ensure that the user inputs both the row and the column
		// Ensure that the values are valid (ex: if it's a 5x5 grid, you can't enter row = 9 and col = 13)
		System.out.print("\nPlease enter the row (1 - " + numRows + ") and column (1 - " + numCols + ") you would like to "); System.out.print("toggle, separated by a space: ");
		
		BufferedReader lineReader = new BufferedReader (new InputStreamReader (System.in));
		StringTokenizer tokenReader = new StringTokenizer (lineReader.readLine());
		
		String input1;
		String input2;
		
		if (tokenReader.hasMoreTokens())
		{
			input1 = tokenReader.nextToken();
		}
		else
		{
			System.out.println("Please make sure you enter two integers, separated by a space.");
			inputChanges();
			return;
		}
		
		if (tokenReader.hasMoreTokens())
		{
			input2 = tokenReader.nextToken();
		}
		else
		{
			System.out.println("Please make sure you enter two integers, separated by a space.");
			inputChanges();
			return;
		}
		
		try
		{
			currentRow = Integer.parseInt(input1) - 1;
			currentCol = Integer.parseInt(input2) - 1;
			
			if ((currentRow < 0) || (currentRow >= numRows) || (currentCol < 0) || (currentCol >= numCols))
			{
				System.out.print("Sorry, your input was invalid. Please make sure that the row is between 1 and ");
				System.out.println(numRows + " and that the column is between 1 and " + numCols + ".");
				inputChanges();
				return;				
			}
			
			if (board[currentRow][currentCol] == 0)
			{
				currentNum = 1;
			}
			else
			{
				currentNum = 0;
			}	
		}
		catch (Exception exception)
		{
			System.out.println("Sorry. Your input was invalid. Please try again.");
			inputChanges();
			return;
		}
	}
	
	// Flood-fill algorithm to update the board
	public static void toggleBoard (int row, int col)
	{
		// Is the current cell out of bounds? If so, return
		if ((row < 0) || (row >= numRows) || (col < 0) || (col >= numCols))
		{
			return;
		}
		// Has the current cell already been visited? If so, return
		if (visited[row][col])
		{
			return;
		}
		
		// Are you only toggling cells from 0 to 1? If so, skip cells that are already 1
		if (board[row][col] == currentNum)
		{
			return;
		}
		
		// Otherwise, mark this cell as visited and toggle it.
		visited[row][col] = true;
		board[row][col] = currentNum;
		
		// Then toggle all its neighbors in a DFS fashion
		toggleBoard(row-1, col);
		toggleBoard(row+1, col);
		toggleBoard(row, col-1);
		toggleBoard(row, col+1);		
	}
	
	// Ask whether the user wishes to stop
	public static void checkStop () throws IOException
	{
		System.out.println("\nThe board has been updated successfully! It is shown below: ");
		printBoard();
		
		System.out.print("Would you like to continue playing? If you enter \"n\", the game will stop: ");
		
		Scanner input = new Scanner (System.in);
		if (input.next().equalsIgnoreCase("n"))	
		{
			play = false;
			System.out.println("\nThanks for playing. Goodbye! ");
		}
	}
}