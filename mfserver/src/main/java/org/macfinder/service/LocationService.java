package org.macfinder.service;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Class to handle loocation lookups.
 */
public class LocationService {

	private final static Logger LOGGER = Logger.getLogger(LocationService.class.getName());
	static {
		LOGGER.setUseParentHandlers(false);
		LOGGER.addHandler(new ConsoleHandler());
	}

	private final static String API_TOKEN = "";
	private final static String SERVER_ADDRESS = "";

	/**
	 * Method to perform a geo lookup.
	 *
	 * Internally calls the Google Location API with
	 * a list of nearby networks to resolve the location.
	 *
	 * @param networks		a list of nearby networks
	 * @throws IOException	if the communication with the API failed
	 */
	public void lookupLocation(List<String> networks) {
		String jsonData = "";
		try {
			URL url = new URL(SERVER_ADDRESS);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setFixedLengthStreamingMode(jsonData.getBytes().length);
			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
			connection.connect();
			OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
			outputStream.write(jsonData.getBytes());
			outputStream.flush();
			outputStream.close();
		} catch (IOException ioe) {

		}
	}

}
