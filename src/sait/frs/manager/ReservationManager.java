package sait.frs.manager;

import sait.frs.gui.ReservationsTab;
import sait.frs.problemdomain.*;

import java.util.*;
import java.io.*;

public class ReservationManager {

	private ArrayList<Reservation> reservations = new ArrayList<Reservation>();	
	
	public Reservation makeReservation(Flight flight, String name, String citizenship) throws IOException
		{
			FileOutputStream f = new FileOutputStream("res/reservation.bin", true);
			ObjectOutputStream file = new ObjectOutputStream(f);
				
			String reserveCode = generateReservationCode(flight);
			
			String flightCode = flight.getCode();
			String airline = flight.getAirlineName();
			double cost = flight.getCostPerSeat();
			boolean active = true;
			
			Reservation reservation = new Reservation(reserveCode, flightCode, airline, name, citizenship, cost, active);
			
			try {
			
			file.writeObject(reserveCode);
			file.writeObject(reservation);
			file.close();
			}
			
			catch (FileNotFoundException e) {
				System.out.println("File not found");
			} catch (IOException e) {
				System.out.println("Error initializing stream");
			}
			
			return reservation;
		}
	
	public ArrayList<Reservation> findReservation(String code, String airline, String name) throws IOException
	{
		ArrayList<Reservation> findReserve = new ArrayList<>();
		
		populateFromBinary();
				
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

	public Reservation findReservationByCode(String code) {
				
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
	
	public void persist() throws IOException
	{
		FileOutputStream nBin = new FileOutputStream("res/reservation.bin", false);
		ObjectOutputStream nFile = new ObjectOutputStream(nBin);
	
		String reserveCode = ReservationsTab.findR.getCode(); // 2+2*5 = 12
		String name = ReservationsTab.findR.getName(); // 
		String citizenship = ReservationsTab.findR.getCitizenship(); //
		boolean active = ReservationsTab.findR.isActive(); // 1
		
		boolean check = false;
		int i=0;	
		while(i<reservations.size() && !check) {
			
			String rCodeCheck = reservations.get(i).getCode();
						
			if(reserveCode.equals(rCodeCheck))
			{
			check = true;
			}
					
			else
			{
			i++;
			}
		}
			
		reservations.get(i).setName(name);
		reservations.get(i).setCitizenship(citizenship);
		reservations.get(i).setActive(active);
				
		if(active==false)
		{
			//soft delete
		}
		
		try
		{
				for(int d=0; d<reservations.size(); d++)
				{
				nFile.writeObject(reservations.get(d).getCode());
				nFile.writeObject(reservations.get(d));
				}
		}
		
			catch (EOFException e) {
		    System.out.println("finnished writing");
		}
			catch (FileNotFoundException ex) {
	        System.err.println("Error: the file was not found!");
	    }
			catch (IOException ex) {
			ex.printStackTrace();
			}
		nFile.reset();
		nFile.close();
			
	}
	
	private int getAvailableSeats(Flight flight)
	{
		return flight.getSeats();
	}
	
	private String generateReservationCode(Flight flight)
	{
		
		String from = flight.getFrom();
		
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
	
	private void populateFromBinary() throws IOException
	{
		reservations.clear();
		FileInputStream bin = new FileInputStream("res/reservation.bin");
		ObjectInputStream file = new ObjectInputStream(bin);
		boolean eof = false;
		
		try
		{
			while(!eof) {
				file.readObject();
				Reservation reserve = (Reservation)file.readObject();
				reservations.add(reserve);
				
				}
		}
			catch (EOFException e) {
		    eof = true;
			System.out.println("finnished reading");
			file.close();
		}
			catch (ClassNotFoundException ex) {
	        ex.printStackTrace();
	    } 	
			catch (FileNotFoundException ex) {
	        System.err.println("Error: the file was not found!");
	    }
			catch (IOException ex) {
			ex.printStackTrace();
	    }
			
	}
}