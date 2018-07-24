package queue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node<Item> head, tail;
    private int size;

    private class Node<Item> {

        public Item item;
        public Node<Item> last;
        public Node<Item> next;

        public Node(Item item) {
            this.item = item;
            this.last = null;
            this.next = null;
        }
    }

    /**
     * construct an empty deque
     */
    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * is the deque empty?
     * @return
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * return the number of items on the deque
     * @return
     */
    public int size() {
        return size;
    }

    /**
     * add the item to the front
     * @param item
     */
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> newNode = new Node<>(item);
        if (head == null) {
            head = newNode;
            tail = head;
        } else {
            newNode.next = head;
            head.last = newNode;
            head = newNode;
        }
        size++;
    }

    /**
     * add the item to the end
     * @param item
     */
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }

        Node<Item> newNode = new Node<>(item);
        if (tail == null) {
            head = newNode;
            tail = head;
        } else {
            tail.next = newNode;
            newNode.last = tail;
            tail = newNode;
        }
        size++;
    }

    /**
     * remove and return the item from the front
     * @return
     */
    public Item removeFirst() {
        if (head == null) {
            throw new NoSuchElementException();
        }

        Node<Item> oldFirst = head;
        head = head.next;
        if (head != null) {
            head.last = null;
        }
        size--;

        return oldFirst.item;
    }

    /**
     * remove and return the item from the end
     * @return
     */
    public Item removeLast() {
        if (tail == null) {
            throw new NoSuchElementException();
        }

        Node<Item> oldLast = tail;
        tail = tail.last;
        if (tail != null) {
            tail.next = null;
        }
        size--;

        return oldLast.item;
    }

    /**
     * return an iterator over items in order from front to end
     * @return
     */
    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator<Item>();
    }

    private class DequeIterator<Item> implements Iterator<Item> {
        private Node current = head;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException();
            }

            Node<Item> ret = current;
            current = current.next;
            return ret.item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

}