import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
  private Item[] random;
  private int size;

  // construct an empty randomized queue
  public RandomizedQueue() {
    random = (Item[]) new Object[2];
  }

  // unit testing (required)
  public static void main(String[] args) {
    int k = Integer.parseInt(args[0]);
    String alphabet = "abcdefghijklmn";
    String elements = alphabet.substring(0, k);
    String[] elementsArray = elements.split("");
    RandomizedQueue<String> strs = new RandomizedQueue<String>();

    for (String element : elementsArray) {
      strs.enqueue(element);
    }

    for (int i = 0; i < k; i++) {
      StdOut.println(strs.dequeue());
    }
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
    if (item == null) {
      throw new IllegalArgumentException("Cannot have a null value");
    }
    if (size == random.length) {
      resize(2 * random.length);
    }
    random[size++] = item;
  }

  // remove and return a random item
  public Item dequeue() {
    if (isEmpty()) {
      throw new NoSuchElementException("empty value");
    }
    int index = getRandomIndex();
    Item item = random[index];
    random[index] = random[size - 1];
    random[size - 1] = null;
    size--;
    if (size > 0 && size == random.length / 4) {
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
    if (size >= capacity) {
      Item[] copy = (Item[]) new Object[capacity];
      for (int i = 0; i < size; i++) {
        copy[i] = random[i];
      }
      random = copy;
    }
  }

  private int getRandomIndex() {
    return StdRandom.uniform(0, size);
  }

  private class RandomArrayIterator implements Iterator<Item> {
    private Item[] r;
    private int i;

    public RandomArrayIterator() {
//            copyQueue();
//            StdRandom.shuffle(r);
    }

    private void copyQueue() {
      r = (Item[]) new Object[size];
      for (int j = 0; j < size; j++) {
        r[j] = random[j];
      }
    }

    public boolean hasNext() {
      return i < size;
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