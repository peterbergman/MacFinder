package org.macfinder.service.location;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
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

	private final static String API_KEY = "AIzaSyDOaDCgsCqvglqKxR8jvoo0XOHPxamWtzI";
	private final static String SERVER_ADDRESS = "https://www.googleapis.com/geolocation/v1/geolocate?key=" + API_KEY;

	private LocationServicerequest request;

	public LocationService(LocationServicerequest request) {
		this.request = request;
	}

	/**
	 * Method to perform a geo lookup.
	 *
	 * Internally calls the Google Location API with
	 * a list of nearby networks to resolve the location.
	 *
	 * @return 				a location object if the lookup was successful, null if not
	 * @throws IOException	if the communication with the API failed
	 */
	public GeoLookup lookupLocation() {
		LOGGER.info("Loooking up geo location...");
		String jsonData = new Gson().toJson(request);
		GeoLookup geoLookup = null;
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

			BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			String line;
			StringBuilder output = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				output.append(line);
			}
			geoLookup = new Gson().fromJson(output.toString(), GeoLookup.class);
			reader.close();
			connection.disconnect();
		} catch (IOException ioe) {
			LOGGER.severe(ioe.toString());
		}
		return geoLookup;
	}

}