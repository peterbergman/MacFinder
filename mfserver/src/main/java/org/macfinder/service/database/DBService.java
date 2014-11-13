package org.macfinder.service.database;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.macfinder.model.User;
import java.net.UnknownHostException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * Class to handle database operations
 */
// TODO: make this class singleton
public class DBService {

	private final static Logger LOGGER = Logger.getLogger(DBService.class.getName());
	static {
		LOGGER.setUseParentHandlers(false);
		LOGGER.addHandler(new ConsoleHandler());
	}

	private final static String DB_URL = "ec2-54-172-75-176.compute-1.amazonaws.com";
	private final static String DB_NAME = "macfinder";
	private final static String COLLECTION_NAME = "users";

	private MongoClient mongoClient;
	private DBCollection collection;
	private Gson gson;

	/**
	 * Constructs a new DBService object.
	 */
	public DBService() {
		try {
			LOGGER.info("Connecting to DB...");
			mongoClient = new MongoClient(DB_URL);
			DB dataBase = mongoClient.getDB(DB_NAME);
			collection = dataBase.getCollection(COLLECTION_NAME);
			gson = new Gson();
			LOGGER.info("DB connected!");
		} catch (UnknownHostException uhe) {
			LOGGER.severe(uhe.toString());
		}
	}

	/**
	 * Updates a user in the database.
	 *
	 * First tries to retrieve the existing user data from the database.
	 * If this fails, then no update is made. If this is successful, then
	 * the machines from the updatedUserData is added to the list of machines
	 * from the existing user. This involves both merging of existing machines
	 * and adding of new machines.
	 * Then the existing (and now updated) user is placed back in the database.
	 * <p></p>
	 * Assumes that the updatedUserData only contains one single machine to add or update
	 * to the existing user.
	 *
	 * @param updatedUser	the updated user data sent from the agent,
	 *                          must be an existing user
	 */
	public void update(User updatedUser, User existingUser) {
		LOGGER.info("Updating user data...");
		existingUser.addMachine(updatedUser.getMachines().get(0));
		BasicDBObject document = (BasicDBObject) (JSON.parse(new Gson().toJson(existingUser)));
		BasicDBObject query = new BasicDBObject("username", updatedUser.getUsername()).append("password", updatedUser.getPassword());
		collection.update(query, document);
		LOGGER.info("Updated record for user: " + updatedUser.getUsername());
	}

	/**
	 * Method to retrieve an existing user from the database.
	 *
	 * @param user	a User object to form the query,
	 *              must consist of at least a username
	 * @return		the User object found in the database that matches the
	 * 				User object in the query. Null if not found.
	 */
	public User get(User user) {
		LOGGER.info("Looking for user: " + user.getUsername() + " ...");
		BasicDBObject query = new BasicDBObject("username", user.getUsername()).append("password", user.getPassword());
		DBObject foundUser = collection.findOne(query);
		if (foundUser == null) {
			LOGGER.warning("Authentication failed for user: " + user.getUsername());
		}
		return gson.fromJson(gson.toJson(foundUser), User.class);
	}

	/**
	 * Method to close the DBService
	 */
	public void close() {
		LOGGER.info("Closing DB connection...");
		if (mongoClient != null) {
			mongoClient.close();
		}
		LOGGER.info("DB connection closed!");
	}

}
