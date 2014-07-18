package com.phoenix.eteacher.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MathUtils {
	
	private static class Node{
		private String value = null;
		private Node left = null;
		private Node right = null;
		private boolean isOpt;
		private boolean isTree = false;
		private boolean noSwitch = false;
		private boolean noChildSwitch = false;
		private boolean noGrandchildSwitch = false;
		
		public Node(String value){
			this.value = value;
			setType(value);
		}
		
		private void setType(String value) {
			this.value = value;
			setType();
		}
		
		private void setType() {
			if(isOperator(value)){
				this.isOpt = true;
			}
			else if(isFloatNum(value)){
				this.isOpt = false;
			}
			else{
				throw new RuntimeException("Node value must be either operator or a number!");
			}
		}

		public Node(String value, Node left, Node right){
			this.value = value;
			this.left = left;
			this.right = right;
			setType(value);
		}
		
		public boolean isOpt() {
			return isOpt;
		}

		
		public void setOpt(boolean isOpt) {
			this.isOpt = isOpt;
		}
		
		

		public boolean isTree() {
			return isTree;
		}

		public void setTree(boolean isTree) {
			this.isTree = isTree;
		}

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public Node getLeft() {
			return left;
		}
		public void setLeft(Node left) {
			this.left = left;
		}
		public Node getRight() {
			return right;
		}
		public void setRight(Node right) {
			this.right = right;
		}

		private static boolean isOperator(String s){
			return "+-*/".indexOf(s) != -1;
		}
		
		private static boolean isNum(String s){
			try{
				Integer.parseInt(s);
				return true;
			}
			catch(Exception ex){
				return false;
			}
		}
		
		private static boolean isFloatNum(String s){
			try{
				Float.parseFloat(s);
				return true;
			}
			catch(Exception ex){
				return false;
			}
		}
		
		public void print(boolean printValue){
			if(printValue){
				System.out.println(this.value);
			}
			if(this.left != null){
				System.out.print(" left: " + this.left.getValue());
				this.left.print(false);
			}
			else{
				System.out.print("         ");
			}
			if(this.right != null){
				System.out.println(" right: " + this.right.getValue());
				this.right.print(false);
			}
		}
		
		public int nodeCount(){
			int i = 1;
			if(this.left != null){
				i += this.left.nodeCount();
			}
			if(this.right != null){
				i += this.right.nodeCount();
			}
			return i;
		}
		
		public boolean isExpression(){
			if (isOpt && (left == null || right == null)){
				return false;
			}
			if(!isOpt && (left != null || right != null)){
				return false;
			}
			return true;
		}
		
		@Override
		public boolean equals(Object obj){
			if(obj instanceof Node){
				Node other = (Node)obj;
				if(!this.value.equals(other.value)){
					return false;
				}
				
				if(this.left != null){
					if(!left.equals(other.left)){
						return false;
					}
				}
				else if(other.left != null){
					return false;
				}
				
				if(this.right != null){
					if (!right.equals(other.right)){
						return false;
					}
				}
				else if(other.right != null){
					return false;
				}
				return true;
			}
			else{
				return false;
			}
		}
		
		@Override
		public int hashCode(){
			return this.value.hashCode();
		}
		
		private Set<Node> getIdenticalTrees(){
			Set<Node> trees = new HashSet<Node>();
			Set<Node> leftTrees = null;
			Set<Node> rightTrees = null;
			trees.add(this);
			if(this.left == null && this.right == null){
				return trees;
			}
			
			if(this.left != null){
				leftTrees = this.left.getIdenticalTrees();
				for(Node tree : leftTrees){
					trees.add(new Node(this.value, tree, this.right));
				}
			}
			
			if(this.right != null){
				rightTrees = this.right.getIdenticalTrees();
				for(Node tree : rightTrees){
					trees.add(new Node(this.value, this.left, tree));
				}
			}
			
			if(leftTrees != null && rightTrees != null){
				for(Node ltree : leftTrees){
					for(Node rtree : rightTrees){
						trees.add(new Node(this.value, ltree, rtree));
					}
				}
			}
			
			if (!noSwitch && (this.value.equals("+") || this.value.equals("*"))){
				Node tree = new Node(this.value, this.right, this.left);
				//set this to avoid infinite loop
				tree.noSwitch = true;
				trees.addAll(tree.getIdenticalTrees());
			}
			
			if ((this.value.equals("+") && this.left.value.equals("+")) || (this.value.equals("*") && this.left.value.equals("*"))){
				if(!noChildSwitch){
					Node lNode = new Node(this.left.value, this.right, this.left.right);
					Node tree = new Node(this.value, lNode, this.left.left);
					//set this to avoid infinite loop
					tree.noChildSwitch = true;
					trees.addAll(tree.getIdenticalTrees());
					
					lNode = new Node(this.left.value, this.left.left, this.right);
					tree = new Node(this.value, lNode, this.left.right);
					//set this to avoid infinite loop
					tree.noChildSwitch = true;
					trees.addAll(tree.getIdenticalTrees());
				}
			}
			
//			if ((this.value.equals("+") && this.left.value.equals("+") && this.right.value.equals("+")) || (this.value.equals("*") && this.left.value.equals("*") && this.right.value.equals("*"))){
//				if(!noGrandchildSwitch){
//					Node lNode = new Node(this.left.value, this.right.left, this.left.right);
//					Node rNode = new Node(this.right.value, this.left.left, this.right.right);
//					Node tree = new Node(this.value, lNode, rNode);
//					//set this to avoid infinite loop
//					tree.noGrandchildSwitch = true;
//					trees.addAll(tree.getIdenticalTrees());
//				}
//			}
			
			if (this.value.equals("*") && (this.left.value.equals("+") || this.left.value.equals("-"))){
				Node lNode = new Node(this.value, this.left.left, this.right);
				Node rNode = new Node(this.value, this.left.right, this.right);
				Node newNode = new Node(this.left.value, lNode, rNode);
				trees.addAll(newNode.getIdenticalTrees());
			}
			return trees;
		}
	}
	
	public static boolean getPostfixExp(String exp, List<Node> sb){
		Stack<String> st = new Stack<String>();
		List<String> eval = new ArrayList<String>();
		String popUp;
		for(int i = 0; i < exp.length(); i++){
			char op = exp.charAt(i);
			if(isOpt(op)){
				if(st.isEmpty() || st.peek().equals("(") || isHigher(op,st.peek())){
					st.push(op+"");
				}
				else{
					popUp = st.pop();
					if(!check(popUp)){
						return false;
					}
					sb.add(new Node(popUp));
					eval.add(popUp);
					st.push(op+"");
				}
			}
			else if(op == '('){
				st.push(op+"");
			}
			else if(op == ')'){
				if(st.size() < 1){
					return false;
				}
				while(!st.peek().equals("(")){
					popUp = st.pop();
					if(!check(popUp)){
						return false;
					}
					sb.add(new Node(popUp));
					eval.add(popUp);
					if(st.size() < 1){
						return false;
					}
				}
				st.pop();
			}
			else{
				int j = 0;
				while(i+j<exp.length() && isNumOrDot(exp.charAt(i+j))){
					j++;
				}
				if(!check(exp.substring(i, i+j))){
					return false;
				}
				sb.add(new Node(exp.substring(i, i+j)));
				eval.add(exp.substring(i, i+j));
				i = i + j - 1;
			}
		}
		while(!st.isEmpty()){
			popUp = st.pop();
			if(!check(popUp)){
				return false;
			}
			sb.add(new Node(popUp));
			eval.add(popUp);
		}
		for(int i = 0; i < eval.size(); i++){
			if (isOpt(eval.get(i))){
				if(st.size() < 2){
					return false;
				}
				float b = Float.parseFloat(st.pop());
				float a = Float.parseFloat(st.pop());
				st.push(String.valueOf(compute(eval.get(i).charAt(0),a,b)));
			}
			else{
				st.push(eval.get(i));
			}
		}
		while(!st.isEmpty()){
			st.pop();
		}
		return true;
	}
	
	private static boolean check(String ele) {
		return isFloatNum(ele) || isOpt(ele);
	}

	private static boolean isFloatNum(String s){
		try{
			Float.parseFloat(s);
			return true;
		}
		catch(Exception ex){
			return false;
		}
	}
	
	
	
	public static Node getExpressionTree(String exp){
		List<Node> nodes = new ArrayList<Node>();
		getPostfixExp(exp, nodes);
		return getExpressionTree(nodes);
	}
	
	public static Node getExpressionTree(List<Node> nodes){
		while(!isTreeCompleted(nodes)){
			for(int i = 0; i < nodes.size(); i++){
				if(nodes.get(i).isOpt() && !nodes.get(i-1).isOpt() && !nodes.get(i-2).isOpt()){
					Node newNode = new Node(nodes.get(i).getValue());
					newNode.setLeft(nodes.get(i-2));
					newNode.setRight(nodes.get(i-1));
					newNode.setOpt(false);
					newNode.setTree(true);
					nodes.remove(i-2);
					nodes.remove(i-2);
					nodes.remove(i-2);
					nodes.add(i-2,newNode);
					i = i-2;
					continue;
				}
			}
		}
		if(nodes.size() != 1){
			return null;
		}
		return nodes.get(0);
	}
	
	private static boolean isTreeCompleted(List<Node> nodes) {
		for(Node node : nodes){
			if(node.isOpt() && !node.isTree()){
				 return false;
			}
		}
		return true;
	}

	
	
	public static boolean isEquivalentExp(String correctExp, String exp){
		Node correntTree = getExpressionTree(preprocess(correctExp));
		Node tree = getExpressionTree(preprocess(exp));
		Set<Node> sameTrees = correntTree.getIdenticalTrees();
		return sameTrees.contains(tree);
	}

	private static String preprocess(String exp) {
		return exp.replaceAll("×", "*");
	}

	private static boolean isNum(char s){
		try{
			Integer.parseInt(s+"");
			return true;
		}
		catch(Exception ex){
			return false;
		}
	}
	
	private static boolean isNumOrDot(char s){
		return isNum(s) || s == '.';
	}
	
	private static float compute(char op, float a, float b){
		float r;
		switch(op){
		case '+':
			r = a + b;
			break;
		case '-':
			r = a - b;
			break;
		case '*':
			r = a * b;
			break;
		case '/':
			r = a / b;
			break;
		default: 
			r = 0;
			break;
		}
		return r;
	}
	
	private static boolean isOpt(char s){
		return "+-*/".indexOf(s) != -1;
	}
	
	private static boolean isOpt(String s){
		return "+-*/".indexOf(s) != -1;
	}
	
	private static boolean isHigher(char o, String op){
		return "*/".indexOf(o) != -1 && "+-".indexOf(op) != -1;
	}
	
	public static boolean isTheSame(String exp1, String exp2){
		ExpressionTree et1 = new ExpressionTree(exp1);
		ExpressionTree et2 = new ExpressionTree(exp2);
		return (et1.isTheSame(et2));
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String exp = "(1+3)*2";
		
		System.out.println();
		exp = "(1+2)*(3+4)*5";
		isEquivalentExp(exp,exp);
	}

	public static ArrayList<String> appendResToStr(Matcher content){
		ArrayList<String> res = new ArrayList<String>();
		while(content.find()){
			res.add(content.group());
		}
		return res;
	}
	
	public static ArrayList<String> returnExpressionForEachLine(String line){
		Pattern p = Pattern.compile("([\\d\\+\\-\\*\\/\\(\\)\\.]+)");
		Matcher matcherAns = p.matcher( line );
		ArrayList<String> ansExpression = MathUtils.appendResToStr(matcherAns);
		return ansExpression;
	}

	public static boolean isExpression(String exp){
		List<Node> postfix = new ArrayList<Node>();
		if(!getPostfixExp(exp, postfix)){
			return false;
		}
		for(Node node : postfix){
			System.err.println("snow: " + node.getValue());
		}
		Node tree = getExpressionTree(postfix);
		if(tree == null){
			return false;
		}
		tree.setType();
		return tree.isExpression();
	}
}
