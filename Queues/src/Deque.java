import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
  private Node first, last;
  private int size = 0;

  private class Node {
    Item item;
    Node next;
    Node prev;
  }

  // construct an empty deque
  public Deque() {
  }

  // unit testing (required)dequeue
  public static void main(String[] args) {
    int n = Integer.parseInt(args[0]);

    Deque<String> deque = new Deque<>();

      deque.addFirst("A");
      deque.addFirst("B");
      deque.addFirst("C");
      deque.addFirst("D");
      deque.addFirst("E");
      deque.addLast("F");
      deque.addLast("H");
      deque.removeFirst();
      deque.removeLast();

      for (Iterator<String> iter = deque.iterator(); iter.hasNext(); ) {
         String deqOut = iter.next();

          StdOut.println(deqOut);
      }

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
    if (item == null) {
      throw new IllegalArgumentException("Cannot have a null value");
    }
    Node oldfirst = first;
    first = new Node();
    first.item = item;
    first.next = oldfirst;
    if (isEmpty()) {
      last = first;
    } else {
      oldfirst.prev = first;
    }
    size++;
  }

  // add the item to the back
  public void addLast(Item item) {
    if (item == null) {
      throw new IllegalArgumentException("Cannot have a null value");
    }
    Node oldlast = last;
    last = new Node();
    last.item = item;
    last.next = null;
    if (isEmpty()) {
      first = last;
    } else {
      oldlast.next = last;
    }

    size++;
  }

  // remove and return the item from the front
  public Item removeFirst() {
    if (isEmpty()) {
      throw new NoSuchElementException("empty value");
    }
    Item item = first.item;
    // empty value if size is 0 or 1
    if (size < 2) {
      first = null;
      last = null;
    } else {
      first = first.next;
      first.prev = null;
    }
    size--;

    return item;
  }

  // remove and return the item from the back
  public Item removeLast() {
    if (isEmpty()) {
      throw new NoSuchElementException("empty value");
    }
    Item item = last.item;
    // empty value if size is 0 or 1
    if (size < 2) {
      first = null;
      last = null;
    } else {
      last = last.prev;
      last.next = null;
    }
    size--;

    return item;
  }

  // return an iterator over items in order from front to back
  public Iterator<Item> iterator() {
    return new ListIterator();
  }

  private class ListIterator implements Iterator<Item> {
    private Node current = first;

    public boolean hasNext() {
      return current != null;
    }

    public void remove() {
      throw new UnsupportedOperationException("Unsupported Operation");
    }

    public Item next() {
      if (current == null) {
        throw new NoSuchElementException("empty current value");
      }
      Item value = current.item;
      current = current.next;
      return value;
    }
  }

}