import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    // construct an empty deque
    public Deque() {

    }

    // unit testing (required)
    public static void main(String[] args) {

    }

    // is the deque empty?
    public boolean isEmpty() {

    }

    // return the number of items on the deque
    public int size() {

    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot have a null value");
        }

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot have a null value");
        }
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty value");
        }

    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException("empty value");
        }
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
//        if(next()) {
//            throw new NoSuchElementException("empty value");
//        }
    }

}