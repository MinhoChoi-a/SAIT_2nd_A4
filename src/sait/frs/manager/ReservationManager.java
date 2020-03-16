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
			while(!check && (i < domestic.length))
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
			file.writeBoolean(active);
			file.close();
			
			Reservation rClass = new Reservation(reserveCode, flightCode, airline, name, citizenship, cost, active);
			return rClass;
		}
	
	
	public ArrayList<Reservation> findReservation(String code, String airline, String name) throws IOException
	{
		RandomAccessFile file = new RandomAccessFile("res/reservation.bin","r");
		RandomAccessFile rFile = new RandomAccessFile("res/fReserve.bin","rw");
		
		file.seek(0);
		
		String rCode="";
		String fCode;
		String aline="";
		String rName;
		String rCitizen;
		double rCost;
		boolean act;
		
		ArrayList<Reservation> checkRes = new ArrayList<Reservation>();
		
		long i =0;	
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
				checkRes.add(res);
				
				rFile.seek(rFile.length());
				rFile.writeUTF(rCode); // 2+2*5 = 12
				rFile.writeUTF(fCode); // 2+2*7 = 16 
				rFile.writeUTF(aline); // 2+2*2 = 6
				rFile.writeUTF(rName); //
				rFile.writeUTF(rCitizen);
				rFile.writeDouble(rCost);
				rFile.writeBoolean(act);
								
				i = file.getFilePointer();
			}}
		
		file.close();
		rFile.close();
		
		return checkRes;
		}

	public Reservation findReservationByCode(String code) throws IOException {
		
		RandomAccessFile rFile = new RandomAccessFile("res/fReserve.bin","r");
				
		rFile.seek(0);
		
		String rCode="";
		String fCode="";
		String aline="";
		String rName="";
		String rCitizen="";
		double rCost=0;
		boolean act=true;
		boolean check = false;
		
		long i =0;	
		while(i<rFile.length() && !check) {
			
			rCode = rFile.readUTF();
			fCode = rFile.readUTF(); 
			aline = rFile.readUTF(); 
			rName = rFile.readUTF(); 
			rCitizen = rFile.readUTF();
			rCost = rFile.readDouble();
			act = rFile.readBoolean();
						
			if(code.equals(rCode))
			{
				check = true;
			}
			
			i = rFile.getFilePointer();
		}
		rFile.close();
		
		Reservation reservation = new Reservation(rCode,fCode,aline,rName,rCitizen,rCost, act);
		return reservation;
	}
}









