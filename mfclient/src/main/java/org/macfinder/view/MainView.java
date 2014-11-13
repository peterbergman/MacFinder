package org.macfinder.view;

import org.macfinder.model.Machine;

import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import java.awt.*;

/**
 * Class to represent the main view.
 */
public class MainView extends JFrame {

	private final static int WIDTH = 800;
	private final static int HEIGHT = 640;
	private final static String TITLE = "MacFinder";

	private MapComponent mapComponent;
	private JPanel controlPanel, buttonPanel;
	private JList<Machine> machineList;
	private JButton lookupLocationButton;

	public MainView() {
		setTitle(TITLE);
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		setUpMapComponent();
		setUpControlPanel();
		setUpMachineList();
		setUpButtonPanel();
		setUpLookupLocationButton();
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

	public void setMachines(List<Machine> machines) {
		machineList.setListData(machines.toArray(new Machine[machines.size()]));
	}

	public Machine getSelectedMachine() {
		return machineList.getSelectedValue();
	}

	public void addLookupButtonActionListener(ActionListener actionListener) {
		lookupLocationButton.addActionListener(actionListener);
	}

	private void setUpMapComponent() {
		mapComponent = new MapComponent();
		add(mapComponent, BorderLayout.CENTER);
	}

	private void setUpMachineList() {
		machineList = new JList<Machine>();
		controlPanel.add(new JScrollPane(machineList));
	}

	private void setUpLookupLocationButton() {
		lookupLocationButton = new JButton("Find Mac!");
		buttonPanel.add(lookupLocationButton);
	}

	private void setUpControlPanel() {
		controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout(5, 5));
		add(controlPanel, BorderLayout.WEST);
	}

	private void setUpButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new GridLayout(0, 1));
		controlPanel.add(buttonPanel, BorderLayout.PAGE_END);
	}
}

