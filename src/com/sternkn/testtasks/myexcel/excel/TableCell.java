package com.sternkn.testtasks.myexcel.excel;


import java.util.Locale;
import java.util.Map;
import java.util.List;
import java.util.Stack;

import org.apache.log4j.Logger;

public class TableCell
{
	public static Logger LOG = Logger.getLogger(TableCell.class);
	
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
	
	public void setResultValue(String val){
		resultValue = val;
		isExpCalc   = true;
	}
	
	public ValueType getResultValueType(){
		return expResultType;
	}
	
	public long getResultValueLong(){
		return resultValueLong;
	}
	
	public double getResultValueDouble(){
		return resultValueDouble;
	}
	
	public ValueType getType(){
		return cellType; 
	}
	
	public String getCellName(){
		return cellName;
	}
	
	public ValueType getCellType(){
		return cellType;
	}
	
	
	public boolean calcValue(Map<String, TableCell>  table)
	{
		if(expResultType != ValueType.EXPRESSION){
		    return true;
		}

		Stack<ExpressionItem> calc = new Stack<ExpressionItem>();
		ValueType calcType = ValueType.LONG;
		List<ExpressionItem> expItems = exp.getRPN();
		for(ExpressionItem expItem: expItems)
		{
			// LOG.debug("expItem = " + expItem);
			
			ExpItemType itemType = expItem.getType();
			if(itemType == ExpItemType.NUMB_INT) {
				calc.push(expItem);
			}
			
			if(itemType == ExpItemType.NUMB_DOUBLE){
				calc.push(expItem);
				calcType = ValueType.DOUBLE;
			}
			
			if(itemType == ExpItemType.VARIABLE)
			{
				String key = expItem.getValue();
				TableCell cell = table.get(key);
				
				// LOG.debug("cell = " + cell);
				
				if(key.equals(cellName) || 
				   cell == null ||
				   cell.getResultValueType() == ValueType.STRING
				  )
				{
					resultValue = "#";
					expResultType = ValueType.STRING;
					isExpCalc = true;
					return true;
				}
				
				if(cell.getResultValueType() == ValueType.EXPRESSION){
					return false;
				}
				
				ExpressionItem item;
				if(cell.getResultValueType() == ValueType.LONG)
				{
					long resultVal = cell.getResultValueLong();
					// LOG.debug("long resultVal = " + resultVal);
					item = new ExpressionItem(String.valueOf(resultVal), ExpItemType.NUMB_INT); 
				}
				else
				{
					calcType = ValueType.DOUBLE;
					double resultVal = cell.getResultValueDouble();
					// LOG.debug("double resultVal = " + resultVal);
					item = new ExpressionItem(String.valueOf(resultVal), ExpItemType.NUMB_DOUBLE);
				}
				
				// LOG.debug("item = " + item);
				calc.push(item);
			}
			
			
			if(itemType == ExpItemType.OPERATOR)
			{
				ExpressionItem arg2 = calc.pop();
				ExpressionItem arg1 = calc.pop();

//				LOG.debug("arg1 = " + arg1);
//				LOG.debug("arg2 = " + arg2);
				
				if(expItem.getValue().equals("/") ||
				   arg1.getType() == ExpItemType.NUMB_DOUBLE ||
				   arg2.getType() == ExpItemType.NUMB_DOUBLE
				  ){
					calcType = ValueType.DOUBLE;
				}
				
				// here  ExpressionItem type may be only  
				// ExpItemType.NUMB_INT or ExpItemType.NUMB_DOUBLE
				try
				{
					ExpressionItem resultItem;
					
					if(calcType == ValueType.DOUBLE)
					{
						double a1 = arg1.getValueDouble();
						double a2 = arg2.getValueDouble();
						
					/*	
						LOG.debug("arg1.getValue() = " + arg1.getValue());
						LOG.debug("arg2.getValue() = " + arg2.getValue());
						LOG.debug("a1 = " + a1 + " , a2 = " + a2 + " operation = " + expItem.getValue());
					*/	
						
						double r;
						if(expItem.getValue().equals("+")){
							r = a1 + a2;
						}
						else if(expItem.getValue().equals("-")){
							r = a1 - a2;
						}
						else if(expItem.getValue().equals("*")){
							r = a1 * a2;
						}
						else {
							r = a1 / a2;
						}
						
						if(Double.isNaN(r) || r == Double.POSITIVE_INFINITY || r == Double.NEGATIVE_INFINITY)
						{
							resultValue = "#";
							expResultType = ValueType.STRING;
							isExpCalc = true;
							return true;
						}
						
						// LOG.debug("r = " + r);
						
						resultItem = new ExpressionItem(String.valueOf(r), ExpItemType.NUMB_DOUBLE); 
					}
					else
					{
						long a1 = arg1.getValueInt();
						long a2 = arg2.getValueInt();
						
						// LOG.debug("a1 = " + a1 + " , a2 = " + a2 + " operation = " + expItem.getValue());
						
						long r;
						if(expItem.getValue().equals("+")){
							r = a1 + a2;
						}
						else if(expItem.getValue().equals("-")){
							r = a1 - a2;
						}
						else {
							r = a1 * a2;
						}
						resultItem = new ExpressionItem(String.valueOf(r), ExpItemType.NUMB_INT);
					}
					calc.push(resultItem);
				}
				catch (ArithmeticException e) 
				{
					resultValue = "#";
					expResultType = ValueType.STRING;
					isExpCalc = true;
					return true;
				}
			}
		}
		
		expResultType = calcType;
		isExpCalc = true;
		ExpressionItem result = calc.pop();
		if(result.getType() == ExpItemType.NUMB_INT)
		{
			resultValueLong   = result.getValueInt();
			resultValueDouble = resultValueLong;
			resultValue = String.valueOf(resultValueLong);
		}
		else 
		{
			resultValueDouble = result.getValueDouble();
			resultValue = String.valueOf(resultValueDouble);
		}
		return true;
	}
	
	
	
	
	public boolean isExpCalc(){
		return isExpCalc;
	}
	
	
	
