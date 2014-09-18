/**
 * 
 */
package com.cognizant.http.utils;

/**
 * This is class is custom exception class. This exception should be thrown when no
 * Internet available.
 * 
 * @author Ravi Bhojani
 *
 */
public class NoNetworkAvailableException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoNetworkAvailableException() {
		super("There is no internet network available.");
	}
}
