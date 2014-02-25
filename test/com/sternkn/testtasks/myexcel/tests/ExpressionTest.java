package com.sternkn.testtasks.myexcel.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runners.JUnit4;
import org.junit.runner.RunWith;

import com.sternkn.testtasks.myexcel.excel.ExpItemType;
import com.sternkn.testtasks.myexcel.excel.Expression;
import com.sternkn.testtasks.myexcel.excel.ExpressionItem;
import com.sternkn.testtasks.myexcel.excel.ExpressionException;

@RunWith(JUnit4.class)
public class ExpressionTest
{

	@Test
	public void testExpression() 
	{
		
		try {
		    new Expression("(A24+2.56*0.59-21 /7+B8)/1.2-6.7*2+G4*7.21");
		    fail("must be throw ExpressionException");
		}
		catch(ExpressionException e){
		}
		
		
    	Expression exp = new Expression("(A24+2.56*0.59-21/7+B8)/1.2-6.7*2+G4*7.21");
    	
    	List<ExpressionItem> items = exp.getRPN(); 
    	
    	ArrayList<ExpressionItem> testItems = new ArrayList<ExpressionItem>();  
    	
    	ExpressionItem testItem = new ExpressionItem("A24", ExpItemType.VARIABLE); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("2.56", ExpItemType.NUMB_DOUBLE); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("0.59", ExpItemType.NUMB_DOUBLE); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("*", ExpItemType.OPERATOR); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("+", ExpItemType.OPERATOR); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("21", ExpItemType.NUMB_INT); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("7", ExpItemType.NUMB_INT); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("/", ExpItemType.OPERATOR); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("-", ExpItemType.OPERATOR); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("B8", ExpItemType.VARIABLE); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("+", ExpItemType.OPERATOR); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("1.2", ExpItemType.NUMB_DOUBLE); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("/", ExpItemType.OPERATOR); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("6.7", ExpItemType.NUMB_DOUBLE); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("2", ExpItemType.NUMB_INT); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("*", ExpItemType.OPERATOR); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("-", ExpItemType.OPERATOR); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("G4", ExpItemType.VARIABLE); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("7.21", ExpItemType.NUMB_DOUBLE); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("*", ExpItemType.OPERATOR); 
    	testItems.add(testItem);
    	testItem = new ExpressionItem("+", ExpItemType.OPERATOR); 
    	testItems.add(testItem);
    	
    	
    	assertTrue(items.size() == testItems.size());
    	
    	for(int i = 0; i < items.size(); i++)
    	{
    		assertTrue(items.get(i).equals(testItems.get(i)));
    	}
        
	}

}
