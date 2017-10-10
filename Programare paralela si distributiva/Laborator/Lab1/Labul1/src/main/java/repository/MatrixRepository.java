package repository;

import domain.Matrix;
import domain.exception.InvalidMatrixException;

import java.io.IOException;
import java.util.List;

/**
 * Created by toade on 10/4/2017.
 */
public interface MatrixRepository {
    Matrix readMatrixFromFile(String inputFile) throws IOException, InvalidMatrixException;

    void writeMatrixToFile(Matrix matrix, String fileName) throws IOException;

    List<Matrix> getAllMatrix();

    String loadMatrixFromDirectory(String directory) throws IOException;

}
