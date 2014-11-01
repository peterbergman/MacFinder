package org.macfinder;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Class to represent the server instance.
 */
public class Server {

	private static final Logger LOGGER = Logger.getLogger(Server.class.getName());
	static {
		LOGGER.setUseParentHandlers(false);
		LOGGER.addHandler(new ConsoleHandler());
	}
	private final static int DEFAULT_PORT = 80;
	private static final int QUEUE_LENGTH = 50;
	private ServerSocket serverSocket;

	public void open() {
		LOGGER.info("Trying to start server...");
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			serverSocket = new ServerSocket(DEFAULT_PORT, QUEUE_LENGTH, localHost);
			LOGGER.info("Server started!");
			while (true) {
				Socket socket = serverSocket.accept();
				ClientConnection client = new ClientConnection(socket);
			}
		} catch (IOException e) {
			LOGGER.severe("Could not start server...." + e);
		} finally {
			if (serverSocket != null) {
				try {
					serverSocket.close();
				} catch (IOException ioe) {}
			}
		}
	}

}
