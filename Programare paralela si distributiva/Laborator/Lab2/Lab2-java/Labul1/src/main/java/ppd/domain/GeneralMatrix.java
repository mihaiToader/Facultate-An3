package ppd.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by toade on 10/5/2017.
 */
public class GeneralMatrix<T> {
    private String name;
    private Integer numberOfLines;
    private Integer numberOfColumns;
    private List<List<T>> content;

    public GeneralMatrix(Integer numberOfLines, Integer numberOfColumns) {
        this.numberOfLines = numberOfLines;
        this.numberOfColumns = numberOfColumns;
        this.content = new ArrayList<>();
        for (int i = 0; i < numberOfLines; i ++) {
            List<T> a = new ArrayList<>();
            for (int j = 0; j < numberOfColumns; j++) {
                a.add(null);
            }
            content.add(a);
        }
    }

    public GeneralMatrix(String name, List<List<T>> content) {
        this.content = content;
        this.numberOfColumns = content.get(0).size();
        this.numberOfLines = content.size();
        this.name = name;
    }

    public GeneralMatrix(List<List<T>> content) {
        this.content = content;
        this.numberOfColumns = content.get(0).size();
        this.numberOfLines = content.size();
    }

    public GeneralMatrix(T[][] matrix) {
        this.content = toContent(matrix);
        this.numberOfColumns = content.get(0).size();
        this.numberOfLines = content.size();
    }

    public List<List<T>> toContent(T[][] matrix) {
        List<List<T>> res = new ArrayList<>();
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

    public List<List<T>> getContent() { return content; }

    public void setContent(List<List<T>> content) { this.content = content; }

    public void setElement(Integer line, Integer column, T elementValue) {
        content.get(line).set(column, elementValue);
    }

    public T getElement(Integer line, Integer column) {
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

    @Override
    public boolean equals(Object o) {
        GeneralMatrix<T> matr = (GeneralMatrix<T>) o;
        if (numberOfColumns != matr.getNumberOfColumns() || numberOfLines != matr.getNumberOfLines()) {
            return false;
        }
        for (int i = 0; i < numberOfLines; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                if (getElement(i,j).equals(matr.getElement(i,j))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = numberOfLines != null ? numberOfLines.hashCode() : 0;
        result = 31 * result + (numberOfColumns != null ? numberOfColumns.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
