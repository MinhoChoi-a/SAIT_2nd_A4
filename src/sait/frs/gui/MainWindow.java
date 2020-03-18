package sait.frs.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;

import javax.swing.*;

import sait.frs.manager.*;


/**
 * This class is the GUI class of main window. This class exploits
 * the main window and covert between Flight and Reservation tab.
 * @author Nick Hamnett, Mohamed
 * @version January 2, 2020
 */
 

public class MainWindow extends JFrame 
{
	private final static String TAB_FLIGHTS = "flights";
	private final static String TAB_RESERVATIONS = "reservations";
	private final int WIDTH = 600,
					  HEIGHT = 600;
	
	/**
	 * Holds the flight and reservation manager.
	 */
    private FlightManager flightManager;
    private ReservationManager reservationManager;
	
    /**
	 * Card layout to display tab content.
	 */
	private CardLayout cardLayout;
	
	/**
	 * North panel.
	 */
	private JPanel northPanel;
	
	/**
	 * Center panel.
	 */
	private JPanel centerPanel;
	
	/**
	 * Flights tab button.
	 */
	private JButton flightsButton;
	
	/**
	 * Reservations tab button.
	 */
	private JButton reservationsButton;
	
	/**
	 * Flights tab panel.
	 */
	private TabBase flightsTab;
	
	/**
	 * Reservations tab panel.
	 */
	private TabBase reservationsTab;
	
	/**
	 * Creates the Main Window and any components inside it.
	 */
	public MainWindow() throws IOException
	{
		this.flightManager = new FlightManager();
		this.reservationManager = new ReservationManager();
		
		setTitle("Flight Reservation Management System");
		
		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		northPanel = createNorthPanel();
		add(northPanel, BorderLayout.NORTH);
		
		centerPanel = createCenterPanel();
		add(centerPanel, BorderLayout.CENTER);
	}
	
	/**
	 * Creates the north panel.
	 * @return JPanel that goes in north.
	 */
	private JPanel createNorthPanel() 
	{
		JPanel panel = new JPanel();
		
		panel.setLayout(new BorderLayout());
			
		JPanel tabPanel = createTabPanel();
		panel.add(tabPanel, BorderLayout.SOUTH);
		
		return panel;
	}
	
	/**
	 * Creates the center panel.
	 * @return JPanel that goes in center.
	 * @throws IOException 
	 */
	private JPanel createCenterPanel() throws IOException 
	{
		JPanel panel = new JPanel();
		
		cardLayout = new CardLayout();
		
		flightsTab = new FlightsTab(flightManager,reservationManager);
		reservationsTab = new ReservationsTab(reservationManager);
		
		panel.setLayout(cardLayout);
		
		panel.add(flightsTab.getPanel(), TAB_FLIGHTS);
		panel.add(reservationsTab.getPanel(), TAB_RESERVATIONS);
		
		cardLayout.first(panel);
		
		return panel;
	}
	
	/**
	 * Creates the tab buttons.
	 * @return JPanel containing tab buttons.
	 */
	private JPanel createTabPanel() {
		JPanel tabPanel = new JPanel();
		
		tabPanel.setLayout(new GridLayout(1, 2));
		
		flightsButton = new JButton("Flights");
		reservationsButton = new JButton("Reservations");
		
		flightsButton.addActionListener(new TabButtonActionListener());
		reservationsButton.addActionListener(new TabButtonActionListener());
		
		tabPanel.add(flightsButton);
		tabPanel.add(reservationsButton);
		
		return tabPanel;
	}
	
	/**
	 * Displays the JFrame window.
	 */
	public void display() 
	{
		setSize(WIDTH,HEIGHT);
		setVisible(true);
	}
	
	/**
	 * Inner action listener class that listens for a tab button to be clicked.
	 * 
	 * @author Nick Hamnett, Mohamed
	 * @version January 2, 2020
	 */
private class TabButtonActionListener implements ActionListener 
	{

		@Override
		public void actionPerformed(ActionEvent e) 
		{
			if (e.getSource() == flightsButton) 
			{
				cardLayout.show(centerPanel, TAB_FLIGHTS);
			} 
			else if (e.getSource() == reservationsButton) 
			{
				cardLayout.show(centerPanel, TAB_RESERVATIONS);
			}
		}
		
	}
}
