package org.macfinder;

import java.util.List;

/**
 * Class to represent a request sent from the agent.
 * This class just acts as a data container that can be
 * converted to JSON.
 */
public class AgentRequest {

	private String userName;
	private String password;
	private List<String> networks;

	/**
	 * Constructs a new AgentRequest-object.
	 *
	 * @param userName	the username to authenticate with the MacFinder server
	 * @param password	the password to authenticate with the MacFinder server
	 * @param networks	a list of networks detected
	 */
	public AgentRequest(String userName, String password, List<String> networks) {
		this.userName = userName;
		this.password = password;
		this.networks = networks;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public List<String> getNetworks() {
		return networks;
	}
}
