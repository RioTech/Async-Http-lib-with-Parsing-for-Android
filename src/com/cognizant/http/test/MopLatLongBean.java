/**
 * 
 */
package com.cognizant.http.test;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author 328073
 *
 */

public class MopLatLongBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public List<MoplocationList> results;
	
	public static class MoplocationList implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		@JsonProperty("geometry")
		public Geometry geometry;
		
	}
	
	
	
	
	public static class Geometry
	{
		@JsonProperty("location")
		public Location location;
	}
	
	public static class Location
	{
		public double lat;
		public double lng;
	}
}
