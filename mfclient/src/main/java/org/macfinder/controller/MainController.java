package org.macfinder.controller;

import org.macfinder.view.MainView;

import javax.swing.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Class to act as a controller for the main view.
 */
public class MainController {

	private MainView mainView;

	public MainController() {
		mainView = new MainView();
	}

	public void start() {
		mainView.open();
		String url = "http://maps.googleapis.com/maps/api/staticmap?"
				+ "center=59.4030598,17.9813378"
				+ "&zoom=14"
				+ "&size=640x640"
				+ "&maptype=roadmap"
				+ "&markers=color:red%7Ccolor:red%7C59.4030598,17.9813378"
				+ "&sensor=false";
		try {
			mainView.setMap(new ImageIcon(new URL(url)));
		} catch (MalformedURLException mfe) {};
	}
}
