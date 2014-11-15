package org.macfinder.utility;

import com.google.gson.Gson;
import org.macfinder.model.User;
import org.macfinder.model.http.HTTPResponse;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Class to handle communication with the server.
 */
public class ServerConnection {

	//private static final String SERVER_ADDRESS = "https://92hn1nhwh76y.runscope.net"; //Runscope
	private final static String SERVER_ADDRESS = "http://192.168.1.74:8080/client"; //Home
	//private static final String SERVER_ADDRESS = "http://77.238.33.55:8080/client"; //DSV

	private final static Gson GSON = new Gson();
	
	/**
	 * Sends data to the MacFinder server.
	 *
	 * Internally uses a HttpURLConnection and sends JSON-data with a POST-request.
	 *
	 * @param data			an AgentRequest object that contains all the necessary information
	 *              		needed to communicate with the server
	 * @throws IOException	if the internal HttpURLConnection fails for any reason
	 */
	public static HTTPResponse sendData(User data) {
		String jsonData = GSON.toJson(data);
		HTTPResponse response = null;
		try {
			jsonData = URLEncoder.encode(jsonData, "utf-8");
			URL url = new URL(SERVER_ADDRESS);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setFixedLengthStreamingMode(jsonData.getBytes().length);
			connection.setRequestProperty("Content-Length", jsonData.getBytes().length + "");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setRequestProperty("Charset", "utf-8");
			OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
			outputStream.write(jsonData.getBytes());
			outputStream.flush();
			response = parseResponse(connection);
			outputStream.close();
			connection.disconnect();
		} catch (IOException ioe) {

		}
		return response;
	}

	/**
	 * Helper method to parse the HTTP response from the server.
	 *
	 * @param connection	the connection object to read the response from
	 * @return				a HTTPResponse object with the parsed body of the response
	 * @throws IOException	if something went wrong opening the stream from the server
	 */
	private static HTTPResponse parseResponse(HttpURLConnection connection) throws IOException {
		HTTPResponse response = new HTTPResponse();
		response.setStatusCode(connection.getResponseCode());
		String[] responseString = connection.getResponseMessage().split("\\\\r\\\\n\\\\r\\\\n");
		String responseHeaders = responseString[0];
		response.setHeaders(responseHeaders);
		String responseData = response.getStatusCode() == 200 ? responseString[1] : "";
		response.setBody(URLDecoder.decode(responseData, "utf-8"));
		return response;
	}

}
