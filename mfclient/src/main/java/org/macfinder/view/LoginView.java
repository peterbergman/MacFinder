package org.macfinder.view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Class to represent the login view.
 */
public class LoginView extends JFrame {

	private final static int WIDTH = 500;
	private final static int HEIGHT = 350;
	private final static String TITLE = "MacFinder";

	private JPanel contentPanel;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;

	public LoginView() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		setUpPasswordField();
		setUpUsernameField();
		setUpLoginButton();
		setUpContentPanel();
	}

	public void addLoginButtonActionListener(ActionListener listener) {
		loginButton.addActionListener(listener);
	}

	public void open() {
		setVisible(true);
	}

	public void close() {
		dispose();
	}

	public String getUsername() {
		return usernameField.getText();
	}

	public String getPassword() {
		return passwordField.getText();
	}

	private void setUpContentPanel() {
		contentPanel = new JPanel();

		contentPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridx = 3;
		gbc.gridwidth = 2;
		contentPanel.add(usernameField, gbc);

		gbc.gridy = 1;
		contentPanel.add(passwordField, gbc);

		gbc.gridy = 2;
		contentPanel.add(loginButton, gbc);

		add(contentPanel, BorderLayout.CENTER);
	}

	private void setUpUsernameField() {
		usernameField = new JTextField(10);
	}

	private void setUpPasswordField() {
		passwordField = new JPasswordField(10);
	}

	private void setUpLoginButton() {
		loginButton = new JButton("login");
	}

}
