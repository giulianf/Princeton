import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] random;
  private int N;

  // construct an empty randomized queue
  public RandomizedQueue() {
    random = (Item[]) new Object[2];
  }

  // unit testing (required)
  public static void main(String[] args) {
      RandomizedQueue<String> randomizedQueue = new RandomizedQueue<String>();

      while (!StdIn.isEmpty()) {
        String enqueueString = StdIn.readString();
          randomizedQueue.enqueue(enqueueString);
      }

    int num = Integer.parseInt(args[0]);
    while(num-- > 0) {
      StdOut.println(randomizedQueue.dequeue());
    }
  }

  // is the randomized queue empty?
  public boolean isEmpty() {
    return N == 0;
  }

  // return the number of items on the randomized queue
  public int size() {
    return N;
  }

  // add the item
  public void enqueue(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Cannot have a null value");
    }
    if (N == random.length) {
      resize(2 * random.length);
    }
    random[N++] = item;
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException("empty value");
    }
    int index = getRandomIndex();
    Item item = random[index];
    random[index] = random[N - 1];
    random[N - 1] = null;
    N--;
    if (N > 0 && N == random.length / 4) {
      resize(random.length / 2);
    }

    return item;
  }

  // return a random item (but do not remove it)
  public Item sample() {
    if (isEmpty()) {
      throw new NoSuchElementException("empty value");
    }
    return random[getRandomIndex()];

  }

  // return an independent iterator over items in random order
  public Iterator<Item> iterator() {
    return new RandomArrayIterator();
  }

  private void resize(int capacity) {
    if (N >= capacity) {
      Item[] copy = (Item[]) new Object[capacity];
      for (int i = 0; i < N; i++) {
        copy[i] = random[i];
      }
      random = copy;
    }
  }

  private int getRandomIndex() {
    return StdRandom.uniform(0, N);
  }

    private class RandomArrayIterator implements Iterator<Item> {
        private Item[] r;
        private int i;

        public RandomArrayIterator() {
//            copyQueue();
//            StdRandom.shuffle(r);
        }

        private void copyQueue() {
            r = (Item[]) new Object[N];
            for (int i = 0; i < N; i++) {
                r[i] = random[i];
            }
        }

        public boolean hasNext() {
            return i < N;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return r[i++];
        }
    }
}