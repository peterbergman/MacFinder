package org.macfinder.service;

import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

/**
 * Class to handle loocation lookups.
 */
public class LocationService {

	private final static String API_TOKEN = "";
	private final static String SERVER_ADDRESS = "";

	/**
	 * Method to perform a geo lookup.
	 *
	 * Internally calls the Google Location API with
	 * a list of nearby networks to resolve the location.
	 *
	 * @param networks	a list of nearby networks
	 */
	public void lookupLocation(List<String> networks) throws IOException {
		String jsonData = "";
		URL url = new URL(SERVER_ADDRESS);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);
		conn.setFixedLengthStreamingMode(jsonData.getBytes().length);

		conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
		conn.setRequestProperty("X-Requested-With", "XMLHttpRequest");

		//open
		conn.connect();

		//setup send
		OutputStream os = new BufferedOutputStream(conn.getOutputStream());
		os.write(jsonData.getBytes());
		//clean up
		os.flush();
	}

}
