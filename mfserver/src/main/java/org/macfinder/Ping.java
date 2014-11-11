package org.macfinder;

import org.macfinder.service.location.GeoLookup;

import java.util.Date;
import java.util.List;

/**
 * Class to represent a ping sent from a machine.
 */
public class Ping {

	private Date timestamp;
	private List<AccessPoint> wifiAccessPoints;
	private GeoLookup geoLookup;

	/**
	 * Constructs a new Ping-object.
	 *
	 * @param timestamp			the timestamp of the ping
	 * @param wifiAccessPoints	a list of networks detected
	 */
	public Ping(Date timestamp, List<AccessPoint> wifiAccessPoints) {
		this.timestamp = timestamp;
		this.wifiAccessPoints = wifiAccessPoints;
	}

	public List<AccessPoint> getWifiAccessPoints() {
		return wifiAccessPoints;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setGeoLookup(GeoLookup geoLookup) {
		this.geoLookup = geoLookup;
	}

	/**
	 * Compares this Ping to the specified object.
	 * <p></p>
	 * The result is true if and only if the argument is not null
	 * and is a Ping object with the same list of AccessPoints as this Ping.
	 * The list of AccessPoints is compared by checking that this Ping's
	 * AccessPoints contains all of the other Ping's AccessPoints and vice versa.
	 * <p></p>
	 * If both Pings does not contain any AccessPoints, then the timestamps
	 * are compared and the result ist true if both timestamps are equal.
	 *
	 * @param other	the object to compare this Ping against
	 * @return		true if this Ping's list of AccessPoints
	 * 				contains all of the AccessPoints in the
	 * 				given Ping's list of AccessPoints and vice versa,
	 * 				false otherwise. If both Pings does not contain any
	 * 				AccessPoints, then true if both Ping's timestamp are equal.
	 */
	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof Ping) {
			Ping otherPing = (Ping) other;
			if (wifiAccessPoints.size() != 0 && otherPing.getWifiAccessPoints().size() != 0) {
				return wifiAccessPoints.containsAll(otherPing.getWifiAccessPoints()) &&
						otherPing.getWifiAccessPoints().containsAll(wifiAccessPoints);
			} else {
				return timestamp.equals(otherPing.getTimestamp());
			}
		} else {
			return false;
		}
	}

	/**
	 * Returns the hash code of the Ping.
	 * <p></p>
	 * The hash code is calculated by calling adding the result
	 * from calling hashCode on each AccessPoint together.
	 * <p></p>
	 * If the Ping does not contain any AccessPoints, then the
	 * hash code is calculated by calling hashCode on the timestamp.
	 *
	 * @return a hash code value for this object
	 */
	@Override
	public int hashCode() {
		if (wifiAccessPoints.size() != 0) {
			int hashCode = 0;
			for (AccessPoint accessPoint : wifiAccessPoints) {
				hashCode += accessPoint.hashCode();
			}
			return hashCode;
		} else {
			return timestamp.hashCode();
		}
	}

}
