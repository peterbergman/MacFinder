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
	private static final String SERVER_ADDRESS = "http://192.168.1.74/agent";

	/**
	 * Constructs a new ServerConnection object.
	 *
	 * Internally uses a URLConnection to open the actual connection to the server.
	 *
	 * @throws IOException	if the internal URLConnection fails for any reason
	 */
	public ServerConnection() {

	}

	/**
	 * Sends data to the MacFinder server.
	 *
	 * Internally uses a HttpURLConnection and sends JSON-data with a POST-request.
	 *
	 * @param data			an AgentRequest object that contains all the necessary information
	 *              		needed to communicate with the server
	 * @throws IOException	if the internal HttpURLConnection fails for any reason
	 */
	public void sendData(AgentRequest data) throws IOException{
		String jsonData = new Gson().toJson(data);
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
