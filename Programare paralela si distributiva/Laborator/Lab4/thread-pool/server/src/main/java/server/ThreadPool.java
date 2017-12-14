package server;

import model.WriteToFile;
import server.Controller.GeneralController;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;

public class ThreadPool {
	private Queue queue = null;
	private Worker[] workerThread = new Worker[20];
	private Receiver reception = null;
	private Scanner keyboard;
	private int numberOfWorkers;
	private int port;
	private ServerSocket serverSocket;
	private WriteToFile logger = new WriteToFile("C:\\Users\\mtoader\\Src\\Facultate-An3\\Programare paralela si distributiva\\Laborator\\Lab4\\logger\\loggerServer.txt");
	private GeneralController contr;

	public ThreadPool(int numberOfWorkers, int maxBufferSize, int port) {
		this.numberOfWorkers = numberOfWorkers;
		this.queue = new Queue(maxBufferSize);
		this.port = port;
		this.contr = new GeneralController(logger);
	}

	public synchronized void initialize() {

		try {
			serverSocket = new ServerSocket(port);
		} catch (IOException e) {
			System.out.println("A problem ocurred openning the socket");
			System.exit(1);
		} catch (IllegalArgumentException e) {
			System.out.println("Port must be between 0 and 65535");
			System.exit(2);
		}
		
		System.out
				.println("[SERVER] to close wait until no activity and press crtl+C or write quit");
		System.out.println("[SERVER] PORT=" + serverSocket.getLocalPort()
				+ " opened");

		for (int i = 0; i < numberOfWorkers; i++)
			workerThread[i] = new Worker(this.queue, contr);
		for (int i = 0; i < numberOfWorkers; i++)
			workerThread[i].start();

		this.reception = new Receiver(this.serverSocket, this.queue);
		reception.start();

		String command = "";
		keyboard = new Scanner(System.in);

		ThreadCheck check = new ThreadCheck(contr);
		Thread tCheck = new Thread(check);
		tCheck.start();
		while (!command.equals("quit")) {
			command = keyboard.nextLine().toLowerCase();
		}
		check.stop();
		logger.write();
		try {
			tCheck.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println("[SERVER] trying to stop Receiver thread");
		reception.stopRunning();
		for (int i = 0; i < numberOfWorkers; i++) {
			System.out.println("[SERVER] Trying to stop Thread-" + i);
			workerThread[i].stopRunning();
		}

		try {
			serverSocket.close();
			System.out.println("[SERVER] Closing socket");
		} catch (IOException e) {
			System.out.println("[S-SOCKET] Cannot close server socket");
		}
		System.out.println("[S-SOCKET] Closed");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
		}
		System.out.println("[SERVER] Closed");
		System.exit(0);
	}
}