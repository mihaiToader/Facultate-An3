package controller;

import domain.Matrix;
import domain.exception.InvalidMatrixException;

import java.io.IOException;
import java.util.List;

/**
 * Created by toade on 10/5/2017.
 */
public interface MatrixController {
    void generateMatrix(int numberLines, int numberColumns, int from, int to, String name) throws InvalidMatrixException, IOException;

    List<Matrix> getAllMatrix();

    String loadMatrixFromDirectory(String directory) throws IOException;
}
