package service.Impl;

import domain.Matrix;
import domain.exception.InvalidMatrixException;
import domain.validator.MatrixValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.MatrixRepository;
import service.MatrixService;
import service.ServiceRandom;
import thread.AddMatrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by toade on 10/5/2017.
 */
@Component
public class MatrixServiceImpl implements MatrixService {

    private final MatrixRepository matrixRepository;

    private final ServiceRandom serviceRandom;

    @Autowired
    public MatrixServiceImpl(MatrixRepository matrixRepository, ServiceRandom serviceRandom) {
        this.matrixRepository = matrixRepository;
        this.serviceRandom = serviceRandom;
    }

    @Override
    public void generateMatrix(int numberLines, int numberColumns, int from, int to, String name) throws InvalidMatrixException, IOException {
        List<List<Integer>> matrixHolder = new ArrayList<>();
        for (int i = 0; i < numberLines; ++i) {
            List<Integer> line = new ArrayList<>();
            for (int j = 0; j < numberColumns; ++j) {
                line.add(serviceRandom.getRandomInteger(from, to));
            }
            matrixHolder.add(line);
        }
        matrixRepository.writeMatrixToFile(MatrixValidator.validate(matrixHolder), name);
    }

    @Override
    public List<Matrix> getAllMatrix() {
        return matrixRepository.getAllMatrix();
    }

    @Override
    public String loadMatrixFromDirectory(String directory) throws IOException {
        return matrixRepository.loadMatrixFromDirectory(directory);
    }

    @Override
    public void sumMatrix(int nrThreads, String nameMatrix1, String nameMatrix2, String fileNameOut) throws Exception {

        Integer[][] a = matrixRepository.findByName(nameMatrix1).toMatrixArray();
        Integer[][] b = matrixRepository.findByName(nameMatrix2).toMatrixArray();
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new Exception("Matrix have to have the same dimensions!");
        }
        Integer[][] c = new Integer[a.length][a[0].length];
        Thread threads[] = new Thread[nrThreads];
        int n = a.length;
        int m = a[0].length;
        int size = n*m;
        int rest = size % nrThreads;
        int capacity = size / nrThreads;
        int start = 0;
        int end = capacity;
        for (int i=0; i<nrThreads; i++) {
            if (rest != 0) {
                rest--;
                end++;
            }
            threads[i] = new Thread(new AddMatrix(start, end, n, m, a, b, c));
            start = end;
            end += capacity;
        }

        for (int i = 0; i<nrThreads; i++) {
            threads[i].run();
        }
        for (int i = 0; i<nrThreads; i++) {
            threads[i].join();
        }
       matrixRepository.writeMatrixToFile(new Matrix(c), fileNameOut);
    }

    @Override
    public Matrix readMatrixFromFile(String inputFile) throws IOException, InvalidMatrixException {
        return matrixRepository.readMatrixFromFile(inputFile);
    }


}
