package controller.Impl;

import controller.MatrixController;
import domain.Matrix;
import domain.exception.InvalidMatrixException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.MatrixService;

import java.io.IOException;
import java.util.List;

/**
 * Created by toade on 10/5/2017.
 */
@Component
public class MatrixControllerImpl implements MatrixController {
    private final MatrixService matrixService;

    @Autowired
    public MatrixControllerImpl(MatrixService matrixService) {
        this.matrixService = matrixService;
    }

    @Override
    public void generateMatrix(int numberLines, int numberColumns, int from, int to, String name) throws InvalidMatrixException, IOException {
        if (numberLines > 0 && numberColumns > 0) {
            matrixService.generateMatrix(numberLines, numberColumns, from, to, name);
        } else {
            throw new IllegalArgumentException("Invalid numbers of lines or columns.");
        }
    }

    @Override
    public List<Matrix> getAllMatrix() {
        return matrixService.getAllMatrix();
    }

    @Override
    public String loadMatrixFromDirectory(String directory) throws IOException {
        return matrixService.loadMatrixFromDirectory(directory);
    }

    @Override
    public void sumMatrix(int nrThreads, String nameMatrix1, String nameMatrix2, String fileNameOut) throws Exception {
        matrixService.sumMatrix(nrThreads, nameMatrix1, nameMatrix2, fileNameOut);
    }
}
