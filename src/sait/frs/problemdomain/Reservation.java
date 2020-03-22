package sait.frs.problemdomain;

/**
 * This class is the reservation class that store the reservation data
 * 
 * @author Minho Choi 812108 Section CC
 * @author Michael Doctor 820167 Section CCC
 * @version 1.0, March 22, 2020
 *
 */

public class Reservation {
	
	private String code; //reservation code
	private String flightCode; //flight code
	private String airline; //airline name
	private String name;
	private String citizenship;
	private double cost;
	private boolean active;
	
	//default constructor
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
	 * get the reservation code
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * get the flight code
	 * @return the flightCode
	 */
	public String getFlightCode() {
		return flightCode;
	}


	/**
	 * get the airline name
	 * @return the airline
	 */
	public String getAirline() {
		return airline;
	}

	/**
	 * get the name
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * change the name
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * get the citizenship
	 * @return the citizenship
	 */
	public String getCitizenship() {
		return citizenship;
	}

	/**
	 * change the citizenship
	 * @param citizenship the citizenship to set
	 */
	public void setCitizenship(String citizenship) {
		this.citizenship = citizenship;
	}

	/**
	 * get the cost
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	
	/**
	 * get the active information
	 * @return the active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * change the active
	 * @param active the active to set
	 */
	public void setActive(boolean active) {
		this.active = active;
	}
	
	/**
	 * make the field of reservation class to the string data
	 */
	public String toString() {
		String toString;
		toString = String.format("%s,%s,%s,%s,%s,%.2f,%b%n", code, flightCode, airline, name, citizenship,cost,active);
		return toString;
	}
}
