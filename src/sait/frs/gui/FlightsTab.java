package sait.frs.gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;

import sait.frs.exception.*;
import sait.frs.manager.*;
import sait.frs.problemdomain.Flight;
import sait.frs.problemdomain.Reservation;

/**
 * This class is the GUI class for Flight tab. This class exploits
 * the design and action listener to search flights.
 * @author Minho Choi 812108 Section CC
 * @author Michael Doctor 820167 Section CCC
 * @version 1.0, March 1, 2020
 *test
 */
public class FlightsTab extends TabBase
{
	/**
	 * Instance of travel manager.
	 */
	private FlightManager manager;
	private ReservationManager reservationManager;

	/**
	 * List of flights.
	 */
	private JList<Flight> flightsList;
	private DefaultListModel<Flight> flightsModel;
	private ArrayList<Flight> findFlightsList;

	//components
	private JTextField flightText, 
					   airlineText, 
					   dayText, 
					   timeText, 
					   costText, 
					   nameText, 
					   citizenshipText;
	private JButton findFlightButton;
	private JComboBox fromCombo, 
					  toCombo, 
					  dayCombo;

	//array for day ComboBox
	private String[] daysList = { manager.WEEKDAY_ANY, manager.WEEKDAY_SUNDAY, manager.WEEKDAY_MONDAY,
			manager.WEEKDAY_TUESDAY, manager.WEEKDAY_WEDNESDAY, manager.WEEKDAY_THURSDAY, manager.WEEKDAY_FRIDAY,
			manager.WEEKDAY_SATURDAY };

	
	//holds selected Combo box values
	private String selectedFrom, 
				   selectedTo, 
				   selectedDay;

	//holds reservation details
	private Reservation reservation;
	
	/**
	 * Creates the components for flights tab.
	 * @throws IOException 
	 */
	public FlightsTab(FlightManager manager, ReservationManager reservationManager) throws IOException
	{
		this.manager = manager;
		this.reservationManager = reservationManager;
		
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
	 * @return JPanel that goes in north of main panel.
	 */
	private JPanel createNorthPanel()
	{
		JPanel panel = new JPanel();

		JLabel title = new JLabel("Flights", SwingConstants.CENTER);
		title.setFont(new Font("serif", Font.PLAIN, 29));
		panel.add(title);

		return panel;
	}

	/**
	 * Creates the center panel.
	 * 
	 * @return JPanel that goes in center of main panel.
	 */
	private JPanel createCenterPanel()
	{
		JPanel panel = new JPanel();

		panel.setLayout(new BorderLayout());

		panel.add(buildListPanel(), BorderLayout.CENTER);
		panel.add(buildReservePanel(), BorderLayout.EAST);
		return panel;
	}

	/**
	 * Creates the center panel JList component
	 * 
	 * @return JPanel that goes in center center of main panel.
	 */
	private JPanel buildListPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		flightsModel = new DefaultListModel<>();
		flightsList = new JList<>(flightsModel);
		findFlightsList = new ArrayList<>();

		// User can only select one item at a time.
		flightsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Wrap JList in JScrollPane so it is scrollable.
		JScrollPane scrollPane = new JScrollPane(this.flightsList);

		//add listener for selected flight
		flightsList.addListSelectionListener(new MyListSelectionListener());

		panel.add(scrollPane, BorderLayout.CENTER);

		//created to match GUI provided
		JLabel fillerLabel = new JLabel("Gap Filler");
		fillerLabel.setForeground(UIManager.getColor("Panel.background"));
		fillerLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		panel.add(fillerLabel, BorderLayout.SOUTH);
		panel.setBorder(BorderFactory.createEmptyBorder(10,20,0,20));
		return panel;
	}

	/**
	 * Creates the center reservation panel.
	 * 
	 * @return JPanel that goes in center east of main panel.
	 */
	private JPanel buildReservePanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		//reserve header
		JLabel header = new JLabel("Reserve");
		header.setHorizontalAlignment(SwingConstants.CENTER);
		header.setFont(new Font("Serif", Font.PLAIN, 29));
		header.setBorder(BorderFactory.createEmptyBorder(10,0,30,0));
		//reserve button
		JButton reserveButton = new JButton("Reserve");
		reserveButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		//action listener for button pressed
		reserveButton.addActionListener(new MakeReservation());

		panel.add(header, BorderLayout.NORTH);
		panel.add(reserveButton, BorderLayout.SOUTH);
		//adds to reserve panel and creates labels center panel of reserve
		panel.add(buildReserveCenterPanel(), BorderLayout.CENTER);

