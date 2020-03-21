package sait.frs.problemdomain;

/**
 * This class creates a Flight object.
 * 
 * @author Minho Choi 812108 Section CC
 * @author Michael Doctor 820167 Section CCC
 * @version 1.0, March 20, 2020
 *
 */
public class Flight
{
	private String code, 
				   airlineName, 
				   from, 
				   to, 
				   weekday, 
				   time;
	private int seats;
	private double costPerSeat;

	/**
	 * No argument constructor.
	 */
	public Flight()
	{

	}

	/**
	 * Flight constructor with arguments.
	 * @param code Flight's code.
	 * @param from Departing airport code.
	 * @param to Arrival airport code.
	 * @param weekday Day of flight.
	 * @param time Time of flight.
	 * @param seats Number of available seats.
	 * @param costPerSeat Cost of flight seat.
	 */
	public Flight(String code, String from, String to, String weekday, String time, int seats, double costPerSeat)
	{
		this.code = code;
		this.from = from;
		this.to = to;
		this.weekday = weekday;
		this.time = time;
		this.seats = seats;
		this.costPerSeat = costPerSeat;

		parseCode(code);
	}

	/**
	 * Get Flight Code.
	 * 
	 * @return the flight code.
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * Get airline name.
	 * 
	 * @return the name of the airline.
	 */
	public String getAirlineName()
	{
		return airlineName;
	}

	/**
	 * Get departing airport code.
	 * 
	 * @return the departing airport code.
	 */
	public String getFrom()
	{
		return from;
	}

	/**
	 * Get arrival airport code.
	 * 
	 * @return the arrival airport code.
	 */
	public String getTo()
	{
		return to;
	}

	/**
	 * Get weekday.
	 * 
	 * @return the day of the flight.
	 */
	public String getWeekday()
	{
		return weekday;
	}

	/**
	 * Get time of flight.
	 * 
	 * @return the time of the flight.
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * Get number of seats available.
	 * 
	 * @return the number of available seats.
	 */
	public int getSeats()
	{
		return seats;
	}
	

	
	public int updateSeats(boolean remove)
	{
		if(remove)
		{
			this.seats -= 1;
		}
		else
		{
			this.seats += 1;
		}

		return seats;
	}

	/**
	 * Get cost of a seat.
	 * 
	 * @return the cost of a seat.
	 */
	public double getCostPerSeat()
	{
		return costPerSeat;
	}

	/**
	 * This method checks if a flight is domestic.
	 * @return a boolean if the flight is within Canada.
	 */
	public boolean isDomestic()
	{
		boolean domestic;

		if (this.from.charAt(0) == 'Y' && this.from.charAt(0) == this.to.charAt(0))
		{
			domestic = true;
		} else
		{
			domestic = false;
		}
		return domestic;

	}

	/**
	 * This method converts the flight code to the full airline name.
	 * @param code Flight's code.
	 */
	private void parseCode(String code)
	{
		if (code.substring(0, 2).equals("OA"))
		{
			this.airlineName = "Otto Airlines";
		} else if (code.substring(0, 2).equals("CA"))
		{
			this.airlineName = "Conned Air";
		} else if (code.substring(0, 2).equals("TB"))
		{
			this.airlineName = "Try a Bus Airways";
		} else if (code.substring(0, 2).equals("VA"))
		{
			this.airlineName = "Vertical Airlines";
		} else
		{
			this.airlineName = "CPRG Airlines";
		}
	}

	/**
	 * This method returns the data of the flight.
	 * @return The flight's data.
	 */
	public String toString()
	{
		String toString;
		toString = String.format("%s, From: %s, To: %s, Day: %s, Cost: %.2f", code, from, to, weekday, costPerSeat);
		return toString;
	}

}
