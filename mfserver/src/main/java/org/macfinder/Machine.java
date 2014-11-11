package org.macfinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a machine.
 */
public class Machine {

	private String name;
	private List<Ping> pings;

	public Machine(String name) {
		this.name = name;
		pings = new ArrayList<Ping>();
	}

	public String getName() {
		return name;
	}

	public void addPing(Ping ping) {
		if (!pings.contains(ping)) {
			pings.add(ping);
		}
	}

	// TODO: might want to refactor this method...
	public Ping getLastPing() {
		return pings.get(pings.size() - 1);
	}

	@Override
	public boolean equals(Object other) {
		if (other instanceof Machine) {
			Machine otherMachine = (Machine) other;
			return name.equalsIgnoreCase(otherMachine.getName());
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
