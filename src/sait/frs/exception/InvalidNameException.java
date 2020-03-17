package sait.frs.exception;

public class InvalidNameException extends Exception
{
	public InvalidNameException()
	{
		super("Name is null or empty!");
	}
}
