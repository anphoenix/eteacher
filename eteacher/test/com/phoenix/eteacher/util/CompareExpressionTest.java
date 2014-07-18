package com.phoenix.eteacher.util;

import junit.framework.TestCase;


import com.phoenix.eteacher.util.CompareExpression;

public class CompareExpressionTest extends TestCase {

	public void testIsEquivalentExp01(){
		String answer = "1+2";
		assertTrue(CompareExpression.isTheSame(answer, "2+1"));
		assertTrue(CompareExpression.isTheSame(answer, "1+2"));
		
		assertFalse(CompareExpression.isTheSame(answer, "1+3"));
	}
	
	public void testIsEquivalentExp02(){
		String answer = "2*(3+1)";
		assertTrue(CompareExpression.isTheSame(answer, "(1+3)*2"));
		assertTrue(CompareExpression.isTheSame(answer, "2*(1+3)"));
		assertTrue(CompareExpression.isTheSame(answer, "2*(3+1)"));
		assertTrue(CompareExpression.isTheSame(answer, "(3+1)*2"));
		
		assertFalse(CompareExpression.isTheSame(answer, "(3+1)*3"));
		assertFalse(CompareExpression.isTheSame(answer, "4*(3+1)"));
	}
	
	public void testIsEquivalentExp03(){
		String answer = "2*(3-1)";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "(3-1)*2"));
		
		assertFalse(CompareExpression.isTheSame(answer, "(1-3)*3"));
		assertFalse(CompareExpression.isTheSame(answer, "3*(3-1)"));
	}
	
	public void testIsEquivalentExp04(){
		String answer = "2*(3-1)+(4/5)";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "(3-1)*2+(4/5)"));
		assertTrue(CompareExpression.isTheSame(answer, "(4/5)+(3-1)*2"));
		
		assertFalse(CompareExpression.isTheSame(answer, "(1-3)*3+(4/5)"));
		assertFalse(CompareExpression.isTheSame(answer, "3*(3-1)+(4/5)"));
		assertFalse(CompareExpression.isTheSame(answer, "(3/5)+(3-1)*2"));
	}
	
	public void testIsEquivalentExp05(){
		String answer = "(2+3)*(4+5)+1";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "(2+3)*(4+5)+1"));
		assertTrue(CompareExpression.isTheSame(answer, "(4+5)*(2+3)+1"));
		assertTrue(CompareExpression.isTheSame(answer, "(4+5)*(3+2)+1"));
		assertTrue(CompareExpression.isTheSame(answer, "1+(4+5)*(3+2)"));
		assertTrue(CompareExpression.isTheSame(answer, "1+(2+3)*(5+4)"));
		assertTrue(CompareExpression.isTheSame(answer, "1+(3+2)*(4+5)"));
		assertTrue(CompareExpression.isTheSame(answer, "1+(3+2)*(5+4)"));
		
		assertTrue(CompareExpression.isTheSame(answer, "(5+4)*(3+2)+1"));
		
		assertFalse(CompareExpression.isTheSame(answer, "(2+3)*(4+5)+2"));
		assertFalse(CompareExpression.isTheSame(answer, "(4+5)*(2+5)+1"));
	}
	
	public void testIsEquivalentExp06(){
		String answer = "1+2+3";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "2+1+3"));
		assertTrue(CompareExpression.isTheSame(answer, "2+3+1"));
		assertTrue(CompareExpression.isTheSame(answer, "1+3+2"));
		assertTrue(CompareExpression.isTheSame(answer, "3+1+2"));
		assertTrue(CompareExpression.isTheSame(answer, "3+2+1"));
		assertTrue(CompareExpression.isTheSame(answer, "1+3+2"));
		
		assertTrue(CompareExpression.isTheSame(answer, "3+(1+2)"));
		assertTrue(CompareExpression.isTheSame(answer, "2+(1+3)"));
		assertTrue(CompareExpression.isTheSame(answer, "(2+3)+1"));
		assertTrue(CompareExpression.isTheSame(answer, "1+(3+2)"));
		assertTrue(CompareExpression.isTheSame(answer, "3+(1+2)"));
		assertTrue(CompareExpression.isTheSame(answer, "(3+2)+1"));
		assertTrue(CompareExpression.isTheSame(answer, "(1+3)+2"));
		
		assertFalse(CompareExpression.isTheSame(answer, "1+3+3"));
		assertFalse(CompareExpression.isTheSame(answer, "1+2+2"));
		assertFalse(CompareExpression.isTheSame(answer, "2+3+3"));
	}
	
	public void testIsEquivalentExp07(){
		String answer = "1*2*3";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "2*1*3"));
		assertTrue(CompareExpression.isTheSame(answer, "2*3*1"));
		assertTrue(CompareExpression.isTheSame(answer, "1*3*2"));
		assertTrue(CompareExpression.isTheSame(answer, "3*1*2"));
		assertTrue(CompareExpression.isTheSame(answer, "3*2*1"));
		assertTrue(CompareExpression.isTheSame(answer, "1*3*2"));
		
		assertTrue(CompareExpression.isTheSame(answer, "3*(1*2)"));
		assertTrue(CompareExpression.isTheSame(answer, "2*(1*3)"));
		assertTrue(CompareExpression.isTheSame(answer, "(2*3)*1"));
		assertTrue(CompareExpression.isTheSame(answer, "1*(3*2)"));
		assertTrue(CompareExpression.isTheSame(answer, "3*(1*2)"));
		assertTrue(CompareExpression.isTheSame(answer, "(3*2)*1"));
		assertTrue(CompareExpression.isTheSame(answer, "(1*3)*2"));
		
		assertFalse(CompareExpression.isTheSame(answer, "1*3*3"));
		assertFalse(CompareExpression.isTheSame(answer, "1*2*2"));
		assertFalse(CompareExpression.isTheSame(answer, "2*3*3"));
	}
	
	public void testIsEquivalentExp08(){
		String answer = "1+2+3+4";
//		assertTrue(testexpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "1+2+4+3"));
		assertTrue(CompareExpression.isTheSame(answer, "2+1+4+3"));
		assertTrue(CompareExpression.isTheSame(answer, "2+1+3+4"));
		assertTrue(CompareExpression.isTheSame(answer, "4+2+3+1"));
		assertTrue(CompareExpression.isTheSame(answer, "4+2+1+3"));
		assertTrue(CompareExpression.isTheSame(answer, "4+3+2+1"));
		assertTrue(CompareExpression.isTheSame(answer, "4+3+1+2"));
		assertTrue(CompareExpression.isTheSame(answer, "3+4+1+2"));
		assertTrue(CompareExpression.isTheSame(answer, "3+4+2+1"));
		assertTrue(CompareExpression.isTheSame(answer, "1+4+2+3"));
		assertTrue(CompareExpression.isTheSame(answer, "1+4+3+2"));
		assertTrue(CompareExpression.isTheSame(answer, "1+3+2+4"));
		assertTrue(CompareExpression.isTheSame(answer, "1+3+4+2"));
		assertTrue(CompareExpression.isTheSame(answer, "2+3+4+1"));
		assertTrue(CompareExpression.isTheSame(answer, "2+3+1+4"));
		assertTrue(CompareExpression.isTheSame(answer, "3+2+1+4"));
		assertTrue(CompareExpression.isTheSame(answer, "3+2+4+1"));
		
		assertTrue(CompareExpression.isTheSame(answer, "1+(2+3+4)"));
		assertTrue(CompareExpression.isTheSame(answer, "1+(2+4)+3"));
		assertTrue(CompareExpression.isTheSame(answer, "(2+1+4)+3"));
		assertTrue(CompareExpression.isTheSame(answer, "(2+1)+3+4"));
		assertTrue(CompareExpression.isTheSame(answer, "(1+2)+(3+4)"));
		assertTrue(CompareExpression.isTheSame(answer, "(1+2)+(4+3)"));
		assertTrue(CompareExpression.isTheSame(answer, "(4+3)+(1+2)"));
		
		assertTrue(CompareExpression.isTheSame("(1+2)+(3+4)", "(4+2)+(3+1)"));
		assertTrue(CompareExpression.isTheSame(answer, "(4+2)+(3+1)"));
		assertTrue(CompareExpression.isTheSame(answer, "(4+2+1)+3"));
		assertTrue(CompareExpression.isTheSame(answer, "4+(3+2+1)"));
		assertTrue(CompareExpression.isTheSame(answer, "4+3+(1+2)"));
		assertTrue(CompareExpression.isTheSame(answer, "3+(4+1)+2"));
		assertTrue(CompareExpression.isTheSame(answer, "3+4+2+1"));
		assertTrue(CompareExpression.isTheSame(answer, "1+4+2+3"));
		assertTrue(CompareExpression.isTheSame(answer, "1+4+3+2"));
		assertTrue(CompareExpression.isTheSame(answer, "1+3+2+4"));
		assertTrue(CompareExpression.isTheSame(answer, "1+3+4+2"));
		assertTrue(CompareExpression.isTheSame(answer, "2+3+4+1"));
		assertTrue(CompareExpression.isTheSame(answer, "2+3+1+4"));
		assertTrue(CompareExpression.isTheSame(answer, "3+2+1+4"));
		assertTrue(CompareExpression.isTheSame(answer, "3+2+4+1"));
		
		assertFalse(CompareExpression.isTheSame(answer, "5+2+3+1"));
		assertFalse(CompareExpression.isTheSame(answer, "1+2+3"));
		assertFalse(CompareExpression.isTheSame(answer, "1+2+3+4+4"));
	}
	
	public void testIsEquivalentExp09(){
		String answer = "1+(2+3)*4+5*6-7";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "1+(2+3)*4+6*5-7"));
		//fail
		assertTrue(CompareExpression.isTheSame(answer, "1+6*5+(2+3)*4-7"));
		assertTrue(CompareExpression.isTheSame(answer, "1+(3+2)*4+6*5-7"));
		assertTrue(CompareExpression.isTheSame(answer, "(3+2)*4+1+6*5-7"));
		assertTrue(CompareExpression.isTheSame(answer, "(3+2)*4+6*5+1-7"));
	}
	
	public void testIsEquivalentExp10(){
		String answer = "(1+2)*3";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "1*3+2*3"));
		assertTrue(CompareExpression.isTheSame(answer, "3*1+2*3"));
		assertTrue(CompareExpression.isTheSame(answer, "3*1+3*2"));
	}
	
	public void testIsEquivalentExp11(){
		String answer = "(1-2)*3";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "1*3-2*3"));
		assertTrue(CompareExpression.isTheSame(answer, "3*1-2*3"));
		assertTrue(CompareExpression.isTheSame(answer, "3*1-3*2"));
	}
	
	public void testIsEquivalentExp12(){
		String answer = "(1+2)*(3+4)";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "1*(3+4)+2*(3+4)"));
		assertTrue(CompareExpression.isTheSame(answer, "(3+4)*1+(3+4)*2"));
		assertTrue(CompareExpression.isTheSame(answer, "(3+4)*1+2*(3+4)"));
		assertTrue(CompareExpression.isTheSame(answer, "(3+4)*1+(3+4)*2"));
		//fail
