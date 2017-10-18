package ppd.controller;

import ppd.domain.Matrix;
import ppd.domain.exception.InvalidMatrixException;

import java.io.IOException;
import java.util.List;

/**
 * Created by toade on 10/5/2017.
 */
public interface MatrixController {
    void generateMatrix(int numberLines, int numberColumns, int from, int to, String name) throws InvalidMatrixException, IOException;

    List<Matrix> getAllMatrix();

    String loadMatrixFromDirectory(String directory) throws IOException;

    void sumMatrix(int nrThreads, String nameMatrix1, String nameMatrix2, String fileNameOut) throws Exception;

    Matrix readMatrixFromFile(String inputFile) throws IOException, InvalidMatrixException;

    String loadMatrix(String directory, String matrix) throws IOException;

    void multiplyMatrix(int nrThreads, String nameMatrix1, String nameMatrix2, String fileNameOut) throws Exception;

    void clearMatrixes();
}
