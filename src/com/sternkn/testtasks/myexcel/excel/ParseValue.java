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
    	if(value.length() == 0) throw new IllegalArgumentException("value is empty");
    	
    	if(value.substring(0, 1).equals("+")){
    		value = value.substring(1);
    	}
    	
    	DecimalFormat df;
    	
    	if(value.matches("-{0,1}\\d+(\\.\\d+){0,1}"))
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
    			LOG.debug("ParseException", e);
    			throw new NumberFormatException(e.getMessage());
    		}
    	}
    	
    	// LOG.debug("not matches");
    	
    	if(value.matches("-{0,1}\\d+(,\\d+){0,1}"))
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
