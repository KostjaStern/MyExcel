package com.sternkn.testtasks.myexcel.excel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

import org.apache.log4j.Logger;

public class ParseValue
{
	public static Logger LOG = Logger.getLogger(ParseValue.class);
	
	public static double parseDouble(String value) throws NumberFormatException
    {
		// LOG.debug("value = " + value);
		
    	if(value == null) throw new IllegalArgumentException("value is null");
    	
    	DecimalFormat df;
    	
    	if(value.matches("\\d+(\\.\\d+)*"))
    	{
    		df = new DecimalFormat();
    		DecimalFormatSymbols decPoint = new DecimalFormatSymbols();
    		decPoint.setDecimalSeparator('.');
    		df.setDecimalFormatSymbols(decPoint);
    		
    		try {
    			// LOG.debug("value = " + value);
    			return df.parse(value).doubleValue();
    		}
    		catch (ParseException e){
    			throw new NumberFormatException(e.getMessage());
    		}
    	}
    	
    	if(value.matches("\\d+(,\\d+)*"))
    	{
        	df = new DecimalFormat();
        	DecimalFormatSymbols decComma = new DecimalFormatSymbols();
        	decComma.setDecimalSeparator(',');
        	
        	df.setDecimalFormatSymbols(decComma);
        	try {
        	    return df.parse(value).doubleValue();
        	} 
        	catch (ParseException e) {
        		throw new NumberFormatException(e.getMessage());
        	}
    	}
    	
    	throw new NumberFormatException(value + " is not double value");
    }
}
