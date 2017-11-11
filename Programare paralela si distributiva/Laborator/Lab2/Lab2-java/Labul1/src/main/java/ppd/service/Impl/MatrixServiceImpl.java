package ppd.service.Impl;

import ppd.domain.ComplexNumber;
import ppd.domain.GeneralMatrix;
import ppd.domain.Matrix;
import ppd.domain.ThreadResults;
import ppd.domain.exception.InvalidMatrixException;
import ppd.domain.validator.MatrixValidator;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ppd.repository.ComplexMatrixRepository;
import ppd.repository.DoubleMatrixRepository;
import ppd.repository.MatrixRepository;
import ppd.repository.ThreadResultsRepository;
import ppd.service.MatrixService;
import ppd.service.ServiceRandom;
import ppd.thread.AddMatrix;
import ppd.thread.GeneralMatrixThread;
import ppd.thread.MultiplyMatrix;
import ppd.thread.ThreadMatrix;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;

import static java.lang.Math.pow;

/**
 * Created by toade on 10/5/2017.
 */
@Component
public class MatrixServiceImpl implements MatrixService {

    private final MatrixRepository matrixRepository;

    private final ServiceRandom serviceRandom;

    private final ThreadResultsRepository threadResultsRepository;

    private final DoubleMatrixRepository doubleMatrixRepository;

    private final ComplexMatrixRepository complexMatrixRepository;

    @Autowired
    public MatrixServiceImpl(MatrixRepository matrixRepository, ServiceRandom serviceRandom, ThreadResultsRepository threadResultsRepository, DoubleMatrixRepository doubleMatrixRepository, ComplexMatrixRepository complexMatrixRepository) {
        this.matrixRepository = matrixRepository;
        this.serviceRandom = serviceRandom;
        this.threadResultsRepository = threadResultsRepository;
        this.doubleMatrixRepository = doubleMatrixRepository;
        this.complexMatrixRepository = complexMatrixRepository;
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

        Matrix matrix1 = matrixRepository.findByName(nameMatrix1);
        Integer[][] a = matrix1.toMatrixArray();
        Matrix matrix2 = matrixRepository.findByName(nameMatrix2);
        Integer[][] b = matrix2.toMatrixArray();
        if (a.length != b.length || a[0].length != b[0].length) {
            throw new Exception("Matrix has to have the same dimensions!");
        }
        Integer[][] c = new Integer[a.length][a[0].length];
        Thread threads[] = new Thread[nrThreads];
        List<ThreadMatrix> addMatrices = new ArrayList<>();
        int n = a.length;
        int m = a[0].length;
        int size = n * m;
        int rest = size % nrThreads;
        int capacity = size / nrThreads;
        int start = 0;
        int end = capacity;
        for (int i = 0; i < nrThreads; i++) {
            if (rest != 0) {
                rest--;
                end++;
            }
            ThreadMatrix target = new AddMatrix(start, end, n, m, a, b, c, "Thread " + i);
            threads[i] = new Thread(target);
            addMatrices.add(target);
            start = end;
            end += capacity;
        }

        LocalDateTime startTime = LocalDateTime.now();
        for (int i = 0; i < nrThreads; i++) {
            threads[i].run();
        }
        for (int i = 0; i < nrThreads; i++) {
            threads[i].join();
        }
        LocalDateTime finishTime = LocalDateTime.now();
        Matrix outMatrix = new Matrix(c);
        outMatrix.setName(FilenameUtils.getBaseName(fileNameOut));

        ThreadResults threadResults = new ThreadResults();
        threadResults.setAddThread(addMatrices);
        threadResults.setExecutionTimeInNano((int) ChronoUnit.NANOS.between(startTime, finishTime));
        threadResults.setExecutionTimeInSeconds((int) ChronoUnit.SECONDS.between(startTime, finishTime));
        threadResults.setFinishTime(finishTime);
        threadResults.setFirstMatrix(matrix1);
        threadResults.setSecondMatrix(matrix2);
        threadResults.setOutMatrix(outMatrix);
        threadResults.setThreadNumber(nrThreads);
        threadResults.setStartTime(startTime);
        threadResults.setOperation("Add matrix!");
        threadResultsRepository.writeResultsToFile(threadResults, fileNameOut);
        matrixRepository.writeMatrixToFile(outMatrix, fileNameOut);
    }

