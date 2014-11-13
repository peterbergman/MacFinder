package org.macfinder.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a machine.
 */
public class Machine {

	private String name;
	private List<Ping> pings;

	/**
	 * Constructs a new Machine object.
	 *
	 * @param name	the name of the Machine, usually
	 *              the hostname of the machine, but could
	 *              be anything String that describes the machine
	 */
	public Machine(String name) {
		this.name = name;
		pings = new ArrayList<Ping>();
	}

	public List<Ping> getPings() {
		return pings;
	}

	public String getName() {
		return name;
	}

	/**
	 * Adds a new ping to the Machine's list of pings,
	 * if the pings is not already present in the list.
	 * <p></p>
	 * If the ping is present in the list and the new ping has
	 * a GeoLookup object attached to it, then the lookup is
	 * added to the existing ping. This will also replace any current
	 * lookups attached to that ping.
	 *
	 * @param ping	the Ping to add
	 */
	public void addPing(Ping ping) {

		boolean foundPing = false;

		for (Ping existingPing : pings) {
			if (existingPing.equals(ping)) {
				if (ping.getGeoLookup() != null) {
					existingPing.setGeoLookup(ping.getGeoLookup());
				}
				foundPing = true;
				break;
			}
		}

		if (!foundPing) {
			pings.add(ping);
		}
	}

	/**
	 * Compares this Machine to the specified object.
	 * <p></p>
	 * The result is true if and only if the argument is not null
	 * and is a Machine object with the same name as this Machine.
	 *
	 * @param other	the object to compare this Machine against
	 * @return		true if the given object represents a Machine with a name
	 * 				that equals this Machine's name, false otherwise
	 */
	@Override
	public boolean equals(Object other) {
		if (other != null && other instanceof Machine) {
			Machine otherMachine = (Machine) other;
			return name.equalsIgnoreCase(otherMachine.getName());
		} else {
			return false;
		}
	}

	/**
	 * Returns the hash code of the Machine.
	 * <p></p>
	 * The hash code is calculated by calling hashCode on the name of the Machine
	 *
	 * @return	a hash code value for this object
	 */
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public String toString() {
		return name;
	}
}
