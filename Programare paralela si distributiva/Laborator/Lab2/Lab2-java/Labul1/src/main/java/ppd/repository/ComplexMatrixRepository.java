package ppd.repository;

import ppd.domain.ComplexNumber;
import ppd.domain.GeneralMatrix;
import ppd.domain.exception.InvalidMatrixException;

import java.io.IOException;

/**
 * Created by toade on 10/30/2017.
 */
public interface ComplexMatrixRepository {
    GeneralMatrix<ComplexNumber> readMatrixFromFile(String inputFile) throws IOException, InvalidMatrixException;

    void writeMatrixToFile(GeneralMatrix<ComplexNumber> matrix, String fileName) throws IOException;
}
