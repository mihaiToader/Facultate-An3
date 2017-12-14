package model;

import java.io.Serializable;

/**
 * Created by mtoader on 12/13/2017.
 */
public class Produs implements Serializable {
    private String name;
    private String cod;
    private Double price;
    private Double units;

    public Produs(String name, String cod, Double price, Double units) {
        this.name = name;
        this.cod = cod;
        this.price = price;
        this.units = units;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getUnits() {
        return units;
    }

    public void setUnits(Double units) {
        this.units = units;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "name='" + name + '\'' +
                ", cod='" + cod + '\'' +
                ", price=" + price +
                ", units=" + units +
                '}';
    }
}
