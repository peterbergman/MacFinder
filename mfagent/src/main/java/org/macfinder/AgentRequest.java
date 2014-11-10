package org.macfinder;

import java.util.List;

/**
 * Class to represent a request sent from the agent.
 * This class just acts as a data container that can be
 * converted to JSON.
 */
public class AgentRequest {

	private String username;
	private String password;
	private List<AccessPoint> wifiAccessPoints;
	private Machine machine;

	/**
	 * Constructs a new AgentRequest-object.
	 *
	 * @param username			the username to authenticate with the MacFinder server
	 * @param password			the password to authenticate with the MacFinder server
	 * @param wifiAccessPoints	a list of networks detected
	 * @param machine			the machine from which the agent request were made
	 */
	public AgentRequest(String username, String password, List<AccessPoint> wifiAccessPoints, Machine machine) {
		this.username = username;
		this.password = password;
		this.wifiAccessPoints = wifiAccessPoints;
		this.machine = machine;
	}

	public List<AccessPoint> getWifiAccessPoints() {
		return wifiAccessPoints;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public Machine getMachine() {
		return machine;
	}
}
