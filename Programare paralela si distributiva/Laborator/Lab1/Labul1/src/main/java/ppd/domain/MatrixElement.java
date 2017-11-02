package ppd.domain;

/**
 * Created by toade on 10/30/2017.
 */
public interface MatrixElement {
    MatrixElement getRandomElement();
    MatrixElement getFromString(String element);
    MatrixElement doOperation(MatrixElement element);
}
