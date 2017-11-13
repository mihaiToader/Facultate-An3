package ppd.domain;

/**
 * Created by toade on 11/5/2017.
 */
public interface LinkedList<T> {
    void add(T value);
    T delete(Integer pos);
    String iterate();
}
