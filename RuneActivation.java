import java.util.*;

public class RuneActivation
{
	//The working grid that we will modify
	public static boolean[][] current;
	//The working grid that we want to have
	public static boolean[][] desired;
	//Storing the solution
	public static boolean[][] solution;
	//Rows and columns
	public static int r, c;
	
	//Print the solution
	public static void printGlyph()
	{
		for(int i = 0; i < r; i++)
		{
			for(int j = 0; j < c; j++)
			{
				if(solution[i][j] == true)
				{
					System.out.print("C");
				}
				else
				{
					System.out.print("-");
				}
			}
			System.out.print("\n");
		}
		System.out.println("");
	}
	
	//Validate the current and desired are the same
	public static boolean checkSolution()
	{
		for(int i = 0; i < r; i++)
		{
			for(int j = 0; j < c; j++)
			{
				if(current[i][j] != desired[i][j])
				{
					return false;
				}
			}
		}
		return true;
	}
	
	//Pruning function
	public static boolean valid(int row, int column)
	{
		//Validate top glyph to see if we can move on
		if(inBounds(row - 1, column))
		{
			if(current[row - 1][column] != desired[row - 1][column])
			{
				return false;
			}
		}
		
		//Validate top left glyph since we cannot change it anymore
		if(inBounds(row - 1, column - 1))
		{
			if(current[row - 1][column - 1] != desired[row - 1][column - 1])
			{
				return false;
			}
		}
		return true;
	}
	
	//Method to check if we can access an index
	public static boolean inBounds(int row, int column)
	{
		if(row < 0 || row >= r || column < 0 || column >= c)
		{
			return false;
		}
		return true;
	}
	
	//Casts at the current grid placement
	public static void cast(int row, int column)
	{
		//Flips current
		if(current[row][column] == true)
		{
			current[row][column] = false;
		}
		else
		{
			current[row][column] = true;
		}
		
		//Flips left
		if(inBounds(row, column - 1))
		{
			if(current[row][column - 1] == true)
			{
				current[row][column - 1] = false;
			}
			else
			{
				current[row][column - 1] = true;
			}
		}
		
		//Flips top
		if(inBounds(row - 1, column))
		{
			if(current[row - 1][column] == true)
			{
				current[row - 1][column] = false;
			}
			else
			{
				current[row - 1][column] = true;
			}
		}
		
		//Flips right
		if(inBounds(row, column + 1))
		{
			if(current[row][column + 1] == true)
			{
				current[row][column + 1] = false;
			}
			else
			{
				current[row][column + 1] = true;
			}
		}
		
		//Flips bottom
		if(inBounds(row + 1, column))
		{
			if(current[row + 1][column] == true)
			{
				current[row + 1][column] = false;
			}
			else
			{
				current[row + 1][column] = true;
			}
		}
	}
	
	public static boolean runeSolve(int row, int column)
	{
		//Check full board after the bottom row has been traversed
		if(row == r)
		{
			return checkSolution();
		}
		
		//If we get to the end of a column, move down a row
		if(column == c)
		{
			return runeSolve(row + 1, 0);
		}
		
		//Cast, update solution, then recurse if it's a valid decision
		cast(row, column);
		if(valid(row, column))
		{	
			if(runeSolve(row, column + 1))
			{
				solution[row][column] = true;
				return true;
			}
		}
		
		//Reverse the cast
		cast(row, column);
		
		//Don't cast, update solution, then recurse 
		if(valid(row, column))
		{
			if(runeSolve(row, column + 1))
			{
				solution[row][column] = false;
				return true;
			}
		}
		
		//If we get here there is no solution
		return false;
	}
	
	//Main method
	public static void main (String[] args)
	{
		Scanner sc = new Scanner(System.in);
		//Read in rows and columns
		r = sc.nextInt();
		c = sc.nextInt();
		
		current = new boolean[r][c];
		desired = new boolean[r][c];
		solution = new boolean[r][c];
		
		//Fill working grid
		for(int i = 0; i < r; i++)
		{
			String inputString = sc.next();
			for(int j = 0; j < c; j++)
			{
				if(inputString.charAt(j) == 'A')
				{
					current[i][j] = true;
				}
				else
				{
					current[i][j] = false;
				}
			}
		}
		
		//Fill desired grid
		for(int i = 0; i < r; i++)
		{
			String outputString = sc.next();
			for(int j = 0; j < c; j++)
			{
				if(outputString.charAt(j) == 'A')
				{
					desired[i][j] = true;
				}
				else
				{
					desired[i][j] = false;
				}
			}
		}
		
		sc.close();
		
		//Print solution grid if there is a solution, -1 if there isn't
		if(runeSolve(0,0))
		{
			printGlyph();
		}
		else
		{
			System.out.println(-1);
		}
	}
}