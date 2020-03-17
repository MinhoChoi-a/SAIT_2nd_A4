package sait.frs.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import sait.frs.exception.InvalidCitizenshipException;
import sait.frs.exception.InvalidNameException;

import sait.frs.gui.*;
import sait.frs.manager.*;
import sait.frs.problemdomain.*;
//import sait.frs.gui.FlightsTab.MyListSelectionListener;

import java.util.*;

/**
 * This class is the GUI class for Reservation tab. This class exploits
 * the design and action listener to search reservation.
 * @author Minho Choi 812108 Section CC
 * @author Michael Doctor 820167 Section CCC
 * @version 1.0, March 1, 2020
 *
 */

public class ReservationsTab extends TabBase {
	/**
	 * Instance of travel manager.
	 */
	private ReservationManager manager;

	// text field for search data
	private JTextField codeInput, airLineInput, nameInput;
	
	private ArrayList<Reservation> reserve = new ArrayList<Reservation>();
	private DefaultListModel<String> reserveCode;

	private JList<String> codeList;
	
	// text filed for search result
	private JTextField codeField, flightField, airlineField, costField, nameField, citizenField;
	
	// active combobox
	private JComboBox comboBox;
	
	// data type of Reservation, Flight class
	private Reservation findR;
	private Flight findF;

	/**
	 * Creates the components for reservations tab.
	 */
	public ReservationsTab(ReservationManager manager) {
		this.manager = manager;
		panel.setLayout(new BorderLayout());

		JPanel northPanel = createNorthPanel();
		panel.add(northPanel, BorderLayout.NORTH);

		JPanel centerPanel = createCenterPanel();
		panel.add(centerPanel, BorderLayout.CENTER);

		JPanel southPanel = createSouthPanel();
		panel.add(southPanel, BorderLayout.SOUTH);
	}

	/**
	 * Creates the north panel.
	 * 
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() {
		JPanel panel = new JPanel();
		JLabel title = new JLabel("Reservations", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	/**
	 * Creates the center panel.
	 * This panel can show the search result and update the citizenship and name.
	 * 
	 * @return JPanel that goes in center.
	 */
	private JPanel createCenterPanel() {

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// panel for reservation information
		JPanel reservePanel = new JPanel();
		panel.add(reservePanel, BorderLayout.EAST);
		reservePanel.setLayout(new BorderLayout(0, 0));

		JLabel resLabel = new JLabel("Reserve");
		resLabel.setHorizontalAlignment(SwingConstants.CENTER);
		resLabel.setFont(new Font("Serif", Font.PLAIN, 29));
		resLabel.setBorder(BorderFactory.createEmptyBorder(10,0,30,0));
		reservePanel.add(resLabel, BorderLayout.NORTH);

		// update button
		JButton updateNewButton = new JButton("Update");
		updateNewButton.addActionListener(new updateButton());
		updateNewButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		reservePanel.add(updateNewButton, BorderLayout.SOUTH);

		// reserve panel
		JPanel centerEastPanel = new JPanel();
		reservePanel.add(centerEastPanel, BorderLayout.CENTER);
		centerEastPanel.setLayout(new GridLayout(1, 2));

		// panel for all labels for each textfield
		JPanel labelPanel = new JPanel();
		centerEastPanel.add(labelPanel);
		labelPanel.setLayout(new GridLayout(8, 1));

		// set label for each textfield
		JLabel codeLabel = new JLabel("Code:");
		codeLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		codeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(codeLabel);

		JLabel flightLabel = new JLabel("Flight:");
		flightLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		flightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(flightLabel);

		JLabel airlineLabel = new JLabel("Airline:");
		airlineLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		airlineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(airlineLabel);

		JLabel costLabel = new JLabel("Cost:");
		costLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		costLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(costLabel);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(nameLabel);

		JLabel citizenLabel = new JLabel("Citizenship:");
		citizenLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		citizenLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(citizenLabel);

		JLabel statusLabel = new JLabel("Status:");
		statusLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		statusLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		labelPanel.add(statusLabel);

		// panel for all textfields
		JPanel fieldPanel = new JPanel();

		centerEastPanel.add(fieldPanel);
		fieldPanel.setLayout(new GridLayout(8, 1));

		// set textfiled which shows the reservation information
		codeField = new JTextField();
		codeField.setHorizontalAlignment(SwingConstants.LEFT);
		codeField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		codeField.setEditable(false);
		codeField.setColumns(10);
		fieldPanel.add(codeField);

		flightField = new JTextField();
		flightField.setHorizontalAlignment(SwingConstants.LEFT);
		flightField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		flightField.setEditable(false);
		flightField.setColumns(10);
		fieldPanel.add(flightField);

		airlineField = new JTextField();
		airlineField.setHorizontalAlignment(SwingConstants.LEFT);
		airlineField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		airlineField.setEditable(false);
		airlineField.setColumns(10);
		fieldPanel.add(airlineField);

		costField = new JTextField();
		costField.setHorizontalAlignment(SwingConstants.LEFT);
		costField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		costField.setEditable(false);
		costField.setColumns(10);
		fieldPanel.add(costField);

		nameField = new JTextField();
		nameField.setHorizontalAlignment(SwingConstants.LEFT);
		nameField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nameField.setColumns(10);
		fieldPanel.add(nameField);

		citizenField = new JTextField();
		citizenField.setHorizontalAlignment(SwingConstants.LEFT);
		citizenField.setFont(new Font("Tahoma", Font.PLAIN, 12));
		citizenField.setColumns(10);
		fieldPanel.add(citizenField);

		final String[] status = { "Active", "Inactive" };
		comboBox = new JComboBox(status);
		fieldPanel.add(comboBox);
		comboBox.addActionListener(new selectChange());

		// set a list panel
		JPanel listPanel = new JPanel();
		panel.add(listPanel, BorderLayout.CENTER);
		listPanel.setLayout(new BorderLayout(0, 0));
		
		// Wrap JList in JScrollPane so it is scrollable.
		reserveCode = new DefaultListModel<>();
		codeList = new JList<>(reserveCode);
		codeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		// set a scroll pane
		JScrollPane scrollPane = new JScrollPane(codeList);
		codeList.addListSelectionListener(new MyListSelectionListener());
		codeList.setFont(new Font("Tahoma", Font.PLAIN, 12));
		listPanel.add(scrollPane, BorderLayout.CENTER);
		JLabel gapLabel = new JLabel("Gap Filler");
		gapLabel.setForeground(UIManager.getColor("Panel.background"));
		gapLabel.setFont(new Font("Tahoma", Font.PLAIN, 50));
		listPanel.add(gapLabel, BorderLayout.SOUTH);
		listPanel.setBorder(BorderFactory.createEmptyBorder(10,20,0,20));
		
		return panel;
	}

