package com.phoenix.eteacher.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import android.content.Context;

public class SampleQuestions {
	public static Map<Integer, Question> questions = new HashMap<Integer, Question>();
	private static boolean isLoaded = false;
	
	public static boolean isCorrect(Integer index, String ans){
		String rightAnswer = questions.get(index).answer;
		if (rightAnswer != null && rightAnswer.equalsIgnoreCase(ans)){
			return true;
		}
		else{
			return false;
		}
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
}
