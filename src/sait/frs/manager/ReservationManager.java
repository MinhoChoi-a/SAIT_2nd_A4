package sait.frs.manager;

import sait.frs.gui.ReservationsTab;
import sait.frs.problemdomain.*;

import java.util.*;
import java.io.*;
/**
 * This class is the back end system for reservation tab of program
 * 
 * @author Minho Choi 812108 Section CC
 * @author Michael Doctor 820167 Section CCC
 * @version 1.0, March 22, 2020
 *
 */
public class ReservationManager {

	private ArrayList<Reservation> reservations  = new ArrayList<Reservation>();
	private String location; //To set the location of jar file
	
	/**
	 * This constructor is for managing the location data
	 * to let users can open jar file from everywhere 
	 * 
	 * @param location the location information of jar file
	 */
	
	public ReservationManager(String location)
	{
		this.location = location;
	}
	
	
	/**
	 * With this method, users can load the selected data from program and save it to the RAF.
	 * Also, it updates the available seats number
	 *  
	 * @param flight Flight class instance
	 * @param name the name what users input
	 * @param citizenship the citizenship information what users input
	 * @return reservation Reservation class instance
	 * @throws IOException
	 */
	 
	public Reservation makeReservation(Flight flight, String name, String citizenship) throws IOException
	{
		//make random access file	
		RandomAccessFile file = new RandomAccessFile(location+"/res/reservation.bin","rw");
			
			file.seek(file.length()); // end of file
			
			String reserveCode = generateReservationCode(flight);
			String flightCode = flight.getCode();
			String airline = flight.getAirlineName();
			double cost = flight.getCostPerSeat();
			String active = "active"; // show the reservation status
			boolean keep = true; // flag data for soft delete
			
			file.writeUTF(reserveCode);
			file.writeUTF(flightCode); 
			file.writeUTF(airline);
			file.writeUTF(name);
			file.writeUTF(citizenship);
			file.writeDouble(cost);
			file.writeUTF(active);
			file.writeBoolean(keep);
			
			file.close();
			
			// Reservation class instance
			Reservation reservation = new Reservation(reserveCode, flightCode, airline, name, citizenship, cost, keep);
						
			// edit available seats information
			FlightManager fManager = new FlightManager(location);
			ArrayList<Flight> fList = fManager.getFlights();
			
			PrintWriter write = new PrintWriter(location+"/res/flights.csv"); //overwrite file with update
			
			// update seats number by using updateSeats method 
			for(int i =0; i< fList.size(); i++)
			{
				if((fList.get(i).getCode()).equals(flightCode))
				{
					fList.get(i).updateSeats(true);
					write.write(fList.get(i).toString());
				}
				else
				{
					write.write(fList.get(i).toString());
				}
			}
			write.close();
			
			return reservation;
	}
	
	/**
	 * With this method, users can find the reservation from reservations array list.
	 * This array list is populated from random access file by using populateBinary()
	 * 
	 * @param code reservation code
	 * @param airline airline name
	 * @param name reserved name
	 * @return findReserve the array list of Reservation object which users want to check
	 * @throws IOException
	 */
		
	public ArrayList<Reservation> findReservation(String code, String airline, String name) throws IOException
	{
		ArrayList<Reservation> findReserve = new ArrayList<>();
		
		//load all of reservation information and add them to array list
		populateBinary();
		
		//find the reservation what users want to check
		for(int i=0; i<reservations.size(); i++) {
			String rCode = reservations.get(i).getCode();
			String fCode = reservations.get(i).getFlightCode();
			String aline = reservations.get(i).getAirline(); 
			String rName = reservations.get(i).getName(); 
			String rCitizen = reservations.get(i).getCitizenship();
			double rCost = reservations.get(i).getCost();
			boolean act = reservations.get(i).isActive();
			
			if(code.equals(rCode) || airline.equals(aline) || name.equals(rName))
			{
				Reservation res = new Reservation(rCode,fCode,aline,rName,rCitizen,rCost, act);
				findReserve.add(res);
			} 
				
		}
					
		return findReserve;
		}

	/**
	 * This method helps to show the reservation information on screen
	 * when users select the reservation code on the program window
	 * 
	 * @param code the selected reservation code
	 * @return the selected object from reservations arraly list
	 */
		
