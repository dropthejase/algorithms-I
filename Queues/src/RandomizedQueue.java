import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;

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
        if (item == null)
            throw new IllegalArgumentException("null argument not allowed");

        // resize if queue is full
        if (size == queue.length) {
            resize(queue.length * 2);
        }
        queue[size++] = item;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty.");

        int idx = StdRandom.uniformInt(size);
        Item removed = queue[idx];

        // replace with last item
        queue[idx] = queue[--size];
        
        // half queue size if queue is quarter full
        if (size > 0 && size - 1 == queue.length / 4)
            resize(queue.length / 2);
        
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
        if (isEmpty())
            throw new NoSuchElementException("Queue is empty.");

        int idx = StdRandom.uniformInt(size);
        return queue[idx];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }
        private class RandomizedQueueIterator implements Iterator<Item> {
            private int i = 0;
            private Item[] queueIterator;
            
            // create copy of queue and shuffle it
            private RandomizedQueueIterator() {
                queueIterator = (Item[]) new Object[size];
                for (int idx = 0; idx < size; idx++) {
                    queueIterator[idx] = queue[idx];
                }
                StdRandom.shuffle(queueIterator);
            }

            public boolean hasNext() { 
                return i < size;
            }
            public Item next() {
                if (hasNext())
                    return queueIterator[i++];
                throw new NoSuchElementException("No more items!");
            }
            public void remove() {
                throw new UnsupportedOperationException("remove() operation not supported");
            }
        }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Object> randomQueue = new RandomizedQueue<>();
        StdOut.println("Empty: " + randomQueue.isEmpty());

        randomQueue.enqueue(0);
        randomQueue.enqueue(1);
        randomQueue.enqueue(2);
        randomQueue.enqueue(3);

        StdOut.println("Size: " + randomQueue.size());
        StdOut.println("Empty: " + randomQueue.isEmpty());
        StdOut.println("Sample: " + randomQueue.sample());


        StdOut.println("Queue Length: " + randomQueue.queue.length);

        for (Object i: randomQueue) {
            StdOut.println(i);
        }

        StdOut.println("Removing: " + randomQueue.dequeue());

        StdOut.println("Size: " + randomQueue.size());
        StdOut.println("Queue Length: " + randomQueue.queue.length);

        for (Object i: randomQueue) {
            StdOut.println(i);
        }
        for (Object i: randomQueue) {
            StdOut.println(i);
        }

        for (Object i: randomQueue) {
            for (Object j: randomQueue) {
                StdOut.print(i);
                StdOut.print(j);
                StdOut.print("\n");
            }
        }
        StdOut.println("Removing: " + randomQueue.dequeue());
        StdOut.println("Removing: " + randomQueue.dequeue());
        StdOut.println("Size: " + randomQueue.size());
        StdOut.println("Queue Length: " + randomQueue.queue.length);

        for (Object i: randomQueue) {
            StdOut.println(i);
        }
    }
}
