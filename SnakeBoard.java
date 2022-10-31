/**
 *	SnakeBoard
 *  Represents the board of the SnakeGame with a character array that contains
 *  the symbols for the border, the snake, and the target. 
 *
 *	@author	Rhea Karthik
 *	@since	May 4th, 2022
 */
public class SnakeBoard {
	
	//field vars
	private char[][] board;		// The 2D array to hold the board
	
	/**	Constructor	*/
	public SnakeBoard(int height, int width)
	{
		board = new char[height+2][width+2];
	}
	
	/**
	 *  Prints the character array of board to the screen in the right format.
	 *  Also prints the snake(@**..) and the target(+) onto the board. This 
	 *  is done by initializing every element within the array so that the
	 *  elements are in the correct places. Then the array is printed out.
	 *  More detailed descriptions are within the method.
	 * 
	 *	@param snake	     the instance of the snake class 
	 *  @param target		 the coordinate instance representing the target
	 *	@return none
	 */
	public void printBoard(Snake snake, Coordinate target)
	{
		SinglyLinkedList theSnake = snake.getSnake(); //the actual snake
		
		//borderline
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
			{
				//check the corners
				if(i == 0 && j == 0)
					board[i][j] = '+';
				else if(i == board.length-1 && j == 0)
					board[i][j] = '+';
				else if(i == 0 && j == board[0].length-1)
					board[i][j] = '+';
				else if(i == board.length-1 && j == board[0].length-1)
					board[i][j] = '+';
				//check the sides
				else if(i == 0 || i == board.length-1)
					board[i][j] = '-';
				else if(j == 0 || j == board[0].length-1)
					board[i][j] = '|';
				else 
					board[i][j] = ' ';
			}
		}
		
		//adding snake and target
		ListNode node = theSnake.get(0);
		Coordinate head = (Coordinate)node.getValue();
		
		int row = head.getRow();
		int column = head.getColumn();
		
		int rowT = target.getRow();
		int colT = target.getColumn();
	
		//iteration through the snake singlylinkedlist
		for(int i = 1; i < board.length; i++)
		{
			for(int j = 1; j < board[0].length; j++)
			{
				//snake
				if(i == row && j == column) 
				{
					board[i][j] = '@'; //head of the snake
					
					for(int k = 1; k < theSnake.size(); k++) //rest of the snake
					{
						Coordinate spot = (Coordinate)theSnake.get(k).getValue();
						int theRow = spot.getRow();
						int theCol = spot.getColumn();
						
						board[theRow][theCol] = '*';
					}
				}
				
				//target
				if(rowT == i && colT == j)
					board[i][j] = '+';
			}
		}
	
		//actually printing the board
		for(int i = 0; i < board.length; i++)
		{
			for(int j = 0; j < board[0].length; j++)
				System.out.print(board[i][j]+" ");
			System.out.println();
		}
	}
	
	/**
	 *  Returns the board field var
	 * 
	 *	@param none	     
	 *	@return board	    the board field var
	 */
	public char[][] getBoard()
	{
		return board;
	}
	
	/********************************************************/
	/********************* For Testing **********************/
	/********************************************************/
	
	public static void main(String[] args) {
		// Create the board
		int height = 10, width = 15;
		SnakeBoard sb = new SnakeBoard(height, width);
		// Place the snake
		Snake snake = new Snake(3, 3);
		// Place the target
		Coordinate target = new Coordinate(1, 7);
		// Print the board
		sb.printBoard(snake, target);
	}
}