	public Reservation findReservationByCode(String code) {
		
		//the flag to check data 
		boolean check = false;
		
		int i =0;	
		while(i<reservations.size() && !check) {
			
			String rCode = reservations.get(i).getCode();
			
			if(rCode.equals(code))
			{
				check = true;
			}
			i++;
		}
					
		return reservations.get(i-1);
	}
	
	
	/**
	 * With this method, users can save the updated reservation information to random access file
	 * 
	 * @throws IOException
	 */
	public void persist() throws IOException
	{
		RandomAccessFile file = new RandomAccessFile(location+"/res/reservation.bin","rw");
		file.seek(0);
		
		String reserveCode = ReservationsTab.findR.getCode(); //reservation code
		String name = ReservationsTab.findR.getName(); //updated name
		String citizenship = ReservationsTab.findR.getCitizenship(); //updated citizenship
		boolean active = ReservationsTab.findR.isActive(); //updated active
		String act=""; //to save the reservation status on RAF
		
		if(active==true)
		{
			act="active";
		}
		
		else
		{
			act="inacitve";
		}
		
		
		//check binary data and find the location of data what we want to update
		boolean check = false;
		long d=0;	
		while(d<file.length() && !check) {
			
			String rCodeCheck = file.readUTF();
			file.readUTF(); //flight code 
			file.readUTF(); //airline 
			file.readUTF(); //name 
			file.readUTF(); //citizenship
			file.readDouble(); //cost
			file.readUTF(); //active
			check = file.readBoolean(); //deleted or not
			
			d = file.getFilePointer();
			
			if(reserveCode.equals(rCodeCheck))
			{
				if(check) {
				file.seek(d-1);
				file.writeBoolean(false); //delete the data
				}
			}
			else
			{
				check=false;
			}
			
		}
		
		file.seek(file.length()); //to the end of file
		
		//add updated data
		file.writeUTF(reserveCode);
		file.writeUTF(ReservationsTab.findR.getFlightCode());
		file.writeUTF(ReservationsTab.findR.getAirline());
		file.writeUTF(name);
		file.writeUTF(citizenship);
		file.writeDouble(ReservationsTab.findR.getCost());
		file.writeUTF(act);
		file.writeBoolean(true);
		
		file.close();
		
		//update the seats number and overwrite the flight.csv
		if(active==false)
		{
			FlightManager fManager = new FlightManager(location);
			
			ArrayList<Flight> fList = fManager.getFlights();
			
			PrintWriter write = new PrintWriter(location+"/res/flights.csv");
			
			for(int i =0; i< fList.size(); i++)
				
				if(fList.get(i).getCode().equals(ReservationsTab.findR.getFlightCode()))
				{
					fList.get(i).updateSeats(false);
					write.write(fList.get(i).toString());
				}
				else
				{
					write.write(fList.get(i).toString());
				}
			write.close();	
		}
		
	}	
	
	/**
	 * Users can get the information of available seats number
	 * 
	 * @param flight the instance of Flight class
	 * @return the number of seats
	 */
	private int getAvailableSeats(Flight flight)
	{
		return flight.getSeats();
	}
	
	/**
	 * This method generate the reservation code.
	 * The code has 2 types based on diffrence between domestic and international 
	 * 
	 * @param flight the selected Flight object
	 * @return reserveCode(String) the reservation code
	 */
		
	private String generateReservationCode(Flight flight)
	{
		// check if the flight is domestic or not
		boolean check = flight.isDomestic();
		
		
		Random rand = new Random();
		int randomNum = rand.nextInt(9000)+1000;
							
		String reserveCode;
		if(check==true)
		{
			reserveCode=String.format("%c%d", 'D',randomNum);
		}
		
		else
		{
			reserveCode=String.format("%c%d", 'I',randomNum);
		}
		
		return reserveCode;
	}
	
	/**
	 * With this method, users can load reservation information from the random access file
	 * By loading the data, it adds each reseravtion to the resrvations array list
	 * 
	 * @throws IOException
	 */
	
	private void populateBinary() throws IOException
	{
		reservations.clear();
		RandomAccessFile file = new RandomAccessFile(location+"/res/reservation.bin","r");
		
		file.seek(0);
		
		long i = 0;
		boolean active = true;
		
		// load data from binary(RAF)
			while(i < file.length()) {
				String rCode = file.readUTF();
				String fCode = file.readUTF(); 
				String aline = file.readUTF();
				String name = file.readUTF();
				String citizen = file.readUTF();
				Double cost = file.readDouble(); 
				String act = file.readUTF();
				
				//transfer the active information from string data to boolean
				if(act.equals("active"))
				{
					active = true;	
				}
				else
				{
					active = false;
				}
				
				Boolean b = file.readBoolean(); //the flag to check if it is deleted or not 
				
				if(!b)
				{
					i=file.getFilePointer();
				}
				else
				{
					Reservation reserve = 
							new Reservation(rCode,fCode,aline,name,citizen,cost,active);
					
					reservations.add(reserve);
					
					i=file.getFilePointer();
				}
				
				
				}
			file.close();
		}
		
}
