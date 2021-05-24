import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    private Node first; // reference to first node in linked list
    private Node last; // reference to last node in linked list
    private int size; // size of array

    // construct an empty deque
    public Deque() {
        first = null;
        last = null;
        size = 0;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            addToEmptyDeque(item);
            size++;
        }
        else {
            Node newFirst = new Node();
            newFirst.item = item;
            newFirst.next = first;
            newFirst.previous = null;
            first.previous = newFirst;
            first = newFirst;
            first.previous = null;
            size++;
        }

    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) {
            addToEmptyDeque(item);
            size++;
        }
        else {
            Node newLast = new Node();
            newLast.item = item;
            newLast.next = null;
            last.next = newLast;
            newLast.previous = last;
            last = newLast;
            last.next = null;
            size++;
        }

    }

    // remove and return the item from the front
    public Item removeFirst() {
        Item item;
        Node oldFirst;
        if (isEmpty()) {

            throw new java.util.NoSuchElementException();
        }
        else {
            oldFirst = first;
            item = first.item;
            first = first.next;
            if (first == null) {
                last = null;
            }
            else {
                first.previous = null;
            }
            oldFirst = null;
            size--;
        }
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        Item item;
        Node oldLast;
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        else {
            oldLast = last;
            item = last.item;
            last = oldLast.previous;
            if (last == null) {
                first = null;
            }
            else {
                last.next = null;
            }
            oldLast = null;
            size--;
        }
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        class NodeIterator implements Iterator<Item> {
            Node current;

            public NodeIterator() {
                current = first;
            }

            public boolean hasNext() {
                return current != null;
            }

            public Item next() {
                if (isEmpty()) {
                    throw new NoSuchElementException();
                }
                else {
                    Item item = current.item;
                    current = current.next;
                    return item;
                }
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }
        return new NodeIterator();


    }


    // unit testing (required)
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        d.addFirst(1);
        d.addFirst(2);
        d.addFirst(3);

        Iterator<Integer> it = d.iterator();
        while (it.hasNext()) {
            StdOut.println(it.next());
        }


    }

    private void addToEmptyDeque(Item item) {
        first = new Node();
        first.item = item;
        first.next = null;
        first.previous = null;
        last = first;
    }
}
