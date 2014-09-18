/**
 * 
 */
package com.cognizant.http;

import java.io.Serializable;

import com.cognizant.http.listeners.ResponseEventListener;

/**
 * @author Ravi Bhojani
 *
 */
public class ServiceBean {

	private Request request;
	private ResponseEventListener responseEventListener;
	private boolean stopNetworkCheck = false;
	private Serializable succesParser;
	private Serializable errorParser;
	public static boolean showLog;
	
	public boolean isShowLog() {
		return showLog;
	}
	public Serializable getSuccesParser() {
		return succesParser;
	}
	public Serializable getErrorParser() {
		return errorParser;
	}
	public Request getRequest() {
		return request;
	}
	public ResponseEventListener getResponseEventListener() {
		return responseEventListener;
	}
	public boolean isStopNetworkCheck() {
		return stopNetworkCheck;
	}
	
	
	private ServiceBean(ServiceBeanBuilder serviceBeanBuilder)
	{
		this.request = serviceBeanBuilder.request;
		this.responseEventListener = serviceBeanBuilder.responseEventListener;
		this.stopNetworkCheck = serviceBeanBuilder.stopNetworkCheck;
		this.succesParser = serviceBeanBuilder.succesParser;
		this.errorParser = serviceBeanBuilder.errorParser;
		this.showLog = serviceBeanBuilder.showLog;
	}
	
	public static class ServiceBeanBuilder
	{
		private Request request;
		private ResponseEventListener responseEventListener;
		private boolean stopNetworkCheck;
		private Serializable succesParser;
		private Serializable errorParser;
		private boolean showLog;

		public ServiceBeanBuilder(Request request, ResponseEventListener responseEventListener, Serializable succesParser,Serializable errorParser)
		{
			this.request = request;
			this.responseEventListener = responseEventListener;
			this.succesParser = succesParser;
			this.errorParser = errorParser;
		}
		
		/**
		 * It will stop checking Network before making any HTTP/HTTPS call if it's set to true
		 * @param stopNetworkCheck
		 * @return
		 */
		public ServiceBeanBuilder stopNetworkCheck(boolean stopNetworkCheck)
		{
			this.stopNetworkCheck = stopNetworkCheck;
			return this;
		}
		
		/**
		 * It will show all the logs if set to true.
		 * @param showLog
		 * @return
		 */
		public ServiceBeanBuilder showLog(boolean showLog)
		{
			this.showLog = showLog;
			return this;
		}
		
		public ServiceBean build()
		{
			return new ServiceBean(this);
		}
	}
}
