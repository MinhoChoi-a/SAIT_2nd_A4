package sait.frs.exception;

/**
 * This class displays an error message when a flight is null.
 * @author Minho Choi 812108 Section CC
 * @author Michael Doctor 820167 Section CCC
 * @version 1.0, March 20, 2020
 *
 */
public class NullFlightException extends Exception
{
	/**
	 * The constructor calls the Exception classes constructor to display string as an error message
	 * if no flight is selected.
	 */
	public NullFlightException()
	{
		super("No flight is selected!");
	}
}
