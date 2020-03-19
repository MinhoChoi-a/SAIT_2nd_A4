package sait.frs.manager;

import sait.frs.gui.ReservationsTab;
import sait.frs.problemdomain.*;

import java.util.*;
import java.io.*;

public class ReservationManager {

	private ArrayList<Reservation> reservations = new ArrayList<Reservation>();	
	
	public Reservation makeReservation(Flight flight, String name, String citizenship) throws IOException
		{
			RandomAccessFile file = new RandomAccessFile("res/reservation.bin","rw");
			
			String reserveCode = generateReservationCode(flight);
			String flightCode = flight.getCode();
			String airline = flight.getAirlineName();
			double cost = flight.getCostPerSeat();
			boolean active = true;
									
			file.seek(file.length());
			file.writeUTF(reserveCode); // 2+5 = 7
			file.writeUTF(flightCode); // 2+7 = 9 
			file.writeUTF(airline); // 2+2 = 4
			file.writeUTF(name); //
			file.writeUTF(citizenship);
			file.writeDouble(cost);
			file.writeBoolean(active);
			file.close();
			
			Reservation reservation = new Reservation(reserveCode, flightCode, airline, name, citizenship, cost, active);
			return reservation;
		}
	
	
	public ArrayList<Reservation> findReservation(String code, String airline, String name) throws IOException
	{
		RandomAccessFile file = new RandomAccessFile("res/reservation.bin","r");
				
		file.seek(0);
		
		String rCode="";
		String fCode;
		String aline="";
		String rName;
		String rCitizen;
		double rCost;
		boolean act;
		
		long i=0;	
		while(i<file.length()) {
			
			rCode = file.readUTF();
			fCode = file.readUTF(); 
			aline = file.readUTF(); 
			rName = file.readUTF(); 
			rCitizen = file.readUTF();
			rCost = file.readDouble();
			act = file.readBoolean();
			
			if(code.equals(rCode) || airline.equals(aline) || name.equals(rName))
			{
				Reservation res = new Reservation(rCode,fCode,aline,rName,rCitizen,rCost, act);
				reservations.add(res);
			} 
			i = file.getFilePointer();	
		}
		file.close();
			
		return reservations;
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
		RandomAccessFile file = new RandomAccessFile("res/reservation.bin","rw");
		file.seek(0);
		
		String reserveCode = ReservationsTab.findR.getCode(); // 2+2*5 = 12
		String name = ReservationsTab.findR.getName(); // 
		String citizenship = ReservationsTab.findR.getCitizenship(); //
		boolean active = ReservationsTab.findR.isActive(); // 1
		
		boolean check = false;
		long d=0;	
		while(d<file.length() && !check) {
			
			String rCodeCheck = file.readUTF();
			String fCode = file.readUTF(); 
			String aline = file.readUTF(); 
			d = file.getFilePointer();
			
			if(!reserveCode.equals(rCodeCheck))
			{
			String rName = file.readUTF(); 
			String rCitizen = file.readUTF();
			double rCost = file.readDouble();
			boolean act = file.readBoolean();
			}
					
			else
			{
			check = true;
			}
			}
			
		file.seek(d); //position to the name
		
		file.writeUTF(name);
		file.writeUTF(citizenship);
		
		if(active==false)
		{
			//soft delete
		}
		
		file.close();
			
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
	
	private void populateFromBinary()
	{
		
	}
}