//		assertTrue(testexpression.isTheSame(answer, "1*3+1*4+2*(3+4)"));
	}
	
	public void testIsEquivalentExp13(){
		String answer = "(1-2)*(3+4)";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "1*(3+4)-2*(3+4)"));
		assertTrue(CompareExpression.isTheSame(answer, "(3+4)*1-(3+4)*2"));
		assertTrue(CompareExpression.isTheSame(answer, "(3+4)*1-2*(3+4)"));
		assertTrue(CompareExpression.isTheSame(answer, "(3+4)*1-(3+4)*2"));
		
		answer = "(1-2)*(3-4)";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "1*(3-4)-2*(3-4)"));
		assertTrue(CompareExpression.isTheSame(answer, "(3-4)*1-(3-4)*2"));
		assertTrue(CompareExpression.isTheSame(answer, "(3-4)*1-2*(3-4)"));
		assertTrue(CompareExpression.isTheSame(answer, "(3-4)*1-(3-4)*2"));
	}
	
	public void testIsEquivalentExp14(){
		String answer = "(1+2)*(3+4)+5";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "1*(3+4)+2*(3+4)+5"));
//		assertTrue(testexpression.isTheSame(answer, "3*1+3*2"));
	}
	
	public void testIsEquivalentExp15(){
		//hangs
		String answer = "(1+2)*(3+4)*5";
		assertTrue(CompareExpression.isTheSame(answer, answer));
		assertTrue(CompareExpression.isTheSame(answer, "(1+2)*5*(3+4)"));
	}
	
	public void testIsEquivalentExp16(){
	//hangs
	String answer = "(1+2)*((3+4)+5)";
		assertTrue(CompareExpression.isTheSame(answer, answer));
//		assertTrue(testexpression.isTheSame(answer, "(1+2)*(6+(3+4)*5)"));
	}
	public void testIsEquivalentExp17(){
		//hangs
		String answer = "(1 + 2)*(( 3 + 4)+5)";
			assertTrue(CompareExpression.isTheSame(answer, answer));
			assertTrue(CompareExpression.isTheSame(answer, "(1+2)×(3+4) + 1 * 5 + 2 * 5"));
			assertTrue(CompareExpression.isTheSame(answer, "(1+2)×[3+4] + 1 * 5 + 2 * 5"));
		}
	public void testIsEquivalentExp18(){
		//hangs
		String answer = "(1 + 2)*(( 3 + 4)/5)";
			assertTrue(CompareExpression.isTheSame(answer, answer));
			assertTrue(CompareExpression.isTheSame(answer, "(3+4) * 1/5 + (3+4)*2 * 1/5"));
//			assertTrue(CompareExpression.isTheSame(answer, "(1+2)×[3+4]/5 + 1 * 1/5 + 2 * 1/5"));
		}

}
