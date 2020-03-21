package sait.frs.exception;

/**
 * This class displays an error message when the there's no more seats.
 * @author Minho Choi 812108 Section CC
 * @author Michael Doctor 820167 Section CCC
 * @version 1.0, March 20, 2020
 *
 */
public class NoMoreSeatsException extends Exception
{
	/**
	 * The constructor calls the Exception classes constructor to display string as an error message
	 * if there are no more seats available.
	 */
	public NoMoreSeatsException()
	{
		super("Number of sesats is less than or equal to 0");
	}
}
