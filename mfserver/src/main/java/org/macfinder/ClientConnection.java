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

	private Socket socket;

	/**
	 * Constructs a new ClientConnection-object.
	 *
	 * @param socket	the socket with the connection to the client.
	 */
	public ClientConnection(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Method to execute upon client connections.
	 *
	 * Extracts the HTTP-request from the socket connection,
	 * parses the request and then passes the request on the the
	 * correct service.
	 */
	public void run() {
		LOGGER.info("Connection active!");
		String request = parseRequest();
		if (validRequest(request)) {
			String[] requestArray = request.split("\n\n");
			String headers = requestArray[0];
			String data = requestArray[1];
			if (requestType(headers).equals(RequestType.AGENT)) {
				LOGGER.info("Agent request found! \n" + data);
			} else if (requestType(headers).equals(RequestType.CLIENT)) {
				// TODO: handle client stuff
			}
		}
	}

	/**
	 * Helper method to parse the HTTP-request.
	 *
	 * @return	a string containing the data from the request, both headers and body
	 */
	private String parseRequest() {
		StringBuilder request = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line;
			while ((line = reader.readLine()) != null) {
				request.append(line + "\n");
			}
		} catch (IOException ioe) {
			LOGGER.severe(ioe.toString());
		}
		return request.toString();
	}

	/**
	 * Method to validate the HTTP-request.
	 *
	 * @param request	a string with the data from the request, both headers and body
	 * @return			true if the request target matches one of the known endpoints,
	 * 					and if the request contains a valid separation between headers and body
	 */
	private boolean validRequest(String request) {
		return (request.contains("/agent") || request.contains("/client")) && request.contains("\n\n");
	}

	/**
	 * Method to compute the request type.
	 *
	 * @param headers		the headers of the HTTP-request
	 * @return RequestType	RequestType.AGENT if the endpoint is /agent,
	 * 						otherwise RequestType.CLIENT
	 */
	private RequestType requestType(String headers) {
		return (headers.contains("/agent") ? RequestType.AGENT : RequestType.CLIENT);
	}
}