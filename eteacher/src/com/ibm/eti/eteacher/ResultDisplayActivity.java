package com.ibm.eti.eteacher;

import java.util.List;

import com.ibm.eti.eteacher.util.SampleQuestions;

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
    }


	private String getGrade() {
		List<String> qs = SampleQuestions.questions;
		int rightCount = 0;
		for(int i = 0; i < qs.size(); i++){
			if (SampleQuestions.isCorrect(qs.get(i), TestActivity.answers.get(i))){
				rightCount++;
			}
		}
		String res = rightCount + " out of " + qs.size() + " questions correct!";
		return res;
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		TestActivity.curQuestionIndex = 0;
		TestActivity.answers.clear();
	}
}
