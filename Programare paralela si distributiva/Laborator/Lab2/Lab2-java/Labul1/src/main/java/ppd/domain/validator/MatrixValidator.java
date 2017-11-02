package ppd.domain.validator;

import ppd.domain.Matrix;
import ppd.domain.exception.InvalidMatrixException;

import java.util.List;

/**
 * Created by toade on 10/5/2017.
 */
public class MatrixValidator {
    public static Matrix validate(List<List<Integer>> matrixHolder) throws InvalidMatrixException {
        if (matrixHolder != null && matrixHolder.size() > 0) {
            if (matrixHolder.get(0) == null) {
                throw new InvalidMatrixException("Invalid matrix, null line 0");
            }
            for (int i = 1; i < matrixHolder.size(); ++i) {
                if (matrixHolder.get(i) == null) {
                    throw new InvalidMatrixException("Invalid matrix, null line " + i);
                }
                if (matrixHolder.get(i).size() != matrixHolder.get(i - 1).size()) {
                    throw  new InvalidMatrixException(String.format("Invalid matrix, line %s and line %s have different size.", i, i-1));
                }
            }
        }
        return new Matrix(matrixHolder);
    }
}
