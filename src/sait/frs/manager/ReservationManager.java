package sait.frs.manager;

import sait.frs.gui.ReservationsTab;
import sait.frs.problemdomain.*;

import java.util.*;
import java.io.*;

public class ReservationManager {

	private ArrayList<Reservation> reservations;
	private String location;
	
	public ReservationManager(String location)
	{
		this.location = location;
		reservations = new ArrayList<Reservation>();	
	}
	
	public Reservation makeReservation(Flight flight, String name, String citizenship) throws IOException
	{
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
			
			Reservation reservation = new Reservation(reserveCode, flightCode, airline, name, citizenship, cost, keep);
			
			
			// edit available seats information
			FlightManager fManager = new FlightManager(location);
			ArrayList<Flight> fList = fManager.getFlights();
			
			PrintWriter write = new PrintWriter(location+"/res/flights.csv"); //overwrite file with update
			
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
	
	
	public ArrayList<Reservation> findReservation(String code, String airline, String name) throws IOException
	{
		ArrayList<Reservation> findReserve = new ArrayList<>();
		
		populateBinary();
				
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
		RandomAccessFile file = new RandomAccessFile(location+"/res/reservation.bin","rw");
		file.seek(0);
		
		String reserveCode = ReservationsTab.findR.getCode(); // 2+2*5 = 12
		String name = ReservationsTab.findR.getName(); // 
		String citizenship = ReservationsTab.findR.getCitizenship(); //
		boolean active = ReservationsTab.findR.isActive(); // 1
		String act=""; //show the reservation status
		
		if(active==true)
		{
			act="active";
		}
		
		boolean check = false;
		
		long d=0;	
		
		while(d<file.length() && !check) {
			
			String rCodeCheck = file.readUTF();
			String code = file.readUTF(); //fcode 
			String air= file.readUTF(); //airline 
			String n = file.readUTF(); //name 
			String c = file.readUTF(); //citizen
			double ct = file.readDouble(); //cost
			String ac = file.readUTF();
			d = file.getFilePointer();
			
			if(!reserveCode.equals(rCodeCheck))
			{
			boolean f= file.readBoolean();
			}
			else
			{
			check = true;
			file.seek(d);
			file.writeBoolean(false); //soft delete
			}
		}
		
		file.seek(file.length()); //to the end of file
		
		file.writeUTF(reserveCode);
		file.writeUTF(ReservationsTab.findR.getFlightCode());
		file.writeUTF(ReservationsTab.findR.getAirline());
		file.writeUTF(name);
		file.writeUTF(citizenship);
		file.writeDouble(ReservationsTab.findR.getCost());
		file.writeUTF(act);
		file.writeBoolean(true);
		
		file.close();
		
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
				
				if(act.equals("active"))
				{
					active = true;	
				}
				else
				{
					active = false;
				}
				
				Boolean b = file.readBoolean();
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
