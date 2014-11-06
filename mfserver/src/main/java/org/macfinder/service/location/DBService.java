package org.macfinder.service.location;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
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

	public DBService() {
		try {
			LOGGER.info("Connecting to DB...");
			mongoClient = new MongoClient(DB_URL);
			DB dataBase = mongoClient.getDB(DB_NAME);
			collection = dataBase.getCollection(COLLECTION_NAME);
			LOGGER.info("DB connected!");
		} catch (UnknownHostException uhe) {
			LOGGER.severe(uhe.toString());
		}
	}

	public void put(String username, String password, GeoLookup geoLookup) {
		LOGGER.info("Inserting new location...");
		User user = new User(username, password);
		if (authenticate(user)) {
			user.addLocation(geoLookup);
			BasicDBObject document = (BasicDBObject) (JSON.parse(new Gson().toJson(user)));
			collection.insert(document);
			LOGGER.info("Location inserted for user: " + username);
		} else {
			LOGGER.warning("Authentication failed for user: " + username);
		}
		close();
	}

	private void close() {
		LOGGER.info("Closing DB connection...");
		if (mongoClient != null) {
			mongoClient.close();
		}
		LOGGER.info("DB connection closed!");
	}

	private boolean authenticate(User user) {
		BasicDBObject userDocument = new BasicDBObject("username", user.getUsername()).append("password", user.getPassword());
		int result = collection.find(userDocument).limit(1).count();
		return result == 1;
	}

}
