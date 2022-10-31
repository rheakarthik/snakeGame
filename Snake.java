/**
 *	The Snake is a singly linked list with snake behaviors.
 * 
 *	@author	Rhea Karthik
 *	@since	May 4th, 2022
 */
public class Snake extends SinglyLinkedList<Coordinate>
{
	//field var
	private SinglyLinkedList<Coordinate> snake; //the singlylinkedlist
	
	/** constructor 1 */
	public Snake()
	{
		this(3, 3);
	}
	
	/** constructor 2 */
	public Snake(int row, int col)
	{
		snake = new SinglyLinkedList();
		
		snake.add(new Coordinate(row, col));
		snake.add(new Coordinate(row+1, col));
		snake.add(new Coordinate(row+2, col));
		snake.add(new Coordinate(row+3, col));
		snake.add(new Coordinate(row+4, col));
	}
	
	/**
	 *	Return the snake singlylinkedlist field var
	 * 
	 *	@param none
	 *	@return snake     the singlylinkedlist
	 */
	public SinglyLinkedList getSnake()
	{
		return snake;
	}
	
	/**
	 *	Sets the field var snake to a new singlylinkedlist
	 * 
	 *	@param newSnakey	the new singlylinkedlist 
	 *	@return none
	 */
	public void setSnake(SinglyLinkedList<Coordinate> newSnakey)
	{
		snake = newSnakey;
	}
}
