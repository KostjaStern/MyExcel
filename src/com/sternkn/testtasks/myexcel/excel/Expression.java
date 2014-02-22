package com.sternkn.testtasks.myexcel.excel;

/**
 * http://ru.wikipedia.org/wiki/%D0%9E%D0%B1%D1%80%D0%B0%D1%82%D0%BD%D0%B0%D1%8F_%D0%BF%D0%BE%D0%BB%D1%8C%D1%81%D0%BA%D0%B0%D1%8F_%D0%B7%D0%B0%D0%BF%D0%B8%D1%81%D1%8C
 * http://en.wikipedia.org/wiki/Reverse_Polish_notation
 */


import java.util.List;
import java.util.Stack;
import java.util.Locale;
import java.util.LinkedList;

import org.apache.log4j.Logger;


public class Expression
{
	public static Logger LOG = Logger.getLogger(Expression.class);
	
    public Expression(String exp)
    {
    	if(exp == null) throw new IllegalArgumentException("exp is null");
    	// expression = exp.toLowerCase(Locale.ENGLISH);
    	
    	LOG.debug("exp = " + exp);
    	
    	
    	expression = exp.trim().toUpperCase(Locale.ENGLISH);  // .replaceAll("[^A-Z0-9()+\\-/*.,]","");
    	
    	LOG.debug("expression = " + expression);
    	
    	rpn = new LinkedList<ExpressionItem>();
    	
    	ExpressionItem expItem = new ExpressionItem();
    	Stack<ExpressionItem> operators = new Stack<ExpressionItem>(); 
    	CharType lastType = CharType.UNKNOWN; 
    	// ExpItemType lastItemType = ExpItemType.EMPTY; 
    	
    	for(int i = 0; i < expression.length(); i++)
    	{
    		char expChar = expression.charAt(i);
    		
    		switch(getCharType(expChar))
    		{
    			case UNKNOWN:
    				throw new ExpressionException("invalid character " + expChar + " in expression " + expression);
    			
    			case BRACKET_OPEN:
    				// System.out.println("BRACKET_OPEN");
    				if(lastType == CharType.LETTER || 
   			  		   lastType == CharType.DECIMAL_SEPARATOR ||
   			  		   lastType == CharType.BRACKET_CLOSE ||
   			  		   lastType == CharType.DIGIT
    				  ){
    					throw new ExpressionException("invalid expression: " + expression);
    				}
    				
    				expItem = new ExpressionItem("(", ExpItemType.BRACKET_OPEN);
    				operators.push(expItem);
    				
    				// System.out.println("operators.size() = " + operators.size());
    				
    				lastType = CharType.BRACKET_OPEN;
    			break;
    			
    			case BRACKET_CLOSE:
    				if(lastType != CharType.DIGIT){
    					throw new ExpressionException("invalid expression: " + expression);
    				}
    				
    				rpn.add(expItem);
    				LOG.debug("expItem = " + expItem);
    				
    				if(operators.isEmpty()){
    					throw new ExpressionException("invalid expression: " + expression);
    				}
    				
    				ExpressionItem operator = operators.pop();
    				
//    				System.out.println("operators.size() = " + operators.size());
    				while(operator.getType() != ExpItemType.BRACKET_CLOSE)
    				{
    					rpn.add(operator);
    					LOG.debug("expItem = " + expItem);
    					
    					if(operators.isEmpty()){
        					throw new ExpressionException("invalid expression: " + expression);
        				}
    					operator = operators.pop();
    				}
    				
    				lastType = CharType.BRACKET_CLOSE;
    			break;
    			
    			case DECIMAL_SEPARATOR:
    				if(lastType != CharType.DIGIT){
    					throw new ExpressionException("invalid expression: " + expression);
    				}
    				else {
    					expItem.appendToValue(expChar);
    					expItem.setType(ExpItemType.NUMB_DOUBLE);
    				}
    				
    				lastType = CharType.DECIMAL_SEPARATOR;
    			break;
    			
    			case LETTER:
    				if(lastType == CharType.LETTER ||
    				   lastType == CharType.DECIMAL_SEPARATOR ||
    				   lastType == CharType.BRACKET_CLOSE ||
					   lastType == CharType.DIGIT
    				  ){
    					throw new ExpressionException("invalid expression: " + expression);
    				}
    				
    				expItem = new ExpressionItem(expChar, ExpItemType.VARIABLE);
    				
    				lastType = CharType.LETTER;
    			break;
    				
    			case DIGIT:
    				if(lastType == CharType.BRACKET_CLOSE){
    					throw new ExpressionException("invalid expression: " + expression);
    				}
    				
    				if(lastType == CharType.UNKNOWN ||
  					   lastType == CharType.BRACKET_OPEN ||
  					   lastType == CharType.OPERATOR
    				  ) {
    					expItem = new ExpressionItem(expChar, ExpItemType.NUMB_INT);
    				}
    				
    				if(lastType == CharType.LETTER ||
   					   lastType == CharType.DIGIT  ||
   					   lastType == CharType.DECIMAL_SEPARATOR
    				  ){
    					expItem.appendToValue(expChar);
    				}
    				
    				lastType = CharType.DIGIT;
    			break;
    			
    			case OPERATOR:
    				if(lastType != CharType.BRACKET_CLOSE &&
					   lastType != CharType.DIGIT 
  					  ){
    					throw new ExpressionException("invalid expression: " + expression);
    				}
    				
    				if(lastType == CharType.DIGIT){
    					rpn.add(expItem);
    					LOG.debug("expItem = " + expItem);
    				}
    				
    				
    				ExpressionItem curOp = new ExpressionItem(expChar, ExpItemType.OPERATOR); 
//    				System.out.println("curOp.getPriority() = " + curOp.getPriority());
//    				System.out.println("curOp.getValue() = " + curOp.getValue());
    				
    				while(!operators.isEmpty() &&  operators.peek().getPriority() >= curOp.getPriority())
    				{
    					expItem = operators.pop(); 
    					rpn.add(expItem);
    					LOG.debug("expItem = " + expItem);
    				}
    				operators.push(curOp);
    				
    				lastType = CharType.OPERATOR;
    			break;
    			
    			default:
    		}
    		
//    		System.out.println("expChar = " + expChar + "  , code = " + (int)expChar);
    	}
    	
    	
//    	for(ExpressionItem item: rpn){
//    		System.out.println("item.getValue() = " + item.getValue() + " " + item.getType());
//    	}
    }
    
    
    private CharType getCharType(char ch)
    {
    	// 32 => " "
    	// if(ch == 32) return CharType.SPACE;
    	
    	// 40 => "(" 
    	if(ch == 40) return CharType.BRACKET_OPEN;
    	
    	// 41 => ")"
    	if(ch == 41) return CharType.BRACKET_CLOSE;
    	
    	// 44 => ","   46 => "."   
    	if(ch == 44 || ch == 46) return CharType.DECIMAL_SEPARATOR; 
    	
    	// 65 => "A" ...  90 => "Z"
    	if(ch > 64 && ch < 91) return CharType.LETTER;
    	
    	// 48 => "0" ...  57 => "9"
    	if(ch > 47 && ch < 58) return CharType.DIGIT;
    	
    	// 42 => "*"   43 => "+"   45 => "-"   47 => "/"  
    	if(ch == 42 || ch == 43 || ch == 45 || ch == 47) return CharType.OPERATOR; 
    	
    	return CharType.UNKNOWN;
    }
    
    
    private String expression; 
    
    private List<ExpressionItem> rpn; // 
    
}
