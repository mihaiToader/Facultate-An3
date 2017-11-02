package ppd.domain;

/**
 * Created by toade on 10/30/2017.
 */
public class MatrixDoubleElement implements MatrixElement<Double> {
    private Double el;

    public MatrixDoubleElement(Double el) {
        this.el = el;
    }

    public MatrixDoubleElement(String el) {
        this.el = getFromString(el);
    }

    public Double getEl() {
        return el;
    }

    public void setEl(Double el) {
        this.el = el;
    }

    @Override
    public Double getRandomElement() {
        return null;
    }

    @Override
    public Double getFromString(String element) {
        return Double.parseDouble(element);
    }

    @Override
    public Double doOperation(Double element) {
        return el * element;
    }

    @Override
    public boolean equals(Object obj) {
        Double element = (Double) obj;
        return el.equals(element);
    }

}
