package com.phoenix.eteacher.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;
import java.util.StringTokenizer;

public class ExpressionTree {
	public class Node{
		public String value;
		public Node leftChild;
		public Node rightChild;
		public Node(String value){
			this.value = value;
			leftChild = null;
			rightChild = null;
		}
	}
	Node root;
	private ArrayList<String> postfixExp = new ArrayList<String>();
	public ArrayList<String> finalExpression;
	//Currently only support + - * and simple /, but not support the negative numbers such as (-1)
	public ExpressionTree(){
		this.root = null;
	}
	
    public ExpressionTree(String expression){
		root = null;
		if ( validateExpression(expression) == false )
			return;
		else 
		   constructExpressionTree(expression);
		this.sortAndOutput();
	}
	
	private boolean validateExpression(String expression) {
		//TODO   
		return true;
	}
	
	private void constructExpressionTree(String expression){
		String expressionFormat = dealWithParenthisAndSpaces(expression);
//		System.out.println("hhh" + expressionFormat);
		transfer(expressionFormat);
		//Contruct the tree based on the postfixExpression
		constructTree();
	}
	
	private void constructTree() {
		Stack<Node> elements = new Stack<Node>();
		for ( int i = 0; i < postfixExp.size(); ++i ){
			if (!isoperator(postfixExp.get(i))){
				Node node = new Node(postfixExp.get(i));
				elements.push(node);
			}
			else{
				Node node = new Node(postfixExp.get(i));
				node.rightChild = elements.pop();
				node.leftChild = elements.pop();
				// node is temply the highest level node
				root = node;
				elements.push(node);
			}
		}
	}
	
	//Only multiply and plus supports
	private void refineTree(){
		Boolean res1 = true, res2 = true;
		this.dealWithMinus(this.root);
		this.dealWithDivide(this.root);
//		this.printTree();
		while( res1 || res2 ){
			res1 = refineEachOneLeft(root);
			res2 = refineEachOneRight(root);
		}
		return;
	}
	

	private boolean refineEachOneLeft(Node node) {
		//if node == null, the second expression would not run
		Boolean res = false, res1, res2;
		if (node == null || node.leftChild == null)
			return false;
		if ( node.value.equals("*") && node.leftChild.value.equals("+") ){
			Node right = node.rightChild;
			Node left1 = node.leftChild.leftChild;
			Node left2 = node.leftChild.rightChild;
			Node newRight = new Node("*");
			node.value = "+";
			node.leftChild.value = "*";
			node.leftChild.leftChild = left1;
			node.leftChild.rightChild = right;
			
			node.rightChild = newRight;
			newRight.leftChild = left2;
			newRight.rightChild = cloneTree(right);
			
			res = true;
		}
		res1 = refineEachOneLeft(node.leftChild);
		res2 = refineEachOneLeft(node.rightChild);
		if ( res || res1 || res2 )
			return true;
		return false;
	}
	
	private boolean refineEachOneRight(Node node) {
		//if node == null, the second expression would not run
		Boolean res = false, res1, res2;
		if (node == null || node.rightChild == null)
			return false;
		if ( node.value.equals("*") && node.rightChild.value.equals("+") ){
			Node left = node.leftChild;
			//if it's an operator, then right child and left child won't be null
			Node right1 = node.rightChild.leftChild;
			Node right2 = node.rightChild.rightChild;
			Node newLeft = new Node("*");
			node.value = "+";
			node.rightChild.value = "*";
			node.rightChild.leftChild = left;
			node.rightChild.rightChild = right1;
			
			node.leftChild = newLeft;
			newLeft.leftChild = cloneTree(left);
			newLeft.rightChild = right2;
			res = true;
		}
		res1 = refineEachOneRight(node.leftChild);
		res2 = refineEachOneRight(node.rightChild);
		if (res || res1 || res2)
			return true;
		return false;
	}
	
	private Node cloneTree(Node node) {
		if ( node == null )
			return null;
		Node nodeNew = new Node(node.value);
		nodeNew.leftChild = cloneTree(node.leftChild);
		nodeNew.rightChild = cloneTree(node.rightChild);
		return nodeNew;
	}
	public void sortAndOutput(){
		this.refineTree();
		ArrayList<String> plusElements = new ArrayList<String>();
		format(root, plusElements);
		this.finalExpression = plusElements;
	}
	
	//Only support multiply and plus
	private void format(Node node, ArrayList<String> plusElements) {
		if (node == null)
			return;
//		System.out.println(node.value);
		if (!isoperator(node.value)){
			plusElements.add(node.value);
			return;
		}	
		else if(node.value.equals("*")){
			plusElements.add(formatMultiply(node));
			return;
		}
		//need to add the support to divide and minus
		else 
			;
		format(node.leftChild, plusElements);
		format(node.rightChild, plusElements);
	}
	
	private String formatMultiply(Node node) {
        ArrayList<String> mulElements = new ArrayList<String>();
        treeToList(node, mulElements);
        Collections.sort(mulElements);
		return mulElements.toString();
	}
	
