package org.macfinder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.List;

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
					List<AccessPoint> wifiAccessPoints = networkInfo.getNetworks();
					Machine machine = new Machine(InetAddress.getLocalHost().getHostName());
					app.connection.sendData(new AgentRequest("user1", "password1", wifiAccessPoints, machine));
				} catch (IOException ioe) {
					System.err.println(ioe);
				}
			}
		}
    }
}
