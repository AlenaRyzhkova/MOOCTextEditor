package textgen;

import java.util.AbstractList;


/** A class that implements a doubly linked list
 * 
 * @author Alena Ryzhkova
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		size = 0;
		head = new LLNode<>(null);
		tail = new LLNode<>(null);
		head.next = tail;
		tail.prev = head;
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element) 
	{
		add(size(), element);
		return true;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) 
	{
		
		if(index<0 || index>size-1)
			throw new IndexOutOfBoundsException("Index is out of bounds linked list. Please, enter integer between 0 and" + (size()-1));
		
		LLNode<E> srch = findNode(index);
		
		return srch.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) 
	{
		// Null elements are not allowed in the list 
		if(element.equals(null))
			throw new NullPointerException("Adding null data is not allowed");
		// Checking for correct bounds
		if(index<0 || index>size)
			throw new IndexOutOfBoundsException("Index is out of bounds linked list. Please, enter integer between 0 and" + size());
		
		// Create new Node
		LLNode<E> node = new LLNode<>(element);
		
		// Find the node from the List before which will insert
		LLNode<E> place = findNode(index);
		// Insert the new node
		node.next = place;
		node.prev = place.prev;
		place.prev.next = node;
		place.prev = node;
		size++;
	}


	/** Return the size of the list */
	public int size() 
	{
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) 
	{
		// Checking that list isn't empty
		if(size()==0)
			throw new IllegalStateException("The list is empty. You can't remove any elements from empty list");
		
		// Checking for correct bounds
		if(index<0 || index>size-1)
			throw new IndexOutOfBoundsException("Index is out of bounds linked list. Please, enter integer between 0 and" + (size()-1));
								
		LLNode<E> rm = findNode(index);
		rm.prev.next = rm.next;
		rm.next.prev = rm.prev;
		size--;
		return rm.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) 
	{
		// Null elements are not allowed in the list 
		if(element.equals(null))
			throw new NullPointerException("Adding null data is not allowed");
		
		// Checking that list isn't empty
		if(size()==0)
			throw new IllegalStateException("The list is empty. You can't set any elements in empty list. Please, use public boolean add(E element) ");
		
		// Checking for correct bounds
		if(index<0 || index>size-1)
			throw new IndexOutOfBoundsException("Index is out of bounds linked list. Please, enter integer between 0 and" + (size()-1));
						
		// Find the node from the List before which will insert
		LLNode<E> place = findNode(index);
		// Save old data for return
		E oldData = place.data;
		// Change data
		place.data = element;
		
		return oldData;
	} 
	
	// Helper method for finding the node with required index
	private LLNode<E> findNode(int index) {
		LLNode<E> node;
		if(index<size()/2) {
			node = head;
			for(int i=0; i<index+1; i++)
				node = node.next;
		}else {
			node = tail;
			for(int i = size; i>index; i--)
				node = node.prev;
		}
		return node;
	}
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
