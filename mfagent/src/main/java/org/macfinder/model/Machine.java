package org.macfinder.model;

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
		pings.add(ping);
	}
}
