package org.macfinder;

import org.macfinder.model.Machine;
import org.macfinder.model.Ping;
import org.macfinder.model.User;
import org.macfinder.model.http.HTTPResponse;
import org.macfinder.utility.*;

import java.util.Date;

public class App {

    public static void main(String[] args) {
		System.setProperty("java.awt.headless", "false");
		User user = FileHelper.readUserInformation();
		if (user != null) {
			Machine machine = new Machine(SystemInfo.getComputerName());
			Ping ping = new Ping(new Date(System.currentTimeMillis()), NetworkInfo.getNetworks());
			machine.addPing(ping);
			user.addMachine(machine);
			ServerConnection.sendData(user);
		}
    }
}
