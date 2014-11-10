package org.macfinder.view;

import javax.swing.*;
import java.awt.*;

/**
 * Class to hold the map component of the main view.
 */
public class MapComponent extends JComponent {

	ImageIcon image;

	public MapComponent() {
		setLayout(null);
	}

	public void setMap(ImageIcon image) {
		this.image = image;
		setPreferredSize(new Dimension(image.getIconWidth(), image.getIconHeight()));
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		if (image != null) {
			super.paintComponent(g);
			g.drawImage(image.getImage(), 0, 0, getWidth(), getHeight(), this);
		}
	}
}