		return panel;
	}

	/**
	 * Creates the reserve panel labels and textfields.
	 * 
	 * @return JPanel that goes in center reserve panel.
	 */
	private JPanel buildReserveCenterPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1, 2));

		//label panel
		panel.add(buildLeftPanel());
		//textbox panel
		panel.add(buildRightPanel());

		return panel;
	}

	/**
	 * Creates the label panel for resereve panel.
	 * 
	 * @return JPanel that goes in center of reserve panel.
	 */
	private JPanel buildLeftPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(8, 1));
		
		//create labels

		JLabel label1 = new JLabel("Flight:");
		label1.setFont(new Font("Tahoma", Font.BOLD, 12));
		label1.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel label2 = new JLabel("Airline:");
		label2.setFont(new Font("Tahoma", Font.BOLD, 12));
		label2.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel label3 = new JLabel("Day:");
		label3.setFont(new Font("Tahoma", Font.BOLD, 12));
		label3.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel label4 = new JLabel("Time:");
		label4.setFont(new Font("Tahoma", Font.BOLD, 12));
		label4.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel label5 = new JLabel("Cost:");
		label5.setFont(new Font("Tahoma", Font.BOLD, 12));
		label5.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel label6 = new JLabel("Name:");
		label6.setFont(new Font("Tahoma", Font.BOLD, 12));
		label6.setHorizontalAlignment(SwingConstants.RIGHT);

		JLabel label7 = new JLabel("Citizenship:");
		label7.setFont(new Font("Tahoma", Font.BOLD, 12));
		label7.setHorizontalAlignment(SwingConstants.RIGHT);

		//add labels to label panel
		panel.add(label1);
		panel.add(label2);
		panel.add(label3);
		panel.add(label4);
		panel.add(label5);
		panel.add(label6);
		panel.add(label7);

		return panel;
	}

	/**
	 * Creates the textfield panel for reservation panel.
	 * 
	 * @return JPanel that goes in center of reservation panel.
	 */
	private JPanel buildRightPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(8, 1));

		//create textfields
		flightText = new JTextField();
		flightText.setHorizontalAlignment(SwingConstants.LEFT);
		flightText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		flightText.setEditable(false);
		flightText.setColumns(10);

		airlineText = new JTextField();
		airlineText.setHorizontalAlignment(SwingConstants.LEFT);
		airlineText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		airlineText.setEditable(false);
		airlineText.setColumns(10);

		dayText = new JTextField();
		dayText.setHorizontalAlignment(SwingConstants.LEFT);
		dayText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		dayText.setEditable(false);
		dayText.setColumns(10);

		timeText = new JTextField();
		timeText.setHorizontalAlignment(SwingConstants.LEFT);
		timeText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		timeText.setEditable(false);
		timeText.setColumns(10);

		costText = new JTextField();
		costText.setHorizontalAlignment(SwingConstants.LEFT);
		costText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		costText.setEditable(false);
		costText.setColumns(10);

		nameText = new JTextField();
		nameText.setHorizontalAlignment(SwingConstants.LEFT);
		nameText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		nameText.setColumns(10);

		citizenshipText = new JTextField();
		citizenshipText.setHorizontalAlignment(SwingConstants.LEFT);
		citizenshipText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		citizenshipText.setColumns(10);

		//add textfields to panel
		panel.add(flightText);
		panel.add(airlineText);
		panel.add(dayText);
		panel.add(timeText);
		panel.add(costText);
		panel.add(nameText);
		panel.add(citizenshipText);

		return panel;
	}

	/**
	 * Creates the south panel.
	 * 
	 * @return JPanel that goes in south.
	 */
	private JPanel createSouthPanel() throws IOException
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JLabel flightFinderLabel = new JLabel("Flight Finder");
		flightFinderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		flightFinderLabel.setFont(new Font("Serif", Font.PLAIN, 29));

		findFlightButton = new JButton("Find Flights");
		findFlightButton.setFont(new Font("Tahoma", Font.BOLD, 15));
		findFlightButton.addActionListener(new FindFlightButton());

		panel.add(flightFinderLabel, BorderLayout.NORTH);
		panel.add(findFlightButton, BorderLayout.SOUTH);
		//create ComboBox Panel and add to south panel
		panel.add(buildFindFlightPanel(), BorderLayout.CENTER);

		return panel;
	}

	/**
	 * Creates the flight panel for south panel.
	 * 
	 * @return JPanel that goes in center south of main panel.
	 * @throws IOException 
	 */
	private JPanel buildFindFlightPanel() throws IOException
	{
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		//create label and ComboBox panel
		panel.add(buildSouthLabelPanel(), BorderLayout.WEST);
		panel.add(buildComboBoxPanel(), BorderLayout.CENTER);

		return panel;
	}

	/**
	 * Creates the label panel for findflight panel.
	 * 
	 * @return JPanel that goes in west of findflight panel.
	 */
	private JPanel buildSouthLabelPanel()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));

		JLabel label1 = new JLabel("From:");
		label1.setHorizontalAlignment(SwingConstants.RIGHT);
		label1.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel label2 = new JLabel("To:");
		label2.setHorizontalAlignment(SwingConstants.RIGHT);
		label2.setFont(new Font("Tahoma", Font.PLAIN, 12));

		JLabel label3 = new JLabel("Day:");
		label3.setHorizontalAlignment(SwingConstants.RIGHT);
		label3.setFont(new Font("Tahoma", Font.PLAIN, 12));

		panel.add(label1);
		panel.add(label2);
		panel.add(label3);

		return panel;
	}

	/**
	 * Creates the ComboBox panel for findflight panel.
	 * 
	 * @return JPanel that goes in center of findflight panel.
	 */
	private JPanel buildComboBoxPanel() throws IOException
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(3, 1));

		ArrayList<String> codeList = new ArrayList<>();
		
		for (int i = 0; i<manager.getAirports().size();i++)
		{
			if (i % 2 == 0)
			{
				codeList.add(manager.getAirports().get(i));
			}
		}
		//create ComboBoxes and add action listeners
		fromCombo = new JComboBox(codeList.toArray());
		fromCombo.setFont(new Font("Tahoma", Font.BOLD, 12));
		fromCombo.addActionListener(new FindFlightListener());

		toCombo = new JComboBox(codeList.toArray());
		toCombo.setFont(new Font("Tahoma", Font.BOLD, 12));
		toCombo.addActionListener(new FindFlightListener());

		dayCombo = new JComboBox(daysList);
		dayCombo.setFont(new Font("Tahoma", Font.BOLD, 12));
		dayCombo.addActionListener(new FindFlightListener());

		panel.add(fromCombo);
		panel.add(toCombo);
		panel.add(dayCombo);

		return panel;
	}

	private class FindFlightListener implements ActionListener
	{
		/**
		 * Called when user selects an item in the ComboBoxes
		 */
		public void actionPerformed(ActionEvent e)
		{
			// from
			selectedFrom = (String) fromCombo.getSelectedItem();
			// to
			selectedTo = (String) toCombo.getSelectedItem();
			// day
			selectedDay = (String) dayCombo.getSelectedItem();
		}

	}

	private class FindFlightButton implements ActionListener
	{
		/**
		 * Called when presses the Find Flights Button.
		 */
		public void actionPerformed(ActionEvent e)
		{
			//reset existing data 
			flightsModel.clear();
			findFlightsList.clear();
			flightsList.removeAll();
			
			//adds corresponding selected flight options to flightsList
			findFlightsList = manager.findFlights(selectedFrom, selectedTo, selectedDay);
			for (Object val : findFlightsList.toArray())
			{
				flightsModel.addElement((Flight) val);
			}
		}

	}

	private class MyListSelectionListener implements ListSelectionListener
	{

		/**
		 * Called when user selects an item in the JList.
		 */
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			//sets textfields to "" if nothing is selected
			if (flightsList.isSelectionEmpty())
			{
				flightText.setText("");
				airlineText.setText("");
				dayText.setText("");
				timeText.setText("");
				costText.setText("");
			} else
			{
				//adds details from selected JList value to textfields
				flightText.setText(flightsList.getSelectedValue().getCode());
				airlineText.setText(flightsList.getSelectedValue().getAirlineName());
				dayText.setText(flightsList.getSelectedValue().getWeekday());
				timeText.setText(flightsList.getSelectedValue().getTime());
				costText.setText(String.format("$%.2f", flightsList.getSelectedValue().getCostPerSeat()));
			}
		}

	}

	private class MakeReservation implements ActionListener
	{
		/**
		 * Called when user presses Reserve Button
		 */
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				if(flightsList.isSelectionEmpty())
				{
					//throws exception if flightsList is empty
					throw new NullFlightException();
				}
			}
			catch (NullFlightException f)
			{
				//prompts user
				JOptionPane.showMessageDialog(null, "ERROR: Flight does not exist!");
			}
			try
			{
				if(flightsList.getSelectedValue().getSeats() <= 0)
				{
					//throws exception if there are no seats available
					throw new NoMoreSeatsException();
				}
					
			}
			catch (NoMoreSeatsException g)
			{
				JOptionPane.showMessageDialog(null, "ERROR: Flight is fully booked!");
			}
			try
			{
				if(nameText.getText().equals(null)  || nameText.getText().equals(""))
				{
					//throws exception when nothing is entered into name textfield
					throw new InvalidNameException();
				}
			}
			catch (InvalidNameException h)
			{
				//prompts user to enter a name text and assigns entered name to name text field
				String namePrompt = JOptionPane.showInputDialog("ERROR: Please enter a name!");
				nameText.setText(namePrompt);
			}
			try
			{
				if(citizenshipText.getText().equals(null)  || citizenshipText.getText().equals(""))
				{
					//if no citizenship is entered
					throw new InvalidCitizenshipException();
				}
			}
			catch (InvalidCitizenshipException i)
			{
				String citizenPrompt = JOptionPane.showInputDialog("ERROR: Please enter a valid citizenship!");
				citizenshipText.setText(citizenPrompt);
			}
			try
			{
				//creates reservation when no exceptions are thrown
				reservation = reservationManager.makeReservation(flightsList.getSelectedValue(), nameText.getText(), citizenshipText.getText());
				
			}
			catch(IOException j)
			{
				JOptionPane.showMessageDialog(null, "File not found");
			}
			//tells user reservation as created and their reservation code
			JOptionPane.showMessageDialog(null, "Reservation Created. Your code is " + reservation.getCode());
		}
	}
}