    @Override
    public void multiplyMatrix(int nrThreads, String nameMatrix1, String nameMatrix2, String fileNameOut) throws Exception {

        Matrix matrix1 = matrixRepository.findByName(nameMatrix1);
        Integer[][] a = matrix1.toMatrixArray();
        Matrix matrix2 = matrixRepository.findByName(nameMatrix2);
        Integer[][] b = matrix2.toMatrixArray();
        if (a[0].length != b.length) {
            throw new Exception("First matrix columns have to be equal with second matrix lines!");
        }
        Thread threads[] = new Thread[nrThreads];
        List<ThreadMatrix> addMatrices = new ArrayList<>();
        int n = a.length;
        int m = b[0].length;
        Integer[][] c = new Integer[n][m];
        int size = n * m;
        int rest = size % nrThreads;
        int capacity = size / nrThreads;
        int start = 0;
        int end = capacity;
        for (int i = 0; i < nrThreads; i++) {
            if (rest != 0) {
                rest--;
                end++;
            }
            ThreadMatrix target = new MultiplyMatrix(start, end, a[0].length, m, a, b, c, "Thread " + i);
            threads[i] = new Thread(target);
            addMatrices.add(target);
            start = end;
            end += capacity;
        }

        LocalDateTime startTime = LocalDateTime.now();
        for (int i = 0; i < nrThreads; i++) {
            threads[i].run();
        }
        for (int i = 0; i < nrThreads; i++) {
            threads[i].join();
        }
        LocalDateTime finishTime = LocalDateTime.now();
        Matrix outMatrix = new Matrix(c);
        outMatrix.setName(FilenameUtils.getBaseName(fileNameOut));

        ThreadResults threadResults = new ThreadResults();
        threadResults.setAddThread(addMatrices);
        threadResults.setExecutionTimeInNano((int) ChronoUnit.NANOS.between(startTime, finishTime));
        threadResults.setExecutionTimeInSeconds((int) ChronoUnit.SECONDS.between(startTime, finishTime));
        threadResults.setFinishTime(finishTime);
        threadResults.setFirstMatrix(matrix1);
        threadResults.setSecondMatrix(matrix2);
        threadResults.setOutMatrix(outMatrix);
        threadResults.setThreadNumber(nrThreads);
        threadResults.setStartTime(startTime);
        threadResults.setOperation("Multiply matrix!");
        threadResultsRepository.writeResultsToFile(threadResults, fileNameOut);
        matrixRepository.writeMatrixToFile(outMatrix, fileNameOut);
    }


    @Override
    public void generateDoubleMatrix(int numberLines, int numberColumns, int from, int to, String name) throws InvalidMatrixException, IOException {
        List<List<Double>> matrixHolder = new ArrayList<>();
        for (int i = 0; i < numberLines; ++i) {
            List<Double> line = new ArrayList<>();
            for (int j = 0; j < numberColumns; ++j) {
                line.add(serviceRandom.getRandomDouble(from, to));
            }
            matrixHolder.add(line);
        }
        doubleMatrixRepository.writeMatrixToFile(new GeneralMatrix<>(matrixHolder), name);
    }

    @Override
    public void generateComplexMatrix(int numberLines, int numberColumns, int from, int to, String name) throws InvalidMatrixException, IOException {
        List<List<ComplexNumber>> matrixHolder = new ArrayList<>();
        for (int i = 0; i < numberLines; ++i) {
            List<ComplexNumber> line = new ArrayList<>();
            for (int j = 0; j < numberColumns; ++j) {
                line.add(serviceRandom.getRandomComplexNumebr(from, to));
            }
            matrixHolder.add(line);
        }
        complexMatrixRepository.writeMatrixToFile(new GeneralMatrix<>(matrixHolder), name);
    }

    @Override
    public void doubleMatrix(int nrThreads, String nameMatrix1, String nameMatrix2, String outFile) throws Exception {

        GeneralMatrix<Double> a = doubleMatrixRepository.readMatrixFromFile(nameMatrix1);
        GeneralMatrix<Double> b = doubleMatrixRepository.readMatrixFromFile(nameMatrix2);
        GeneralMatrix<Double> c = new GeneralMatrix<>(a.getNumberOfLines(), a.getNumberOfColumns());
        BinaryOperator<Double> function = (d1, d2) -> d1 * d2;
        createGeneralThreads(nrThreads, a, b, c, function, "Double matrix product");
        doubleMatrixRepository.writeMatrixToFile(c, outFile);
    }

    @Override
    public void complexMatrix(int nrThreads, String nameMatrix1, String nameMatrix2, String outFile) throws Exception {
        GeneralMatrix<ComplexNumber> a = complexMatrixRepository.readMatrixFromFile(nameMatrix1);
        GeneralMatrix<ComplexNumber> b = complexMatrixRepository.readMatrixFromFile(nameMatrix2);
        GeneralMatrix<ComplexNumber> c = new GeneralMatrix<>(a.getNumberOfLines(), a.getNumberOfColumns());
        BinaryOperator<ComplexNumber> function = ComplexNumber::multiply;
        createGeneralThreads(nrThreads, a, b, c, function, "Complex matrix product");
        complexMatrixRepository.writeMatrixToFile(c, outFile);
    }

