/**
 * 
 */
package com.cognizant.http.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This class determines if Internet is available or not. 
 * 
 * @author Ravi Bhojani
 *
 */
public class NetworkAccessUtil 
{
	/**
	 * This method will return true if device is connected with Internet, else it will return false. 
	 * Kindly add {@link android.permission.ACCESS_NETWORK_STATE} permission to AndroidManifest.xml before using this method.
	 * @param context
	 * @return boolean
	 */
	public static boolean isNetworkAvailable(Context context)
	{
		ConnectivityManager connectivityManager =  (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
		if (activeNetwork != null && activeNetwork.isConnected()) 
		{
			return true;
		} 
		else
		{
			return false;
		}
	}
}
