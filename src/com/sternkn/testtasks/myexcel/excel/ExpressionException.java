package com.sternkn.testtasks.myexcel.excel;


public class ExpressionException extends RuntimeException
{
	static final long serialVersionUID = -70348971966939L;
	
    public ExpressionException(){
    	super();
    }
    
    
    public ExpressionException(String msg){
    	super(msg);
    }

    
    public ExpressionException(String msg, Throwable cause) {
        super(msg, cause);
    }

    
    public ExpressionException(Throwable cause) {
        super(cause);
    }
}
