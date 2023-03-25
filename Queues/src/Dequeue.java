import java.util.Iterator;
import java.util.NoSuchElementException;

public class Dequeue<Item> implements Iterable<Item>{

    private int size = 0;
    private Node head;
    private Node tail;

    public Dequeue() {
        head = new Node();
        tail = head;
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
        
        if (size() == 0) {
            head.item = item;
        }
        else {
            Node newNode = new Node();
            newNode.item = item;
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
        head = head.next;
        head.prev = null;
        size--;
        return removed;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null)
            throw new IllegalArgumentException();

        if (size() == 0) {
            head.item = item;
        }
        else {
            Node newNode = new Node();
            newNode.item = item;
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
        tail = tail.prev;
        tail.next = null;
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
        Dequeue<Object> deQueue = new Dequeue<>();
        System.out.println("Empty: " + deQueue.isEmpty());

        // test adding operations
        deQueue.addFirst(1);
        deQueue.addFirst(0);
        deQueue.addLast(2);
        System.out.println("Empty: " + deQueue.isEmpty());

        for (Object i: deQueue) {
            System.out.println(i);
        }
        System.out.println("Size: " + deQueue.size());
        System.out.println("Head: " + deQueue.head.item);
        System.out.println("Head Next: " + deQueue.head.next.item);
        System.out.println("Head Next Prev: " + deQueue.head.next.prev.item);
        System.out.println("Tail: " + deQueue.tail.item);   
        System.out.println("Tail Prev: " + deQueue.tail.prev.item);
        System.out.println("Tail Prev Prev: " + deQueue.tail.prev.prev.item);

        // test removeFirst operations
        System.out.println("Removed: " + deQueue.removeFirst());
        for (Object i: deQueue) {
            System.out.println(i);
        }
        System.out.println("Size: " + deQueue.size());
        System.out.println("Head: " + deQueue.head.item);
        System.out.println("Tail: " + deQueue.tail.item);
        
        System.out.println("Head Prev: " + deQueue.head.prev); // should be null
        System.out.println("Head Next: " + deQueue.head.next.item); // should be 2
        System.out.println("Head Next Prev: " + deQueue.head.next.prev.item); // should be 1
        System.out.println("Tail: " + deQueue.tail.item); // should be 1
        System.out.println("Tail Prev: " + deQueue.tail.prev.item); // should be 0
        System.out.println("Tail Prev Prev: " + deQueue.tail.prev.prev); // should be null

        // add back 0 in the front
        deQueue.addFirst(0);
        for (Object i: deQueue) {
            System.out.println(i);
        }

        // test removeLast
        System.out.println("Removed: " + deQueue.removeLast());
        for (Object i: deQueue) {
            System.out.println(i);
        }

        System.out.println("Size: " + deQueue.size());
        System.out.println("Head: " + deQueue.head.item);
        System.out.println("Tail: " + deQueue.tail.item);
        
        System.out.println("Head Prev: " + deQueue.head.prev); // should be null
        System.out.println("Head Next: " + deQueue.head.next.item); // should be 1
        System.out.println("Head Next Prev: " + deQueue.head.next.prev.item); // should be 0
        System.out.println("Tail: " + deQueue.tail.item); // should be 1
        System.out.println("Tail Prev: " + deQueue.tail.prev.item); // should be 0
        System.out.println("Tail Prev Prev: " + deQueue.tail.prev.prev); // should be null
    }
}
