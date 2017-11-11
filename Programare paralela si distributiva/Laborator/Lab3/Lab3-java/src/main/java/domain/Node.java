package domain;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by toade on 11/4/2017.
 */
public class Node<T> {
    private T value;
    private Node<T> right;
    private Node<T> left;
    public Lock lock = new ReentrantLock();

    public Node(T value, Node<T> right, Node<T> left) {
        this.value = value;
        this.right = right;
        this.left = left;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public Node<T> getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public Node<T> getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }
}
