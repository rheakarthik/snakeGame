public class tester
{
	private SinglyLinkedList thingy;
	
	public tester()
	{
		thingy = new SinglyLinkedList();
	}
	
	public SinglyLinkedList getThingy()
	{
		return thingy;
	}
	
	public static void main(String[] args)
	{
		tester thing = new tester();
		SinglyLinkedList theThing = thing.getThingy();
		theThing.add(new Coordinate(3,3));
		theThing.add(new Coordinate(3,4));
		theThing.add(new Coordinate(3,5));
		theThing.add(new Coordinate(3,6));
		theThing.add(new Coordinate(3,7));
		
		theThing.printList();
		System.out.println("done! \n");
		theThing.reverseLinkedList(null, theThing.get(0));
		theThing.printList();
	}
}
