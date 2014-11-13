package org.macfinder.controller;

import com.google.gson.Gson;
import org.macfinder.model.Machine;
import org.macfinder.model.User;
import org.macfinder.model.http.HTTPResponse;
import org.macfinder.utility.ServerConnection;
import org.macfinder.view.MainView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to act as a controller for the main view.
 */
public class MainController {

	private final static Gson GSON = new Gson();

	private MainView mainView;
	private User user;

	public MainController(User user) {
		mainView = new MainView();
		mainView.addLookupButtonActionListener(new LookupButtonActionActionListener());
		mainView.setMachines(user.getMachines());
		this.user = user;
	}

	public void start() {
		mainView.open();
		String url = "http://maps.googleapis.com/maps/api/staticmap?"
				+ "center=59.4030598,17.9813378"
				+ "&zoom=14"
				+ "&size=640x640"
				+ "&maptype=roadmap"
				+ "&markers=color:red%7Ccolor:red%7C59.4030598,17.9813378"
				+ "&sensor=false";
		try {
			mainView.setMap(new ImageIcon(new URL(url)));
		} catch (MalformedURLException mfe) {};
	}

	private class LookupButtonActionActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			List<Machine> selectedMachine = new ArrayList<Machine>();
			selectedMachine.add(mainView.getSelectedMachine());
			if (selectedMachine != null) {
				user.getMachines().retainAll(selectedMachine);
				HTTPResponse response = ServerConnection.sendData(user);
				user = GSON.fromJson(response.getBody(), User.class);
				System.out.println(user);
			}
		}
	}
}
