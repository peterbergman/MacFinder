package org.macfinder;

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

	@Override
	public boolean equals(Object other) {
		if (other instanceof AccessPoint) {
			AccessPoint otherAccessPoint = (AccessPoint) other;
			return macAddress.equalsIgnoreCase(otherAccessPoint.getMacAddress());
		} else {
			return false;
		}
	}
}
