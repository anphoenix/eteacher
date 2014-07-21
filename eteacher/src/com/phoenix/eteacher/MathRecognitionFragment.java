package com.phoenix.eteacher;

import com.phoenix.eteacher.R;
import com.phoenix.eteacher.util.SampleQuestions;
import com.phoenix.eteacher.util.SimpleResourceHelper;
import com.phoenix.eteacher.view.CustomEditText;
import com.visionobjects.math.MathWidgetApi;
import com.visionobjects.myscript.certificate.MyCertificate;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MathRecognitionFragment extends Fragment{
	
	private TestActivity  activity = null;
	private MathWidgetApi mWidget;
	private CustomEditText mEditText = null;
	private TextView mLabelText;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.math_fragment, container, false);
	    
	    mWidget = (MathWidgetApi) view.findViewById(R.id.vo_math_widget);
	    this.activity.setmMathWidget(mWidget);
	    mWidget.setOnConfigureListener(this.activity);
	    mWidget.setOnRecognitionListener(this.activity);
	    mWidget.setOnGestureListener(this.activity);
	    mWidget.setOnWritingListener(this.activity);
	    mWidget.setOnTimeoutListener(this.activity);
	    mWidget.setSolverEnabled(false);

	    mLabelText  = (TextView) activity.findViewById(R.id.labelTextField);
	    
	    // Connect clear button
	    View clearButton = view.findViewById(R.id.vo_math_clearButton);
	    if (clearButton != null)
	    {
	      clearButton.setOnClickListener(new View.OnClickListener()
	      {
	        @Override
	        public void onClick(final View view)
	        {
	          mWidget.clear(true /* allow undo */);
	        }
	      });
	    }
	    
	    View textButton = view.findViewById(R.id.vo_tw_textButton);
	    if (textButton != null)
	    {
	    	textButton.setOnClickListener(new View.OnClickListener()
	      {
	        @Override
	        public void onClick(final View view)
	        {
	        	TextRecognitionFragment textFragment = new TextRecognitionFragment();
	        	Bundle args = new Bundle();
	        	args.putBoolean("fromMath", true);
	        	args.putString("currentInput", mEditText.getText().toString());
				textFragment.setArguments(args);
	        	FragmentTransaction transaction = getFragmentManager().beginTransaction();

	        	// Replace whatever is in the fragment_container view with this fragment,
	        	// and add the transaction to the back stack so the user can navigate back
	        	transaction.replace(R.id.fragment_container, textFragment, "textFragment");
//	        	transaction.addToBackStack(null);

	        	// Commit the transaction
	        	transaction.commit();
	        }
	      });
	    }
	    
	    view.findViewById(R.id.vo_math_enterButton).setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	mEditText.append("\n");
	        	mWidget.clear(true /* allow undo */);
	        }
	      });
	    
	    view.findViewById(R.id.vo_tw_mathnextButton).setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	if (TestActivity.curQuestionIndex < SampleQuestions.questions.size() - 1){
		        	TestActivity.setAnswer(TestActivity.curQuestionIndex, mEditText.getText().toString().trim());
		        	mLabelText.setText(SampleQuestions.getReadableQuestion(++TestActivity.curQuestionIndex));
		        	String answer = TestActivity.getAnswer(TestActivity.curQuestionIndex);
		        	mEditText.setText(answer);
		        	mWidget.clear(true /* allow undo */);
		        	return;
	        	}
	        	else{
	        		Intent intent = new Intent();
	        		TestActivity.answers.add(mEditText.getText().toString());
	        		intent.setClass(v.getContext(), ResultDisplayActivity.class);
	        		v.getContext().startActivity(intent);
		        	activity.finish();
	        	}
	        }
	      });
	    
	    view.findViewById(R.id.vo_tw_mathprevButton).setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	if (TestActivity.curQuestionIndex > 0){
	        		TestActivity.setAnswer(TestActivity.curQuestionIndex, mEditText.getText().toString().trim());
	        		mLabelText.setText(SampleQuestions.getReadableQuestion(--TestActivity.curQuestionIndex));
		        	String answer = TestActivity.getAnswer(TestActivity.curQuestionIndex);
		        	mEditText.setText(answer);
		        	mWidget.clear(true);
		        	return;
	        	}
	        	else{
	        		//TODO: should give a alert
	        	}
	        }
	      });
	    
	    view.findViewById(R.id.vo_tw_mathspaceButton).setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	mEditText.append(" ");
	        }
	    });
	    
	    view.findViewById(R.id.vo_tw_mathdeleteButton).setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	String oldStr = mEditText.getText().toString();
	        	if(oldStr.length() > 0){
	        		mEditText.setText(oldStr.substring(0, oldStr.length() - 1));
	        	}
	        }
	    });
	    
	    mEditText = (CustomEditText) this.activity.findViewById(R.id.textField);
	    
	    // Configure equation recognition engine
	    configure();
	    return view;
	}
	
	 @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    this.activity = (TestActivity)activity;
	  }
	 
	 @Override
	  public void onDetach() {
	    super.onDetach();
	    mWidget.release(this.activity);
	  }
	 
	 private void configure()
	  {
	    // Equation resource    
	    final String[] resources = new String[]
	    {
	      "equation-ak.res",
	      "equation-grm-mathwidget.res"
	    };
		  	  
	    // Prepare resources
	    final String subfolder = "equation";
	    String resourcePath = new String(this.activity.getFilesDir().getPath() + java.io.File.separator + subfolder);
	    SimpleResourceHelper.copyResourcesFromAssets(this.activity.getAssets(), subfolder /* from */, resourcePath /* to */, resources /* resource names */);
		
	    // Configure math widget
	    mWidget.setResourcesPath(resourcePath);
	    mWidget.configure(this.activity, resources, MyCertificate.getBytes());
	  }
}
