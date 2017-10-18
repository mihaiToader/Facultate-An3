package ppd.repository;

import ppd.domain.Matrix;
import ppd.domain.exception.InvalidMatrixException;

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

    Matrix findByName(String name) throws Exception;

    String loadMatrix(String directory, String matrix) throws IOException;

    void clear();
}
