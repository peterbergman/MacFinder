package org.macfinder.controller;

import org.macfinder.model.User;
import org.macfinder.utility.ServerConnection;
import org.macfinder.view.LoginView;
import org.macfinder.view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Class to act as a controller for the login view.
 */
public class LoginController {

	private LoginView loginView;

	public LoginController() {
		loginView = new LoginView();
		loginView.addLoginButtonActionListener(new LoginButtonActionListener());
	}

	public void start() {
		loginView.open();
	}

	/**
	 * Class to handle click on the login button.
	 */
	private class LoginButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			User user = new User(loginView.getUsername(), loginView.getPassword());
			User existingUser = ServerConnection.sendData(user);
			if (loginView.getUsername().equals("") && loginView.getPassword().equals("")) {
				loginView.close();
				MainController mainController = new MainController();
				mainController.start();
			}
		}
	}

}
