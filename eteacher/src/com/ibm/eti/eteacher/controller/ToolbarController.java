package com.ibm.eti.eteacher.controller;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.ibm.eti.eteacher.R;
import com.ibm.eti.eteacher.TestActivity;
import com.ibm.eti.eteacher.util.SampleQuestions;
import com.ibm.eti.eteacher.view.CustomEditText;

/** This class implements a simple toolbar controller. */
public class ToolbarController
{
  public interface OnSpaceButtonClickedListener {
    public void onSpaceButtonClicked();
  }
  public interface OnDeleteButtonClickedListener {
    public void onDeleteButtonClicked();
  }

  private OnSpaceButtonClickedListener      mOnSpaceButtonClickedListener;
  private OnDeleteButtonClickedListener     mOnDeleteButtonClickedListener;

  public ToolbarController(View v)
  {
    v.findViewById(R.id.vo_tw_spaceButton).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        notifySpaceButtonClicked();
      }
    });
    
    v.findViewById(R.id.vo_tw_deleteButton).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        notifyDeleteButtonClicked();
      }
    });
    
//    v.findViewById(R.id.vo_tw_nextButton).setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//        	Intent intent = new Intent();
//        	if (SampleActivity.curQuestionIndex < SampleQuestions.questions.size() - 1){
//	        	intent.putExtra("questionIdx", ++SampleActivity.curQuestionIndex);
//	        	intent.setClass(v.getContext(), SampleActivity.class);
//	        	
//	        	CustomEditText mEditText = (CustomEditText) v.findViewById(R.id.textField);
//	        	SampleActivity.answers.add(mEditText.getText().toString());
//        	}
//        	else{
//        		intent.setClass(v.getContext(), SampleActivity.class);
//        	}
//        	v.getContext().startActivity(intent);
//        }
//      });
    
  }

  public void setOnSpaceButtonClickedListener(final OnSpaceButtonClickedListener l)
  {
    mOnSpaceButtonClickedListener = l;
  }

  public void setOnDeleteButtonClickedListener(final OnDeleteButtonClickedListener l)
  {
    mOnDeleteButtonClickedListener = l;
  }
  
  public void notifySpaceButtonClicked()
  {
    if (mOnSpaceButtonClickedListener != null) {
      mOnSpaceButtonClickedListener.onSpaceButtonClicked();
    }
  }
  
  public void notifyDeleteButtonClicked()
  {
    if (mOnDeleteButtonClickedListener != null) {
      mOnDeleteButtonClickedListener.onDeleteButtonClicked();
    }
  }
}
