package model;

import java.io.Serializable;

/**
 * Created by mtoader on 12/13/2017.
 */
public class Vanzare implements Serializable {
    private String data;
    private Produs produs;
    private Integer cantitate;

    public Vanzare(String data, Produs produs, Integer cantitate) {
        this.data = data;
        this.produs = produs;
        this.cantitate = cantitate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    @Override
    public String toString() {
        return "Vanzare{" +
                "data='" + data + '\'' +
                ", produs=" + produs +
                ", cantitate=" + cantitate +
                '}';
    }
}
