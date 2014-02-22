package com.sternkn.testtasks.myexcel;

import com.sternkn.testtasks.myexcel.excel.ExcelTable; 
import com.sternkn.testtasks.myexcel.excel.ExpressionItem;
import com.sternkn.testtasks.myexcel.excel.TableCell;
import com.sternkn.testtasks.myexcel.excel.Expression;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

public class Main
{
	public static Logger LOG = Logger.getLogger(Main.class);
	
    public static void main(String[] args)
    {
    	Config.initLogs();
    	

/*    	
    	String test1 = "28";
    	String test2 = "28.23";
    	String test3 = "2,23";
    	String test4 = "2,23zxczxcz";
    	
    	try {
    	    long long1 = Long.parseLong(test1);
    	    System.out.println("long1 = " + long1);
    	}
    	catch (NumberFormatException e){
    		System.out.println("NumberFormatException: " + e.getMessage());
    	}
    	
    	try {
    	    long long2 = Long.parseLong(test2);
    	    System.out.println("long2 = " + long2);
    	}
    	catch (NumberFormatException e){
    		System.out.println("NumberFormatException: " + e.getMessage());
    	}
    	
    	try {
    	    long long3 = Long.parseLong(test3);
    	    System.out.println("long3 = " + long3);
    	}
    	catch (NumberFormatException e){
    		System.out.println("NumberFormatException: " + e.getMessage());
    	}
    	
    	
    	try {
    		double dob1 = parseDouble(test2);
    	    System.out.println("dob1 = " + dob1);
    	}
    	catch (NumberFormatException e){
    		System.out.println("NumberFormatException: " + e.getMessage());
    	}
    	
    	try {
    		double dob2 = parseDouble(test3);
    	    System.out.println("dob2 = " + dob2);
    	}
    	catch (NumberFormatException e){
    		System.out.println("NumberFormatException: " + e.getMessage());
    	}
    	
    	try {
    		double dob3 = parseDouble(test4);
    	    System.out.println("dob3 = " + dob3);
    	}
    	catch (NumberFormatException e){
    		System.out.println(e.getMessage());
    	}
    	
    	
    	System.out.println("isDouble(test1) = " + isDouble(test1));
    	System.out.println("isDouble(test2) = " + isDouble(test2));
    	System.out.println("isDouble(test3) = " + isDouble(test3));
    	System.out.println("isDouble(test4) = " + isDouble(test4));
    	
    	ExcelTable table = new ExcelTable("test.csv");
*/
    	
    	// Expression exp = new Expression("a1(b3 - C1 + 2* 3.14) + 5,213  + 0.1289 + Z23 + C3 / b3 ^ 3");
    	Expression exp = new Expression("((A24/5)+B8)*B6");
    	
    	/**
    	 *   A => 65
    	 *   Z => 90
    	 *   a => 97
    	 *   z => 122
    	 */
    	
//    	TableCell tableCell = new TableCell("=A1*(B3+C1)+C3/B3", 6, 10); 
//    	System.out.println("tableCell = " + tableCell);
    	
/*    	
    	String test = "::::ddd::test:2:3:444:::fff";
    	
    	String[] list = test.split(":");
        
    	for(String item: list)
    	{
    		System.out.println("item = " + item);
    	}
*/    	
    }
    
    
    public static double parseDouble(String value) throws NumberFormatException
    {
    	if(value == null) throw new NullPointerException("value is null");
    	
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
    
    public static boolean isDouble(String str)
    {
        return str.matches("-?\\d+([\\.|\\,]\\d+)?");  //match a number with optional '-' and decimal.
    }
}


