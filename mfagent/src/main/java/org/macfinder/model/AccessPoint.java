package org.macfinder.model;

/**
 * Class to represent a WiFi access point.
 */
public class AccessPoint {

	private String macAddress;
	private int signalStrength;

	public AccessPoint(String macAddress, int signalStrength) {
		this.macAddress = macAddress;
		this.signalStrength = signalStrength;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public int getSignalStrength() {
		return signalStrength;
	}
}
