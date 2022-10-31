/**
 *	SinglyLinkedList. A variation of a linked list that has more methods
 *  to help make the list more usable. 
 *
 *	@author	Rhea Karthik
 *	@since	April 11th, 2022
 */
 
//import statement
import java.util.NoSuchElementException;

public class SinglyLinkedList<Coordinate extends Comparable<Coordinate>>
{
	//field vars
	private ListNode<Coordinate> head; //the head pointer
	private ListNode<Coordinate> tail; //the tail pointer
	
	/** No-args Constructors */
	public SinglyLinkedList()
	{
		head = new ListNode<Coordinate>(null, null);
		tail = new ListNode<Coordinate>(null, null);
	}
	
	/** Copy constructor */
	public SinglyLinkedList(SinglyLinkedList<Coordinate> oldList)
	{
		ListNode<Coordinate> nodes = oldList.get(0);
		while(nodes.getNext() != null)
		{
			this.add(nodes.getValue());
			nodes = nodes.getNext();
		}
		
		this.add(oldList.tail.getValue());
	}
	
	/** 
	 *  Clears the list of elements
	 * 
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public void clear()
	{
		head = null;
		tail = null;
	}
	
	/**	
	 *  Add the object to the end of the list
	 * 
	 *	@param obj		the object to add
	 *	@return			true if successful; false otherwise
	 */
	public boolean add(Coordinate obj)
	{
		ListNode<Coordinate> nodes = new ListNode<Coordinate>(obj);
		
		if(head.getValue() == null)
		{
			head = nodes;
		}
		else if(head.getNext() == null)
		{
			head.setNext(tail);
			tail.setNext(nodes);
			tail = nodes;
		}
		else
		{
			tail.setNext(nodes);
			tail = nodes;
		}
		
		return true;
	}
	
	/**	
	 *  Add the object at the specified index
	 * 
	 *	@param index		the index to add the object
	 *	@param obj			the object to add
	 *	@return				true if successful; false otherwise
	 *	@throws NoSuchElementException if index does not exist
	 */
	public boolean add(int index, Coordinate obj) 
	{
		if(index == size())
		{
			add(obj);
			return true;
		}
		else if(index < 0 || index > size())
		{
			throw new NoSuchElementException();
		}
		else
		{
			ListNode<Coordinate> other = new ListNode<Coordinate>(obj);
			if(index == 0)
			{
				other.setNext(head);
				head = other;
			}
			else
			{
				ListNode<Coordinate> previous = get(index-1);
				ListNode<Coordinate> node = previous.getNext();
				previous.setNext(other);
				other.setNext(node);
			}
			return true;
		}
	}
	
	/**	
	/**	Finds the size of the list
	 * 
	 *	@param none
	 *	@return	count		the number of elements in the list
	 */
	public int size()
	{
		int count = 0;
		if(head == null)
			return 0;
			
		ListNode<Coordinate> node = null;
		ListNode<Coordinate> theHead = head;
		
		while(theHead.getNext() != null)
		{
			count++;
			theHead = theHead.getNext();
		}
		
		return count;
	}
	
	/**	
	 *  Return the ListNode at the specified index
	 *  
	 *	@param index		the index of the ListNode
	 *	@return				the ListNode at the specified index
	 *	@throws NoSuchElementException if index does not exist
	 */
	public ListNode<Coordinate> get(int index)
	{
		if(index >= size() || index < 0)
		{
			throw new NoSuchElementException();
		}
		if(index == 0)
			return head;
			
		ListNode<Coordinate> node = null;
		ListNode<Coordinate> theHead = head;
		
		for(int i = 0; i < size(); i++)
		{
			if(i == index)
			{
				node = theHead.getNext();
			}
			theHead = theHead.getNext();
		}
		
		return node;
	}
	
	/**	
	 *  Replace the object at the specified index
	 * 
	 *	@param index		the index of the object
	 *	@param obj			the object that will replace the original
	 *	@return				the object that was replaced
	 *	@throws NoSuchElementException if index does not exist
	 */
	public Coordinate set(int index, Coordinate obj)
	{
		if(index < 0 || index >= size())
			throw new NoSuchElementException();
		else
		{
			ListNode<Coordinate> node = get(index);
		
			Coordinate oldVal = node.getValue();
			node.setValue(obj);
			
			return oldVal;
		}
	}
	
	/**	
	 *  Remove the element at the specified index
	 * 
	 *	@param index		the index of the element
	 *	@return				the object in the element that was removed
	 *	@throws NoSuchElementException if index does not exist
	 */
	public Coordinate remove(int index)
	{
		if(size() == 0 || index < 0 || index >= size())
			throw new NoSuchElementException();
		
		ListNode<Coordinate> remove = null;
		if(index == 0)
		{
			remove = head;
			head = head.getNext();
		}
		else
		{
			ListNode<Coordinate> previous = get(index-1);
			remove = previous.getNext();
			previous.setNext(remove.getNext());
		}
		
		return remove.getValue();
	}
	
	/**	
	 *  If the list is empty or not
	 * 
	 *	@param none
	 *	@return	true	if the list is empty
	 */
	public boolean isEmpty()
	{
		if(size() == 0)
			return true;
		return false;
	}
	
	/**	
	 *  Tests whether the list contains the given object
	 * 
	 *	@param object		the object to test
	 *	@return				true if the object is in the list; false otherwise
	 */
	public boolean contains(Coordinate object)
	{
		boolean val = false;
		ListNode<Coordinate> node = head;
		int counter = 0;
		
		if(head.getValue().equals(object) || get(size()-1).getValue().equals(object))
			return true;
		
		while(node.getNext() != null)
		{
			if(node.getValue().equals(object))
				val = true;
			counter++;
			node = node.getNext();
		}
		
		return val;
	}
	
	/**	
	 *  Return the first index matching the element
	 * 
	 *	@param element		the element to match
	 *	@return				if found, the index of the element; otherwise returns -1
	 */
	public int indexOf(Coordinate element)
	{
		ListNode<Coordinate> node = head;
		int count = 0;
		
		if(head.getValue().equals(element))
			return 0;
		else if(get(size()-1).getValue().equals(element))
			return size()-1;
		
		while(node.getNext() != null)
		{
			if(node.getValue().equals(element))
				return count;
			count++;
			node = node.getNext();
		}
		
		return -1;
	}
	
	public void reverseLinkedList(ListNode<Coordinate> prior, ListNode<Coordinate> node)
	{
		ListNode<Coordinate> next = node;
		
		if(node == null)
			return;
		
		next = next.getNext();
		node.setNext(prior);
		
		reverseLinkedList(node, next);
	}
	
	/**	
	 *  Prints the list of elements
	 * 
	 *	@param none
	 *	@return	none
	 */
	public void printList()
	{
		ListNode<Coordinate> ptr = head;
		while (ptr != null)
		{
			System.out.print(ptr.getValue() + "; ");
			ptr = ptr.getNext();
		}
	}
}
