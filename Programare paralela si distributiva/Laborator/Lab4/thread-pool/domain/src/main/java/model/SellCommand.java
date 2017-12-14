package model;

import java.io.Serializable;

/**
 * Created by mtoader on 12/13/2017.
 */
public class SellCommand implements Serializable {
    public String p;
    public Integer cantitate;
    public String nume;
    public String data;

    public SellCommand() {
    }

    public SellCommand(String p, Integer cantitate, String nume, String data) {
        this.p = p;
        this.cantitate = cantitate;
        this.nume = nume;
        this.data = data;
    }
}
