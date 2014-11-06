package org.macfinder;

import org.macfinder.service.location.GeoLookup;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to represent a user.
 */
public class User {

	private String username, password;
	private List<GeoLookup> geoLookups;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		geoLookups = new ArrayList<GeoLookup>();
	}

	public void addLocation(GeoLookup geoLookup) {
		geoLookups.add(geoLookup);
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
}
