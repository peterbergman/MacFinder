package org.macfinder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Class to represent the server instance.
 */
public class Server {

	private final static Logger LOGGER = Logger.getLogger(Server.class.getName());
	static {
		LOGGER.setUseParentHandlers(false);
		LOGGER.addHandler(new ConsoleHandler());
	}
	private final static int PORT = 80;
	private final static int QUEUE_LENGTH = 50;
	private final static int THREAD_COUNT = 4;
	private ServerSocket serverSocket;
	private ExecutorService pool;

	public Server() {
		pool = Executors.newFixedThreadPool(THREAD_COUNT);
	}

	public void open() {
		LOGGER.info("Trying to start server...");
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			serverSocket = new ServerSocket(PORT, QUEUE_LENGTH, localHost);
			LOGGER.info("Server started!");
			while (true) {
				Socket socket = serverSocket.accept();
				Runnable task = new ConnectionTask(socket);
				pool.execute(task);
			}
		} catch (IOException e) {
			LOGGER.severe("Could not start server...." + e);
		}
	}

	public void close() {
		if (serverSocket != null) {
			try {
				serverSocket.close();
			} catch (IOException ioe) {}
		}
		if (pool != null) {
			pool.shutdown();
		}
	}

}
