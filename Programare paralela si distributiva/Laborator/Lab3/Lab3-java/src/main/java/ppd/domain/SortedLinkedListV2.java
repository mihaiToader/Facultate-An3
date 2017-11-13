package ppd.domain;

import java.util.Iterator;

/**
 * Created by toade on 11/4/2017.
 */

//sincronizare	la	nivelul	intregii liste
public class SortedLinkedListV2<T extends Comparable> implements Iterable<T>, LinkedList<T> {
    private Node<T> first;

    public SortedLinkedListV2() {
        first = null;
    }

    public Node<T> getFirst() {
        return first;
    }

    public void setFirst(Node<T> first) {
        this.first = first;
    }

    private void insertBefore(Node<T> start, Node<T> theNewNode) {
        Node<T> left = start.getLeft();

        theNewNode.setLeft(left);
        theNewNode.setRight(start);

        left.setRight(theNewNode);
        start.setLeft(theNewNode);
    }

    public void add(T value) {
        synchronized (this) {
            if (first == null) {
                first = new Node<>(value, null, null);
            } else if (first.getValue().compareTo(value) > 0) {
                Node<T> theNewNode = new Node<>(value, first, null);
                first.setLeft(theNewNode);
                first = theNewNode;
            } else {
                Node<T> start = first;
                Node<T> prev = null;
                while (start != null && start.getValue().compareTo(value) <= 0) {
                    prev = start;
                    start = start.getRight();
                }
                if (start == null) {
                    Node<T> theNewNode = new Node<>(value, null, null);
                    prev.setRight(theNewNode);
                    theNewNode.setLeft(prev);
                } else {
                    insertBefore(start, new Node<>(value, null, null));
                }
            }
        }
    }

    public T delete(Integer pos) {
        synchronized (this) {
            Integer index = 0;
            Node<T> start = first;
            while (start != null && !index.equals(pos)) {
                start = start.getRight();
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
                left.setRight(right);
                if (right != null) {
                    right.setLeft(left);
                }
            }
            return start.getValue();
        }
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

