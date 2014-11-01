package org.macfinder;

import java.io.IOException;

public class App {

	private ServerConnection connection;

	public App() {
		connection = new ServerConnection();
	}

    public static void main( String[] args ) {
		App app = new App();
		Installer installer = new Installer();
		if (args.length == 0) {
			installer.install();
		} else if (args.length == 1) {
			if (args[0].equals("execute")) {
				NetworkInfo networkInfo = new NetworkInfo();
				try {
					app.connection.sendData(new AgentRequest("user1", "password1", networkInfo.getNetworks()));
				} catch (IOException ioe) {
					System.err.println(ioe);
				}
			}
		}
    }
}
