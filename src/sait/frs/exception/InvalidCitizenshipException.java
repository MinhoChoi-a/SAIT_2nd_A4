package sait.frs.exception;

/**
 * This class displays an error message when the citizenship is invalid.
 * @author Minho Choi 812108 Section CC
 * @author Michael Doctor 820167 Section CCC
 * @version 1.0, March 20, 2020
 *
 */
public class InvalidCitizenshipException extends Exception
{
	/**
	 * The constructor calls the Exception classes constructor to display string as an error message
	 * if the citizenship is invalid.
	 */
	public InvalidCitizenshipException()
	{
		super("Citizenship is null or empty!");
	}
}
