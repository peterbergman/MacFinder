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

	/**
	 * Compares this AccessPoint to the specified object.
	 * <p></p>
	 * The result is true if and only if the argument is not null
	 * and is an AccessPoint object with the same mac address as this AccessPoint.
	 *
	 * @param other	the object to compare this AccessPoint against
	 * @return		true if the given object represents an AccessPoint with a mac address
	 * 				that equals this AccessPoint's mac address, false otherwise
	 */
	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof AccessPoint) {
			AccessPoint otherAccessPoint = (AccessPoint) other;
			return macAddress.equalsIgnoreCase(otherAccessPoint.getMacAddress());
		} else {
			return false;
		}
	}

	/**
	 * Returns the hash code of the AccessPoint.
	 * <p></p>
	 * The hash code is calculated by calling hashCode on the mac address
	 * of the AccessPoint.
	 *
	 * @return a hash code value for this object
	 */
	@Override
	public int hashCode() {
		return macAddress.hashCode();
	}
}
