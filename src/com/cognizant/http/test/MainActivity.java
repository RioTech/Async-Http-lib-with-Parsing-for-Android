package com.cognizant.http.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.cognizant.http.HttpConstant;
import com.cognizant.http.R;
import com.cognizant.http.Request;
import com.cognizant.http.ServiceBean;
import com.cognizant.http.ServiceCall;
import com.cognizant.http.ServiceCallAsyncTask;
import com.cognizant.http.listeners.ResponseEventListener;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//new MyTask().execute();
		
		Map<String, String> requestHeader = new HashMap<String, String>();
		
		requestHeader.put("Content-Type", "application/json");
		requestHeader.put("X-Application-Version", "5.6.0");
		requestHeader.put("X-Client-Platform", "Android");
		requestHeader.put("Content-Length", "126");
		requestHeader.put("X-DID", "f5674394f460bb8b7fe2d08de7342c7d92030e1c680f49861b9d83675737299f");
		requestHeader.put("X-OID", "5cf40c764558760306b61d192fa5f725d467afe7e677f514b7d7c4851990a4d5");
		requestHeader.put("X-SID", "241a8d1d58199c01b8db3625c8425d9f598bc78f849c41b0aa6134dd1df713e1");
		
		FastcheckToken  token = new FastcheckToken("eyJyleP3l/xqX1Ma+N+2lXMK9/ygaiUi5y08WxCwHid+N1/sMSfEEkBPMqIZzn6ZJ22YOoIgnE+US0wkYc33/Q==");
		
		Request request = new Request.RequestBuilder("https://mst0.mapi.discovercard.com/cardsvcs/acs/quickview/v1/view")
		.httpMethod(HttpConstant.POST_METHOD)
		.requestHeader(requestHeader)
		.requestData(token)
		.build();
		
		ServiceBean bean = new ServiceBean.ServiceBeanBuilder(request, new ResponseEventListener() {
			
			@Override
			public void onSuccess(Object data, Map<String, List<String>> responseHeader) {
				// TODO Auto-generated method stub
				Log.i("Ravi > ", "Success ");
				/*MopLatLongBean bean = (MopLatLongBean) data;
				Log.i("Ravi > ", "Success "+bean.results.size());
				Log.i("Ravi > ", "Success "+bean.results.get(0).geometry.location.lat+ " lan "+bean.results.get(0).geometry.location.lng);*/
				
				Set<String> headerKeySet = responseHeader.keySet();
				for(String key : headerKeySet)
				{
					Log.i("Ravi > ", "Header Key "+key+" value "+responseHeader.get(key));
				}
			}
			
			@Override
			public void onException(Throwable e) {
				Log.i("Ravi > ", "Exception  "+e.getMessage());
			}
			
			@Override
			public void onError(Object data, int httpStatus,
					Map<String, List<String>> responseHeader) {
				Log.i("Ravi > ", "Error ");
			}

			@Override
			public void onPreExecute() {
				// TODO Auto-generated method stub
				
			}
		}, null, null)
		.showLog(true)
		.build();
		
		ServiceCallAsyncTask asyncTask = new ServiceCallAsyncTask(this,bean);
		asyncTask.execute();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
