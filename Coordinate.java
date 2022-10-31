/**
 *	Represents a coordinate on a grid. Consists of a row and a column.
 *
 *	@author Rhea Karthik
 *  @since May 4th, 2022
 */

public class Coordinate implements Comparable<Coordinate>
{
	//field vars
	private int row;
	private int column;
		
	/** constructor */
	public Coordinate(int r, int c)
	{
		row = r;
		column = c;
	}
	
	/**
	 *  returns the row var
	 * 
	 *	@param none	  
	 *	@return row 	the row field var
	 */
	public int getRow()
	{ 
		return row;
	}
	
	/**
	 *  sets the row to a new value
	 * 
	 *	@param r		the new value for the row	  
	 *	@return none
	 */
	public void setRow(int r)
	{
		row = r;
	}
	
	/**
	 *  returns the column var
	 * 
	 *	@param none	  
	 *	@return column 	 the column field var
	 */
	public int getColumn()
	{ 
		return column; 
	}
	
	/**
	 *  sets the column var
	 * 
	 *	@param c		the new value for the column	  
	 *	@return none
	 */
	public void setColumn(int c)
	{
		column = c;
	}
	
	/**
	 *  The equals method. Only overriden in order to declare the class
	 *  as a comparable type (necessary in order to use in the singlylinkedlist
	 *  class)
	 * 
	 *	@param other	other object comparing to	  
	 *	@return true	if this object and the other object are equal
	 */
	@Override
	public boolean equals(Object other)
	{
		if (other != null && other instanceof Coordinate &&
		row == ((Coordinate)other).row &&
		column == ((Coordinate)other).column)
			return true;
		return false;
	}
	
	/**
	 *	Coordinate is greater when:
	 *	1. x is greater or
	 *	2. x is equal and y is greater
	 *	3. otherwise Coordinates are equal
	 * 
	 * 	@param other	other object comparing to
	 *	@return	(-,+,0)	negative if less than, 0 if equal, positive if greater than
	 */
	public int compareTo(Coordinate other)
	{
		if (! (other instanceof Coordinate))
			throw new IllegalArgumentException("compareTo not Coordinate object");
		if (row > ((Coordinate)other).row || row < ((Coordinate)other).row)
			return row - ((Coordinate)other).row;
		if (column > ((Coordinate)other).column || column < ((Coordinate)other).column)
			return column - ((Coordinate)other).column;
		return 0;
	}
	
	public String toString()
	{
		return "("+row+", "+column+")";
	}
}
