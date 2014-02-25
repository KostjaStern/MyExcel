package com.sternkn.testtasks.myexcel.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

// import com.sternkn.testtasks.myexcel.Config;
import com.sternkn.testtasks.myexcel.excel.*;

import java.util.*;


@RunWith(JUnit4.class)
public class TableCellTest
{
//    static {
//    	Config.initLogs();
//    }
	
	@Test
	public void testSimpleLaw() 
	{
		
		TableCell tableCell = new TableCell("=2,5*1.7+0.3*(23+11,6)", "A1"); 
		
		assertTrue(tableCell.getCellType() == ValueType.EXPRESSION);
		assertTrue(tableCell.calcValue(null));
		assertTrue(tableCell.getResultValue().equals("14.63"));
		
		
        tableCell = new TableCell("=5/0", "A1"); 
		
		assertTrue(tableCell.getCellType() == ValueType.EXPRESSION);
		assertTrue(tableCell.calcValue(null));
		assertTrue(tableCell.getResultValue().equals("#"));
		
        
		tableCell = new TableCell("56", "A1"); 
		
		assertTrue(tableCell.getCellType() == ValueType.LONG);
		assertTrue(tableCell.calcValue(null));
		assertTrue(tableCell.getResultValueLong() == 56);
		
	}

	
	@Test
	public void testCalc()
	{
		HashMap<String, TableCell> cells = new HashMap<String, TableCell>(); 
		
		TableCell tableCell = new TableCell("2,5", "A1");
		assertTrue(tableCell.getCellType() == ValueType.DOUBLE);
		assertTrue(Double.compare(tableCell.getResultValueDouble(), 2.5) == 0);
		
		cells.put("A1", tableCell);
		
		
		tableCell = new TableCell("3.7", "G3");
		assertTrue(tableCell.getCellType() == ValueType.DOUBLE);
		assertTrue(Double.compare(tableCell.getResultValueDouble(), 3.7) == 0);
		
		cells.put("G3", tableCell);
		
		
		tableCell = new TableCell("-4", "D5");
		assertTrue(tableCell.getCellType() == ValueType.LONG);
		assertTrue(tableCell.getResultValueLong() == -4);
		
		cells.put("D5", tableCell);
		
		
		tableCell = new TableCell("=(2*A1+2.3/D5)*4.3-20,2+3.1*G3", "E2");
		assertTrue(tableCell.getCellType() == ValueType.EXPRESSION);
		assertTrue(tableCell.calcValue(cells));
		
		
		assertTrue(Double.compare(tableCell.getResultValueDouble(), 10.297500000000001) == 0);
	}
}
