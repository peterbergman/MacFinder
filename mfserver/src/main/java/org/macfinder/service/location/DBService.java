package org.macfinder.service.location;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.util.JSON;
import org.macfinder.User;
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

	public void put(User user) {
		LOGGER.info("Inserting new location...");
		User existingUser = get(user);
		if (existingUser != null) {
			existingUser.addMachine(user.getLastMachine());
			BasicDBObject document = (BasicDBObject) (JSON.parse(new Gson().toJson(existingUser)));
			BasicDBObject query = new BasicDBObject("username", user.getUsername()).append("password", user.getPassword());
			collection.update(query, document);
			LOGGER.info("Updated record for user: " + user.getUsername());
		} else {
			LOGGER.warning("Authentication failed for user: " + user.getUsername());
		}
		close();
	}

	public User get(User user) {
		BasicDBObject query = new BasicDBObject("username", user.getUsername()).append("password", user.getPassword());
		DBObject foundUser = collection.findOne(query);
		return gson.fromJson(gson.toJson(foundUser), User.class);
	}

	private void close() {
		LOGGER.info("Closing DB connection...");
		if (mongoClient != null) {
			mongoClient.close();
		}
		LOGGER.info("DB connection closed!");
	}

}
