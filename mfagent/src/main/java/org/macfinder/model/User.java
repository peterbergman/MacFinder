package org.macfinder.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a user.
 */
public class User implements Serializable {

	private String username;
	private String password;
	private List<Machine> machines;

	/**
	 * Constructs a new User object.
	 *
	 * @param username	the username of the user.
	 * @param password	the password of the user.
	 */
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

	public List<Machine> getMachines() {
		return machines;
	}

	/**
	 * Method to add a Machine to a User.
	 *<p></p>
	 * Initiates the machine list if null and adds
	 * the new machine to the list.
	 * <p></p>
	 * If the new machine is already present in the User's
	 * list of machines, then the Ping from the new machine
	 * is added to the existing machine. This also assumes that
	 * the machine only contains one Ping to add to the existing
	 * machine.
	 * <p></p>
	 * If the machine is not present in the User's list of machines,
	 * then the new machine is added to the list.
	 *
	 * @param machine
	 */
	public void addMachine(Machine machine) {
		boolean foundMachine = false;
		if (machines == null) {
			machines = new ArrayList<Machine>();
			machines.add(machine);
		} else {
			for (Machine existingMachine : machines) {
				if (existingMachine.equals(machine)) {
					existingMachine.addPing(machine.getPings().get(0));
					foundMachine = true;
					break;
				}
			}
			if (!foundMachine) {
				machines.add(machine);
			}
		}
	}

}
