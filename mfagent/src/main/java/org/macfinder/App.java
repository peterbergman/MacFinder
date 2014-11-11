package org.macfinder;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;

public class App {

    public static void main( String[] args ) {
		try {
			Machine machine = new Machine(InetAddress.getLocalHost().getHostName());
			User user = new User("user1", "pass1");
			Ping ping = new Ping(new Date(System.currentTimeMillis()), NetworkInfo.getNetworks());
			machine.addPing(ping);
			user.addMachine(machine);
			ServerConnection.sendData(user);
		} catch (IOException ioe) {
			System.err.println(ioe);
		}
    }
}
