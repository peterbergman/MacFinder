package org.macfinder.model;

/**
 * Class to represent a position returned by the location API.
 */
public class GeoLookup {

	private Location location;
	private double accuracy;

	public double getLatitude() {
		return location.getLatitude();
	}

	public double getLongitude() {
		return location.getLongitude();
	}

	/**
	 * Class to represent a location that contains latitude and longitude.
	 */
	private class Location {
		private double lng, lat;

		public double getLatitude() {
			return lat;
		}

		public double getLongitude() {
			return lng;
		}
	}

}