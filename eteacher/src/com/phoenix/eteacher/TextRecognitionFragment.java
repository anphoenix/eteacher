package com.phoenix.eteacher;

import com.phoenix.eteacher.R;
import com.phoenix.eteacher.controller.CandidateBarController;
import com.phoenix.eteacher.controller.ToolbarController;
import com.phoenix.eteacher.reco.EditionBehavior;
import com.phoenix.eteacher.util.SampleQuestions;
import com.phoenix.eteacher.util.SimpleResourceHelper;
import com.phoenix.eteacher.view.CustomEditText;
import com.visionobjects.myscript.certificate.MyCertificate;
import com.visionobjects.textwidget.TextWidget;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class TextRecognitionFragment extends Fragment {
	
	private TestActivity  activity = null;
	private TextWidget mWidget;
	
	@Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	    Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.recognizer_fragment, container, false);
	    
	    View toolbarView = view.findViewById(R.id.vo_tw_toolbar);
	    ToolbarController mToolbarController = new ToolbarController(toolbarView);
	    
	    final CustomEditText mEditText = (CustomEditText) activity.findViewById(R.id.textField);
	    
	    final TextView mLabelText = (TextView) activity.findViewById(R.id.labelTextField);
	    
	    toolbarView.findViewById(R.id.vo_tw_nextButton).setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	Intent intent = new Intent();
	        	if (TestActivity.curQuestionIndex < SampleQuestions.questions.size() - 1){
		        	TestActivity.setAnswer(TestActivity.curQuestionIndex, mEditText.getText().toString().trim());
		        	mLabelText.setText(SampleQuestions.getReadableQuestion(++TestActivity.curQuestionIndex));
		        	String answer = TestActivity.getAnswer(TestActivity.curQuestionIndex);
		        	mEditText.setText(answer);
		        	mWidget.setText(answer);
		        	return;
	        	}
	        	else{
	        		TestActivity.answers.add(mEditText.getText().toString());
	        		intent.setClass(v.getContext(), ResultDisplayActivity.class);
	        		v.getContext().startActivity(intent);
		        	activity.finish();
	        	}
	        }
	      });
	    
	    toolbarView.findViewById(R.id.vo_tw_prevButton).setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	if (TestActivity.curQuestionIndex > 0){
	        		TestActivity.setAnswer(TestActivity.curQuestionIndex, mEditText.getText().toString().trim());
	        		mLabelText.setText(SampleQuestions.getReadableQuestion(--TestActivity.curQuestionIndex));
		        	String answer = TestActivity.getAnswer(TestActivity.curQuestionIndex);
		        	mEditText.setText(answer);
		        	mWidget.setText(answer);
		        	return;
	        	}
	        	else{
	        		//TODO: should give a alert
	        	}
	        }
	      });
	    
	    toolbarView.findViewById(R.id.vo_tw_enterButton).setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
//	        	mEditText.append("\n");
	        	mWidget.setText(mEditText.getText().toString() + "\n");
	        }
	      });
	    
	    toolbarView.findViewById(R.id.vo_tw_mathButton).setOnClickListener(new View.OnClickListener() {
	        @Override
	        public void onClick(View v) {
	        	MathRecognitionFragment mathFragment = new MathRecognitionFragment();
	        	FragmentTransaction transaction = getFragmentManager().beginTransaction();

	        	// Replace whatever is in the fragment_container view with this fragment,
	        	// and add the transaction to the back stack so the user can navigate back
	        	transaction.replace(R.id.fragment_container, mathFragment);
//	        	transaction.addToBackStack(null);

	        	// Commit the transaction
	        	transaction.commit();
	        }
	      });
	    
	    
	    
	    CandidateBarController mCandidateBarController = new CandidateBarController(view.findViewById(R.id.vo_tw_candidatebar));
	    
	    mWidget = (TextWidget) view.findViewById(R.id.vo_text_widget);
	    this.activity.setmWidget(mWidget);
	    mWidget.setOnConfigureListener(activity);
	    mWidget.setOnRecognitionListener(activity);
	    mWidget.setOnCursorHandleDragListener(activity);
	    mWidget.setOnInsertHandleDragListener(activity);
	    mWidget.setOnInsertHandleClickedListener(activity);
	    mWidget.setOnGestureListener(activity);
	    mWidget.setOnUserScrollListener(activity);
	    
	    
	    // hovering functionality is disabled by default
	    mWidget.setHoverEnabled(true);

	    EditionBehavior mEditionBehavior = new EditionBehavior(mWidget, mEditText, mCandidateBarController);
	    
	    configure(mEditionBehavior, mCandidateBarController, mToolbarController, mEditText);
	    
	    setEditText(mEditText);
	    
	    return view;
	  }
	  

	  private void setEditText(CustomEditText mEditText) {
		Bundle args = this.getArguments();
		if (args != null && args.getBoolean("fromMath")){
			String curInput = args.getString("currentInput");
			mEditText.setTextKeepState(curInput);
			mWidget.setText(curInput);
		}
	  }


	@Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    this.activity = (TestActivity)activity;
	  }
	  
	  @Override
	  public void onDetach() {
	    super.onDetach();
	    mWidget.releaseEngine();
	    mWidget.removeAllViews();
	  }
	  
	  // ----------------------------------------------------------------------
	  // Handwriting recognition configuration
	  
	  private void configure(EditionBehavior mEditionBehavior, CandidateBarController mCandidateBarController, ToolbarController mToolbarController, CustomEditText mEditText)
	  {
	    SimpleResourceHelper helper = new SimpleResourceHelper(activity);

	    String[] resources = new String[] {
	    		"zh_CN/zh_CN_gb18030-ak-cur.lite.res",
	            "zh_CN/zh_CN_gb18030-lk-text.lite.res"
//	        "en_US/en_US-ak-cur.lite.res",
//	        "en_US/en_US-lk-text.lite.res"
	      };

	    String[] paths = helper.getResourcePaths(resources);
	      
	    String[] lexicon = new String[] {
	        // add your user dictionary here
	    };

	    long startTime = System.currentTimeMillis();
	    mWidget.configure("zh_CN", paths, lexicon, MyCertificate.getBytes());
	    long endTime = System.currentTimeMillis();
	   
	    
	    // configure visual and behavior of the widget
	    mWidget.setAutoScrollEnabled(true);
	    mWidget.setAutoTypesetEnabled(true);
	    mWidget.setScrollbarResource(R.drawable.vo_tw_scrollbar_xml);
	    mWidget.setScrollArrowLeftResource(R.drawable.vo_tw_arrowleft_xml);
	    mWidget.setScrollArrowRightResource(R.drawable.vo_tw_arrowright_xml);
	    mWidget.setOnTextChangedListener(mEditionBehavior);
	    mWidget.setOnSelectionChangedListener(mEditionBehavior);

	    mCandidateBarController.setOnCandidateButtonClickedListener(mEditionBehavior);
	    mToolbarController.setOnSpaceButtonClickedListener(mEditionBehavior);
	    mToolbarController.setOnDeleteButtonClickedListener(mEditionBehavior);
	    mEditText.setOnCursorIndexChangedListener(mEditionBehavior);
	  }
	  
}
