package server;

import model.Command;
import server.Controller.GeneralController;

import java.io.*;
import java.net.Socket;

public class Worker extends Thread{

	private Queue queue;
	private GeneralController contr;
	private volatile boolean isRunning;
	// Constructors
	public Worker(Queue queue, GeneralController contr) {
		this.queue = queue;
		this.isRunning = true;
		this.contr = contr;
	}

	@Override
	public void run(){
		String threadName = Thread.currentThread().getName();
		System.out.println("[" + threadName + "] Running...");
		System.out.println("[" + threadName + "] Initalizing variables...");
		
		//Initializing variables
		Socket socket;
		ObjectInputStream objectInput = null;
		Command cmd = null;
		ObjectOutputStream objectOutput = null;

		while (isRunning) {
			while (!queue.isEmpty()) {
				//Takes the corresponding socket that was first in the queue
				socket = (Socket) queue.dequeue();
				
				System.out.println("[" + threadName + "] Connecting with: "
						+ socket.getPort());
				//Initiates the input stream communication
				try {
					objectInput = new ObjectInputStream(socket.getInputStream());
					try {
						cmd = (Command)objectInput.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}
					System.out.println("[" + threadName + "] Command: " + cmd.command
							+ " from " + socket.getPort());

				} catch (IOException e) {
					System.out
							.println("["
									+ threadName
									+ "] Error: something get wrong with the inputStream");
				}
				
				//Creates a new object Definition
				Command c = contr.processCommand(cmd);
				
				System.out.println("[" + threadName
						+ "] Sending defintion of: " + c.command + " to "
						+ socket.getPort());
				
				//Output stream: response
				OutputStream output;
				try {
					output = socket.getOutputStream();
					objectOutput = new ObjectOutputStream(output);
					objectOutput.writeObject(c);
				} catch (IOException e1) {
					System.out
							.println("["
									+ threadName
									+ "] Error: something get wrong while sending response");
				}

				System.out.println("[" + threadName
						+ "] Closing connections with: " + socket.getPort());
				try {
					objectInput.close();
					objectOutput.close();
					socket.close();
				} catch (Exception e) {
					System.out
							.println("["
									+ threadName
									+ "] Error: something get wrong while closing socket");
				}
			}
		}
		System.out.println("[" + threadName + "] Finished");	
	}
	
	//Method helps breaking the while loop
	public synchronized void stopRunning()
	{
		this.isRunning = false;
	}
}
