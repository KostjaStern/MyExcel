package com.sternkn.testtasks.myexcel.excel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;

public class TableCell
{

	public TableCell(String cell)
	{
		if(cell == null) throw new NullPointerException("cell is null");
		
		srcValue = cell.trim();
		resultValue = srcValue;
		
		try 
		{
			resultValueLong = Long.parseLong(srcValue);
			cellType        = ValueType.LONG;
		}
		catch (NumberFormatException e)
		{
			try
			{
				resultValueDouble = parseDouble(srcValue);
				cellType          = ValueType.DOUBLE;
			}
			catch (NumberFormatException e1)
			{
				if(srcValue.substring(0, 1).equals("=")){
					cellType = ValueType.EXPRESSION;
				}
				else {
					cellType = ValueType.STRING;
				}
			}
		}
		
	}
	
	public String getSrcValue(){
		return srcValue; 
	}
	
	public String getResultValue(){
		return resultValue; 
	}
	
	public ValueType getType(){
		return cellType; 
	}
	
	
	private double parseDouble(String value) throws NumberFormatException
    {
    	if(value == null) throw new IllegalArgumentException("value is null");
    	
    	if(!value.matches("-?\\d+([\\.|\\,]\\d+)?")){
    		throw new NumberFormatException(value + " is not double value");
    	}
    	
    	DecimalFormat df = new DecimalFormat();
    	DecimalFormatSymbols decComma = new DecimalFormatSymbols();
    	decComma.setDecimalSeparator(',');
    	
    	df.setDecimalFormatSymbols(decComma);
    	
    	try {
    	    return df.parse(value).doubleValue();
    	}
    	catch (ParseException e)
    	{
    		DecimalFormatSymbols decPoint = new DecimalFormatSymbols();
    		decPoint.setDecimalSeparator('.');
    		df.setDecimalFormatSymbols(decPoint);
    		
    		try {
    			return df.parse(value).doubleValue();
    		}
    		catch (ParseException e1){
    			throw new NumberFormatException(e1.getMessage());
    		}
    	}
    }
	
	
	private final String srcValue;
	private       String resultValue;
	private       long   resultValueLong;
	private       double resultValueDouble;
	
	private       ValueType cellType;

}
