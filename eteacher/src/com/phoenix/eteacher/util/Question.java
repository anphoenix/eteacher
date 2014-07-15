package com.phoenix.eteacher.util;

public class Question{
	public static int TYPE_CHOICE = 2;
	public static int TYPE_COMPLETION = 1;
	public static int TYPE_APPLITION = 3;
	Integer ID;
	String question;
	String answer;
	Integer type;
	public Question(Integer type, Integer ID, String question, String answer) {
		super();
		this.ID = ID;
		this.question = question;
		this.answer = answer;
		this.type = type;
	}
	public Integer getID() {
		return ID;
	}
	public void setID(Integer iD) {
		ID = iD;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getReadableQuestion(Integer index){
		StringBuilder builder = new StringBuilder();
		builder.append("第" + (index+1) + "题 (" + getReadableType() + ")\n");
		builder.append(question);
		builder.append("\n你的答案:");
		return builder.toString();
	}
	
	public String getReadableType(){
		if(type == TYPE_CHOICE) return "选择题";
		else if(type == TYPE_COMPLETION) return "填空题";
		else if(type == TYPE_APPLITION) return "应用题";
		else return "附加题";
	}
	
	public static Question createQuestion(String line){
		String[] ss = line.split("#");
		Integer type = Integer.parseInt(ss[0]);
		Integer ID = Integer.parseInt(ss[1]);
		String[] ques = ss[2].split("&");
		String question = ques[0];
		if(type == Question.TYPE_CHOICE){
			StringBuilder builder = new StringBuilder();
			String[] choices = question.split("  ");
			for(int i = 0; i < choices.length; i++) {
				builder.append(choices[i]);
				builder.append("\n");
			}
			question = builder.toString();
		}
		return new Question(type, ID, question, ques[1]);
	}
	
	public static void main(String[] args){
		String line = "2#4#1+2的结果是:  (A) 1  (B) 2  (C) 3  (D) 4=C";
		Question q = Question.createQuestion(line);
		System.out.println(q.getReadableQuestion(1));
	}
}
