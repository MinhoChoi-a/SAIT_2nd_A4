package sait.frs.application;

import java.util.Scanner;
import java.io.*;
import sait.frs.gui.*;

/**
 * This class is the main driver of the program. This class calls
 * the methods in the other sait.frs packages.
 * @author Minho Choi 812108 Section CC
 * @author Michael Doctor 820167 Section CCC
 * @version 1.0, March 1, 2020
 *
 */
public class AppDriver {

	/**
	 * Entry point to Java application.
	 * @param args
	 */
	public static void main(String[] args) throws IOException //ADD CUSTOM
	{
		MainWindow mainWindow = new MainWindow();
		mainWindow.display();
	}

}
