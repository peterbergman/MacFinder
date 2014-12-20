package org.macfinder.utility;

import org.macfinder.model.User;

import java.io.*;

/**
 * Class to handle file operations.
 */
public class FileHelper {

	/**
	 * Tries to read the file mf.dat located at /opt/macfinder and
	 * parse the contents to a User object.
	 *
	 * @return	a User object if successful, otherwise null
	 */
	public static User readUserInformation() {
		FileInputStream fileInputStream = null;
		ObjectInputStream objectInputStream = null;
		User user = null;
		try {
			System.out.println("Looking for " + System.getProperty("user.home") + "/Library/MacFinder/mf.dat");
			fileInputStream = new FileInputStream(System.getProperty("user.home") + "/Library/MacFinder/mf.dat");
			objectInputStream = new ObjectInputStream(fileInputStream);
			user = (User) objectInputStream.readObject();
		} catch (IOException ioe) {
			System.out.println("Got error: " + ioe);
		} catch (ClassNotFoundException cfe) {

		} finally {
			try {
				if (fileInputStream != null) {
					fileInputStream.close();
				}
				if (objectInputStream != null) {
					objectInputStream.close();
				}
			} catch (IOException ioe) {

			}
		}
		return user;
	}

	public static void saveUserInformation(User user) {
		FileOutputStream fileOutputStream = null;
		ObjectOutputStream objectOutputStream = null;
		try {
			File directory = new File(System.getProperty("user.home") + "/Library/MacFinder");
			if (!directory.exists()) {
				directory.mkdir();
			}
			fileOutputStream = new FileOutputStream(System.getProperty("user.home") + "/Library/MacFinder/mf.dat");
			objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(user);
		} catch (IOException ioe) {

		} finally {
			try {
				if (fileOutputStream != null) {
					fileOutputStream.close();
				}
				if (objectOutputStream != null) {
					objectOutputStream.close();
				}
			} catch (IOException ioe) {

			}
		}
	}
}
