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
	private List<AccessPoint> wifiAccessPoints;

	/**
	 * Constructs a new AgentRequest-object.
	 *
	 * @param userName	the username to authenticate with the MacFinder server
	 * @param password	the password to authenticate with the MacFinder server
	 * @param wifiAccessPoints	a list of networks detected
	 */
	public AgentRequest(String userName, String password, List<AccessPoint> wifiAccessPoints) {
		this.userName = userName;
		this.password = password;
		this.wifiAccessPoints = wifiAccessPoints;
	}

	public List<AccessPoint> getWifiAccessPoints() {
		return wifiAccessPoints;
	}
}
