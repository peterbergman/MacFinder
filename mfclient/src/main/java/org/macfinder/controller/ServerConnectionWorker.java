package org.macfinder.controller;

import org.macfinder.model.User;
import org.macfinder.model.http.HTTPResponse;
import org.macfinder.utility.ServerConnection;

import javax.swing.*;

/**
 * Class to handle the long running asynchronous tasks for the GUI.
 */
public class ServerConnectionWorker extends SwingWorker<HTTPResponse, Void> {

	private Controller controller;
	private User user;

	public ServerConnectionWorker(Controller controller, User user) {
		this.controller = controller;
		this.user = user;
	}

	@Override
	public HTTPResponse doInBackground() {
		return ServerConnection.sendData(user);
	}

	@Override
	protected void done()  {
		try {
			controller.workerCallback(get());
		} catch (Exception e) {}
	}

}
