package org.macfinder;

import com.google.gson.Gson;
import org.macfinder.model.*;
import org.macfinder.model.http.HTTPRequest;
import org.macfinder.model.http.HTTPResponse;
import org.macfinder.service.database.DBService;
import org.macfinder.service.location.LocationService;
import org.macfinder.service.location.LocationServiceRequest;

import java.io.*;
import java.net.Socket;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
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
	 *
	 * Delegates the request and then closes the I/O-streams when done.
	 */
	@Override
	public void run() {
		LOGGER.info("Connection active!");
		try {
			delegateRequest();
		} catch (IOException ioe) {
			LOGGER.severe(ioe.toString());
		} finally {
			try {
				closeStreams();
			} catch (IOException ioe){};
		}
	}

	/**
	 * Delegates requests.
	 *
	 * Extracts the HTTP-request from the socket connection,
	 * parses the request and then passes the request on the the
	 * correct handle method.
	 *
	 * @throws IOException
	 */
	private void delegateRequest() throws IOException {
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
	 * @param request		the data sent from the agent, assumes
	 *              		that this is a JSON-string representing
	 *              		a User object.
	 * @throws IOException
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

	/**
	 * Helper method to handle requests sent from a client.
	 *
	 * @param request			the data sent from the client, assumes
	 *              			that this is a JSON-string representing
	 *              			a User object.
	 * @throws IOException
	 */
	private void handleClientRequest(HTTPRequest request) throws IOException{
		LOGGER.info("Handling client request...");
		HTTPResponse response = new HTTPResponse();
		User user = GSON.fromJson(request.getBody(), User.class);
		DBService dbService = new DBService();
		User existingUser = dbService.get(user);

		if (existingUser == null) {
			response.setStatusCode(401);
		} else if (user.getMachines().size() > 0) {
			LOGGER.info("Looking location for machine...");
			List<Ping> pings = user.getMachines().get(0).getPings();
			List<AccessPoint> accessPoints = pings.get(pings.size()-1).getWifiAccessPoints();
			if (pings.get(pings.size()-1).getGeoLookup() == null) {
				LOGGER.info("Contacting Maps API for geo lookup...");
				LocationServiceRequest lookupRequest = new LocationServiceRequest(accessPoints); // TODO: redundant class?
				LocationService locationService = new LocationService(lookupRequest);
				GeoLookup lookup = locationService.lookupLocation();
				pings.get(pings.size()-1).setGeoLookup(lookup);
				existingUser = dbService.update(user, existingUser);
			} else {
				LOGGER.info("Cached geo lookup found!");
			}
		}

		response.setBody(URLEncoder.encode(GSON.toJson(existingUser), "utf-8"));
		dbService.close();
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

	/**
	 * Closes the I/O-streams used in the request.
	 *
	 * @throws IOException
	 */
	private void closeStreams() throws IOException {
		if (reader != null) {
			reader.close();
		}
		if (writer != null) {
			writer.close();
		}
	}
}
