package com.sternkn.testtasks.myexcel;


import com.sternkn.testtasks.myexcel.excel.*;

import org.apache.log4j.Logger;

import java.util.Scanner;
import java.io.File;
import java.io.FileFilter;

public class Main
{
	public static Logger LOG = Logger.getLogger(Main.class);
	
    public static void main(String[] args)
    {
    	Config.initLogs();

    	// String exp = "B1 24 + C2 (2.6 67) B3 G4(F1 + H2)L3(D1- V2)(S3 / S4)";
    	
    	System.out.println("Enter command : ");
    	System.out.println("    go   - for bagining calculation");
    	System.out.println("    exit - for exit  ");
    	
    	Scanner scanIn = new Scanner(System.in);
    	String command;
    	
    	while(true)
    	{
    		command = scanIn.nextLine();
    		
    		if(command.equals("go"))
    		{
    			File inFolder = new File("in/");
    			if(!inFolder.exists()){
    				System.out.println("in/ folder not exist");
    				break;
    			}
    			
    			File outFolder = new File("out/");
    			outFolder.mkdir();
    			
    			FileFilter filter = new CSVFileFilter();
    			
    			File[] files = inFolder.listFiles(filter);
    			
    			for(File file: files)
    			{
    				System.out.println("file: " + file.getPath());
    				ExcelTable table = new ExcelTable(file);
        	    	table.calcTable();
        	    	table.saveTable("out/" + file.getName());
        	    	
        	    	System.out.println("done");
    			}
    		}
    		else 
    		if(command.equals("exit")){
    			break;
    		}
    		else {
    			System.out.println("Error command, try again ...");
    		}
    	}
    	
    	scanIn.close();
    }
}


