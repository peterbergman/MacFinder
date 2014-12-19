package org.macfinder;

import org.macfinder.model.User;
import org.macfinder.model.http.HTTPResponse;
import org.macfinder.utility.DialogueHelper;
import org.macfinder.utility.FileHelper;
import org.macfinder.utility.ServerConnection;

public class Authenticate {
	public static void main(String[] args) {
		User user = DialogueHelper.showUserInformationDialogue(null);
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
}
