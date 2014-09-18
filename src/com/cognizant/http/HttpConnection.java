/**
 * 
 */
package com.cognizant.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.Set;

import com.cognizant.http.jackson.JacksonObjectMapperHolder;
import com.cognizant.http.utils.HTTPUtil;

/**
 * @author Ravi Bhojani
 *
 */
public class HttpConnection {

	private final String LOG_TAG = this.getClass().getSimpleName();
	private HttpURLConnection urlConnection;
	private ServiceBean serviceBean;
	
	public void connect(ServiceBean serviceBean) throws ProtocolException, SocketTimeoutException, IOException
	{
		this.serviceBean = serviceBean;
		URL url = new URL(serviceBean.getRequest().getRequestUrl());
		urlConnection = (HttpURLConnection) url.openConnection();
		urlConnection.setConnectTimeout(serviceBean.getRequest().getConnectionTimeout() * 1000);
		urlConnection.setReadTimeout(serviceBean.getRequest().getReadTimeout() * 1000);
		
		//Setting up headers
		if(serviceBean.getRequest().getRequestHeader() != null)
		{
			Set<String> headerKeySet = serviceBean.getRequest().getRequestHeader().keySet();
			for(String key : headerKeySet)
			{
				HTTPUtil.log(LOG_TAG, "Header Key "+key+" value "+serviceBean.getRequest().getRequestHeader().get(key));
				urlConnection.setRequestProperty(key, serviceBean.getRequest().getRequestHeader().get(key));
			}
		}
		
		switch (serviceBean.getRequest().getHttpMethod())
		{
			case HttpConstant.GET_METHOD:
				urlConnection.setRequestMethod("GET");
				urlConnection.connect();
				break;
				
			case HttpConstant.POST_METHOD:
				urlConnection.setRequestMethod("POST");
				urlConnection.setDoOutput(true);
				
				if(serviceBean.getRequest().getRequestData() != null)
				{
					//Converting Object into stream
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			        JacksonObjectMapperHolder.getMapper().writeValue(byteArrayOutputStream,serviceBean.getRequest().getRequestData());
			        
			        //Opning connection
			        urlConnection.connect();
			        
			        //Writing data to stream
			        OutputStream outputStream = urlConnection.getOutputStream();
			        outputStream.write(byteArrayOutputStream.toByteArray());
			        outputStream.close();
				}
				else
				{
					//Opning connection
			        urlConnection.connect();
				}
				break;
				
			case HttpConstant.PUT_METHOD:
				urlConnection.setRequestMethod("PUT");
				urlConnection.setDoOutput(true);
				
				if(serviceBean.getRequest().getRequestData() != null)
				{
					//Converting Object into stream
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			        JacksonObjectMapperHolder.getMapper().writeValue(byteArrayOutputStream,serviceBean.getRequest().getRequestData());
			        
			        //urlConnection.setFixedLengthStreamingMode(byteArrayOutputStream.toByteArray().length);
			        //Opning connection
			        urlConnection.connect();
			        
			        //Writing data to stream
			        OutputStream outputStream = urlConnection.getOutputStream();
			        outputStream.write(byteArrayOutputStream.toByteArray());
			        outputStream.close();
				}
				else
				{
					//Opning connection
			        urlConnection.connect();
				}
				break;
			
			case HttpConstant.DELETE_METHOD:
				urlConnection.setRequestMethod("DELETE");
				urlConnection.connect();
				break;
				
			default:
				throw new IllegalArgumentException("Currently GET, POST, PUT and DELETE methods are only supported");
		}
		
		int statusCode = urlConnection.getResponseCode();
		
		HTTPUtil.log(LOG_TAG, " statusCode "+statusCode);
		
		if(statusCode == HttpURLConnection.HTTP_OK)
		{
			success(serviceBean,urlConnection);
		}
		else
		{
			error(serviceBean,urlConnection);
		}
	}
	/**
	 * In case of success response, this method will parsed the data and repose back to calling function
	 * @param serviceBean
	 * @param connection
	 * @throws IOException
	 */
	public void success(ServiceBean serviceBean, HttpURLConnection connection) throws IOException
	{
		if(connection.getInputStream() != null)
		{
			if(serviceBean.getSuccesParser() != null)
			{
				Serializable successDataHolder = serviceBean.getSuccesParser();
				
                successDataHolder = JacksonObjectMapperHolder.getMapper().readValue
                		(convertInputSteamToString(connection.getInputStream()),successDataHolder.getClass());
				serviceBean.getResponseEventListener().onSuccess(successDataHolder,connection.getHeaderFields());
			}
			else
			{
				serviceBean.getResponseEventListener().onSuccess(connection.getInputStream(),connection.getHeaderFields());
			}
		}
		else
		{
			serviceBean.getResponseEventListener().onSuccess(null,connection.getHeaderFields());
		}
	}
	
	/**
	 * In case of error response, this method will parse the error data and response back to calling function 
	 * @param serviceBean
	 * @param connection
	 * @throws IOException
	 */
	public void error(ServiceBean serviceBean, HttpURLConnection connection) throws IOException
	{
		//If there are any data then parse it and sending back to calling function
		if(connection.getInputStream() != null)
		{
			if(serviceBean.getErrorParser() != null)
			{
				Serializable errorDataHolder = serviceBean.getErrorParser();
				errorDataHolder = JacksonObjectMapperHolder.getMapper().readValue
						(convertInputSteamToString(connection.getInputStream()),errorDataHolder.getClass());
				serviceBean.getResponseEventListener().onError(errorDataHolder,connection.getResponseCode(),connection.getHeaderFields());
			}
			else
			{
				serviceBean.getResponseEventListener().onError(connection.getInputStream(),connection.getResponseCode(),connection.getHeaderFields());
			}
		}
		
		//If there are no data to parse it then send only response code and header values if any.
		else
		{
			serviceBean.getResponseEventListener().onError(null, connection.getResponseCode(), connection.getHeaderFields());
		}
	}
	
	/**
	 * This method converts {@link InputStream} to {@link String}
	 * @param inputStream
	 * @return
	 * @throws IOException
	 */
	public String convertInputSteamToString(InputStream inputStream) throws IOException
	{
		final BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        final StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) 
        {
            sb.append(line);
        }
        
        HTTPUtil.log(LOG_TAG, " response stream "+sb.toString());
        return sb.toString();
	}
}
