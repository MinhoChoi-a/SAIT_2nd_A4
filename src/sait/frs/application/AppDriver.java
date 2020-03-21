package sait.frs.application;

import java.io.*;
import java.util.Scanner;

import sait.frs.gui.*;

/**
 * This class is the main driver of the program. This class calls the methods in
 * the other sait.frs packages.
 * 
 * @author Minho Choi 812108 Section CC
 * @author Michael Doctor 820167 Section CCC
 * @version 1.0, March 1, 2020
 *
 */
public class AppDriver
{

	private String location;

	/**
	 * Constructor used to locate the parent folder of res.
	 * 
	 * @param keyboard Scanner using the standard input.
	 */
	public AppDriver(Scanner keyboard)
	{
		System.out.print("Enter path to the folder containing the jar file: ");
		String temp = keyboard.nextLine();
		location = temp.replace("\\", "/");

	}

	/**
	 * Returns user provided path
	 * 
	 * @return location path to parent folder of res.
	 */
	public String getLocation()
	{
		return location;
	}

	/**
	 * Entry point to Java application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) throws IOException // ADD CUSTOM
	{
		MainWindow mainWindow = new MainWindow();
		mainWindow.display();
	}

}
