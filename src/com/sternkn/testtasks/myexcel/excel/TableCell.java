package com.sternkn.testtasks.myexcel.excel;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;

public class TableCell
{

	public TableCell(String value, String name)
	{
	    parseValue(value);
	    parseName(name);
	}
	
	public TableCell(String value, int col, int row)
	{
		parseValue(value);
		cellName = getNameByRowCol(col, row);
		column   = col;
		this.row = row;
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
	
	
	private String getNameByRowCol(int col, int row)
	{
		if(col < 1) throw new IllegalArgumentException("col must be >= 1 and < 27");
		if(row < 1) throw new IllegalArgumentException("row must be >= 1");
		
		// A => 1  ,  Z => 26
		if(col > 26) throw new IllegalArgumentException("col must be >= 1 and < 27");
		
		// A => 65 , Z => 90
		return String.valueOf((char)(col + 64)) + String.valueOf(row);
	}
	
	private void parseValue(String value)
	{
		if(value == null) throw new IllegalArgumentException("value is null");
		srcValue = value.trim();
		resultValue = srcValue;
		
		if(srcValue.equals("")){
			cellType = ValueType.STRING;
			return;
		}
		
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
	
	
	private void parseName(String name)
	{
    	if(name == null) throw new IllegalArgumentException("name is null");
    	
    	cellName = name.toUpperCase(Locale.ENGLISH);
    	
    	if(!cellName.matches("[A-Z]\\d+")){
    		throw new IllegalArgumentException("invalid name => " + name);
    	}
    	
    	/**
    	 *   A => 65
    	 *   Z => 90
    	 */
    	
    	column = cellName.charAt(0) - 64;
    	
    	try {
    	    row = Integer.parseInt(cellName.substring(1)); 
    	}
    	catch (NumberFormatException e) {
    		throw new IllegalArgumentException("invalid name => " + name);
    	}

	}
	
	private double parseDouble(String value) throws NumberFormatException
    {
    	if(value == null) throw new IllegalArgumentException("value is null");
    	
    	if(!value.matches("\\d+[\\.|\\,]\\d+")){
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
	
	public String getCellName(){
		return cellName;
	}
	
	
	@Override
	public String toString()
	{
		return "TableCell[cellName = " + cellName + " , srcValue = " + srcValue + " , resultValue = " + resultValue +
			   " , cellType = " + cellType + " , column = " + column + " , row = " + row + "]";
	}
	
	private       String cellName; 
	private       String srcValue;
	private       String resultValue;
	private       long   resultValueLong;
	private       double resultValueDouble;
	
	private       int  column;  // 1 .. 26  (A-Z)
	private       int  row;     // 1 .. 
	
	private       ValueType cellType;

}
