package com.sternkn.testtasks.myexcel.excel;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;


public class ExcelTable
{
	public final String CELL_SEPARATOR = ":";
	
	
	
    public ExcelTable(String filePath)
    {
    	File file = new File(filePath);
    	
    	BufferedReader reader = null;

    	try 
    	{
    		try
    		{
	    	    reader = new BufferedReader(new FileReader(file));
	    	    String text = null;
	
	    	    while ((text = reader.readLine()) != null) {
	    	        System.out.println("text = " + text);
	    	    }
    		}
    		finally
    		{
    	        if (reader != null) {
    	            reader.close();
    	        }
    		}
    	} 
    	catch (FileNotFoundException e) {
    	    e.printStackTrace();
    	} 
    	catch (IOException e) {
    	    e.printStackTrace();
    	}
    }
}
