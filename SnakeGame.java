/**
 *	Snake Game
 *  Runs the snake game. The snake game class contains the run
 *  method which executes the game played as the following: given a board,
 *  the snake, and a target (e.g. food source), the player controls the snake 
 *  using (a,s,d,w) to move it towards the target in order to grow longer. 
 *  The score is updated with every move that reaches the target. The target
 *  changes location randomly everytime the snake reaches it.
 *  In order to win, the player must reach max score, which is when there are
 *  only 5 spots it can move to left on the board. The player loses if the snake
 *  runs out of spots to move (e.g. getting trapped) before the max score, 
 *  runs into any of the bordering walls, or moves onto itself. 
 *	
 *	@author	Rhea Karthik
 *	@since	May 4th, 2022
 */
 
//import statements (for writing and printing to files)
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class SnakeGame
{
	//field vars
	private Snake snake;		//the snake in the game
	private SnakeBoard board;	//the game board
	private Coordinate target;	//the target for the snake
	private int score;			//the score of the game
	private FileUtils fileObj;  //instance of fileutils class

	/**	Constructor	*/
	public SnakeGame()
	{
		snake = new Snake(3, 3);
		board = new SnakeBoard(20, 25);
		target = new Coordinate(0, 0);
		changeTarget(); //randomizes the target every time the game starts
		score = 0;
		fileObj = new FileUtils();
	}
	
	/**
	 *	The main method. Calls the run method which executes the program.
	 * 
	 *	@param args
	 *	@return none     
	 */
	public static void main(String[] args)
	{
		SnakeGame snakes = new SnakeGame();
		snakes.run();
	}
	
	/**
	 *	Executes the program by calling all the neccessary helper methods
	 *  in order based on the user's input all in a big while loop.
	 *  The while loop is exited when the game's status matches the criteria
	 *  for the player either winning or losing and prints the apropriate outputs.
	 *  (more detail is described throughout the method)
	 * 
	 *	@param none
	 *	@return none
	 */
	public void run()
	{
		System.out.println("\n\n");
		
		//basic var(s)
		boolean over = false;
		boolean won = false;
		
		//coordinate stuff
		Coordinate oldSpot;
		Coordinate maybeTarget = null;
		Coordinate toAdd = null;
		
		int theRow = 0;
		int theCol = 0;
		
		//while loop running the whole program
		while(!over)
		{
			//print and user stuff
			board.printBoard(snake, target);
			oldSpot = (Coordinate)snake.getSnake().get(0).getValue();
			
			if(!canMove(oldSpot))
				over = true;
			else if(only5SpotsLeft())
			{
				over = true;
				won = true;
			}
				
			if(!over)
			{
				System.out.print("Score: "+score+" ");
				String theCommand = Prompt.getString("(w - North, s - South, d - East, a - West, h - Help) ");
				boolean noWorks = true;
				
				//checking user input and performing the next actions to be taken in those cases
				while(noWorks)
				{
					if(theCommand.equals("h")) //help menu
					{
						noWorks = false;
						helpMenu();
					}
					else if(theCommand.equals("q")) //quit 
					{
						noWorks = false;
						String yesno = Prompt.getString("\nDo you really want to quit? (y or n) ");
						
						if(yesno.equals("y"))
							over = true;
					}
					else if(theCommand.equals("w")) //north
					{
						noWorks = false;
						
						theRow = oldSpot.getRow();
						theCol = oldSpot.getColumn();
						
						theRow -= 1;
						
						maybeTarget = new Coordinate(theRow, theCol);
						
						if(isValidNewSpot(maybeTarget))
							toAdd = moveThem(maybeTarget);
						else
							over = true;
					}
					else if(theCommand.equals("s")) //south
					{
						noWorks = false;
						
						theRow = oldSpot.getRow();
						theCol = oldSpot.getColumn();
						
						theRow += 1;
						
						maybeTarget = new Coordinate(theRow, theCol);
						
						if(isValidNewSpot(maybeTarget))
							toAdd = moveThem(maybeTarget);
						else
							over = true;
					}
					else if(theCommand.equals("d")) //east
					{
						noWorks = false;
						
						theRow = oldSpot.getRow();
						theCol = oldSpot.getColumn();
						
						theCol += 1;
						
						maybeTarget = new Coordinate(theRow, theCol);
						
						if(isValidNewSpot(maybeTarget))
							toAdd = moveThem(maybeTarget);
						else
							over = true;
					}
					else if(theCommand.equals("a")) //west
					{
						noWorks = false;
						
						theRow = oldSpot.getRow();
						theCol = oldSpot.getColumn();
						
						theCol -= 1;
						
						maybeTarget = new Coordinate(theRow, theCol);
						
						if(isValidNewSpot(maybeTarget))
							toAdd = moveThem(maybeTarget);
						else
							over = true;
					}
					else if(theCommand.equals("f")) //save file
					{
						noWorks = false;
						
						 String yesno = Prompt.getString("\nSave game? (y or n) ");
						 if(yesno.equals("y"))
						 {
							 File outFile = new File("snakeGameSave.txt");
							 PrintWriter printer = fileObj.openToWrite("snakeGameSave.txt");
							 
							 printer.write("Score "+score+"\n"); //score
							 
							 int rowT = target.getRow();
							 int colT = target.getColumn();
							 printer.write("Target "+rowT+" "+colT+"\n"); //target coordinates
							 
							 printer.write("Snake "+snake.getSnake().size()+"\n"); //snake size
							 
							 for(int i = 0; i < snake.getSnake().size(); i++) //snake coordinates
							 {
								 Coordinate place = (Coordinate)snake.getSnake().get(i).getValue();
								 int x = place.getRow();
								 int y = place.getColumn();
								 
								 printer.write(x+" "+y+"\n");
							 }
							 
							 printer.close();
							 
							 System.out.println("\nSaving game to file snakeGameSave.txt");
							 noWorks = false;
							 over = true;
						 }
					}
					else if(theCommand.equals("r")) //restore file
					{
						noWorks = false;
						
						Scanner scans = fileObj.openToRead("snakeGameSave.txt");
						
						String scoreLine = scans.nextLine();
						score = Integer.valueOf(scoreLine.substring(6)); //get score
						
						String targetCoords =  scans.nextLine(); //get target coordinates
						String sub = targetCoords.substring(7);
						
						int indexOfSpace = sub.indexOf(" ");
						theRow = Integer.valueOf(sub.substring(0, indexOfSpace));
						theCol = Integer.valueOf(sub.substring(indexOfSpace+1));
						
						target.setRow(theRow);
						target.setColumn(theCol);
						
						String snakeStr = scans.nextLine(); //get snake size
						int snakeSize = Integer.valueOf(snakeStr.substring(6));
						
						SinglyLinkedList<Coordinate> tempSnakey = new SinglyLinkedList<Coordinate>();
						for(int i = 0; i < snakeSize; i++) //get all the snake coordinates
						{
							String getCoords = scans.nextLine();
							
							indexOfSpace = getCoords.indexOf(" ");
							theRow = Integer.valueOf(getCoords.substring(0, indexOfSpace));
							theCol = Integer.valueOf(getCoords.substring(indexOfSpace+1));
							
							tempSnakey.add(new Coordinate(theRow, theCol));
						}
						
						snake.setSnake(tempSnakey);
					}
					
					if(noWorks) //if the input given by the user is invalid, ask the prompt again
					{
						System.out.print("Score: "+score+" ");
						theCommand = Prompt.getString("(w - North, s - South, d - East, a - West, h - Help) ");
					}
				}
				
				//checking if the move the user choose led the head of the snake to 
				//reach the target spot
				if(maybeTarget != null && over != true)
				{
					int row = maybeTarget.getRow();
					int col = maybeTarget.getColumn();
					
					if(row == target.getRow() && col == target.getColumn())
					{
						snake.getSnake().add(toAdd);
						score++;
						changeTarget();
					}
				}
				
				//reset
				maybeTarget = null;
				toAdd = null;
			}
		}
		
		//print appropriate ending print statements
		System.out.print("\nGame is over");
		if(won)
			System.out.print(". You won!");
		System.out.println("\nScore = "+score+"\n");
		System.out.println("Thanks for playing SnakeGame!!");
	}
	
	/**
	 *  Determines whether there are only 5 spots left on the board or not. 
	 * 
	 *	@param none
	 *	@return true    if only 5 places left; false otherwise
	 */
	public boolean only5SpotsLeft()
	{
		char[][] theBoard = board.getBoard();
		
		int totalSpots = (theBoard[0].length-1)*(theBoard.length-1);
		int takenSpots = snake.size(); //net amount of spots
		
		if(totalSpots - takenSpots <= 5)
			return true;
		return false;
	}
	
	/**
	 *  Determines whether the snake is able to move by taking into account the 
	 *  availability of the neighboring spots around it 
	 * 
	 *	@param location 	new possible location of the head of the snake
	 *	@return true        if the snake can move (to at least 1 spot)
	 */
	public boolean canMove(Coordinate location)
	{
		Coordinate sides;
		boolean[] moves = new boolean[4];
		int row = location.getRow();
		int col = location.getColumn();
		
		//right (east)
		sides = new Coordinate(row, col+1);
		moves[0] = isValidNewSpot(sides);
		//left (west)
		sides = new Coordinate(row, col-1);
		moves[1] = isValidNewSpot(sides);
		//up (north)
		sides = new Coordinate(row-1, col);
		moves[2] = isValidNewSpot(sides);
		//down (south)
		sides = new Coordinate(row+1, col);
		moves[3] = isValidNewSpot(sides);
		
		int falses = 0;
		for(int i = 0; i < 4; i++)
		{
			if(moves[i] == false)
				falses++;
		}
		
		if(falses == 4)
			return false;
		return true;
	}
	
	/**
	 *  Changes the target coordinates when called and uses Math.random to 
	 *  generate appropriate random values that are not on the snake or 
	 *  on the borderlines of the board
	 * 
	 *	@param none
	 *	@return none
	 */
	public void changeTarget()
	{
		char[][] theBoard = board.getBoard();
		
		int row = (int)(Math.random()*(theBoard.length-2)+1);
		int	col = (int)(Math.random()*(theBoard[0].length-2)+1);
		boolean equals = true;
		
		while(equals)
		{
			equals = false;
			for(int i = 0; i < snake.getSnake().size(); i++)
			{
				Coordinate element = (Coordinate)snake.getSnake().get(i).getValue();
				int elementR = element.getRow();
				int elementC = element.getColumn();
				
				if(row == elementR && col == elementC)
					equals = true;
			}
			
			if(equals)
			{
				row = (int)(Math.random()*(theBoard.length-2)+1);
				col = (int)(Math.random()*(theBoard[0].length-2)+1);
			}
		}
		
		target = new Coordinate(row, col);
	}
	
	/**
	 *  Through usage of if-statements, determines whether the coordinate to move to 
	 *  exists on the board and is valid to move to.
	 * 
	 *	@param sampleSpot	the possible new spot for the snake to move to
	 *	@return true	    if that spot is valid on the board or is not 
	 * 						on the snake
	 */
	public boolean isValidNewSpot(Coordinate sampleSpot)
	{
		char[][] theBoard = board.getBoard();
		
		
		int row = sampleSpot.getRow()+1;
		int col = sampleSpot.getColumn()+1;
		boolean equals = false;
		
		//is valid on the board part 1
		if(row > 1 && row <= theBoard.length-1)
		{
			//is valid on the board part 2
			if(col > 1 && col <= theBoard[0].length-1)
			{
				//is valid in terms of moving onto the snake itself
				for(int i = 0; i < snake.getSnake().size(); i++)
				{
					Coordinate element = (Coordinate)snake.getSnake().get(i).getValue();
					int elementR = element.getRow()+1;
					int elementC = element.getColumn()+1;
					
					if(row == elementR && col == elementC)
						equals = true;
				}
				
				return !equals;
			}
			else //not valid..
				return false;
		}
		else //not valid
			return false;
	}
	
	/**
	 *  Moves the snake over by shifting each element over given the new coordinate
	 *  value starting at the head.
	 * 
	 *	@param sampleSpot	     the possible new spot for the snake to move to
	 *	@return storedSpot	     the coordinate of the last element in the list 
	 * 							 (before it was changed)
	 */
	public Coordinate moveThem(Coordinate newSpot)
	{
		Coordinate storedSpot = new Coordinate(0,0);
		
		for(int i = 0; i < snake.getSnake().size(); i++)
		{
			storedSpot = (Coordinate)snake.getSnake().get(i).getValue();
			snake.getSnake().set(i, newSpot);
			newSpot = storedSpot;
		}
		
		return storedSpot;
	}
	
	/**
	 *  Prints the game's introduction.
	 * 
	 *	@param none	 
	 *	@return none
	 */
	public void printIntroduction()
	{
		System.out.println("  _________              __            ________");
		System.out.println(" /   _____/ ____ _____  |  | __ ____  /  _____/_____    _____   ____");
		System.out.println(" \\_____  \\ /    \\\\__  \\ |  |/ // __ \\/   \\  ___\\__  \\  /     \\_/ __ \\");
		System.out.println(" /        \\   |  \\/ __ \\|    <\\  ___/\\    \\_\\  \\/ __ \\|  Y Y  \\  ___/");
		System.out.println("/_______  /___|  (____  /__|_ \\\\___  >\\______  (____  /__|_|  /\\___  >");
		System.out.println("        \\/     \\/     \\/     \\/    \\/        \\/     \\/      \\/     \\/");
		System.out.println("\nWelcome to SnakeGame!");
		System.out.println("\nA snake @****** moves around a board " +
							"eating targets \"+\".");
		System.out.println("Each time the snake eats the target it grows " +
							"another * longer.");
		System.out.println("The objective is to grow the longest it can " +
							"without moving into");
		System.out.println("itself or the wall.");
		System.out.println("\n");
	}
	
	/**
	 *  Prints the help menu
	 * 
	 *	@param none	  
	 *	@return none	   
	 */
	public void helpMenu()
	{
		System.out.println("\nCommands:\n" +
							"  w - move north\n" +
							"  s - move south\n" +
							"  d - move east\n" +
							"  a - move west\n" +
							"  h - help\n" +
							"  f - save game to file\n" +
							"  r - restore game from file\n" +
							"  q - quit");
		Prompt.getString("Press enter to continue");
	}
}
