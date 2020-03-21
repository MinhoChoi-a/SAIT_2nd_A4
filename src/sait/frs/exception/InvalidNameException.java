package sait.frs.exception;

/**
 * This class displays an error message when the name is invalid.
 * @author Minho Choi 812108 Section CC
 * @author Michael Doctor 820167 Section CCC
 * @version 1.0, March 20, 2020
 *
 */
public class InvalidNameException extends Exception
{
	/**
	 * The constructor calls the Exception classes constructor to display string as an error message 
	 * if the name is invalid.
	 */
	public InvalidNameException()
	{
		super("Name is null or empty!");
	}
}
