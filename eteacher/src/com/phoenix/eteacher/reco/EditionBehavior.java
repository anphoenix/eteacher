package com.phoenix.eteacher.reco;

import com.phoenix.eteacher.controller.CandidateBarController;
import com.phoenix.eteacher.controller.ToolbarController;
import com.phoenix.eteacher.view.CustomEditText;
import com.visionobjects.textwidget.TextWidget;
import com.visionobjects.textwidget.TextWidgetApi;

import android.text.Editable;
import android.util.Log;

public class EditionBehavior
implements
  TextWidgetApi.OnTextChangedListener,
  TextWidgetApi.OnSelectionChangedListener,
  ToolbarController.OnSpaceButtonClickedListener,
  ToolbarController.OnDeleteButtonClickedListener,
  CandidateBarController.OnCandidateButtonClickedListener,
  CustomEditText.OnCursorIndexChangedListener
{
  private static final String TAG = "CursiveBehavior";
  
  private CustomEditText            mEditText;
  private CandidateBarController    mCandidateBarController;
  private TextWidget                mWidget;

  public EditionBehavior(TextWidget widget, CustomEditText editText, CandidateBarController candidatebarController)
  {
    mWidget = widget;
    mEditText = editText;
    mCandidateBarController = candidatebarController;
  }

  // ----------------------------------------------------------------------
  // Toolbar controller interface

  public void onSpaceButtonClicked()
  {
    Log.d(TAG, "Space button clicked");
    
    final int start = mEditText.getSelectionStart();
    final int end = mEditText.getSelectionEnd();
    
    mWidget.replaceCharacters(start, end, " ");
    mEditText.setCursorIndex(start + 1);
  }

  public void onDeleteButtonClicked()
  {
    Log.d(TAG, "Delete button clicked");

    final int start = mEditText.getSelectionStart();
    final int end = mEditText.getSelectionEnd();
    
    if (start != end) {
      mWidget.replaceCharacters(start, end, null);
    } else if (start != 0) {
      mWidget.replaceCharacters(start - 1, start, null);
      mEditText.setCursorIndex(start - 1);
    }
  }
  
  // ----------------------------------------------------------------------
  // Candidate bar controller interface

  public void onCandidateButtonClicked(final int start, final int end, final String label)
  {
    Log.d(TAG, "Candidate label \"" + label + "\" clicked");
    mWidget.replaceCharacters(start, end, label);
  }

  // ----------------------------------------------------------------------
  // Custom EditText interface
  
  public void onCursorIndexChanged(final int index)
  {
    Log.d(TAG, "Cursor index changed to " + index + " from input field");
    
    if (!mWidget.isCursorHandleDragging())
    {
      if (mWidget.isInsertionMode())
      {
        // switch to correction mode if the cursor is moved to a
        // position different from the current insert index
        
        if (index != mWidget.getInsertIndex()) {
          mWidget.setCorrectionMode();
        }
      }
      else
      {
        // switch to insertion mode if the cursor is moved to the
        // end of the text
        final Editable editable = mEditText.getText();
        if((editable != null ) && (index == editable.length())) {
          mWidget.setInsertionMode(index);
        }
      }
    }
    
    // update cursor index
    // this may trigger an onSelectionChanged() event
    mWidget.setCursorIndex(index);
    // scroll to cursor if widget is in correction mode
    if (!mWidget.isInsertionMode()) {
      mWidget.scrollToCursor();
    }
  }
  
  // ----------------------------------------------------------------------
  // Text management

  public void onTextChanged(final String text, final boolean intermediate)
  {
    Log.d(TAG, "Text changed to \"" + text.replace('\n', '\u00B6') + "\" " + (intermediate ? "(intermediate)" : "(stable)"));
    
    // get the text currently stored in the target field
    String previousText = "";
    final Editable editable = mEditText.getText();
    if(editable != null)
      previousText = editable.toString();

    // temporarily disable selection changed listener
    // because setText() automatically places the cursor at the beginning
    // of the text
    
    mEditText.setOnCursorIndexChangedListener(null);
    
    mEditText.setTextKeepState(text);

    mEditText.setOnCursorIndexChangedListener(this);
    
    if (mWidget.isInsertionMode()) {
      // widget is in insertion mode
      // put cursor at current widget insert index
      mEditText.setCursorIndex(mWidget.getInsertIndex());
    } else {
      // widget is in correction mode
      // auto-switch to insertion mode if user appended text or text is empty
      if (text.length() == 0 || text.length() > previousText.length() && text.startsWith(previousText)) {
        mWidget.setInsertionMode(text.length());
      }
    }
  }
  
  // ----------------------------------------------------------------------
  // Text selection

  public void onSelectionChanged(final int start, final int end, final String[] labels, final int selectedIndex)
  {
    Log.d(TAG, "Selection changed range " + start + "-" + end);

    if (labels != null) {
        for (int i=0; i<labels.length; i++) {
          String flags;
          if (i == selectedIndex) {
            flags = " (*)";
          } else {
            flags = "";
          }
          Log.d(TAG, "labels[" + i + "] = \"" + labels[i] + "\"" + flags);
        }
      }

    mCandidateBarController.setLabels(start, end, labels, selectedIndex);
  }
}