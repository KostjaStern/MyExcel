package com.sternkn.testtasks.myexcel.excel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;


import org.apache.log4j.Logger;

public class ExcelTable
{
	public static Logger LOG = Logger.getLogger(ExcelTable.class);
	
	public final String CELL_SEPARATOR = ":";
	
	public ExcelTable(File file){
		parseCSV(file);
		
//	    for (Map.Entry<String, TableCell> entry : table.entrySet())	{
//			System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
//		}		
	}
	
    public ExcelTable(String filePath)
    {
    	File file = new File(filePath);
    	parseCSV(file);
    	
//    	for (Map.Entry<String, TableCell> entry : table.entrySet())	{
//    		System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
//    	}
    }
    
    
    public void calcTable()
    {
    	int countCalc = table.size();
    	int prevCount = 0;
    	
    	while(countCalc != prevCount)
    	{
    		prevCount = countCalc; 
    		countCalc = 0;
    		
    		for (Map.Entry<String, TableCell> entry : table.entrySet())	
        	{
        		// System.out.println("Key : " + entry.getKey() + " Value : " + entry.getValue());
    			TableCell cell = entry.getValue();
    			if(cell.calcValue(table)){
    				countCalc++;
    			}
        	}
    	}
    	
    	for (Map.Entry<String, TableCell> entry : table.entrySet())	
    	{
    		TableCell cell = entry.getValue();
    		if(!cell.isExpCalc()){
    			cell.setResultValue("#");
    		}
    	}
    }
    
    
    public void saveTable(String filePath)
    {
    	File file = new File(filePath);
    	save(file);
    }
    
    
    public void saveTable(File file){
    	save(file);
    }
    
    private void parseCSV(File file)
    {
    	table = new HashMap<String, TableCell>();
    	
    	BufferedReader reader = null;

    	try 
    	{
    		try
    		{
	    	    reader = new BufferedReader(new FileReader(file));
	    	    String text = null;
	
	    	    int row = 0;
	    	    while ((text = reader.readLine()) != null) 
	    	    {
	    	    	row++;
	    	    	
	    	    	// System.out.println("text = " + text);
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
	    	    }
	    	    
	    	    countRows = row; 
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
    
    
    private void save(File file) 
    {
    	BufferedWriter writer = null;
    	
    	try
    	{
    		try
    		{
    			file.getParentFile().mkdir();
		    	file.createNewFile();
				writer = new BufferedWriter(new FileWriter(file));
		    	
		    	for(int row = 1; row < countRows + 1; row++)
		    	{
		    		StringBuilder line = new StringBuilder();
		    		for(int col = 0; col < cols.length; col++)
		    		{
		    			String key = cols[col] + String.valueOf(row);
		    			if(table.get(key) == null){
		    				break;
		    			}
		    			line.append(table.get(key).getResultValue());
		    			line.append(CELL_SEPARATOR);
		    		}
		    		
		    		line.append("\n");
		    		writer.write(line.toString());
		    	}
    		}
    		finally
    		{
    			if(writer != null){
    				writer.close();
    			}
    		}
    	}
    	catch (IOException e){
    		e.printStackTrace();
    	}    	
    }
    
    private Map<String, TableCell>  table;
    private final String[] cols = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
    		                       "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", 
    		                       "U", "V", "W", "X", "Y", "Z"};
    private int countRows;
}
