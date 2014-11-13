package org.macfinder;

import org.macfinder.model.Machine;
import org.macfinder.model.Ping;
import org.macfinder.model.User;
import org.macfinder.model.http.HTTPResponse;
import org.macfinder.utility.*;

import java.io.IOException;
import java.util.Date;

public class App {

    public static void main(String[] args) {
		User user = FileHelper.readUserInformation();
		if (user == null) {
			user = DialogueHelper.showUserInformationDialogue(null);
			boolean authenticated = false;
			while (!authenticated) {
				HTTPResponse response = ServerConnection.sendData(user);
				if (response != null && response.getStatusCode() == 401) {
					user = DialogueHelper.showUserInformationDialogue("Incorrect password for existing user.");
				} else {
					authenticated = true;
				}
			}
			FileHelper.saveUserInformation(user);
		}
		Machine machine = new Machine(SystemInfo.getComputerName());
		Ping ping = new Ping(new Date(System.currentTimeMillis()), NetworkInfo.getNetworks());
		machine.addPing(ping);
		user.addMachine(machine);
		ServerConnection.sendData(user);
    }
}
