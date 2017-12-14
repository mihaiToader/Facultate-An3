package model;

import java.io.Serializable;

/**
 * Created by mtoader on 12/13/2017.
 */
public class Factura implements Serializable {
    private String name;
    private Vanzare vanzare;
    private Double totalSum;

    public Factura(String name, Vanzare vanzare, Double totalSum) {
        this.name = name;
        this.vanzare = vanzare;
        this.totalSum = totalSum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vanzare getVanzare() {
        return vanzare;
    }

    public void setVanzare(Vanzare vanzare) {
        this.vanzare = vanzare;
    }

    public Double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(Double totalSum) {
        this.totalSum = totalSum;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "name='" + name + '\'' +
                ", vanzare=" + vanzare +
                ", totalSum=" + totalSum +
                '}';
    }
}
