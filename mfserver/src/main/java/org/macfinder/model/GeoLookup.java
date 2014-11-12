package org.macfinder.model;

/**
 * Class to represent a position returned by the location API.
 */
public class GeoLookup {

	private Location location;
	private double accuracy;

	/**
	 * Class to represent a location that contains latitude and longitude.
	 */
	private class Location {
		private double lng, lat;
	}

}