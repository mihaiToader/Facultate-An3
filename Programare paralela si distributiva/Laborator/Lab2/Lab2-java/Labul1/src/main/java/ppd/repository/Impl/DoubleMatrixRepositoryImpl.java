package ppd.repository.Impl;

import org.apache.commons.io.FilenameUtils;
import ppd.domain.GeneralMatrix;
import ppd.domain.Matrix;
import ppd.domain.MatrixDoubleElement;
import ppd.domain.exception.InvalidMatrixException;
import ppd.domain.validator.MatrixValidator;
import ppd.repository.DoubleMatrixRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by toade on 10/30/2017.
 */
public class DoubleMatrixRepositoryImpl implements DoubleMatrixRepository {
    @Override
    public GeneralMatrix<Double> readMatrixFromFile(String inputFile) throws IOException, InvalidMatrixException {
        try (Stream<String> stream = Files.lines(Paths.get(inputFile))) {
            GeneralMatrix<Double> matrix = new GeneralMatrix<>(
                    stream.map(line -> line.split(" "))
                            .map(line -> {
                                List<Double> res = new ArrayList<>();
                                for (String aLine : line) {
                                    res.add(Double.parseDouble(aLine));
                                }
                                return res;
                            })
                            .collect(Collectors.toList()));
            matrix.setName(FilenameUtils.getBaseName(inputFile));
            return matrix;
        }
    }

    @Override
    public void writeMatrixToFile(GeneralMatrix<Double> matrix, String fileName) throws IOException {
        File outputFile = new File(fileName);
        outputFile.createNewFile();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write(matrix.toString());
        }
    }
}
