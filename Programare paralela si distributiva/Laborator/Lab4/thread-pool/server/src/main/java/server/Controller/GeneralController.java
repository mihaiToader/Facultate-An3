package server.Controller;

import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mtoader on 12/13/2017.
 */
public class GeneralController {
    private HashMap<String, Produs> produse = new HashMap<>();
    private HashMap<String, Stoc> stocuri = new HashMap<>();
    private ArrayList<Vanzare> vanzari = new ArrayList<>();
    private ArrayList<Factura> facturi = new ArrayList<>();
    private Double soldTotal = 0.0;
    private WriteToFile logger;

    public GeneralController(WriteToFile logger) {
        this.logger = logger;
        addProdus(new Produs("ciocolata", "1111", 5.6, 1.2), 100);
        addProdus(new Produs("ghete", "1112", 15.6, 1.2), 100);
        addProdus(new Produs("masina", "1113", 23125.6, 1.2), 100);
        addProdus(new Produs("faina", "1114", 35.6, 1.2), 100);
        addProdus(new Produs("roata", "1115", 45.6, 1.2), 100);
        addProdus(new Produs("cooler", "1116", 55.6, 1.2), 100);
        addProdus(new Produs("laptop", "1117", 35.6, 1.2), 100);
    }

    public Map<String, Produs> getProduse() {
        return produse;
    }

    public Map<String, Stoc> getStocuri() {
        return stocuri;
    }

    public List<Vanzare> getVanzari() {
        return vanzari;
    }

    public List<Factura> getFacturi() {
        return facturi;
    }

    public Double getSoldTotal() {
        return soldTotal;
    }

    public void addProdus(Produs p, Integer cantitate) {
        System.out.println("add prdus " + p.getCod());
//        soldTotal -= p.getPrice() * cantitate;
        if (produse.containsKey(p.getCod())) {
            stocuri.get(p.getCod()).addCantitate(cantitate);
        } else {
            produse.put(p.getCod(), p);
            stocuri.put(p.getCod(), new Stoc(p.getCod(), cantitate));
        }
    }

    public synchronized void sellProdus(Produs p, Integer cantitate, String nume, String data) {
        System.out.println("[Controller]: vanzare produs " + p.getName() + " lui " + nume + " in cantitate de " + cantitate + " cod: " + p.getCod());
        if (stocuri.containsKey(p.getCod())) {
            Stoc stoc = stocuri.get(p.getCod());
            if (stoc.getCantitate() >= cantitate) {
                stoc.addCantitate(-cantitate);
                if (stoc.getCantitate() == 0) {
                    System.out.println("[Controller]: produsul " + p.getCod() + " este sters deoarece cantitatea a ajuns la 0");
                    produse.remove(p.getCod());
                    stocuri.remove(p.getCod());
                }
                Double sum = cantitate * p.getPrice();
                Vanzare v = new Vanzare(data, p, cantitate);
                Factura f = new Factura(nume, v, sum);
                soldTotal += sum;
                facturi.add(f);
                vanzari.add(v);
                System.out.println("[Controller]: vanzarea produsului " + p.getName() + " "  + p.getCod() + " a fost efectuata. Felicitari posesorului: " + nume);
            } else {
                System.out.println("[Controller]: " + p.getCod() + " prea mare cantitatea");
            }
        } else {
            System.out.println("[Controller]: " + p.getCod() + " nu e in stoc");
        }
    }

    public Command processCommand(Command c) {
        switch (c.command) {
            case "Buy":
                SellCommand s = (SellCommand) c.payload;
                if (produse.containsKey(s.p)) {
                    Produs p = produse.get(s.p);
                    sellProdus(produse.get(s.p), s.cantitate, s.nume, s.data);
                    return new Command("buy_succes", p.getPrice() * s.cantitate);
                } else {
                    System.out.println(s.p + " nu exista!");
                }
                return new Command("", "");
            case "GetProduse":
                return new Command("GetProduse", new ArrayList(produse.values()));
            default:
                return new Command("don't know", "");
        }
    }

    public WriteToFile getLogger() {
        return logger;
    }

    public String getProduseStr() {
        String res = "";
        for (Produs p :produse.values()) {
            res += p + "\r\n";
        }
        return res;
    }

    public String getStocuriStr() {
        String res = "";
        for (Stoc p :stocuri.values()) {
            res += p + "\r\n";
        }
        return res;
    }

    public String getVanzariStr() {
        String res = "";
        for (Vanzare p :vanzari) {
            res += p + "\r\n";
        }
        return res;
    }

    public String getFacturiStr() {
        String res = "";
        for (Factura p :facturi) {
            res += p + "\r\n";
        }
        return res;
    }
}
