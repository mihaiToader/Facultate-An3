package ppd.repository.Impl;

import org.apache.commons.io.FilenameUtils;
import ppd.domain.ComplexNumber;
import ppd.domain.GeneralMatrix;
import ppd.domain.exception.InvalidMatrixException;
import ppd.repository.ComplexMatrixRepository;

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
public class ComplexMatrixRepositoryImpl implements ComplexMatrixRepository {
    @Override
    public GeneralMatrix<ComplexNumber> readMatrixFromFile(String inputFile) throws IOException, InvalidMatrixException {
        try (Stream<String> stream = Files.lines(Paths.get(inputFile))) {
            GeneralMatrix<ComplexNumber> matrix = new GeneralMatrix<>(
                    stream.map(line -> line.split(" "))
                            .map(line -> {
                                List<ComplexNumber> res = new ArrayList<>();
                                for (String aLine : line) {
                                    res.add(new ComplexNumber(aLine));
                                }
                                return res;
                            })
                            .collect(Collectors.toList()));
            matrix.setName(FilenameUtils.getBaseName(inputFile));
            return matrix;
        }
    }

    @Override
    public void writeMatrixToFile(GeneralMatrix<ComplexNumber> matrix, String fileName) throws IOException {
        File outputFile = new File(fileName);
        outputFile.createNewFile();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write(matrix.toString());
        }
    }
}
