package server;

import model.Factura;
import model.WriteToFile;
import server.Controller.GeneralController;


/**
 * Created by mtoader on 12/13/2017.
 */
public class ThreadCheck implements Runnable {
    private GeneralController contr;
    private boolean stop = false;
    private WriteToFile logger;
    public ThreadCheck(GeneralController contr) {
        this.contr = contr;
        logger = contr.getLogger();
    }

    public void stop() {
        stop = true;
    }

    @Override
    public void run() {
        logger.log("Checking thread started!");
        while (!stop) {
            Double checkSold = 0.0;
            for (Factura f : contr.getFacturi()) {
                checkSold += f.getVanzare().getCantitate() * f.getVanzare().getProdus().getPrice();
            }
            logger.log("!!!!! Check sold: " + checkSold + " | Actual sold: " + contr.getSoldTotal() + "!!!!!");
            logger.logWithoutDate("============================================================================================");
            logger.log("Produse:\r\n" + contr.getProduseStr());
            logger.logWithoutDate("********************************************************************************************");
            logger.log("Stocuri:\r\n" + contr.getStocuriStr());
            logger.logWithoutDate("********************************************************************************************");
            logger.log("Vanzari:\r\n" + contr.getVanzariStr());
            logger.logWithoutDate("********************************************************************************************");
            logger.log("Facturi:\r\n" + contr.getFacturiStr());
            logger.logWithoutDate("============================================================================================");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
