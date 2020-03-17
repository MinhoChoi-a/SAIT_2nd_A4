package sait.frs.exception;

public class NullFlightException extends Exception
{
	public NullFlightException()
	{
		super("No flight is selected!");
	}
}
