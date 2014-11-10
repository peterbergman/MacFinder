package org.macfinder;

import org.macfinder.controller.LoginController;

/**
 * Hello world!
 *
 */
public class App {
    public static void main( String[] args ) {
		LoginController loginController = new LoginController();
		loginController.start();
    }
}