	@Override
	public String toString()
	{
		return "TableCell[cellName = " + cellName + " , srcValue = " + srcValue + " , resultValue = " + resultValue +
			   " , cellType = " + cellType + " , column = " + column + " , row = " + row + "]";
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
		
		isExpCalc = true;
		
		if(srcValue.equals("")){
			resultValue = "";
			cellType      = ValueType.STRING;
			expResultType = ValueType.STRING;
			return;
		}
		
		try 
		{
			resultValueLong = Long.parseLong(srcValue);
			cellType        = ValueType.LONG;
			expResultType   = ValueType.LONG;
		}
		catch (NumberFormatException e)
		{
			try
			{
				resultValueDouble = ParseValue.parseDouble(srcValue);
				cellType          = ValueType.DOUBLE;
				expResultType     = ValueType.DOUBLE;
			}
			catch (NumberFormatException e1)
			{
				// LOG.debug("srcValue = " + srcValue);
				if(srcValue.substring(0, 1).equals("="))
				{
					cellType = ValueType.EXPRESSION;
					
					try {
						isExpCalc = false;
						exp = new Expression(srcValue.substring(1));
						expResultType = ValueType.EXPRESSION;
					}
					catch (ExpressionException e2) {
						isExpCalc = true;
						resultValue = "#";
						expResultType = ValueType.STRING;
					}
				}
				else {
					cellType      = ValueType.STRING;
					expResultType = ValueType.STRING;
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
	
	private String cellName; 
	private String srcValue;
	
	private boolean isExpCalc;
	private ValueType expResultType;
	private Expression exp;
	
	
	private String resultValue;
	private long   resultValueLong;
	private double resultValueDouble;
	
	private int  column;  // 1 .. 26  (A-Z)
	private int  row;     // 1 .. 
	
	private ValueType cellType;

}
