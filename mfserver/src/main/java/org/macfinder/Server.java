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

	private ServerSocket serverSocket;
	private ExecutorService pool;
	private int port, queueLength;

	/**
	 * Constructs a new Server object.
	 *
	 * @param port			the port to be used when opening the server
	 * @param queueLength	the queue length to be used when opening the server
	 * @param threadCount	the number of concurrent active threads to handle connections
	 */
	public Server(int port, int queueLength, int threadCount) {
		pool = Executors.newFixedThreadPool(threadCount);
		this.port = port;
		this.queueLength = queueLength;
	}

	/**
	 * Method to start the server.
	 * <p></p>
	 * Opens the server for connections on the specified port
	 * with the specified queue length.
	 */
	public void start() {
		LOGGER.info("Trying to start server...");
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			serverSocket = new ServerSocket(port, queueLength, localHost);
			LOGGER.info("Server started on " + serverSocket.getInetAddress().getHostAddress());
			while (true) {
				Socket socket = serverSocket.accept();
				Runnable task = new ConnectionTask(socket);
				pool.execute(task);
			}
		} catch (IOException e) {
			LOGGER.severe("Could not start server...." + e);
		}
	}

	/**
	 * Metohd to stop the server.
	 */
	public void stop() {
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
