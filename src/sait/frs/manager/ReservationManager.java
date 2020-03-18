package sait.frs.manager;

import sait.frs.gui.ReservationsTab;
import sait.frs.problemdomain.*;

import java.util.*;
import java.io.*;

public class ReservationManager {

	private ArrayList<Reservation> reservations; 
	
	public Reservation makeReservation(Flight flight, String name, String citizenship) throws IOException
		{
			RandomAccessFile file = new RandomAccessFile("res/reservation.bin","rw");
			
			String reserveCode = generateReservationCode(flight);
			String flightCode = flight.getCode();
			String airline = flight.getAirlineName();
			double cost = flight.getCostPerSeat();
			boolean active = true;
									
			file.seek(file.length());
			file.writeUTF(reserveCode); // 2+2*5 = 12
			file.writeUTF(flightCode); // 2+2*7 = 16 
			file.writeUTF(airline); // 2+2*2 = 6
			file.writeUTF(name); //
			file.writeUTF(citizenship);
			file.writeDouble(cost);
			file.writeBoolean(active);
			file.close();
			
			Reservation reservation = new Reservation(reserveCode, flightCode, airline, name, citizenship, cost, active);
			return reservation;
		}
	
	public ArrayList<Reservation> findReservations(String code, String airline, String name) throws IOException
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
				this.reservations.add(res);
						
				i = file.getFilePointer();
			}}
		
		file.close();
			
		return this.reservations;
		}

	public Reservation findReservationByCode(String code) throws IOException {
		
		boolean check = false;
		int i =0;	
		while(i<reservations.size() && !check) {
			
			String rCode = reservations.get(i).getCode();
			
			if(rCode.equals(code))
			{
				check = true;
			}
		}
				
		return reservations.get(i);
	}
	
	public void persist() throws IOException
	{
		RandomAccessFile file = new RandomAccessFile("res/reservation.bin","rw");
		int dataSize = 0;
		
		file.seek(file.getFilePointer() - dataSize);
		
		ReservationsTab.findR.getCode();
			
		
		file.close();
	}
	
	private int getAvailableSeats(Flight flight)
	{
		return flight.getSeats();
	}
	
	private String generateReservationCode(Flight flight)
	{
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