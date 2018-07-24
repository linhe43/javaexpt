package queue;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] itemArr = null;
    private int size;

    private int arraySize = 10;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        itemArr = (Item[]) new Object[arraySize];
        size = 0;
    }

    /**
     * is the randomized queue empty?
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of items on the randomized queue
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * add the item
     * @param item
     */
    public void enqueue(Item item) {
        if (size + 1 == arraySize) {
            resize(2 * arraySize);
        }
        itemArr[size] = item;
        size++;
    }

    /**
     * remove and return a random item
     * @return
     */
    public Item dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("The queue is empty!");
        }

        int idx = StdRandom.uniform(size);
        Item ret = itemArr[idx];
        itemArr[idx] = itemArr[size - 1];
        itemArr[size - 1] = null;
        size--;

        if (size == arraySize / 4) {
            resize(arraySize / 2);
        }

        return ret;
    }

    /**
     * return a random item (but do not remove it)
     * @return
     */
    public Item sample() {
        if (size == 0) {
            throw new NoSuchElementException("The queue is empty!");
        }

        return itemArr[StdRandom.uniform(size)];
    }

    /**
     * return an independent iterator over items in random order
     * @return
     */
    public Iterator<Item> iterator() {
        return new RandomizedIterator<Item>();
    }

    private class RandomizedIterator<Item> implements Iterator<Item> {

        private int curIdx = 0;
        private int[] idxArr;

        public RandomizedIterator() {
            idxArr = new int[size];
            for (int i = 0; i < size; i++) {
                idxArr[i] = i;
            }

            StdRandom.shuffle(idxArr);
        }

        @Override
        public boolean hasNext() {
            return curIdx < size;
        }

        @Override
        public Item next() {
            if (curIdx >= size) {
                throw new NoSuchElementException("No more item in the queue!");
            }

            return (Item) itemArr[idxArr[curIdx++]];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported!");
        }
    }



    /**
     * resize the array
     */
    private void resize(int capacity) {
        arraySize = capacity;
        Item[] newItemArr = (Item[]) new Object[arraySize];
        System.arraycopy(itemArr, 0, newItemArr, 0, size);
        itemArr = newItemArr;
    }

    /**
     * unit testing
     * @param args
     */
    public static void main(String[] args) {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        while (StdIn.hasNextLine()) {
            String line = StdIn.readLine();
            if (line.equalsIgnoreCase("-")) {
                int item = rq.dequeue();
                System.out.println("remove " + item);
            } else if (line.trim().equalsIgnoreCase("")) {
                break;
            } else {
                int item = Integer.parseInt(line.trim());
                rq.enqueue(item);
                System.out.println("add " + item);
            }
        }

        for (int i = 0; i < 3; i++) {
            Iterator<Integer> it = rq.iterator();
            while (it.hasNext()) {
                System.out.print(it.next() + ", ");
            }
            System.out.println();
        }
    }
}