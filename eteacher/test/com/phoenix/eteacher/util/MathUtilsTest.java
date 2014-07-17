package com.phoenix.eteacher.util;


import junit.framework.TestCase;

public class MathUtilsTest extends TestCase{
	
	public void testIsEquivalentExp01(){
		String answer = "1+2";
		assertTrue(MathUtils.isEquivalentExp(answer, "2+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+2"));
		
		assertFalse(MathUtils.isEquivalentExp(answer, "1+3"));
	}
	
	public void testIsEquivalentExp02(){
		String answer = "2*(3+1)";
		assertTrue(MathUtils.isEquivalentExp(answer, "(1+3)*2"));
		assertTrue(MathUtils.isEquivalentExp(answer, "2*(1+3)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "2*(3+1)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(3+1)*2"));
		
		assertFalse(MathUtils.isEquivalentExp(answer, "(3+1)*3"));
		assertFalse(MathUtils.isEquivalentExp(answer, "4*(3+1)"));
	}
	
	public void testIsEquivalentExp03(){
		String answer = "2*(3-1)";
		assertTrue(MathUtils.isEquivalentExp(answer, answer));
		assertTrue(MathUtils.isEquivalentExp(answer, "(3-1)*2"));
		
		assertFalse(MathUtils.isEquivalentExp(answer, "(1-3)*3"));
		assertFalse(MathUtils.isEquivalentExp(answer, "3*(3-1)"));
	}
	
	public void testIsEquivalentExp04(){
		String answer = "2*(3-1)+(4/5)";
		assertTrue(MathUtils.isEquivalentExp(answer, answer));
		assertTrue(MathUtils.isEquivalentExp(answer, "(3-1)*2+(4/5)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(4/5)+(3-1)*2"));
		
		assertFalse(MathUtils.isEquivalentExp(answer, "(1-3)*3+(4/5)"));
		assertFalse(MathUtils.isEquivalentExp(answer, "3*(3-1)+(4/5)"));
		assertFalse(MathUtils.isEquivalentExp(answer, "(3/5)+(3-1)*2"));
	}
	
	public void testIsEquivalentExp05(){
		String answer = "(2+3)*(4+5)+1";
		assertTrue(MathUtils.isEquivalentExp(answer, answer));
		assertTrue(MathUtils.isEquivalentExp(answer, "(2+3)*(4+5)+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(4+5)*(2+3)+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(4+5)*(3+2)+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+(4+5)*(3+2)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+(2+3)*(5+4)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+(3+2)*(4+5)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+(3+2)*(5+4)"));
		
		assertTrue(MathUtils.isEquivalentExp(answer, "(5+4)*(3+2)+1"));
		
		assertFalse(MathUtils.isEquivalentExp(answer, "(2+3)*(4+5)+2"));
		assertFalse(MathUtils.isEquivalentExp(answer, "(4+5)*(2+5)+1"));
	}
	
	public void testIsEquivalentExp06(){
		String answer = "1+2+3";
		assertTrue(MathUtils.isEquivalentExp(answer, answer));
		assertTrue(MathUtils.isEquivalentExp(answer, "2+1+3"));
		assertTrue(MathUtils.isEquivalentExp(answer, "2+3+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+3+2"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3+1+2"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3+2+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+3+2"));
		
		assertTrue(MathUtils.isEquivalentExp(answer, "3+(1+2)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "2+(1+3)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(2+3)+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+(3+2)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3+(1+2)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(3+2)+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(1+3)+2"));
		
		assertFalse(MathUtils.isEquivalentExp(answer, "1+3+3"));
		assertFalse(MathUtils.isEquivalentExp(answer, "1+2+2"));
		assertFalse(MathUtils.isEquivalentExp(answer, "2+3+3"));
	}
	
	public void testIsEquivalentExp07(){
		String answer = "1*2*3";
		assertTrue(MathUtils.isEquivalentExp(answer, answer));
		assertTrue(MathUtils.isEquivalentExp(answer, "2*1*3"));
		assertTrue(MathUtils.isEquivalentExp(answer, "2*3*1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1*3*2"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3*1*2"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3*2*1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1*3*2"));
		
		assertTrue(MathUtils.isEquivalentExp(answer, "3*(1*2)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "2*(1*3)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(2*3)*1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1*(3*2)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3*(1*2)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(3*2)*1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(1*3)*2"));
		
		assertFalse(MathUtils.isEquivalentExp(answer, "1*3*3"));
		assertFalse(MathUtils.isEquivalentExp(answer, "1*2*2"));
		assertFalse(MathUtils.isEquivalentExp(answer, "2*3*3"));
	}
	
	public void testIsEquivalentExp08(){
		String answer = "1+2+3+4";
		assertTrue(MathUtils.isEquivalentExp(answer, answer));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+2+4+3"));
		assertTrue(MathUtils.isEquivalentExp(answer, "2+1+4+3"));
		assertTrue(MathUtils.isEquivalentExp(answer, "2+1+3+4"));
		assertTrue(MathUtils.isEquivalentExp(answer, "4+2+3+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "4+2+1+3"));
		assertTrue(MathUtils.isEquivalentExp(answer, "4+3+2+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "4+3+1+2"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3+4+1+2"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3+4+2+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+4+2+3"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+4+3+2"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+3+2+4"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+3+4+2"));
		assertTrue(MathUtils.isEquivalentExp(answer, "2+3+4+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "2+3+1+4"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3+2+1+4"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3+2+4+1"));
		
		assertTrue(MathUtils.isEquivalentExp(answer, "1+(2+3+4)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+(2+4)+3"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(2+1+4)+3"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(2+1)+3+4"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(1+2)+(3+4)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(1+2)+(4+3)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(4+3)+(1+2)"));
		
//		assertTrue(MathUtils.isEquivalentExp("(1+2)+(3+4)", "(4+2)+(3+1)"));
//		assertTrue(MathUtils.isEquivalentExp(answer, "(4+2)+(3+1)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "(4+2+1)+3"));
		assertTrue(MathUtils.isEquivalentExp(answer, "4+(3+2+1)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "4+3+(1+2)"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3+(4+1)+2"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3+4+2+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+4+2+3"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+4+3+2"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+3+2+4"));
		assertTrue(MathUtils.isEquivalentExp(answer, "1+3+4+2"));
		assertTrue(MathUtils.isEquivalentExp(answer, "2+3+4+1"));
		assertTrue(MathUtils.isEquivalentExp(answer, "2+3+1+4"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3+2+1+4"));
		assertTrue(MathUtils.isEquivalentExp(answer, "3+2+4+1"));
		
		assertFalse(MathUtils.isEquivalentExp(answer, "5+2+3+1"));
		assertFalse(MathUtils.isEquivalentExp(answer, "1+2+3"));
		assertFalse(MathUtils.isEquivalentExp(answer, "1+2+3+4+4"));
	}
}
