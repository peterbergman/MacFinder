package org.macfinder;

import java.util.Date;
import java.util.List;

/**
 * Class to represent a ping sent from a machine.
 * This class just acts as a data container that can be
 * converted to JSON.
 */
public class Ping {

	private Date timestamp;
	private List<AccessPoint> wifiAccessPoints;

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

	@Override
	public boolean equals(Object other) {
		if (other instanceof Ping) {
			Ping otherPing = (Ping) other;
			return wifiAccessPoints.containsAll(otherPing.getWifiAccessPoints());
		} else {
			return false;
		}
	}

}
