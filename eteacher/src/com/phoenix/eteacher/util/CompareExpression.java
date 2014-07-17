package com.phoenix.eteacher.util;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompareExpression {
	public static boolean isTheSame(String exp1, String exp2){
		ExpressionTree et1 = new ExpressionTree(exp1);
		ExpressionTree et2 = new ExpressionTree(exp2);
		return (et1.isTheSame(et2));
	}
	public static void main(String[] args){
		String rightAnswera = "1+3+3";
		String answera = "1+(2+3*3+3*4)*2";
		String ans = "(1 + 2)*( 3 + 4)/5";
		String ans1 = "(1 + 2)/5*( 3 + 4)";
//		ExpressionTree et = new ExpressionTree(ans);
		ExpressionTree et1 = new ExpressionTree(ans1);
//		et.printTree();
		
		System.out.println("Now");
		System.out.println("Now");
//		ArrayList<String> opt = et.sortAndOutput();
//		ArrayList<String> opt1 = et1.sortAndOutput();
//		et.printTree();
		for (int i = 0; i < et1.finalExpression.size(); ++i)
			System.out.println(et1.finalExpression.get(i));
		System.out.println("Now");
		et1.printExp();
		//for (int i = 0; i < opt.size(); ++i)
		//	System.out.println(opt.get(i));
		System.out.println(isTheSame(ans, ans1));
		
	}

}
