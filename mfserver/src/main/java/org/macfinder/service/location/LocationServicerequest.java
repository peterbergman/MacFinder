package org.macfinder.service.location;

import org.macfinder.AccessPoint;

import java.util.List;

/**
 * Class to represent a request to the geo location API.
 */
public class LocationServicerequest {

	private List<AccessPoint> wifiAccessPoints;

	public LocationServicerequest(List<AccessPoint> wifiAccessPoints) {
		this.wifiAccessPoints = wifiAccessPoints;
	}
}
