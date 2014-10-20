package org.macfinder;

import java.io.*;

/**
 * Class to handle the installation of the agent.
 */
public class Installer {

	private static final String DIRECTORY = "/Library/MacFinder";

	public void install() {
		makeDirectory();
		createPlist();
	}

	private void makeDirectory() {
		File file = new File(DIRECTORY);
		file.mkdir();
	}

	private void createPlist() {
		System.out.println(readPlistFromJar());

		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("/Library/LaunchAgents/mf.agent.plist"), "utf-8"));
			writer.write(readPlistFromJar());
		} catch (IOException ex) {
			// TODO: handle...
		} finally {
			try {
				writer.close();
			} catch (Exception ex) {}
		}
	}

	private String readPlistFromJar() {
		BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/mf.agent.plist")));
		String line = "";
		StringBuffer buffer = new StringBuffer();
		try {
			while ((line = reader.readLine()) != null){
				buffer.append(line + "\n");
			}
		} catch (IOException ioe) {

		}
		return buffer.toString();
	}
}
