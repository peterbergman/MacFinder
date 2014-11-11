package org.macfinder;

/**
 * The server entry point.
 */
public class App {
    public static void main(String[] args) {
		Server server = new Server(8080, 50, 4);
		server.start();
    }
}
