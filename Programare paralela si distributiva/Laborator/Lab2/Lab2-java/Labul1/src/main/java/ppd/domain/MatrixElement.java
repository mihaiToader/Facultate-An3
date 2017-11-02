package ppd.domain;

/**
 * Created by toade on 10/30/2017.
 */
public interface MatrixElement<T> {
    T getRandomElement();
    T getFromString(String element);
    T doOperation(T element);
}
