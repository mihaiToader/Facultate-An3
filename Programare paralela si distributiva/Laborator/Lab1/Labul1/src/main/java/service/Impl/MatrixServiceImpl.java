package service.Impl;

import domain.Matrix;
import domain.exception.InvalidMatrixException;
import domain.validator.MatrixValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import repository.MatrixRepository;
import service.MatrixService;
import service.ServiceRandom;

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


}
