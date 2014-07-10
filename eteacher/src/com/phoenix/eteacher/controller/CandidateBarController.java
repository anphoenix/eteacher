package com.phoenix.eteacher.controller;

import com.phoenix.eteacher.R;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;


/** This class implements a simple candidate bar controller. */
public class CandidateBarController
{
  public interface OnCandidateButtonClickedListener {
    public void onCandidateButtonClicked(int start, int end, String label);
  }

  private OnCandidateButtonClickedListener mOnCandidateButtonClickedListener;

  private LinearLayout mCandidateBarLayout;
  
  private int mStartIndex;
  private int mEndIndex;
  
  private Context mContext;

  public CandidateBarController(View v)
  {
    mContext = v.getContext();
    
    mCandidateBarLayout = (LinearLayout) v.findViewById(R.id.vo_tw_candidatebarLayout);
  }

  public void setOnCandidateButtonClickedListener(final OnCandidateButtonClickedListener l)
  {
    mOnCandidateButtonClickedListener = l;
  }
  
  public void setLabels(final int start, final int end, String[] labels, final int selectedIndex)
  {
    mStartIndex = start;
    mEndIndex = end;
    
    mCandidateBarLayout.removeAllViews();
    
    if (labels == null) {
      return;
    }
    if (labels.length < 2) {
      return;
    }

    for (int i=0; i<labels.length; i++) {
      final Button b = (Button) View.inflate(mContext, R.layout.vo_tw_candidate_button, null);

      final String label = labels[i];
      b.setText(label);
      b.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          if (mOnCandidateButtonClickedListener != null) {
            mOnCandidateButtonClickedListener.onCandidateButtonClicked(mStartIndex, mEndIndex, label);
          }
        }
      });
      b.setSelected(i == selectedIndex);
      mCandidateBarLayout.addView(b);
    }
  }
}
