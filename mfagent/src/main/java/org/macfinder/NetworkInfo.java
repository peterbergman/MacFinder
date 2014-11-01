package org.macfinder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to fetch network information by calling
 * the native Airport process
 */
public class NetworkInfo {

	/**
	 * Method to fetch a list of all nearby access points.
	 *
	 * Internally calls the getNetworkInfo-method to perform the network scan.
	 *
	 * @return List	a list of access points
	 */
	public List<AccessPoint> getNetworks() {
		List<AccessPoint> networks = new ArrayList<AccessPoint>();
		String networkInfo = getNetworkInfo();
		Pattern pattern = Pattern.compile("([a-fA-F0-9]{2}[:-]){5}[a-fA-F0-9]");
		Matcher matcher = pattern.matcher(networkInfo);
		while (matcher.find()) {
			networks.add(new AccessPoint(matcher.group()));
		}
		return networks;
	}

	/**
	 * Creates a new Process that executes the Airport utility and performs a network scan.
	 *
	 * Looks for Airport in the default location: /System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/
	 *
	 * @return String the output from the network scan
	 */
	private String getNetworkInfo() {
		String line = "";
		StringBuffer info = new StringBuffer();
		try {
			Process process = Runtime.getRuntime().exec("/System/Library/PrivateFrameworks/Apple80211.framework/Versions/Current/Resources/airport -s");
			process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = reader.readLine())!= null) {
				info.append(line + " ");
			}
		} catch (IOException ioe) {
			// TODO: handle exception...
		} catch (InterruptedException ie) {
			// TODO: handle exception...
		}
		return info.toString();
	}
}