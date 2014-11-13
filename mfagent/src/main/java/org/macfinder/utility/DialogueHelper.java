package org.macfinder.utility;

import org.macfinder.model.User;
import org.macfinder.view.UserInformationView;

import javax.swing.*;

/**
 * Class to handle dialogues.
 */
public class DialogueHelper {

	private final static String DEFAULT_MESSAGE = " Enter your credentials. If you don't already have an account, a new one will be created.";

	public static User showUserInformationDialogue(String message) {
		message = "<html>" + (message != null ? "<center>" + message + "</center><br>" : "") + DEFAULT_MESSAGE + "</html>";
		UserInformationView userInformationView = new UserInformationView(message);
		JOptionPane.showMessageDialog(null, userInformationView, "MacFinder", JOptionPane.PLAIN_MESSAGE);
		return new User(userInformationView.getUserName(), userInformationView.getPassword());
	}
}
