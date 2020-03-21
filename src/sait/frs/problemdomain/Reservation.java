package sait.frs.problemdomain;

import java.io.*;
import java.util.*;

public class Reservation implements Serializable{
	
	private String code;
	private String flightCode;
	private String airline;
	private String name;
	private String citizenship;
	private double cost;
	private boolean active;
	
	public Reservation()
	{
		
	}
	
	
	
	public Reservation(String code, String flightCode, String airline, String name, String citizenship, double cost,boolean active)
	{
		this.code = code;
		this.flightCode = flightCode;
		this.airline = airline;
		this.name = name;
		this.citizenship = citizenship;
		this.cost = cost;	
		this.active = active;
		this.cost = cost;
		this.active = active;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return the flightCode
	 */
	public String getFlightCode() {
		return flightCode;
	}


	/**
	 * @return the airline
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the citizenship
	 */
	public String getCitizenship() {
		return citizenship;
	}

	/**
	 * @param citizenship the citizenship to set
	 */
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}

	/**
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	public String toString() {
		
		ArrayList<String> reserv = new ArrayList<String>();
		
		reserv.add(getCode());
		reserv.add(getFlightCode());
		reserv.add(getAirline());
		reserv.add(getName());
		reserv.add(getCitizenship());
		reserv.add(String.valueOf(getCost()));
		reserv.add(String.valueOf(isActive()));
		
		return reserv.toString();
	}
}
