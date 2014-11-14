package org.macfinder.utility;

import org.macfinder.model.GeoLookup;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;

/**
 * Class to handle communication with the Google Static Map server.
 */
public class MapServerConnection {

	private final static String SERVER_ADDRESS = "http://maps.googleapis.com/maps/api/staticmap";
	private final static String MAP_TYPE = "roadmap";
	private final static int ZOOM_LEVEL = 14;
	private final static String SIZE = "640x640";
	private final static String MARKER_COLOR = "blue";
	private final static boolean SENSOR_ENABLED = false;


	public static ImageIcon getImageFromLookup(GeoLookup lookup) {
		ImageIcon image = null;
		try {
			String markers = "color:" + MARKER_COLOR + "%7C" + lookup.getLatitude() + "," + lookup.getLongitude();
			StringBuilder query = new StringBuilder();
			query.append("?");
			query.append("center=");
			query.append(lookup.getLatitude());
			query.append(",");
			query.append(lookup.getLongitude());
			query.append("&");
			query.append("zoom=");
			query.append(ZOOM_LEVEL);
			query.append("&");
			query.append("size=");
			query.append(SIZE);
			query.append("&");
			query.append("maptype=");
			query.append(MAP_TYPE);
			query.append("&");
			query.append("markers=");
			query.append(markers);
			query.append("&");
			query.append("sensor=");
			query.append(SENSOR_ENABLED);
			URL url = new URL(SERVER_ADDRESS + query.toString());
			image = new ImageIcon(url);
		} catch (IOException ioe) {}
		return image;
	}
}
