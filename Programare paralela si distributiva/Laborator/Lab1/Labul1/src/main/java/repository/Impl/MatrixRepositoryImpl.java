package repository.Impl;

import domain.Matrix;
import domain.exception.InvalidMatrixException;
import domain.validator.MatrixValidator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Component;
import repository.MatrixRepository;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by toade on 10/4/2017.
 */
@Component
public class MatrixRepositoryImpl implements MatrixRepository {
    private List<Matrix> allMatrix = new ArrayList<>();

    public Matrix readMatrixFromFile(String inputFile) throws IOException, InvalidMatrixException {
        try (Stream<String> stream = Files.lines(Paths.get(inputFile))) {
            Matrix matrix = MatrixValidator.validate(
                    stream.map(line -> line.split(" "))
                            .map(line -> {
                                List<Integer> res = new ArrayList<>();
                                for (String aLine : line) {
                                    res.add(Integer.parseInt(aLine));
                                }
                                return res;
                            })
                            .collect(Collectors.toList()));
            matrix.setName(FilenameUtils.getName(inputFile));
            return matrix;
        }
    }

    @Override
    public void writeMatrixToFile(Matrix matrix, String fileName) throws IOException {
        File outputFile = new File(fileName);
        outputFile.createNewFile();
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(fileName))) {
            writer.write(matrix.toString());
        }
    }

    public String loadMatrixFromDirectory(String directory) throws IOException {
        String errors = "";
        final File folder = new File(directory);
        if (folder.listFiles() == null) {
            errors += "No matrix found!";
        } else {
            for (final File fileEntry : folder.listFiles()) {
                try {
                    allMatrix.add(readMatrixFromFile(fileEntry.toString()));
                } catch (InvalidMatrixException e) {
                    e.printStackTrace();
                }
            }
        }
        return errors;
    }

    public List<Matrix> getAllMatrix() {
        return allMatrix;
    }
}
