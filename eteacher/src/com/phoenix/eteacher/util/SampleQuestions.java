package com.phoenix.eteacher.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

import android.content.Context;

public class SampleQuestions {
	public static Map<Integer, Question> questions = new HashMap<Integer, Question>();
	private static boolean isLoaded = false;
	
	public static boolean isCorrect(Integer index, String ans){
		if (questions.get(index).type == Question.TYPE_APPLITION)
			return isCorrectApplication(index, ans);
		String rightAnswer = questions.get(index).answer;
		ans = ans.replaceAll("\\s*", "");
		if (rightAnswer != null && rightAnswer.equalsIgnoreCase(ans)){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean isCorrectApplication(Integer index, String ans){
		String rightAnswer = questions.get(index).answer;
//		ans = ans.replaceAll("\\s*", "");
		Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
		Matcher rightAns = p.matcher(rightAnswer);
		String[] answAll = ans.split("\n");
		String myLastLine = answAll[answAll.length - 1];
		myLastLine = myLastLine.replaceAll("\\s*", "");
		Matcher myAns = p.matcher(myLastLine);
		ArrayList myAnsList = appendResToStr(myAns);
		ArrayList rightAnsList = appendResToStr(rightAns);
		if ( isContaining( myAnsList, rightAnsList ) )
			return true;
		return false;
		
	}
	
	public static int size(){
		return questions.size();
	}
	
	public static String getCorrectAnswer(Integer index){
		return questions.get(index).answer;
	}
	
	public static Question getQuestion(Integer index){
		return questions.get(index);
	}
	
	public static String getReadableQuestion(Integer index){
		return questions.get(index).getReadableQuestion(index);
	}
	
	

	public static void load(Context context) {
		if (!isLoaded){
			SimpleResourceHelper helper = new SimpleResourceHelper(context);
			String[] paths = helper.getResourcePaths(new String[]{"tests.txt"});
			for(String path : paths){
				BufferedReader reader = null;
				try{
					reader = new BufferedReader(new FileReader(path));
					String line = reader.readLine();
					int count = 0;
					while(line != null){
						questions.put(count++, Question.createQuestion(line));
						line = reader.readLine();
					}
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				finally{
					if(reader != null){
						try {
							reader.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			isLoaded = true;
		}
	}
	public static ArrayList appendResToStr(Matcher content){
		ArrayList res = new ArrayList();
		while(content.find()){
			res.add(content.group());
		}
		return res;
	}
	public static Boolean isContaining(ArrayList source, ArrayList target){
		Integer sourceNum = source.size();
		Integer targetNum = target.size();
		Integer i, j;
		Boolean res = false, tmp = false;
		res = source.containsAll(target);
		return res;
	}
}
