import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;
    private int first;
    private int last;
    private Deque<Integer> emptyIndices;


    // construct an empty randomized queue
    public RandomizedQueue() {
        items = (Item[]) new Object[1];
        size = 0;
        first = 0;
        last = -1;
        emptyIndices = new Deque<Integer>();

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
        if (emptyIndices.isEmpty()) {
            if (size == items.length) resize(2 * items.length);
            if (last++ >= (items.length)) {
                last = 0;
            }
            items[last] = item;
        }
        else {
            items[emptyIndices.removeFirst()] = item;
        }
        size++;
    }

    // remove and return a random item
    public Item dequeue() {
        int rand = StdRandom.uniform(size);
        while (items[rand] == null) {
            rand = StdRandom.uniform(items.length);
        }
        Item item = items[rand];
        items[rand] = null;
        size--;
        if (size > 0 && size == items.length / 4) resize(items.length / 2);

        //  emptyIndices.addLast(rand);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        int rand = StdRandom.uniform(items.length);
        while (items[rand] == null) {
            rand = StdRandom.uniform(items.length);
        }
        Item item = items[rand];
        return item;
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        class RandIterator implements Iterator<Item> {
            Item[] randItems;
            int index = 0;

            public RandIterator() {
                randItems = (Item[]) new Object[size];
                int s = size;
                for (int i = 0; i < s; i++) {
                    randItems[i] = dequeue();
                }
                for (int i = 0; i < randItems.length; i++) {
                    enqueue(randItems[i]);
                }

            }

            public boolean hasNext() {
                return index < size;
            }

            public Item next() {
                return randItems[index++];
            }

            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        return new RandIterator();
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<Integer> r = new RandomizedQueue<Integer>();
        r.enqueue(1);
        r.enqueue(2);
        r.enqueue(3);
        r.enqueue(4);
        r.enqueue(5);
        Iterator<Integer> it = r.iterator();

        while (it.hasNext()) {
            StdOut.println(it.next());
        }
        StdOut.println("size: " + r.size());

    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];

        int i = 0;
        int count = 0;
        while (i < items.length) {
            if (items[i] != null) {
                copy[count++] = items[i];
            }
            i++;
        }
        items = copy;
    }

}
