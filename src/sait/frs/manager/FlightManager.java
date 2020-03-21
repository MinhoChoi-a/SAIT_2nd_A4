package sait.frs.manager;

import java.util.*;
import sait.frs.problemdomain.*;
import java.io.*;
import sait.frs.application.*;

/**
 * This class manages the flights.
 * 
 * @author Minho Choi 812108 Section CC
 * @author Michael Doctor 820167 Section CCC
 * @version 1.0, March 20, 2020
 *
 */
public class FlightManager
{
	public final static String WEEKDAY_ANY = "Any",
							   WEEKDAY_SUNDAY = "Sunday",
						       WEEKDAY_MONDAY = "Monday",
						       WEEKDAY_TUESDAY = "Tuesday",
						       WEEKDAY_WEDNESDAY = "Wednesday",
						       WEEKDAY_THURSDAY = "Thursday",
						       WEEKDAY_FRIDAY = "Friday",
						       WEEKDAY_SATURDAY = "Saturday";
	
	private ArrayList<Flight> flights;
	private ArrayList<String> airports;
	private String location;
	
	/**
	 * This constructor method instantiates two new ArrayLists and calls the getFlights method.
	 * @throws IOException
	 */
	public FlightManager(String location) throws IOException
	{
		this.location = location;
		this.airports = new ArrayList<>();
		this.flights = new ArrayList<>();
		populateAirports();
		populateFlights();
	}
	
	/**
	 * This method populates the airports ArrayList and returns it.
	 * @return The airports ArrayList.
	 * @throws IOException
	 */
	public ArrayList<String> getAirports() throws IOException
	{
		return airports;
	}
	
	/**
	 * This method populates the flights ArrayList and returns it.
	 * @return The flights ArrayList.
	 * @throws IOException
	 */
	public ArrayList<Flight> getFlights() throws IOException
	{
		return flights;
	}
	
	/**
	 * This method returns the airport name based on the airport code.
	 * @param code Flight's code.
	 * @return The full name of the airport.
	 */
	public String findAirportByCode(String code)
	{
		return airports.get(airports.indexOf(code) + 1);
	}
	
	/**
	 * This method finds a flight based on the provided flight code.
	 * @param code Flight's code.
	 * @return The flight with that specific flight code.
	 */
	public Flight findFlightByCode(String code)
	{
		int position = 0;
		for(int i = 0;i<flights.size();i++)
		{
			if (code == flights.get(i).getCode())
			{
				position = i;
				i = flights.size();
			}
		}
		return flights.get(position);
	}
	
	/**
	 * This method finds matching flights based on the departing airport, arrival airport, and day of flight. 
	 * The matching airports 
	 * @param from Departing airport code.
	 * @param to Arrival airport code.
	 * @param weekday The day of the flight.
	 * @return An ArrayList containing matching flights.
	 */
	public ArrayList<Flight> findFlights(String from, String to, String weekday)
	{
		ArrayList<Flight> flightsList = new ArrayList<>();
		
		
		for(int i = 0; i<this.flights.size();i++)
		{
			if(this.flights.get(i).getFrom().equals(from) && this.flights.get(i).getTo().equals(to) && flights.get(i).getWeekday().equals(weekday))
			{
				flightsList.add(this.flights.get(i));
			}
			else if(this.flights.get(i).getFrom().equals(from) && this.flights.get(i).getTo().equals(to) && weekday.equals(WEEKDAY_ANY))
			{
				flightsList.add(this.flights.get(i));
			}
		}
		return flightsList;
	}
	
	/**
	 * This method populates the flights ArrayList from the provided database.
	 * @throws IOException
	 */
	private void populateFlights() throws IOException
	{
		String code, 
			   from,
			   to,
			   weekday,
			   time;
		int seats;
		double costPerSeat;
		
		
		File file = new File(location+"/res/flights.csv");//FIX LATER
		Scanner flightRead = new Scanner(file);
		flightRead.useDelimiter(",");
		while(flightRead.hasNext())
		{
			code = flightRead.next();
			from = flightRead.next();
			to = flightRead.next();
			weekday = flightRead.next();
			time = flightRead.next();
			seats = flightRead.nextInt();
			costPerSeat = Double.parseDouble(flightRead.nextLine().substring(1));
			
			Flight flight = new Flight(code, from,to,weekday,time,seats,costPerSeat);
			flights.add(flight);
			
		}
		flightRead.close();
	}
	
	/**
	 * This method populates the airports ArrayList from the provided database.
	 * @throws IOException
	 */
	private void populateAirports() throws IOException
	{
		String code,
			   airport;
		this.airports = new ArrayList<>();
		
		File file = new File(location+"/res/airports.csv");
		Scanner read = new Scanner(file);
		read.useDelimiter(",");
		
		
		while(read.hasNext())
		{
			code = read.next();
			airport = read.nextLine().substring(1);
			airports.add(code);
			airports.add(airport);
		}
		read.close();
		
	}

}
