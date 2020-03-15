package sait.frs.manager;

import java.util.*;
import sait.frs.problemdomain.*;
import java.io.*;

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
	
	public FlightManager()
	{
		this.airports = new ArrayList<>();
		
		this.flights = new ArrayList<>();
	}
	
	public ArrayList<String> getAirports() throws IOException
	{
		populateAirports();
		System.out.print(airports.get(0));
		return airports;
	}
	
	public ArrayList<Flight> getFlights() throws IOException
	{
		populateFlights();
		return flights;
	}
	
	public String findAirportByCode(String code)
	{
		
	}
	
	public Flight findFlightByCode(String code)
	{
		
	}
	
	public ArrayList<Flight> findFlights(String from, String to, String weekday)
	{
		
	}
	
	private void populateFlights() throws IOException
	{
		String code, 
			   airlineName,
			   from,
			   to,
			   weekday,
			   time;
		int seats;
		double costPerSeat;
		
		File file = new File("res/flights.csv");//FIX LATER
		Scanner flightRead = new Scanner(file);
		flightRead.useDelimiter(",");
		while(flightRead.hasNext())
		{
			code = flightRead.next();
			airlineName = flightRead.next();
			from = flightRead.next();
			to = flightRead.next();
			weekday = flightRead.next();
			time = flightRead.next();
			seats = flightRead.nextInt();
			costPerSeat = Double.parseDouble(flightRead.nextLine().substring(1));
			
			Flight flight = new Flight(code, airlineName,from,to,weekday,time,seats,costPerSeat);
			flights.add(flight);
			
		}
		flightRead.close();
	}
	
	//fix exceptions
	private void populateAirports() throws IOException
	{
		String line,
			   airport;
		
		this.airports = new ArrayList<>();
		
		File file = new File("res/airports.csv");
		Scanner read = new Scanner(file);
		
		
		while(read.hasNext())
		{
			line = read.nextLine();
			airport = line.substring(0, 3);
			airports.add(airport);
		}
		read.close();
		
	}

}
