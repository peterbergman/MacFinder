package org.macfinder.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Class to fetch system information by calling
 * the native applications
 */
public class SystemInfo {
	public static String getComputerName() {
		String line = "";
		StringBuffer name = new StringBuffer();
		try {
			Process process = Runtime.getRuntime().exec("scutil --get ComputerName");
			process.waitFor();
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = reader.readLine())!= null) {
				name.append(line);
			}
		} catch (IOException ioe) {
			// TODO: handle exception...
		} catch (InterruptedException ie) {
			// TODO: handle exception...
		}
		return name.toString();
	}
}
