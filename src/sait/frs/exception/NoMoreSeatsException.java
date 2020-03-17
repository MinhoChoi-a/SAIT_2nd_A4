package sait.frs.exception;

public class NoMoreSeatsException extends Exception
{
	public NoMoreSeatsException()
	{
		super("Number of sesats is less than or equal to 0");
	}
}
