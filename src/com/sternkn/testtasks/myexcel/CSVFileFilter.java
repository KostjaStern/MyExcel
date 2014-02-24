package com.sternkn.testtasks.myexcel;

import java.io.File;
import java.io.FileFilter;
import java.util.Locale;


public class CSVFileFilter implements FileFilter
{
    public boolean accept(File file)
    {
    	if (file.isDirectory()) {
    	    return false;
    	}
    	
	    String path = file.getAbsolutePath().toLowerCase(Locale.ENGLISH);
        if (path.endsWith(".csv") ){
	        return true;
	    }
	 
	    return false;
    }
}
