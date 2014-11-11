package org.macfinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a user.
 */
public class User {
	private String username;
	private String password;
	private List<Machine> machines;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		machines = new ArrayList<Machine>();
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void addMachine(Machine machine) {
		machines.add(machine);
	}

	public List<Machine> getMachines() {
		return new ArrayList<Machine>(machines);
	}


}
