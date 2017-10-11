package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by toade on 10/5/2017.
 */
public class Matrix {
    private String name;
    private Integer numberOfLines;
    private Integer numberOfColumns;
    private List<List<Integer>> content;

    public Matrix(String name, List<List<Integer>> content) {
        this.content = content;
        this.numberOfColumns = content.get(0).size();
        this.numberOfLines = content.size();
        this.name = name;
    }

    public Matrix(List<List<Integer>> content) {
        this.content = content;
        this.numberOfColumns = content.get(0).size();
        this.numberOfLines = content.size();
    }

    public Matrix(Integer[][] matrix) {
        this.content = toContent(matrix);
        this.numberOfColumns = content.get(0).size();
        this.numberOfLines = content.size();
    }

    public List<List<Integer>> toContent(Integer[][] matrix) {
        List<List<Integer>> res = new ArrayList<>();
        for (int i = 0; i < matrix.length; ++i) {
            res.add(new ArrayList<>());
            for (int j=0; j < matrix[0].length; ++j) {
                res.get(i).add(matrix[i][j]);
            }
        }
        return res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNumberOfLines() { return numberOfLines; }

    public void setNumberOfLines(Integer numberOfLines) { this.numberOfLines = numberOfLines; }

    public Integer getNumberOfColumns() { return numberOfColumns; }

    public void setNumberOfColumns(Integer numberOfColumns) { this.numberOfColumns = numberOfColumns; }

    public List<List<Integer>> getContent() { return content; }

    public void setContent(List<List<Integer>> content) { this.content = content; }

    public void setElement(Integer line, Integer column, Integer elementValue) {
        content.get(line).set(column, elementValue);
    }

    public Integer getElement(Integer line, Integer column) {
        return content.get(line).get(column);
    }

    @Override
    public String toString() {
        StringBuilder matrixStr = new StringBuilder();
        for (int i = 0; i < numberOfLines; ++i) {
            for (int j = 0; j< numberOfColumns; ++j) {
                matrixStr.append(getElement(i, j))
                        .append(" ");
            }
            matrixStr.append("\n");
        }
        return matrixStr.toString();
    }

    public String toNiceString() {
        StringBuilder matrixStr = new StringBuilder();
        matrixStr.append("Name: ").append(name).append("\n");
        for (int i = 0; i < numberOfLines; ++i) {
            for (int j = 0; j< numberOfColumns; ++j) {
                matrixStr.append(getElement(i, j))
                        .append(" ");
            }
            matrixStr.append("\n");
        }
        matrixStr.append("\n");
        return matrixStr.toString();
    }

    public Integer[][] toMatrixArray() {
        Integer[][] res = new Integer[numberOfLines][numberOfColumns];
        for (int i = 0; i < numberOfLines; ++i) {
            for (int j=0; j < numberOfColumns; ++j) {
                res[i][j] = getElement(i,j);
            }
        }
        return res;
    }
}
