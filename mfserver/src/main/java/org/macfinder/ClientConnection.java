package org.macfinder;

import com.google.gson.Gson;

import java.io.*;
import java.net.Socket;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Class to represent connections from agents.
 */
public class ClientConnection implements Runnable {

	private static final Logger LOGGER = Logger.getLogger(ClientConnection.class.getName());
	static {
		LOGGER.setUseParentHandlers(false);
		LOGGER.addHandler(new ConsoleHandler());
	}

	private Thread internalThread;
	private Socket socket;

	public ClientConnection(Socket socket) {
		this.socket = socket;
		internalThread = new Thread(this);
		internalThread.start();
	}

	public void run() {
		while (!internalThread.isInterrupted()) {
			try {
				LOGGER.info("Connection active!");
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String line;
				StringBuilder request = new StringBuilder();
				while ((line = reader.readLine()) != null) {
					request.append(line + "\n");
				}
				if (validRequest(request.toString())) {
					String[] requestArray = request.toString().split("\n\n");
					String headers = requestArray[0];
					String data = requestArray[1];
					if (requestType(headers).equals(RequestType.AGENT)) {

					} else if (requestType(headers).equals(RequestType.CLIENT)) {
						// TODO: implement
					}
				}
				internalThread.interrupt();
			} catch (IOException ioe) {
				LOGGER.severe(ioe.toString());
			}
		}
	}

	private boolean validRequest(String request) {
		return (request.contains("/agent") || request.contains("/client")) && request.contains("/n/n");
	}

	private String requestType(String headers) {
		return (headers.contains("/agent") ? "agent" : "client");
	}
}