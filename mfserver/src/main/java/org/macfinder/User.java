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
		boolean foundMachine = false;
		if (machines == null) {
			machines = new ArrayList<Machine>();
			machines.add(machine);
		} else {
			for (Machine existingMachine : machines) {
				if (existingMachine.equals(machine)) {
					existingMachine.addPing(machine.getLastPing());
					foundMachine = true;
					break;
				}
			}
			if (!foundMachine) {
				machines.add(machine);
			}
		}
	}

	public Machine getLastMachine() {
		return machines.get(machines.size() - 1);
	}

}
