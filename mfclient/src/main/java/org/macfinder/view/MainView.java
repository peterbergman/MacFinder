package org.macfinder.view;

import javax.swing.*;
import java.awt.*;

/**
 * Class to represent the main view.
 */
public class MainView extends JFrame {

	private final static int WIDTH = 640;
	private final static int HEIGHT = 640;
	private final static String TITLE = "MacFinder";

	private MapComponent mapComponent;

	public MainView() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		setUpMapComponent();
	}

	public void open() {
		setVisible(true);
	}

	public void close() {
		dispose();
	}

	public void setMap(ImageIcon image) {
		mapComponent.setMap(image);
	}

	private void setUpMapComponent() {
		mapComponent = new MapComponent();
		add(mapComponent);
	}

}
