package com.phoenix.eteacher;

import java.util.List;

import com.phoenix.eteacher.R;
import com.phoenix.eteacher.util.SampleQuestions;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ResultDisplayActivity extends Activity {

    private TextView mResultText;
   
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        setTitle(R.string.result_title);

        mResultText = (TextView) findViewById(R.id.resultTextField);       
        
        mResultText.setText(getGrade());
        
        connectEvents();
    }
    
    private void connectEvents(){
    	findViewById(R.id.vo_tw_redoButton).setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Intent intent = new Intent();
	        		TestActivity.reset();
	        		intent.setClass(v.getContext(), TestActivity.class);
	        		v.getContext().startActivity(intent);
		        	finish();
	        	}
	      });
    }


	private String getGrade() {
		List<String> qs = SampleQuestions.questions;
		StringBuilder builder = new StringBuilder();
		int rightCount = 0;
		for(int i = 0; i < qs.size(); i++){
			if (SampleQuestions.isCorrect(qs.get(i), TestActivity.answers.get(i))){
				rightCount++;
			} else {
				builder.append("第" + (i + 1) + "问题答错了，正确答案是："+SampleQuestions.getCorrectAnswer(qs.get(i)) +" ,输入答案是：" + TestActivity.answers.get(i) +"\n");
			}
		}
		String res = "一共" + qs.size() + "题，你答对了" + rightCount + "题\n\n";
		
		builder.append("\n\n 重新答题");
		return res + builder.toString();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		TestActivity.curQuestionIndex = 0;
		TestActivity.answers.clear();
	}
}
