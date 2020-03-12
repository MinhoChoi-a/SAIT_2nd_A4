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
			
			file.writeUTF(reserveCode); //
			file.writeUTF(flightCode);
			file.writeUTF(airline);
			file.writeUTF(name);
			file.writeUTF(citizenship);
			file.writeDouble(cost);
			
			return Reservation(reserveCode, flightCode, airline, name, citizenship, cost, active);
		}
	
	
	public ArrayList<Reservation> findReservation(String code, String airline, String name)
	{
		
	}
	}


