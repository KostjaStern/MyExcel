package com.sternkn.testtasks.myexcel.excel;

import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class ExcelTable
{
	public final String CELL_SEPARATOR = ":";
	
	
	
    public ExcelTable(String filePath)
    {
    	File file = new File(filePath);
    	table = new HashMap<String, TableCell>();
    	
    	BufferedReader reader = null;

    	try 
    	{
    		try
    		{
	    	    reader = new BufferedReader(new FileReader(file));
	    	    String text = null;
	
	    	    int row = 1;
	    	    while ((text = reader.readLine()) != null) 
	    	    {
	    	    	System.out.println("text = " + text);
	    	    	String[] items = text.split(CELL_SEPARATOR);
	    	        // System.out.println("items.length = " + items.length);
	    	    	int col = 1;
	    	    	for(String item: items)
	    	    	{
	    	    		// System.out.println("item = " + item);
	    	    		TableCell tableCell = new TableCell(item, col, row);
	    	    		table.put(tableCell.getCellName(), tableCell);
	    	    		col++;
	    	    	}
	    	        
	    	    	row++;
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
    	
    	
    	
    	for (Map.Entry<String, TableCell> entry : table.entrySet()) 
    	{
    		System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
    	}
    }
    
    
    private Map<String, TableCell>  table;
}
