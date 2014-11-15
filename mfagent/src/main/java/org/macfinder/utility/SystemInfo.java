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
		String line;
		StringBuilder name = new StringBuilder();
		BufferedReader reader = null;
		try {
			Process process = Runtime.getRuntime().exec("scutil --get ComputerName");
			process.waitFor();
			reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			while ((line = reader.readLine())!= null) {
				name.append(line);
			}
		} catch (IOException ioe) {
		} catch (InterruptedException ie) {
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException ioe) {}
		}
		return name.toString();
	}
}
