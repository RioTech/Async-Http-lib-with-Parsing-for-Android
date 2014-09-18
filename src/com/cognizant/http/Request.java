/**
 * 
 */
package com.cognizant.http;

import java.io.Serializable;
import java.util.Map;

import org.apache.http.protocol.HTTP;

/**
 * This class prepares Request object for the client. Using RequestBuilder, calling functionality can 
 * create customize request object.
 * 
 * @author Ravi Bhojani
 *
 */
public class Request {

	private String requestUrl;
	private int httpMethod;
	private Serializable requestData;
	private Map<String, String> requestHeader;
	private int connectionTimeout;
	private int readTimeout;
	
	
	public int getConnectionTimeout() {
		return connectionTimeout;
	}

	public int getReadTimeout() {
		return readTimeout;
	}

	public Map<String, String> getRequestHeader() {
		return requestHeader;
	}

	public String getRequestUrl() {
		return requestUrl;
	}
	
	public int getHttpMethod() {
		return httpMethod;
	}
	
	public Serializable getRequestData() {
		return requestData;
	}
	
	private Request(RequestBuilder requestBuilder)
	{
		this.requestUrl = requestBuilder.requestUrl;
		this.httpMethod = requestBuilder.httpMethod;
		this.requestData = requestBuilder.requestData;
		this.requestHeader = requestBuilder.requestHeader;
		this.connectionTimeout = requestBuilder.connectionTimeout;
		this.readTimeout = requestBuilder.readTimeout;
	}
	
	public static class RequestBuilder
	{
		private String requestUrl;
		private int httpMethod;
		private Serializable requestData;
		private Map<String, String> requestHeader;
		private int connectionTimeout = HttpConstant.Connection.CONNECTION_TIME_OUT_SECOND;
		private int readTimeout = HttpConstant.Connection.READ_TIME_OUT_SECOND;
		
		public RequestBuilder(String requestUrl) {
			this.requestUrl = requestUrl;
		}
		
		/**
		 * This method will set Http method type. Calling function can set Get {@link HttpConstant.GET_METHOD},
		 * Post {@link HttpConstant.POST_METHOD}, Put {@link HttpConstant.PUT_METHOD} and Delete {@link HttpConstant.DELETE_METHOD}.
		 * 
		 * If calling function set out side of this value then it will throw {@link IllegalArgumentException}
		 * @param httpMethod
		 * @return
		 */
		public RequestBuilder httpMethod(int httpMethod)
		{
			if(httpMethod == HttpConstant.GET_METHOD || httpMethod == HttpConstant.POST_METHOD
					|| httpMethod == HttpConstant.PUT_METHOD || httpMethod == HttpConstant.DELETE_METHOD)
			{
				this.httpMethod = httpMethod;
			}
			else
			{
				throw new IllegalArgumentException("Use either Get, Post, Put or Delete method");
			}
			return this;
		}
		
		/**
		 * Calling function can set object which needs to sent to the web server. Based on Configuration of {@link ServiceBean},
		 * request object can use Xml or Json to convert object into byte stream.   
		 *  
		 * @param requestData
		 * @return
		 */
		public RequestBuilder requestData(Serializable requestData)
		{
			this.requestData = requestData;
			return this;
		}
		
		/**
		 * This method will set {@link HTTP} header which needs to send server for processing. 
		 * @param requestHeader
		 * @return
		 */
		public RequestBuilder requestHeader(Map<String, String> requestHeader)
		{
			this.requestHeader = requestHeader;
			return this;
		}
		
		/**
		 * This method will set connection timeout. It's in second. By default it's value is {@link HttpConstant.Connection.CONNECTION_TIME_OUT_SECOND} 
		 * @param connectionTimeout
		 */
		public RequestBuilder connectionTimeout(int connectionTimeout)
		{
			this.connectionTimeout = connectionTimeout;
			return this;
		}
		
		/**
		 * This method will set read timeout. It's in second. By default it's value is {@link HttpConstant.Connection.READ_TIME_OUT_SECOND}
		 * @param readTimeout
		 */
		public RequestBuilder readTimeout(int readTimeout)
		{
			this.readTimeout = readTimeout;
			return this;
		}
		
		public Request build()
		{
			return new Request(this);
		}
	}
}
