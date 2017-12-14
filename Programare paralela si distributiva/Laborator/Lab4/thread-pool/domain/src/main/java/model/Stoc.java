package model;

import java.io.Serializable;

/**
 * Created by mtoader on 12/13/2017.
 */
public class Stoc implements Serializable {
    private String codProdus;
    private Integer cantitate;

    public Stoc(String codProdus, Integer cantitate) {
        this.codProdus = codProdus;
        this.cantitate = cantitate;
    }

    public String getCodProdus() {
        return codProdus;
    }

    public void setCodProdus(String codProdus) {
        this.codProdus = codProdus;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }

    public void addCantitate(Integer cantitate) {
        this.cantitate += cantitate;
    }

    @Override
    public String toString() {
        return "Stoc{" +
                "codProdus='" + codProdus + '\'' +
                ", cantitate=" + cantitate +
                '}';
    }
}
