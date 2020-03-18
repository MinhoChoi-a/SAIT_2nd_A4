package sait.frs.problemdomain;

public class Flight {
	private String code, 
				   airlineName,
				   from,
				   to,
				   weekday,
				   time;
	private int seats;
	private double costPerSeat;
	
	public Flight()
	{
		
	}
	
	public Flight(String code, String from, String to, String weekday, String time,
			int seats, double costPerSeat)
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
	 * @return the code
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * Get airline name.
	 * @return the airlineName
	 */
	public String getAirlineName()
	{
		return airlineName;
	}

	/**
	 * Get departing airport code.
	 * @return the from
	 */
	public String getFrom()
	{
		return from;
	}

	/**
	 * Get arrival airport code.
	 * @return the to
	 */
	public String getTo()
	{
		return to;
	}

	/**
	 * Get weekday.
	 * @return the weekday
	 */
	public String getWeekday()
	{
		return weekday;
	}

	/**
	 * Get time of flight.
	 * @return the time
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * Get number of seats available.
	 * @return the seats
	 */
	public int getSeats()
	{
		return seats;
	}

	/**
	 * Get cost of a seat.
	 * @return the costPerSeat
	 */
	public double getCostPerSeat()
	{
		return costPerSeat;
	}
	
	public boolean isDomestic()
	{
		boolean domestic;
		
		if(this.from.charAt(0) == 'Y' && this.from.charAt(0) == this.to.charAt(0))
		{
			domestic = true;
		}
		else
		{
			domestic = false;
		}
		return domestic;
		
	}
	
	private void parseCode(String code)
	{
		if(code.substring(0, 2).equals("OA"))
		{
			this.airlineName = "Otto Airlines";
		}
		else if(code.substring(0, 2).equals("CA"))
		{
			this.airlineName = "Conned Air";
		}
		else if(code.substring(0, 2).equals("TB"))
		{
			this.airlineName = "Try a Bus Airways";
		}
		else if(code.substring(0, 2).equals("VA"))
		{
			this.airlineName = "Vertical Airlines";
		}
		else 
		{
			this.airlineName = "CPRG Airlines";
		}
	}

	
	public String toString()
	{
		String toString;
		toString = String.format("%s, From: %s, To: %s, Day: %s, Cost: %.2f", code, from, to, weekday, costPerSeat);
		return toString;
	}
	


}
