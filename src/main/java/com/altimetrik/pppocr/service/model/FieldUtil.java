/**
 * 
 */
package com.altimetrik.pppocr.service.model;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lakshmi
 *
 */
public class FieldUtil
{
	private static FieldUtil util = new FieldUtil();
	
	private Map<String, FieldCoordinate> coordinates = new HashMap<String, FieldCoordinate>();
	
	private FieldUtil() 
	{

		coordinates.put("EIN-Block", new FieldCoordinate("EIN-1", 1, 638, 274, 1000, 75,5));
		

		coordinates.put("EIN-1", new FieldCoordinate("EIN-1", 1, 638, 274, 81, 75,0));
		coordinates.put("EIN-2", new FieldCoordinate("EIN-2", 1, 741, 274, 81, 75,0));
		coordinates.put("EIN-3", new FieldCoordinate("EIN-3", 1, 902, 274, 81, 75,0));
		coordinates.put("EIN-4", new FieldCoordinate("EIN-4", 1, 1008, 274, 81, 75,0));
		coordinates.put("EIN-5", new FieldCoordinate("EIN-5", 1, 1113, 274, 81, 75,0));
		coordinates.put("EIN-6", new FieldCoordinate("EIN-6", 1, 1219, 274, 81, 75,0));
		coordinates.put("EIN-7", new FieldCoordinate("EIN-7", 1, 1325, 274, 81, 75,0));
		coordinates.put("EIN-8", new FieldCoordinate("EIN-8", 1, 1430, 274, 81, 75,0));
		coordinates.put("EIN-9", new FieldCoordinate("EIN-9", 1, 1536, 274, 81, 75,0));


		coordinates.put("name", new FieldCoordinate("name", 1, 568, 374, 1050, 75,5));
		coordinates.put("tradeName", new FieldCoordinate("tradeName", 1, 478, 474, 1150, 75,5));
		coordinates.put("addressline1", new FieldCoordinate("addressline1", 1, 328, 574, 1140, 75,5));
		coordinates.put("city", new FieldCoordinate("city", 1, 328, 699, 780, 75,5));
		coordinates.put("state", new FieldCoordinate("state", 1, 1139, 699, 150, 75,5));
		coordinates.put("zip", new FieldCoordinate("zip", 1, 1319, 699, 300, 75,5));
		
		coordinates.put("employees-1", new FieldCoordinate("employees", 1, 1859, 1124, 540, 62,0));
		coordinates.put("wages-2", new FieldCoordinate("wages", 1, 1859, 1224, 540, 62,4));
		coordinates.put("wages-3", new FieldCoordinate("wages", 1, 1859, 1324, 540, 62,0));
		

		coordinates.put("wages-5a-c1", new FieldCoordinate("wages", 1, 899, 1549, 390, 62,0));
		coordinates.put("wages-5b-c1", new FieldCoordinate("wages", 1, 899, 1624, 390, 62,0));
		coordinates.put("wages-5c-c1", new FieldCoordinate("wages", 1, 899, 1699, 390, 62,0));
		coordinates.put("wages-5d-c1", new FieldCoordinate("wages", 1, 899, 1799, 390, 62,0));
		
		coordinates.put("wages-5a-c2", new FieldCoordinate("wages", 1, 1469, 1549, 390, 62,0));
		coordinates.put("wages-5b-c2", new FieldCoordinate("wages", 1, 1469, 1624, 390, 62,0));
		coordinates.put("wages-5c-c2", new FieldCoordinate("wages", 1, 1469, 1699, 390, 62,0));
		coordinates.put("wages-5d-c2", new FieldCoordinate("wages", 1, 1469, 1799, 390, 62,0));
		
		coordinates.put("wages-5e", new FieldCoordinate("wages", 1, 1859, 1899, 540, 62,0));
		coordinates.put("wages-5f", new FieldCoordinate("wages", 1, 1859, 1999, 540, 62,0));
		
		
		coordinates.put("wages-6", new FieldCoordinate("wages", 1, 1859, 2099, 540, 62,0));
		coordinates.put("wages-7", new FieldCoordinate("wages", 1, 1859, 2199, 540, 62,0));
		coordinates.put("wages-8", new FieldCoordinate("wages", 1, 1859, 2299, 540, 62,0));
		coordinates.put("wages-9", new FieldCoordinate("wages", 1, 1859, 2399, 540, 62,0));
		coordinates.put("wages-10", new FieldCoordinate("wages", 1, 1859, 2499, 540, 62,0));
		coordinates.put("wages-11", new FieldCoordinate("wages", 1, 1859, 2599, 540, 62,0));
		coordinates.put("wages-12", new FieldCoordinate("wages", 1, 1859, 2699, 540, 62,0));
		coordinates.put("wages-13", new FieldCoordinate("wages", 1, 1859, 2824, 540, 62,0));
		coordinates.put("wages-14", new FieldCoordinate("wages", 1, 1859, 2924, 540, 62,0));


		
		

		coordinates.put("Q1", new FieldCoordinate("Q1", 1, 1769, 392, 60, 60,3));
		coordinates.put("Q2", new FieldCoordinate("Q2", 1, 1769, 464, 60, 60,3));
		coordinates.put("Q3", new FieldCoordinate("Q3", 1, 1769, 535, 60, 60,3));
		coordinates.put("Q4", new FieldCoordinate("Q4", 1, 1769, 606, 60, 60,3));


		
	}
	
	public static FieldUtil getFieldUtil()
	{
		return util;
	}
	
	public  Map<String, FieldCoordinate> getFieldCoordinates()
	{
		return this.coordinates;
	}
}
