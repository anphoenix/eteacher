package com.phoenix.eteacher;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.phoenix.eteacher.util.SampleQuestions;

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
		
		StringBuilder builder = new StringBuilder();
		int rightCount = 0;
		for(int i = 0; i < SampleQuestions.size(); i++){
			if (SampleQuestions.isCorrect(i, TestActivity.answers.get(i))){
				rightCount++;
			} else {
				builder.append("第" + (i + 1) + "题答错了,");
				builder.append("正确答案是："+SampleQuestions.getCorrectAnswer(i));
				if(TestActivity.answers.get(i).length() > 0)
					builder.append(" ,输入答案是：" + TestActivity.answers.get(i) +"\n");
			}
		}
		builder.insert(0, "一共" + SampleQuestions.size() + "题，你答对了" + rightCount + "题\n\n");
		
		builder.append("\n\n 重新答题\n");
		return builder.toString();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		TestActivity.curQuestionIndex = 0;
		TestActivity.answers.clear();
	}
}
