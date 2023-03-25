import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {

        int count = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<>();

        // add inputs into queue
        while (!StdIn.isEmpty()) {
            String value = StdIn.readString();
            queue.enqueue(value);
        }

        // sample from randomized queue
        for (int i = 0; i < count; i++) {
            StdOut.println(queue.dequeue());
        }
    }
}
