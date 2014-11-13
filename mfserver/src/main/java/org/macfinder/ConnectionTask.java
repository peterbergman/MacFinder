package org.macfinder;

import com.google.gson.Gson;
import org.macfinder.model.RequestType;
import org.macfinder.model.User;
import org.macfinder.model.http.HTTPRequest;
import org.macfinder.model.http.HTTPResponse;
import org.macfinder.service.database.DBService;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Class to represent a task to handle a request.
 */
public class ConnectionTask implements Runnable {

	private final static Logger LOGGER = Logger.getLogger(ConnectionTask.class.getName());
	static {
		LOGGER.setUseParentHandlers(false);
		LOGGER.addHandler(new ConsoleHandler());
	}

	private final static Gson GSON = new Gson();

	private Socket socket;
	private BufferedReader reader = null;
	private BufferedWriter writer = null;

	/**
	 * Constructs a new ClientConnection-object.
	 *
	 * @param socket	the socket with the connection to the client.
	 */
	public ConnectionTask(Socket socket) {
		this.socket = socket;
	}

	/**
	 * Method to execute upon client connections.
	 *<p></p>
	 * Extracts the HTTP-request from the socket connection,
	 * parses the request and then passes the request on the the
	 * correct service.
	 */
	@Override
	public void run() {
		LOGGER.info("Connection active!");
		try {
			HTTPRequest request = parseRequest();
			if (request != null) {
				if (requestType(request.getPath()).equals(RequestType.AGENT)) {
					handleAgentRequest(request);
				} else if (requestType(request.getPath()).equals(RequestType.CLIENT)) {
					handleClientRequest(request);
				}
			} else {
				LOGGER.info("Invalid request received and discarded!");
			}
		} catch (IOException ioe) {
			LOGGER.severe(ioe.toString());
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (writer != null) {
					writer.close();
				}
			} catch (IOException ioe){};
		}
	}

	/**
	 * Helper method to parse the HTTP-request.
	 *
	 * @return				a HTTPRequest object with the body and headers separated
	 * @throws IOException	if something went wrong opening the stream from the client
	 */
	private HTTPRequest parseRequest() throws IOException {
		HTTPRequest request = null;
		StringBuilder stringBuilder = new StringBuilder();
		reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String line;

		while ((line = reader.readLine()).length() != 0) {
			stringBuilder.append(line + " ");
		}
		if (validateRequest(stringBuilder.toString())) {
			request = new HTTPRequest();
			request.setHeaders(stringBuilder.toString().replaceAll(":", "").split(" "));
			stringBuilder.setLength(0);
			int contentLength = Integer.parseInt(request.getHeaderValue("Content-Length"));

			for (int i = 0; i < contentLength; i++) {
				stringBuilder.append((char)reader.read());
			}
			String decodedBody = URLDecoder.decode(stringBuilder.toString(), "utf-8");
			request.setBody(decodedBody);
		}
		return request;
	}

	/**
	 * Method to validate the HTTP-request.
	 *
	 * @param requestHeaders	a string with the headers from the request,
	 * @return					true if the request is a POST request and
	 * 							the target matches one of the known endpoints
	 */
	private boolean validateRequest(String requestHeaders) {
		return (requestHeaders.contains("/agent") || requestHeaders.contains("/client")) && requestHeaders.contains("POST");
	}

	/**
	 * Helper method to compute the request type.
	 *
	 * @param requestPath		the path of the HTTP-request
	 * @return RequestType		RequestType.AGENT if the endpoint is /agent,
	 * 							otherwise RequestType.CLIENT
	 */
	private RequestType requestType(String requestPath) {
		return (requestPath.equals("/agent") ? RequestType.AGENT : RequestType.CLIENT);
	}

	/**
	 * Helper method to handle requests sent from an agent.
	 *
	 * @param request	the data sent from the agent, assumes
	 *              	that this is a JSON-string representing
	 *              	a User object.
	 */
	private void handleAgentRequest(HTTPRequest request) throws IOException{
		LOGGER.info("Handling agent request...");
		DBService dbService = new DBService();
		User updatedUser = GSON.fromJson(request.getBody(), User.class);
		User existingUser = dbService.get(updatedUser);
		HTTPResponse response = new HTTPResponse();
		if (existingUser != null) {
			response.setBody(request.getBody());
			if (updatedUser.getMachines().size() > 0) {
				dbService.update(updatedUser, existingUser);
			}
		} else {
			response.setStatusCode(401);
		}
		dbService.close();
		sendResponse(response);
	}

	private void handleClientRequest(HTTPRequest request) throws IOException{
		LOGGER.info("Handling client request...");
		User user = GSON.fromJson(request.getBody(), User.class);
		DBService dbService = new DBService();
		User existingUser = dbService.get(user);

		// If request.method == GET
		// iterate over all of the user's machines
		// if the latest ping for the machine does not have a geo lookup
		// then do the geo lookup based on that ping and add it to the machine
		// then update user in the DB

		dbService.close();
		HTTPResponse response = new HTTPResponse();
		response.setBody(GSON.toJson(existingUser));
		sendResponse(response);
	}

	/**
	 * Method to send back response to the client.
	 *
	 * @param response		the response to send back to the client
	 * @throws IOException	if something went wrong when opening the stream to the client
	 */
	private void sendResponse(HTTPResponse response) throws IOException {
		writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		writer.write(response.toString());
		writer.flush();
	}
}
