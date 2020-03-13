package sait.frs.manager;

import sait.frs.problemdomain.*;

import java.util.*;
import java.io.*;

public class ReservationManager {

	private ArrayList<Reservation> reservations; 
	
	public Reservation makeReservation(Flight flight, String name, String citizenship) throws IOException
		{
			RandomAccessFile file = new RandomAccessFile("res/reservation.bin","rw");
			
			String from = flight.getFrom();
			
			boolean check = false;
			String[] domestic = {"YYC","YEG","YUL","YOW","YYZ","YVR","YWG"}; 
						
			Random rand = new Random();
			
			int randomNum = rand.nextInt(9000)+1000;
			
			int i=0;
			while(!check || (i < domestic.length))
			{
				if(from.equals(domestic[i]))
				{
					check=true;
				}
				i++;
			}
						
			String reserveCode;
			
			if(check==true)
			{
				reserveCode=String.format("%c%d", "D",randomNum);
			}
			
			else
			{
				reserveCode=String.format("%c%d", "I",randomNum);
			}
			
			
			String flightCode = flight.getCode();
			String airline = flight.getAirline();
			double cost = flight.getCostPerSeat();
			boolean active = true;
			
			file.seek(file.length());
			file.writeUTF(reserveCode); // 2+2*5 = 12
			file.writeUTF(flightCode); // 2+2*7 = 16 
			file.writeUTF(airline); // 2+2*2 = 6
			file.writeUTF(name); //
			file.writeUTF(citizenship);
			file.writeDouble(cost);
			file.close();
			
			return Reservation(reserveCode, flightCode, airline, name, citizenship, cost, active);
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
		
		ArrayList<Reservation> checkRes = new ArrayList<Reservation>();
				
		if(code != null)
		{
			int i = 0;
			
			while(i<file.length()) {
			
			rCode = file.readUTF();
			
			if(code.equals(rCode))
			{
				fCode = file.readUTF(); 
				aline = file.readUTF(); 
				rName = file.readUTF(); 
				rCitizen = file.readUTF();
				rCost = file.readDouble();
				
				Reservation res = new Reservation(rCode,fCode,aline,rName,rCitizen,rCost);
				checkRes.add(res);			
			}
			else
			{
				file.readUTF(); 
				file.readUTF(); 
				file.readUTF(); 
				file.readUTF();
				file.readDouble();
			}
			}
		}
		
		else if(aline != null) {}
		
		
		
	}
	}