    @Override
    public void doubleMatrixWeird(int nrThreads, String nameMatrix1, String nameMatrix2, String outFile) throws Exception {

        GeneralMatrix<Double> a = doubleMatrixRepository.readMatrixFromFile(nameMatrix1);
        GeneralMatrix<Double> b = doubleMatrixRepository.readMatrixFromFile(nameMatrix2);
        GeneralMatrix<Double> c = new GeneralMatrix<>(a.getNumberOfLines(), a.getNumberOfColumns());
        BinaryOperator<Double> function = (d1, d2) -> 1 / (1 / d1 + 1 / d2);
        createGeneralThreads(nrThreads, a, b, c, function, "Double matrix weird operation");
        doubleMatrixRepository.writeMatrixToFile(c, outFile);
    }

    @Override
    public void complexMatrixWeird(int nrThreads, String nameMatrix1, String nameMatrix2, String outFile) throws Exception {
        GeneralMatrix<ComplexNumber> a = complexMatrixRepository.readMatrixFromFile(nameMatrix1);
        GeneralMatrix<ComplexNumber> b = complexMatrixRepository.readMatrixFromFile(nameMatrix2);
        GeneralMatrix<ComplexNumber> c = new GeneralMatrix<>(a.getNumberOfLines(), a.getNumberOfColumns());
        BinaryOperator<ComplexNumber> function = (n1, n2) -> new  ComplexNumber(((n1.getA() + n2.getA())*(n1.getA() * n2.getA() - n1.getB()*n2.getB()) +
                        (n1.getB() + n2.getB())*(n1.getA()*n2.getB() + n2.getA()*n1.getB())) /
                        (pow(n1.getA() + n2.getA(), 2) - pow(n1.getB() + n2.getB(), 2)),
                ((n1.getA() + n2.getA())*(n1.getA()*n2.getB() + n2.getA()*n1.getB())
                        + (n1.getB() + n2.getB())*(-n1.getA() * n2.getA() + n1.getB()*n2.getB())) /
                        (pow(n1.getA() + n2.getA(), 2) - pow(n1.getB() + n2.getB(), 2)));
        createGeneralThreads(nrThreads, a, b, c, function, "Complex matrix weird operation");
        complexMatrixRepository.writeMatrixToFile(c, outFile);
    }
    public <T> void createGeneralThreads(int nrThreads, GeneralMatrix<T> a, GeneralMatrix<T> b, GeneralMatrix<T> c, BinaryOperator<T> func, String operation) throws InterruptedException {
        Thread threads[] = new Thread[nrThreads];
        List<Integer> threadLoad = new ArrayList<>();
        int n = a.getNumberOfLines();
        int m = a.getNumberOfColumns();
        int size = n * m;
        int rest = size % nrThreads;
        int capacity = size / nrThreads;
        int start = 0;
        int end = capacity;
        for (int i = 0; i < nrThreads; i++) {
            if (rest != 0) {
                rest--;
                end++;
            }
            GeneralMatrixThread<T> target = new GeneralMatrixThread<>(start, end, n, m, a, b, c, "Thread " + i, func);
            threads[i] = new Thread(target);
            threadLoad.add(end - start);
            start = end;
            end += capacity;
        }

        LocalDateTime startTime = LocalDateTime.now();
        for (int i = 0; i < nrThreads; i++) {
            threads[i].run();
        }
        for (int i = 0; i < nrThreads; i++) {
            threads[i].join();
        }
        LocalDateTime finishTime = LocalDateTime.now();
        String threadResults = "Results:\n" +
                "-> operation = " + operation + "\n" +
                "-> threadNumber = " + nrThreads + "\n" +
                "-> startTime = " + startTime + "\n" +
                "-> finishTime = " + finishTime + "\n" +
                "-> executionTimeInSeconds = " + ChronoUnit.SECONDS.between(startTime, finishTime) + "\n" +
                "-> executionTimeInMilliseconds = " + ChronoUnit.MILLIS.between(startTime, finishTime) + "\n";
        threadResults += "\n\n";
        int index = 1;
        for (Integer load : threadLoad) {
            threadResults += "------------------------------------------------------------------------------\n";
            threadResults += "-> threadName: " + index + " load " + load + "\n";
            index++;
        }
        threadResults += "------------------------------------------------------------------------------\n";
        threadResults += "\n\n";
        threadResults += "First matrix:\n" + a.getName() + " " + a.getNumberOfLines() + "X" + a.getNumberOfColumns() + "\n";
        threadResults += "Second matrix:\n" + b.getName() + " " + b.getNumberOfLines() + "X" + b.getNumberOfColumns() + "\n";
        System.out.println(threadResults);
    }

    @Override
    public void clearMatrix() {
        matrixRepository.clear();
    }

    @Override
    public Matrix readMatrixFromFile(String inputFile) throws IOException, InvalidMatrixException {
        return matrixRepository.readMatrixFromFile(inputFile);
    }

    @Override
    public String loadMatrix(String directory, String matrix) throws IOException {
        return matrixRepository.loadMatrix(directory, matrix);
    }


}
