package client;

import model.Command;
import model.WriteToFile;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by mtoader on 12/13/2017.
 */
public class Controller {
    private String address;
    private Integer port;
    private WriteToFile logger;

    public Controller(String address, Integer port, WriteToFile logger) {
        this.address = address;
        this.port = port;
        this.logger = logger;
    }

    public Command sendCommand(Command c){
        Socket socket = null;
        try {
            socket = new Socket(address, port);
        } catch (NumberFormatException ex) {
            System.out.println("Bad port format. Please enter a number.\n");
        } catch (UnknownHostException ex) {
            System.out.println("Cannot find the specified host. Please check host name...\n");
        } catch (IOException ex) {
            System.out.println(" >Cannot connect with server...");
            System.out.println(" >Please check if port number corresponds to port number in server...");
            System.out.println(" >Possibly server is not operating. Please try again later...\n");
        }catch(IllegalArgumentException ex) {
            System.out.println("Please enter a number between 0 and 65535 for the port.\n");
        }

        try {
            return request(socket, c);
        } catch (IOException ex) {
            System.out.println("Something get wrong with the connection...\n");
        }
        return null;
    }

    public Command request(Socket socket, Command cmd) throws IOException
    {
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream dataOutput = new ObjectOutputStream(outputStream);
        dataOutput.writeObject(cmd);

//        logger.log("Controller sending request...");
//        logger.log("Controller sending command: " + cmd.command);

        InputStream inputStream = socket.getInputStream();
        ObjectInputStream objectInput = new ObjectInputStream(inputStream);

        Command c = null;
        try {
            c = (Command) objectInput.readObject();
//            logger.log("Controller received command: " + c.command);

        } catch (ClassNotFoundException e) {
            System.out.println("An error occured while receiving please try again later");
        }



        dataOutput.close();
        outputStream.close();
        objectInput.close();
        inputStream.close();

        socket.close();

        return c;
    }
}
