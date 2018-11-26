package textgen;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 * @author Alena Ryzhkova
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH =10; 

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;
	MyLinkedList<Integer> list2;
	MyLinkedList<Integer> list3;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
	    shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		
		emptyList = new MyLinkedList<Integer>();
		
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			longerList.add(i);
		}
		
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);
		
		list2 = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++)
		{
			list2.add(i);
		}
		
		list3 = new MyLinkedList<Integer>();
	}

	
	/** Test if the get method is working correctly.
	 */
	/*You should not need to add much to this method.
	 * We provide it as an example of a thorough test. */
	@Test
	public void testGet()
	{
		//test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
			
		}
		
		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));
		
		try {
			shortList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		// test longer list contents
		for(int i = 0; i<LONG_LIST_LENGTH; i++ ) {
			assertEquals("Check "+i+ " element", (Integer)i, longerList.get(i));
		}
		
		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		
		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
	}
	
	
	@Test
	public void testRemove()
	{
		try {
			emptyList.remove(0);
			fail("Check removing from empty list");
		}
		catch (IllegalStateException e) {
		}
		
		try {
			list1.remove(-1);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		try {
			list1.remove(3);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer)21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());
		
	}
	
	/** Test adding an element into the end of the list, specifically
	 *  public boolean add(E element)
	 * */
	@Test
	public void testAddEnd()
	{
		Boolean b = shortList.add("D");
        assertEquals("AddEnd: check return true", true, b);
        assertEquals("AddEnd: check last element is correct", "D", shortList.tail.prev.data);
        assertEquals("AddEnd: check last but one element is correct", "B", shortList.tail.prev.prev.data);
        assertEquals("AddEnd: check size is correct", 3, shortList.size());
        
	}

	
	/** Test the size of the list */
	@Test
	public void testSize()
	{
		assertEquals("Size: check that size of empty list is 0", 0, list3.size());
		for(int i=0; i<5; i++)
			list3.add(i);
		assertEquals("Size: check after adding at the end",5, list3.size());
		list3.add(3, 42);
		assertEquals("Size: check after adding at the index",6, list3.size());
		list3.set(4, 12);
		assertEquals("Size: check after setting",6, list3.size());
		list3.remove(2);
		assertEquals("Size: check after removing",5, list3.size());
	}

	
	
	/** Test adding an element into the list at a specified index,
	 * specifically:
	 * public void add(int index, E element)
	 * */
	@Test
	public void testAddAtIndex()
	{
		try {
			longerList.add(LONG_LIST_LENGTH+1,1000);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		try {
			longerList.add(-1,1000);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		try {
			longerList.add(1,null);
			fail("Chek null ponter exception during adding null");
		}
		catch (NullPointerException e) {
		}
		
		longerList.add(2,1000);  // adding 1000 to 3-d position (index 2)
        assertEquals("AddAtIndex: check 3-d element is correct", Integer.valueOf(1000), longerList.get(2));
        assertEquals("AddAtIndex: check 2-d element element is correct", Integer.valueOf(1), longerList.get(1));
        assertEquals("AddAtIndex: check size is correct", LONG_LIST_LENGTH+1, longerList.size());
		
	}
	
	/** Test setting an element in the list */
	@Test
	public void testSet()
	{
		
		try {
			emptyList.set(0,2000);
			fail("Check set to empty list");
		}
		catch (IllegalStateException e) {
		}
		
		try {
			list2.set(LONG_LIST_LENGTH,2000);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		try {
			list2.set(-1,2000);
			fail("Check out of bounds");
		}
		catch (IndexOutOfBoundsException e) {
		}
		
		try {
			list2.set(1,null);
			fail("Chek null ponter exception during adding null");
		}
		catch (NullPointerException e) {
		}
	    
		
		Integer old = list2.set(1, 2000);
		assertEquals("Set: check that return element is correct",Integer.valueOf(1),old);
		assertEquals("Set: check that the new element in the set place is correct",Integer.valueOf(2000),list2.get(1));
		assertEquals("Set: check that the neighboring element is correct",Integer.valueOf(0),list2.get(0));
		assertEquals("Set: check that the neighboring element is correct",Integer.valueOf(2),list2.get(2));
		assertEquals("Set: check that size don't change",LONG_LIST_LENGTH,list2.size());
		
		Integer oldLast = list2.set(LONG_LIST_LENGTH-1, 3000);
		assertEquals("Set: check that return element is correct",Integer.valueOf(LONG_LIST_LENGTH-1),oldLast);
		assertEquals("Set: check that the new element in the set place is correct",Integer.valueOf(3000),list2.get(LONG_LIST_LENGTH-1));
		assertEquals("Set: check that the neighboring element is correct",Integer.valueOf(LONG_LIST_LENGTH-2),list2.get(LONG_LIST_LENGTH-2));
		assertEquals("Set: check that size don't change",LONG_LIST_LENGTH,list2.size());
	}
	
}
