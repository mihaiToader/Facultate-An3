package ppd.domain;

/**
 * Created by toade on 10/30/2017.
 */
public class ComplexNumber {
    private Double a;
    private Double b;

    public ComplexNumber(Double a, Double b) {
        this.a = a;
        this.b = b;
    }

    public ComplexNumber() {
    }

    public ComplexNumber(String nb) {
        String[] nr = nb.split(";");
        a = Double.parseDouble(nr[0]);
        b = Double.parseDouble(nr[1]);
    }

    public Double getA() {
        return a;
    }

    public void setA(Double a) {
        this.a = a;
    }

    public Double getB() {
        return b;
    }

    public void setB(Double b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return a + ";" + b;
    }

    public ComplexNumber multiply(ComplexNumber cn) {
        return new ComplexNumber(a*cn.getA() - b*cn.getB(), a*cn.getB()  + cn.getA() *b);
    }

    public ComplexNumber divide(Integer nr) {
        return new ComplexNumber(nr / a, nr / b);
    }

    public ComplexNumber add(ComplexNumber cn) {
        return new ComplexNumber(a + cn.getA(), b + cn.getB());
    }

    public ComplexNumber weird(ComplexNumber cn) {
        return (this.divide(1).add(cn.divide(1))).divide(1);
    }
}
