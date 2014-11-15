package org.macfinder.controller;

import com.google.gson.Gson;
import org.macfinder.model.User;
import org.macfinder.model.http.HTTPResponse;
import org.macfinder.view.LoginView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class to act as a controller for the login view.
 */
public class LoginController implements Controller {

	private final static Gson GSON = new Gson();

	private LoginView loginView;

	public LoginController() {
		loginView = new LoginView();
		loginView.addLoginButtonActionListener(new LoginButtonActionListener());
	}

	public void start() {
		loginView.open();
	}

	public void workerCallback(HTTPResponse response) {
		if (response.getStatusCode() == 200) {
			loginView.close();
			User user = GSON.fromJson(response.getBody(), User.class);
			MainController mainController = new MainController(user);
			mainController.start();
		} else {
			JOptionPane.showMessageDialog(loginView, "Login failed!", "Error", JOptionPane.ERROR_MESSAGE);
			loginView.setInputEnabled(true);
		}
	}

	public void workerCallback(ImageIcon imageIcon) {
		// no-op
	}

	/**
	 * Class to handle click on the login button.
	 */
	private class LoginButtonActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			loginView.setInputEnabled(false);
			User user = new User(loginView.getUsername(), loginView.getPassword());
			(new ServerConnectionWorker(LoginController.this, user)).execute();
		}
	}

}
