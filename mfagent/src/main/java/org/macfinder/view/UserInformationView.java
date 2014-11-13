package org.macfinder.view;

import javax.swing.*;

/**
 * Class to represent the user information view.
 */
public class UserInformationView extends JPanel {

	private JPanel firstRow, secondRow, thirdRow;
	private JLabel infoLabel, usernameLabel, passwordLabel;
	private JTextField usernameField;
	private JPasswordField passwordField;

	public UserInformationView(String message) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		firstRow = new JPanel();
		secondRow =  new JPanel();
		thirdRow = new JPanel();
		infoLabel = new JLabel(message);
		usernameLabel = new JLabel("Username: ");
		passwordLabel = new JLabel("Password: ");
		usernameField = new JTextField(10);
		passwordField = new JPasswordField(10);
		firstRow.add(infoLabel);
		secondRow.add(usernameLabel);
		secondRow.add(usernameField);
		thirdRow.add(passwordLabel);
		thirdRow.add(passwordField);
		add(firstRow);
		add(secondRow);
		add(thirdRow);
	}

	public String getUserName() {
		return usernameField.getText();
	}

	public String getPassword() {
		return passwordField.getText();
	}

}