	/**
	 * Creates the south(search) panel.
	 * Through this panel, users can find the reseravtion with code, flight or name. 
	 * @return JPanel that goes in south.
	 */
	private JPanel createSouthPanel() {
		
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JLabel finderLabel = new JLabel("Search");
		finderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		finderLabel.setFont(new Font("Serif", Font.PLAIN, 29));
		panel.add(finderLabel, BorderLayout.NORTH);
		
		// button to active the finding
		JButton findButton = new JButton("Find Reservations");
		findButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		panel.add(findButton, BorderLayout.SOUTH);
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BorderLayout());

		JPanel labelPanel = new JPanel();
		searchPanel.add(labelPanel, BorderLayout.WEST);
		labelPanel.setLayout(new GridLayout(3, 1));

		JLabel codeLabel = new JLabel("Code:");
		codeLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		codeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPanel.add(codeLabel);

		JLabel airlineLabel = new JLabel("Airline:");
		airlineLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		airlineLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPanel.add(airlineLabel);

		JLabel nameLabel = new JLabel("Name:");
		nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		labelPanel.add(nameLabel);

		// text filed panel to input data
		JPanel textPanel = new JPanel();
		
		searchPanel.add(textPanel, BorderLayout.CENTER);
		textPanel.setLayout(new GridLayout(3, 1));

		codeInput = new JTextField();
		codeInput.setHorizontalAlignment(SwingConstants.LEFT);
		textPanel.add(codeInput);

		airLineInput = new JTextField();
		airLineInput.setHorizontalAlignment(SwingConstants.LEFT);
		textPanel.add(airLineInput);

		nameInput = new JTextField();
		nameInput.setHorizontalAlignment(SwingConstants.LEFT);
		textPanel.add(nameInput);

		findButton.addActionListener(new findButtonActionListener());
		panel.add(searchPanel, BorderLayout.CENTER);

		return panel;
	}
	
	/**
	 * Action listener for find button
	 * 
	 */
	
	private class findButtonActionListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			reserve.clear();
			reserveCode.clear();
			codeList.removeAll();

			String code = codeInput.getText();
			String airLine = airLineInput.getText();
			String name = nameInput.getText();

			reserve = manager.findReservations(code, airLine, name);

			for (int i = 0; i < reserve.size(); i++) {
				reserveCode.addElement(reserve.get(i).getCode());
			}
		}
	}

	/**
	 * Action listener to apply the change of active combobox
	 * 
	 */
	
	private class selectChange implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (comboBox.getSelectedIndex() == 0) {
				findR.setActive(true);
			} else {
				findR.setActive(false);
			}
		}
	}

	/**
	 * Action listener to apply the change of reservation
	 * @throws InvalidNameException, InvalidCitzenshipException 
	 * 
	 */
	
	private class updateButton implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				if (nameField.getText().equals(null) || nameField.getText().equals("")) {
					throw new InvalidNameException();
				}
			} catch (InvalidNameException h) {
				String namePrompt = JOptionPane.showInputDialog("ERROR: Please enter a name!");
				nameField.setText(namePrompt);
			}
			try {
				if (citizenField.getText().equals(null) || citizenField.getText().equals("")) {
					throw new InvalidCitizenshipException();
				}
			} catch (InvalidCitizenshipException i) {
				String citizenPrompt = JOptionPane.showInputDialog("ERROR: Please enter a valid citizenship!");
				citizenField.setText(citizenPrompt);
			}
			try {
				findR.setCitizenship(citizenField.getText());
				findR.setName(nameField.getText());
				manager.persist();
			} catch (InvalidNameException | InvalidCitizenshipException ex) {

			}
			JOptionPane.showMessageDialog(null, "Reservation Updated.");

		}
	}

	/**
	 * Called when user selects an item in the JList.
	 */
	
	private class MyListSelectionListener implements ListSelectionListener {
		
		@Override
		public void valueChanged(ListSelectionEvent e) {

			if (codeList.isSelectionEmpty()) {
				codeField.setText("");
				flightField.setText("");
				airlineField.setText("");
				costField.setText("");
				nameField.setText("");
				citizenField.setText("");

			} else {

				findR = manager.findReservationByCode(codeList.getSelectedValue());
				findF = findR.getFlight();
				String cost = String.format("$%.2f", findF.getCostPerSeat());

				codeField.setText(findR.getCode());
				flightField.setText(findF.getCode());
				airlineField.setText(findF.getAirline());
				costField.setText(cost);
				nameField.setText(findR.getName());
				citizenField.setText(findR.getCitizenship());

				if (findR.isActive() == true) {
					comboBox.setSelectedIndex(0);
				} else {
					comboBox.setSelectedIndex(1);
				}
			}
		}
	}
}