import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private int size = 0;
    private Node head;
    private Node tail;

    public Deque() {
    }
        private class Node {
            Item item;
            Node next;
            Node prev;
        }

    // is the deque empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null)
            throw new IllegalArgumentException();
        
        Node newNode = new Node();
        newNode.item = item;

        if (size() == 0) {
            head = newNode;
            tail = newNode;
        }
        else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
        size++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item removed = head.item;

        if (size == 1) {
            head = null;
            tail = null;
        }
        else {
        head = head.next;
        head.prev = null;
        }

        size--;
        return removed;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException("null argument not allowed");

        Node newNode = new Node();
        newNode.item = item;

        if (size() == 0) {
            head = newNode;
            tail = newNode;
        }
        else {
            newNode.prev = tail;
            tail.next = newNode;
            tail = tail.next;
        }
        size++;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new NoSuchElementException();

        Item item = tail.item;

        if (size == 1) {
            head = null;
            tail = null;
        }
        else {
            tail = tail.prev;
            tail.next = null;
        }
        
        size--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new DequeueIterator();
    }
        private class DequeueIterator implements Iterator<Item> {
            private Node current = head;
            public boolean hasNext() { 
                return current != null;
            }
            public Item next() {
                if (!hasNext())
                    throw new NoSuchElementException("No more items!");   
                else {
                    Item item = current.item;
                    current = current.next;
                    return item;
                }
            }
            public void remove() {
                throw new UnsupportedOperationException("remove() operation not supported");
            }
        }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<Object> deQue = new Deque<>();
        StdOut.println("Empty: " + deQue.isEmpty());

        // test adding operations
        deQue.addFirst(1);
        deQue.addFirst(0);
        deQue.addLast(2);
        StdOut.println("Empty: " + deQue.isEmpty());

        for (Object i: deQue) {
            StdOut.println(i);
        }
        StdOut.println("Size: " + deQue.size());
        StdOut.println("Head: " + deQue.head.item);
        StdOut.println("Head Next: " + deQue.head.next.item);
        StdOut.println("Head Next Prev: " + deQue.head.next.prev.item);
        StdOut.println("Tail: " + deQue.tail.item);   
        StdOut.println("Tail Prev: " + deQue.tail.prev.item);
        StdOut.println("Tail Prev Prev: " + deQue.tail.prev.prev.item);

        // test removeFirst operations
        StdOut.println("Removed: " + deQue.removeFirst());
        for (Object i: deQue) {
            StdOut.println(i);
        }
        StdOut.println("Size: " + deQue.size());
        StdOut.println("Head: " + deQue.head.item);
        StdOut.println("Tail: " + deQue.tail.item);
        
        StdOut.println("Head Prev: " + deQue.head.prev); // should be null
        StdOut.println("Head Next: " + deQue.head.next.item); // should be 2
        StdOut.println("Head Next Prev: " + deQue.head.next.prev.item); // should be 1
        StdOut.println("Tail: " + deQue.tail.item); // should be 1
        StdOut.println("Tail Prev: " + deQue.tail.prev.item); // should be 0
        StdOut.println("Tail Prev Prev: " + deQue.tail.prev.prev); // should be null

        // add back 0 in the front
        deQue.addFirst(0);
        for (Object i: deQue) {
            StdOut.println(i);
        }

        // test removeLast
        StdOut.println("Removed: " + deQue.removeLast());
        for (Object i: deQue) {
            StdOut.println(i);
        }

        StdOut.println("Size: " + deQue.size());
        StdOut.println("Head: " + deQue.head.item);
        StdOut.println("Tail: " + deQue.tail.item);
        
        StdOut.println("Head Prev: " + deQue.head.prev); // should be null
        StdOut.println("Head Next: " + deQue.head.next.item); // should be 1
        StdOut.println("Head Next Prev: " + deQue.head.next.prev.item); // should be 0
        StdOut.println("Tail: " + deQue.tail.item); // should be 1
        StdOut.println("Tail Prev: " + deQue.tail.prev.item); // should be 0
        StdOut.println("Tail Prev Prev: " + deQue.tail.prev.prev); // should be null

        // remove everything
        StdOut.println("Removed: " + deQue.removeLast());
        StdOut.println("Size: " + deQue.size());

        StdOut.println("Removed: " + deQue.removeFirst());
        StdOut.println("Head: " + deQue.head);
        StdOut.println("Tail: " + deQue.tail);       
    }
}
