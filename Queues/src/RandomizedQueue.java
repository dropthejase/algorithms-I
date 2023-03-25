import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int size = 0;
    private Item[] queue;

    public RandomizedQueue() {
        size = 0;
        queue = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return size == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return size;
    }

    // add the item
    public void enqueue(Item item) {
        // resize if queue is full
        if (size == queue.length) {
            resize(queue.length * 2);
        }
        queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {

        int idx = StdRandom.uniformInt(size - 1);
        Item removed = queue[idx];

        // shift items in queue
        for (int i = idx; i < size - 1; i++) {
            queue[i] = queue[i + 1];
        }
        queue[queue.length - 1] = null;
        
        // half queue size if queue is quarter full
        if (size > 0 && size - 1 == queue.length / 4)
            resize(queue.length / 2);
        
        size--;
        return removed;
    }

    // resize
    private void resize(int newSize) {
        Item[] copy = (Item[]) new Object[newSize];
        for (int i = 0; i < size; i++) {
            copy[i] = queue[i];
        }
        queue = copy;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int idx = StdRandom.uniformInt(size);
        return queue[idx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
        private class RandomizedQueueIterator implements Iterator<Item> {
            private int i = 0;
            public boolean hasNext() { 
                return i < size;
            }
            public Item next() {
                if (hasNext())
                    return queue[i++];
                throw new NoSuchElementException("No more items!");
            }
            public void remove() {
                throw new UnsupportedOperationException("remove() operation not supported");
            }
        }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Object> randomQueue = new RandomizedQueue<>();
        System.out.println("Empty: " + randomQueue.isEmpty());

        randomQueue.enqueue(0);
        randomQueue.enqueue(1);
        randomQueue.enqueue(2);
        randomQueue.enqueue(3);

        System.out.println("Size: " + randomQueue.size());
        System.out.println("Empty: " + randomQueue.isEmpty());
        System.out.println("Sample: " + randomQueue.sample());


        System.out.println("Queue Length: " + randomQueue.queue.length);

        for (Object i: randomQueue) {
            System.out.println(i);
        }

        System.out.println("Removing: " + randomQueue.dequeue());

        System.out.println("Size: " + randomQueue.size());
        System.out.println("Queue Length: " + randomQueue.queue.length);

        for (Object i: randomQueue) {
            System.out.println(i);
        }

        System.out.println("Removing: " + randomQueue.dequeue());
        System.out.println("Removing: " + randomQueue.dequeue());
        System.out.println("Size: " + randomQueue.size());
        System.out.println("Queue Length: " + randomQueue.queue.length);

        for (Object i: randomQueue) {
            System.out.println(i);
        }
    }
}
