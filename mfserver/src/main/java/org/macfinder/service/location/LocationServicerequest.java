package org.macfinder.service.location;

import org.macfinder.model.AccessPoint;

import java.util.List;

/**
 * Class to represent a request to the geo location API.
 */
public class LocationServiceRequest {

	private List<AccessPoint> wifiAccessPoints;

	public LocationServiceRequest(List<AccessPoint> wifiAccessPoints) {
		this.wifiAccessPoints = wifiAccessPoints;
	}
}
