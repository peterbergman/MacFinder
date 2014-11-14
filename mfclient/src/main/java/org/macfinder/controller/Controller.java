package org.macfinder.controller;

import org.macfinder.model.http.HTTPResponse;

import javax.swing.*;

/**
 * Generic interface for controllers.
 */
public interface Controller {
	void workerCallback(HTTPResponse response);
	void workerCallback(ImageIcon imageIcon);
}
