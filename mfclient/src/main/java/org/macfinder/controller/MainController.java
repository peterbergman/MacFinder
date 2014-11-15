package org.macfinder.controller;

import com.google.gson.Gson;
import org.macfinder.model.Machine;
import org.macfinder.model.Ping;
import org.macfinder.model.User;
import org.macfinder.model.http.HTTPResponse;
import org.macfinder.utility.MapServerConnection;
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
public class MainController implements Controller {

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
				+ "&zoom=10"
				+ "&size=640x640"
				+ "&maptype=roadmap"
				+ "&sensor=false";
		try {
			mainView.setMap(new ImageIcon(new URL(url)));
		} catch (MalformedURLException mfe) {};
	}

	public void workerCallback(HTTPResponse response) {
		user = GSON.fromJson(response.getBody(), User.class);
		List<Ping> pings = mainView.getSelectedMachine().getPings();
		(new MapServerConnectionWorker(this, pings.get(pings.size()-1).getGeoLookup())).execute();
	}

	public void workerCallback(ImageIcon imageIcon) {
		mainView.setMap(imageIcon);
		mainView.setInputEnabled(true);
	}

	private class LookupButtonActionActionListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			List<Machine> selectedMachine = new ArrayList<Machine>();
			selectedMachine.add(mainView.getSelectedMachine());
			if (selectedMachine.size() != 0) {
				user.getMachines().retainAll(selectedMachine);
				mainView.setInputEnabled(false);
				(new ServerConnectionWorker(MainController.this, user)).execute();
			}
		}
	}
}
