package org.macfinder.controller;

import org.macfinder.model.GeoLookup;
import org.macfinder.utility.MapServerConnection;

import javax.swing.*;

/**
 * Class to handle the long running asynchronous tasks for the GUI.
 */
public class MapServerConnectionWorker extends SwingWorker<ImageIcon, Void> {

	private Controller controller;
	private GeoLookup geoLookup;

	public MapServerConnectionWorker(Controller controller, GeoLookup geoLookup) {
		this.controller = controller;
		this.geoLookup = geoLookup;
	}

	@Override
	public ImageIcon doInBackground() {
		return MapServerConnection.getImageFromLookup(geoLookup);
	}

	@Override
	protected void done()  {
		try {
			controller.workerCallback(get());
		} catch (Exception e) {}
	}

}
