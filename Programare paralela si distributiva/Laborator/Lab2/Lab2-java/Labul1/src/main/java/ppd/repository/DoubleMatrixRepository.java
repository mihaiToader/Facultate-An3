package ppd.repository;

import ppd.domain.GeneralMatrix;
import ppd.domain.Matrix;
import ppd.domain.exception.InvalidMatrixException;

import java.io.IOException;

/**
 * Created by toade on 10/30/2017.
 */
public interface DoubleMatrixRepository {
    GeneralMatrix<Double> readMatrixFromFile(String inputFile) throws IOException, InvalidMatrixException;

    void writeMatrixToFile(GeneralMatrix<Double> matrix, String fileName) throws IOException;
}
