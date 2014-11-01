package org.macfinder;

/**
 * Class to represent a WiFi access point.
 */
public class AccessPoint {

	private String macAddress;

	public AccessPoint(String macAddress) {
		this.macAddress = macAddress;
	}

	public String getMacAddress() {
		return macAddress;
	}
}
