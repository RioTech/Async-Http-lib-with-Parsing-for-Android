/**
 * 
 */
package com.cognizant.http.utils;

import android.util.Log;

import com.cognizant.http.ServiceBean;

/**
 * @author Ravi Bhojani
 *
 */
public class HTTPUtil {

	public static void log(String tag, String msg)
	{
		if(ServiceBean.showLog)
		{
			Log.d(tag, msg);
		}
	}
}
