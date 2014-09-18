/**
 * 
 */
package com.cognizant.http;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;

import android.content.Context;

import com.cognizant.http.utils.NetworkAccessUtil;
import com.cognizant.http.utils.NoNetworkAvailableException;


/**
 * @author Ravi Bhojani
 *
 */
public class ServiceCall {

	public void execute(ServiceBean serviceBean, Context context)
	{	
		try 
		{
			if(context != null && !serviceBean.isStopNetworkCheck())
			{
				if(NetworkAccessUtil.isNetworkAvailable(context))
				{
					HttpConnection httpConnection = new HttpConnection();
					httpConnection.connect(serviceBean);
				}
				else
				{
					throw new NoNetworkAvailableException();
				}
			}
			else
			{
				HttpConnection httpConnection = new HttpConnection();
				httpConnection.connect(serviceBean);
			}
		} 
		catch (SocketTimeoutException e) 
		{
			serviceBean.getResponseEventListener().onException(e);
		} 
		catch (MalformedURLException e)
		{
			serviceBean.getResponseEventListener().onException(e);
		} 
		catch (IOException e) 
		{
			serviceBean.getResponseEventListener().onException(e);
		} 
		catch (NoNetworkAvailableException e) 
		{
			serviceBean.getResponseEventListener().onException(e);
		}
	}
}
