package client;

import model.Produs;
import model.WriteToFile;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Thread.sleep;

/**
 * Created by mtoader on 12/14/2017.
 */
public class ThreadClient implements Runnable {
    private ClientUI client = new ClientUI();
    private Boolean stop = false;
    private String name;
    private WriteToFile logger;

    public ThreadClient(String name, WriteToFile logger) {
        this.name = name;
        this.logger = logger;

    }


    public void stop() {
        stop = true;
    }

    @Override
    public void run() {
        client.setContr(new Controller("localhost", client.getPort(), logger));
        while(!stop) {
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.log("Client " + name + " try to get all products!");
            ArrayList<Produs> produse = client.getProduse();
            if (produse.size() == 0) {
                logger.log("Client " + name + " can't buy anything no more products in the shop");
            } else {
                int nrProdus = ThreadLocalRandom.current().nextInt(0, produse.size());
                int cantitate = ThreadLocalRandom.current().nextInt(0, 20);
                logger.log("Client " + name + " wants to buy product " + produse.get(nrProdus).getName() + " with code " + produse.get(nrProdus).getCod() + " in quantity " + cantitate);
                Double p = client.buyProdus(produse.get(nrProdus).getCod() + "|" + cantitate + "|" + name + "|" + new Date());
                if (p == 0) {
                    logger.log("Client " + name + " can't buy product " + produse.get(nrProdus).getName() + " with code " + produse.get(nrProdus).getCod() + " cause it doesn't exists anymore");
                } else {
                    logger.log("Client " + name + " bought product " + produse.get(nrProdus).getName() + " with code " + produse.get(nrProdus).getCod() + " and spent " + p);
                }
            }
        }
    }
}
