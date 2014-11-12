package org.macfinder.utility;

import com.google.gson.Gson;
import org.macfinder.model.User;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class to handle communication with the server.
 */
public class ServerConnection {

	//private static final String SERVER_ADDRESS = "https://3cxivtljvvbg.runscope.net"; //Runscope
	//private static final String SERVER_ADDRESS = "http://192.168.1.74:8080/client"; //Home
	private static final String SERVER_ADDRESS = "http://130.237.241.80:8080/agent"; //DSV


	/**
	 * Sends data to the MacFinder server.
	 *
	 * Internally uses a HttpURLConnection and sends JSON-data with a POST-request.
	 *
	 * @param data			an AgentRequest object that contains all the necessary information
	 *              		needed to communicate with the server
	 * @throws IOException	if the internal HttpURLConnection fails for any reason
	 */
	public static User sendData(User data) {
		String jsonData = new Gson().toJson(data);
		try {
			URL url = new URL(SERVER_ADDRESS);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setFixedLengthStreamingMode(jsonData.getBytes().length);
			connection.setRequestProperty("Content-Length", jsonData.getBytes().length + "");
			connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
			//connection.connect();
			OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
			outputStream.write(jsonData.getBytes());
			outputStream.flush();
			outputStream.close();

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			reader.close();

			connection.disconnect();
		} catch (IOException ioe) {

		}
		return null;
	}

}
