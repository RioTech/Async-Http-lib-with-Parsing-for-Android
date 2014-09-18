/**
 * 
 */
package com.cognizant.http.listeners;

import java.util.List;
import java.util.Map;

/**
 * 
 * This listener will response back with success, error or exception event based on 
 * success, error or find any exception while executing http/https calls.  
 * 
 * @author Ravi Bhojani
 *
 */
public interface ResponseEventListener {

	/**
	 * If server responds with HTTP 200 status, parser will parse the success response and will send back to calling
	 * function with parsed data.
	 * @param data
	 */
	public void onSuccess(Object data, Map<String, List<String>> responseHeader);
	
	/**
	 * If server responds with HTTP not 200 status, which is error response,parser will parse the error response 
	 * and will send back to calling function with parsed error data.
	 * 
	 * @param data
	 */
	public void onError(Object data, int httpStatus, Map<String, List<String>> responseHeader);

	
	/**
	 * 
	 * While executing code, if there are any exception occurred, that exception will be send back to
	 * calling function with the throwable of that exception. 
	 * @param e
	 */
	public void onException(Throwable e);
	
	/**
	 * Before starting execution of HTTP in thread this method will be notified to calling function. This call runs in UI thread.
	 * So calling functionality can perform UI operation before starting of HTTP thread.  
	 */
	public void onPreExecute();
}
