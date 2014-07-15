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
		String[] rightAnswerArr = rightAnswer.split("\\|");
		Pattern p = Pattern.compile("[\\u4e00-\\u9fa5]+|\\d+");
		Matcher rightAns = p.matcher(rightAnswerArr[rightAnswerArr.length - 1]);
		String[] answAll = ans.split("\n");
		String myLastLine = answAll[answAll.length - 1];
//		myLastLine = myLastLine.replaceAll("\\s*", "");
		Matcher myAns = p.matcher(myLastLine);
		ArrayList<String> myAnsList = appendResToStr(myAns);
		ArrayList<String> rightAnsList = appendResToStr(rightAns);
		return myAnsList.containsAll(rightAnsList) && containsKeypoint(ans, getKeyPoints(rightAnswerArr));
	}
	
	private static String[] getKeyPoints(String[] rightAnswerArr) {
		String[] kp = new String[rightAnswerArr.length-1];
		System.arraycopy(rightAnswerArr, 0, kp, 0, rightAnswerArr.length-1);
		return kp;
	}

	private static boolean containsKeypoint(String ans, String[] keyp) {
		//remove all white spaces except the new line feed
		String replaced = ans.replaceAll("\n", "@").replaceAll("\\s*", "").replaceAll("@", "\n");
		for(String kp : keyp){
			if(replaced.contains(kp)){
				return true;
			}
		}
		return false;
	}

	public static int size(){
		return questions.size();
	}
	
	public static String getCorrectAnswer(Integer index){
		Question q = questions.get(index);
		String ans = q.answer;
		if(q.type == Question.TYPE_APPLITION){
			String[] ss = ans.split("\\|");
			return ss[ss.length - 1];
		}
		else{
			return ans;
		}
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
	public static ArrayList<String> appendResToStr(Matcher content){
		ArrayList<String> res = new ArrayList<String>();
		while(content.find()){
			res.add(content.group());
		}
		return res;
	}
}
