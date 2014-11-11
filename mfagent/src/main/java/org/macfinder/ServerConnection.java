package org.macfinder;

import com.google.gson.Gson;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class to handle communication with the server.
 */
public class ServerConnection {

	//private static final String SERVER_ADDRESS = "https://3cxivtljvvbg.runscope.net";
	private static final String SERVER_ADDRESS = "http://192.168.1.74:8080/agent";

	/**
	 * Sends data to the MacFinder server.
	 *
	 * Internally uses a HttpURLConnection and sends JSON-data with a POST-request.
	 *
	 * @param data			an AgentRequest object that contains all the necessary information
	 *              		needed to communicate with the server
	 * @throws IOException	if the internal HttpURLConnection fails for any reason
	 */
	public static void sendData(User data) throws IOException{
		String jsonData = new Gson().toJson(data);
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
			connection.disconnect();
		} catch (IOException ioe) {

		}
	}

}
