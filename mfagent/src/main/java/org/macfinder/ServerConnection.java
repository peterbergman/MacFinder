package org.macfinder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Class to handle communication with the server.
 */
public class ServerConnection {

	private static final String SERVER_ADDRESS = "";
	private URLConnection urlConnection;

	public ServerConnection() throws IOException{
		this.urlConnection = new URL(SERVER_ADDRESS).openConnection();
		urlConnection.setDoOutput(true);
	}

	public void sendData(List<String> data) {

	}

}
