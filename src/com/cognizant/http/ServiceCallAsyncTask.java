/**
 * 
 */
package com.cognizant.http;

import android.content.Context;
import android.os.AsyncTask;

/**
 * @author 328073
 *
 */
public class ServiceCallAsyncTask extends AsyncTask<Void, Void, Void>
{
	private Context context;
	private ServiceBean serviceBean;
	
	public ServiceCallAsyncTask(Context context, ServiceBean serviceBean) 
	{
		this.context = context;
		this.serviceBean = serviceBean;
	}
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		serviceBean.getResponseEventListener().onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... params) {
		
		ServiceCall call = new ServiceCall();
		call.execute(serviceBean, context);
		return null;
	}
}
