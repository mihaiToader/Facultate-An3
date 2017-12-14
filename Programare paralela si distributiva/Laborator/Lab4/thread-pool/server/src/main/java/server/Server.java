package server;

import java.util.Scanner;

import static java.lang.Integer.valueOf;

public class Server {

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);

        System.out.print("Dati portul:");
		int port;
        port = valueOf(in.next());
        int numberOfThreads = 4;
		int bufferSize = 100;
		
		ThreadPool pool = new ThreadPool(numberOfThreads, bufferSize, port);
		pool.initialize();	
	}
}
