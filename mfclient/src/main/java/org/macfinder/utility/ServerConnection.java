package org.macfinder.utility;

import com.google.gson.Gson;
import org.macfinder.model.User;
import org.macfinder.model.http.HTTPRequest;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

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
		Gson gson = new Gson();
		String jsonData = gson.toJson(data);
		User existingUser = null;
		try {
			jsonData = URLEncoder.encode(jsonData, "utf-8");
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
			HTTPRequest request = parseRequest(connection);
			existingUser = gson.fromJson(request.getBody(), User.class);
			connection.disconnect();
		} catch (IOException ioe) {

		}
		return existingUser;
	}

	/**
	 * Helper method to parse the HTTP response from the server.
	 *
	 * @return				a HTTPRequest object with the body and headers separated
	 * @throws IOException	if something went wrong opening the stream from the server
	 */
	private static HTTPRequest parseRequest(HttpURLConnection connection) throws IOException {
		HTTPRequest request = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;

		while ((line = reader.readLine()).length() != 0) {
			stringBuilder.append(line + " ");
		}
		request = new HTTPRequest();
		request.setHeaders(stringBuilder.toString().replaceAll(":", "").split(" "));
		stringBuilder.setLength(0);
		int contentLength = Integer.parseInt(request.getHeaderValue("Content-Length"));

		for (int i = 0; i < contentLength; i++) {
			stringBuilder.append((char)reader.read());
		}
		reader.close();
		String decodedBody = URLDecoder.decode(stringBuilder.toString(), "utf-8");
		request.setBody(decodedBody);
		return request;
	}

}
