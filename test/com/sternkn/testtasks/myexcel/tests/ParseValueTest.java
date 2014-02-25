package com.sternkn.testtasks.myexcel.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

// import com.sternkn.testtasks.myexcel.Config;
import com.sternkn.testtasks.myexcel.excel.ParseValue;


@RunWith(JUnit4.class)
public class ParseValueTest
{
	
	@Test
	public void testParseDouble()
	{
		double val = ParseValue.parseDouble("-3.7");
		assertTrue(Double.compare(val, -3.7) == 0);
		
		val = ParseValue.parseDouble("-6,2");
		assertTrue(Double.compare(val, -6.2) == 0);
		
		val = ParseValue.parseDouble("+2.9");
		assertTrue(Double.compare(val, 2.9) == 0);
		
		val = ParseValue.parseDouble("2.2345");
		assertTrue(Double.compare(val, 2.2345) == 0);
		
		val = ParseValue.parseDouble("2,25");
		assertTrue(Double.compare(val, 2.25) == 0);
		
		val = ParseValue.parseDouble("23");
		assertTrue(Double.compare(val, 23) == 0);
		
		
		try{
			val = ParseValue.parseDouble("2.3s");
			fail("must be trow NumberFormatException");
		}
		catch (NumberFormatException e){
		}
		
	}
}
