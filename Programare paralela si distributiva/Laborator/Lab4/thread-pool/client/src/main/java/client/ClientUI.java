package client;

import model.Command;
import model.Produs;
import model.SellCommand;
import model.WriteToFile;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by mtoader on 12/13/2017.
 */
public class ClientUI {
    private Controller contr = null;
    private Scanner in = new Scanner(System.in);
    private ArrayList<Thread> tClients = new ArrayList<>();
    private ArrayList<ThreadClient> clients = new ArrayList<>();
    private Integer port = 1234;
    private WriteToFile logger = new WriteToFile("C:\\Users\\mtoader\\Src\\Facultate-An3\\Programare paralela si distributiva\\Laborator\\Lab4\\logger\\loggerClient.txt");

    public Controller getContr() {
        return contr;
    }

    public void setContr(Controller contr) {
        this.contr = contr;
    }

    public void doSomething(String cmd) {
        if (contr == null) {
            readAddressAndPort();
        }
        switch(cmd.split(" ")[0]) {
            case "1":
                readAddressAndPort();
                break;
            case "2":
                getAllProduse();
                break;
            case "3":
                buyProdus(cmd.split(" ")[1]);
                break;
            case "4":
                createThreadClient(cmd.split(" ")[1]);
                break;
            case "5":
                stopAllClientThreads();
                break;
            default:
                System.out.println("Command unknown");
        }
    }

    private void createThreadClient(String data) {
        ThreadClient c = new ThreadClient(data, logger);
        Thread tC = new Thread(c);
        clients.add(c);
        tClients.add(tC);
        tC.start();
    }

    private void stopAllClientThreads() {
        for (ThreadClient c: clients) {
            c.stop();
        }
        for (Thread t: tClients) {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public Double buyProdus(String data) {
        String[] dates = data.split("\\|");
        Command buy = contr.sendCommand(new Command("Buy", new SellCommand(dates[0], Integer.parseInt(dates[1]), dates[2], dates[3])));
        if (buy.command.equals("don't know")) {
            return 0.0;
        }
        Double p = (Double)(buy.payload);
        return p;
    }

    public ArrayList<Produs> getProduse() {
        return (ArrayList<Produs>)(contr.sendCommand(new Command("GetProduse", "")).payload);
    }

    private void getAllProduse() {
        System.out.println("Produse:");
        getProduse().forEach(System.out::println);
    }

    private void readAddressAndPort() {
//        System.out.print("Address:");
//        String address = in.next();
        /*System.out.print("Port:");
        Integer port = in.nextInt();*/
        contr = new Controller("localhost", port, logger);
    }

    public void printMenu() {
        System.out.println("1                               - Set address and port");
        System.out.println("2                               - Get produse");
        System.out.println("3 <cod_produs>|<cantitate>|<nume>|<data>    - Buy produs");
        System.out.println("4 <name>                       - Create client thread");
        System.out.println("5                               - Stop all clients threads");
        System.out.println("0                               - Exit");
        System.out.print("--->");
    }

    public void start() {
        String cmd;
        Boolean stop = true;
        while (stop) {
            printMenu();
            cmd = in.nextLine();
            switch (cmd){
                case "0":
                    stop = false;
                    logger.write();
                    break;
                default:
                    doSomething(cmd);
            }
        }
    }

    public Integer getPort() {
        return port;
    }
}
