package com.ibm.eti.eteacher.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

public class SampleQuestions {

	public static Map<String, String> answerMap = new HashMap<String, String>();
	public static List<String> questions = new ArrayList<String>();
	private static boolean isLoaded = false;
	
//	static{
//		questions.add("举头望明月");
//		questions.add("春眠�?觉晓");
//		questions.add("飞�?直下三�?�尺");
//		
//		answerMap.put("举头望明月", "低头�?故乡");
//		answerMap.put("春眠�?觉晓", "处处闻啼鸟");
//		answerMap.put("飞�?直下三�?�尺", "疑是银河�?��?天");
//	}
	
	public static boolean isCorrect(String que, String ans){
		String rightAnswer = answerMap.get(que);
		if (rightAnswer != null && rightAnswer.equals(ans)){
			return true;
		}
		else{
			return false;
		}
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
					while(line != null){
						String[] ss = line.split("=");
						questions.add(ss[0]);
						answerMap.put(ss[0], ss[1]);
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
