package com.sternkn.testtasks.myexcel.excel;

public class ExpressionItem
{
    public ExpressionItem(String value, ExpItemType type)
    {
    	if(value == null) throw new IllegalArgumentException("value is null");
    	
    	this.type  = type;
    	this.value = new StringBuilder();
    	this.value.append(value);
    	initPriority();
    }
    
    public ExpressionItem(char value, ExpItemType type)
    {
    	this.type  = type;
    	this.value = new StringBuilder();
    	this.value.append(value);
    	initPriority();
    }
    
    public ExpressionItem()
    {
    	value = new StringBuilder();
    	type  = ExpItemType.EMPTY;
    	initPriority();
    }
    
    
    private void initPriority()
    {
    	priority = 0;
    	
    	if(type == ExpItemType.OPERATOR)
    	{
    		String op = value.toString();
    		
    		if(op.equals("+") || op.equals("-")){
    			priority = 1;
    		}
    		
    		if(op.equals("*") || op.equals("/")){
    			priority = 2;
    		}
    	}
    }
    

    public boolean isEmpty(){
    	return (value.length() == 0);
    }
    
    public String getValue(){
    	return value.toString();
    }
    
    public int getValueInt()
    {
    	try {
    		return Integer.parseInt(getValue());
    	}
    	catch (NumberFormatException e){
    		return 0;
    	}
    }
    
    public double getValueDouble()
    {
    	try {
    		return ParseValue.parseDouble(getValue());
    	}
    	catch (NumberFormatException e){
    		return 0;
    	}
    }
    
    public void setValue(String value){
    	this.value = new StringBuilder(value);
    }

    public void appendToValue(String value){
    	this.value.append(value);
    }
    
    public void appendToValue(char value){
    	this.value.append(value);
    }
    
    public ExpItemType getType(){
    	return type;
    }
    
    public void setType(ExpItemType type){
    	this.type = type;
    }

    
    public int getPriority(){
    	return priority;
    }
    
    
    @Override
    public String toString()
    {
    	return "ExpressionItem[value = " + value + " , type = " + type + " , priority = " + priority + "]";
    }
    
    
    @Override
    public boolean equals(Object otherObj)
    {
    	if(this == otherObj) return true;
    	if(otherObj == null) return false;
    	
    	if(getClass() != otherObj.getClass()) return false;
    	
    	ExpressionItem other = (ExpressionItem)otherObj; 
    	
    	String val = value.toString();
    	
    	return val.equals(other.getValue()) &&
    		   type == other.getType();
    }
    
    private StringBuilder value;
    private ExpItemType   type;
    private int           priority;
}
