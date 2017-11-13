package ppd.domain;

import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by toade on 11/4/2017.
 */

//sincronizare la nivel de nod sau portiune din lista
public class SortedLinkedListV1<T extends Comparable> implements Iterable<T>, LinkedList<T> {
    private Node<T> first;
    private Lock iteratorLock = new ReentrantLock();

    public SortedLinkedListV1() {
        first = null;
    }

    public Node<T> getFirst() {
        return first;
    }

    public void setFirst(Node<T> first) {
        this.first = first;
    }

    private void insertAfter(Node<T> start, Node<T> theNewNode) {
        Node<T> rightNode = null;
        if (start.getRight() != null) {
            rightNode = start.getRight();
            rightNode.setLeft(theNewNode);
        }

        start.setRight(theNewNode);
        theNewNode.setLeft(start);
        theNewNode.setRight(rightNode);
    }

    private void insertBefore(Node<T> start, Node<T> theNewNode) {
        Node<T> left = start.getLeft();
        theNewNode.setLeft(left);
        theNewNode.setRight(start);

        left.setRight(theNewNode);
        start.setLeft(theNewNode);
        left.lock.unlock();
        start.lock.unlock();
    }

    public void add(T value) {
        if (first == null) {
            first = new Node<>(value, null, null);
        } else if (first.getValue().compareTo(value) > 0) {
            Node<T> theNewNode = new Node<>(value, first, null);
            first.lock.lock();
            first.setLeft(theNewNode);
            first = theNewNode;
            first.getRight().lock.unlock();
        } else {
            Node<T> start = first;
            Node<T> prev = null;
            start.lock.lock();
            while (start != null && start.getValue().compareTo(value) <= 0) {
                if (prev != null) {
                    prev.lock.unlock();
                }
                prev = start;
                start = start.getRight();
                if (start != null) {
                    start.lock.lock();
                }
            }
            if (start == null) {
                Node<T> theNewNode = new Node<>(value, null, null);
                prev.setRight(theNewNode);
                theNewNode.setLeft(prev);
                prev.lock.unlock();
            } else {
                insertBefore(start, new Node<>(value, null, null));
            }
        }
    }

    public T delete(Integer pos) {
        Integer index = 0;
        Node<T> start = first;
        if (start != null) {
            start.lock.lock();
        }
        while (start != null && !index.equals(pos)) {
            start.lock.unlock();
            start = start.getRight();
            if (start != null) {
                start.lock.lock();
            }
            index++;
        }
        if (!index.equals(pos) || start == null) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> left = start.getLeft();
        Node<T> right = start.getRight();

        if (start == first) {
            first = right;
        } else {
            left.lock.lock();
            left.setRight(right);
            if (right != null) {
                right.lock.lock();
                right.setLeft(left);
                right.lock.unlock();
            }
            left.lock.unlock();
        }
        start.lock.unlock();
        return start.getValue();
    }


    public String iterate() {
        synchronized (this) {
            String res = "";
            for (T value : this) {
                res += value + " ";
            }
            return res;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Node<T> current = first;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                T value = current.getValue();
                current = current.getRight();
                return value;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
}