	private void treeToList(Node node, ArrayList<String> mulElements) {
		if (node == null)
			return;
		if ( node.value.equals("1") )
			return;
		if ( !isoperator(node.value) )
			mulElements.add(node.value);
		treeToList(node.leftChild, mulElements);
		treeToList(node.rightChild, mulElements);
	}
	
	public void printTree(){
		visitTree(root);
	}
	
	private void visitTree(Node node) {
		if (node == null)
			return;
		visitTree(node.leftChild);
		visitTree(node.rightChild);
		System.out.println(node.value);
	}
	
	//transfer the expression to the postfix expression
	private void transfer(String expression) {
		ArrayList<String> expressionList = new ArrayList<String>();
		StringTokenizer el = new StringTokenizer(expression, "+-*/()", true);
		//The stack to store the operators
		Stack<String> operator = new Stack<String>();
		while(el.hasMoreTokens())
			expressionList.add(el.nextToken());
		//to deal with the expression
		for ( int i = 0; i < expressionList.size(); ++i ){
			if ( expressionList.get(i).toString().equals("(") )
				operator.push("(");
			else if ( isoperator(expressionList.get(i)) ){
				if ( operator.isEmpty() )
					operator.push(expressionList.get(i));
				else if (ordar(expressionList.get(i)) <= ordar(operator.peek()) ){
					//小于等于是因为，如果是小于，相同优先级的运算就是右结合，例如a*b*c为a*(b*c)，小于等于就是左结合
					postfixExp.add(operator.pop());
					//operator.push(expressionList.get(i));
					//很恶心，希望有更好的方法。这里因为在pop了一个之后，可能前面还有运算优先级高的算符，所以需要向前搜索，但是可能会碰到括号之类的，所以用--i的方式让其重新进入第i层循环
					--i;
					continue;
				}
				else
					operator.push(expressionList.get(i));
			}
			else if (expressionList.get(i).equals(")") ){
				String op = null;
				while ( !(op = operator.pop()).equals("(")  )
					postfixExp.add(op);
			}
			else 
				postfixExp.add(expressionList.get(i));
		}
		// After reading the expression, some of the operators still remain in the stack
		while (!operator.isEmpty())
			postfixExp.add(operator.pop());	
	}
	
	private int ordar(String operator) {
		if ( operator.equals("*") || operator.equals("/") )
			return 2;
		//if searched the "(", return to calculation, but don't pop it out until we have ")".
		else if ( operator.equals("(") )
			return 0;
		return 1;
	}
	
    private boolean isoperator(String object) {
		if (object.equals("*") || object.equals("/") || object.equals("+") || object.equals("-"))
			return true;
		return false;
	}
	
	public void printExp(){
		for (int i = 0; i < postfixExp.size(); ++i)
			System.out.println(postfixExp.get(i));
	}
	
	public boolean isTheSame(ExpressionTree tree2){
		ArrayList<String> tr1 = this.finalExpression;
		ArrayList<String> tr2 = tree2.finalExpression;
		if ( tr1.size() == tr2.size() ){
			Collections.sort(tr1);
			Collections.sort(tr2);
			for ( int i = 0; i < tr1.size(); ++i ){
				if ( !tr1.get(i).equals(tr2.get(i)) )
					return false;
			}
			return true;	
		}
		return false;
	}

	private void dealWithMinus(Node node){
		if ( node == null )
			return;
		if ( node.value.equals("-") ){
			node.value = "+";
			//Already parsed the number and operators, so no affection to the trees
			Node right = new Node("*");
			Node rightnum = new Node("-1");
			right.leftChild = rightnum;
			right.rightChild = node.rightChild;
			node.rightChild = right;
			dealWithMinus(right.rightChild);
			dealWithMinus(node.leftChild);
		}
		else{
		    dealWithMinus(node.leftChild);
		    dealWithMinus(node.rightChild);
		}
	}
	
	private void dealWithDivide(Node node){
		//Can only deal with the simple case, that is like 1/3
		if ( node == null )
			return;
		if ( node.value.equals("/") ){
			node.value = "*";
			//Already parsed the number and operators, so no affection to the trees
			node.rightChild.value = "1/" + node.rightChild.value;
			dealWithDivide(node.rightChild.rightChild);
			dealWithDivide(node.rightChild.leftChild);
			dealWithDivide(node.leftChild);
		}
		else{
		    dealWithDivide(node.leftChild);
		    dealWithDivide(node.rightChild);
		}
	}
	
	private String dealWithParenthisAndSpaces(String exp){
		String exp1 = exp.replace('×', '*');
		exp1 = exp1.replace(" ","");
		exp1 = exp1.replace('{','(');
		exp1 = exp1.replace('[','(');
		exp1 = exp1.replace('}',')');
		exp1 = exp1.replace(']',')');
		exp1 = exp1.replace('{','(');
		return exp1;
	}
}
