package com.ibm.eti.eteacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.ibm.eti.eteacher.controller.CandidateBarController;
import com.ibm.eti.eteacher.controller.ToolbarController;
import com.ibm.eti.eteacher.reco.EditionBehavior;
import com.ibm.eti.eteacher.util.SampleQuestions;
import com.ibm.eti.eteacher.util.SimpleResourceHelper;
import com.ibm.eti.eteacher.view.CustomEditText;
import com.visionobjects.myscript.certificate.MyCertificate;
import com.visionobjects.textwidget.TextWidget;
import com.visionobjects.textwidget.TextWidgetApi;
import com.visionobjects.textwidget.TextWidgetStyleable;

/** This class demonstrates how to integrate all features of the {@link TextWidgetStyleable} widget in a single application. */
public class TestActivity extends Activity implements
TextWidgetApi.OnConfigureListener,
TextWidgetApi.OnRecognitionListener,
TextWidgetApi.OnCursorHandleDragListener,
TextWidgetApi.OnInsertHandleDragListener,
TextWidgetApi.OnInsertHandleClickedListener,
TextWidgetApi.OnGestureListener,
TextWidgetApi.OnUserScrollListener
{
  // load the StylusCore library when this class is loaded
  static {

    // you can either load the library by name
    // Android will search the default paths for a dynamic library with file name libStylusCore.so
    System.loadLibrary("StylusCore");
    
    // or you can load the library by specifying a full path
    // this is useful when you want to load a specific version of the StylusCore library from the filesystem
    //System.load("/data/data/com.visionobjects.textwidget.demos/lib/libStylusCore.so");
  }

  private static final String TAG = "SampleActivity";
  
  private CustomEditText            mEditText;
  private TextView mLabelText;
  private ToolbarController         mToolbarController;
  private CandidateBarController    mCandidateBarController;

  private TextWidget                mWidget;
  
  private EditionBehavior           mEditionBehavior;
  
  public static int curQuestionIndex = 0;
  public static List<String> answers = new ArrayList<String>();

  @TargetApi(11)
  @Override
  protected void onCreate(final Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    
    SampleQuestions.load(this);
    
    mEditText = (CustomEditText) findViewById(R.id.textField);
    
    mLabelText = (TextView) findViewById(R.id.labelTextField);
    
    Intent intent = getIntent();
    if(intent != null && intent.getExtras() != null){
    	int idx = intent.getExtras().getInt("questionIdx");
    	mLabelText.setText(SampleQuestions.questions.get(idx));
    }
    else{
    	mLabelText.setText(SampleQuestions.questions.get(0));
    }
    
    View toolbarView = findViewById(R.id.vo_tw_toolbar);
    mToolbarController = new ToolbarController(toolbarView);
    
    toolbarView.findViewById(R.id.vo_tw_nextButton).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        	Intent intent = new Intent();
        	if (TestActivity.curQuestionIndex < SampleQuestions.questions.size() - 1){
	        	TestActivity.answers.add(mEditText.getText().toString());
	        	mLabelText.setText(SampleQuestions.questions.get(++TestActivity.curQuestionIndex));
	        	mEditText.setText("");
	        	mWidget.setText("");
	        	return;
        	}
        	else{
        		TestActivity.answers.add(mEditText.getText().toString());
        		intent.setClass(v.getContext(), ResultDisplayActivity.class);
        	}
        	v.getContext().startActivity(intent);
        	finish();
        }
      });
    
    mCandidateBarController = new CandidateBarController(findViewById(R.id.vo_tw_candidatebar));
    
    mWidget = (TextWidget) findViewById(R.id.vo_text_widget);    
    mWidget.setOnConfigureListener(this);
    mWidget.setOnRecognitionListener(this);
    mWidget.setOnCursorHandleDragListener(this);
    mWidget.setOnInsertHandleDragListener(this);
    mWidget.setOnInsertHandleClickedListener(this);
    mWidget.setOnGestureListener(this);
    mWidget.setOnUserScrollListener(this);
    
    
    // hovering functionality is disabled by default
    mWidget.setHoverEnabled(true);

    mEditionBehavior = new EditionBehavior(mWidget, mEditText, mCandidateBarController);
    
    configure();

    setTitle(getResources().getString(R.string.activity_name) + " " + com.visionobjects.textwidget.Build.VERSION.RELEASE);
  }
  
  @Override
  protected void onResume()
  {
    super.onResume();
    
    // pipe current text of field to the widget
    final Editable editable = mEditText.getText();
    if(editable != null)
      mWidget.setText(editable.toString());
    else
      mWidget.setText("");
    // set insertion mode at the end of the text
    mWidget.setInsertionMode(mEditText.getText().length());
    // place the cursor at the end of the text
    mEditText.setCursorIndex(mEditText.getText().length());
  }
  
  // ----------------------------------------------------------------------
  // Handwriting recognition configuration
  
  private void configure()
  {
    SimpleResourceHelper helper = new SimpleResourceHelper(this);

    String[] resources = new String[] {
    		"zh_CN/zh_CN_gb18030-ak-cur.lite.res",
            "zh_CN/zh_CN_gb18030-lk-text.lite.res"
//        "en_US/en_US-ak-cur.lite.res",
//        "en_US/en_US-lk-text.lite.res"
      };

    String[] paths = helper.getResourcePaths(resources);
      
    String[] lexicon = new String[] {
        // add your user dictionary here
    };

    long startTime = System.currentTimeMillis();
    mWidget.configure("zh_CN", paths, lexicon, MyCertificate.getBytes());
    long endTime = System.currentTimeMillis();
    
    Log.d(TAG, "configure() API processing time=" + (endTime - startTime) + "ms");
    
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

  // ----------------------------------------------------------------------
  // Handwriting recognition engine configuration
  
  @Override
  public void onConfigureBegin()
  {
    Log.d(TAG, "Handwriting configuration begin");
  }

  @Override
  public void onConfigureEnd(final boolean success)
  {
    if (success) {
      Log.d(TAG, "Handwriting configuration succeeded");
//      Toast.makeText(this, R.string.vo_tw_configSuccess, Toast.LENGTH_SHORT).show();
    } else {
      Log.d(TAG, "Handwriting configuration failed (" + mWidget.getErrorString() + ")");
      Log.e(TAG, "Handwriting configuration error");
      Toast.makeText(this, R.string.vo_tw_configFailed, Toast.LENGTH_SHORT).show();
    }
    
    // hide soft keyboard if it is currently shown
    InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
    manager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
  }

  // ----------------------------------------------------------------------
  // Handwriting recognition process
  
  @Override
  public void onRecognitionBegin()
  {
    Log.d(TAG, "Handwriting recognition begins");
  }

  @Override
  public void onRecognitionEnd()
  {
    Log.d(TAG, "Handwriting recognition end");
  }
  
  // ----------------------------------------------------------------------
  // Cursor handle listeners
  
  @Override
  public void onCursorHandleDragBegin()
  {
    Log.d(TAG, "Cursor handle drag begins");
  }
  
  @Override
  public void onCursorHandleDragEnd(final boolean scrolledAtEnd)
  {
    Log.d(TAG, "Cursor handle drag ends (at end=" + scrolledAtEnd + ")");
    
    if (scrolledAtEnd) {
      final Editable editable = mEditText.getText();
      if(editable != null)
        mWidget.setInsertionMode(editable.length());
    }
  }
  
  @Override
  public void onCursorHandleDrag(int index)
  {
    Log.d(TAG, "Cursor handle dragged to index " + index);

    mEditText.setCursorIndex(index);
  }
  
  // ----------------------------------------------------------------------
  // Insert handle listeners
  
  @Override
  public void onInsertHandleDragBegin()
  {
    Log.d(TAG, "Insert handle drag begin");
  }
  
  @Override
  public void onInsertHandleDragEnd(final boolean snapped)
  {
    Log.d(TAG, "Insert handle drag ends (snapped=" + snapped + ")");
    
    if (snapped) {
      // switch to correction mode
      mWidget.setCorrectionMode();
      // make sure cursor is visible
      mWidget.moveCursorToVisibleIndex();
    }
  }
  
  @Override
  public void onInsertHandleClicked()
  {
    Log.d(TAG, "Insert handle clicked");
    
    // switch to correction mode
    mWidget.setCorrectionMode();
    // make sure cursor is visible
    mWidget.moveCursorToVisibleIndex();
  }
  
  // ----------------------------------------------------------------------
  // Handwriting recognition gestures
  
  @Override
  public void onSingleTapGesture(final int index)
  {
    Log.d(TAG, "Handwriting recognition single tap at index " + index);
    
    mEditText.setCursorIndex(index);
  }

  @Override
  public void onInsertGesture(final int index)
  {
    Log.d(TAG, "Handwriting recognition insert gesture at index " + index);
    
    mWidget.setInsertionMode(index);
  }
  
  @Override
  public void onJoinGesture(final int index)
  {
    Log.d(TAG, "Handwriting recognition join gesture at index " + index);
    
    final Editable editable = mEditText.getText();
    String text = "";
    if(editable != null)
      text = editable.toString();
    final int length = text.length();
    int start = index;
    int end = index;
    
    // find bounds of space region to remove from text
    if(start >= length)
      start = length - 1;
    while (start > 0 && text.charAt(start - 1) == ' ') {
      start--;
    }

    if(end >= length)
      end = length - 1;
    while (end < length && text.charAt(end) == ' ') {
      end++;
    }

    if((start >=0) && (end >= 0))
      mWidget.replaceCharacters(start, end, null);
  }
  
  @Override
  public void onSelectionGesture(final int start, final int end)
  {
    Log.d(TAG, "Handwriting recognition selection gesture at range " + start + "-" + end);
    
    mEditText.setSelection(start, end);
  }
  
  @Override
  public void onUnderlineGesture(final int start, final int end)
  {
    Log.d(TAG, "Handwriting recognition underline gesture at range " + start + "-" + end);

    mEditText.setSelection(start, end);
  }
  
  @Override
  public void onReturnGesture(final int index)
  {
    Log.d(TAG, "Handwriting recognition return gesture at index " + index);

    final String text = String.format((Locale) null, getString(R.string.vo_tw_returnGestureToast), index);
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    
    mWidget.replaceCharacters(index, index, "\n");
  }
  
  @Override
  public void onPinchGesture()
  {
    Log.d(TAG, "Pinch gesture detected");

    Toast.makeText(this, R.string.vo_tw_pinchGestureToast, Toast.LENGTH_SHORT).show();
  }
  
  // ----------------------------------------------------------------------
  // User scrolling

  @Override
  public void onUserScrollBegin()
  {
    Log.d(TAG, "User scroll begins");
  }
  
  public void onUserScrollEnd(final boolean scrolledAtEnd)
  {
    Log.d(TAG, "User scroll ends (at end=" + scrolledAtEnd + ")");
    
    if (scrolledAtEnd) {
      if (!mWidget.isInsertionMode()) {
        final Editable editable = mEditText.getText();
        int length = 0;
        if(editable != null)
          length = editable.length();
        // switch to insertion mode at the end of the text
        mWidget.setInsertionMode(length);
        // place the cursor at the end of the text
        mEditText.setCursorIndex(length);
      }
    } else {
      if (mWidget.isInsertionMode()) {
        // switch to correction mode if user scrolled back into the text
        mWidget.setCorrectionMode();
        // make sure cursor is visible
        mWidget.moveCursorToVisibleIndex();
        // synchronize field cursor
        mEditText.setCursorIndex(mWidget.getCursorIndex());
      }
    }
  }

  @Override
  public void onUserScroll()
  {
    Log.d(TAG, "User scroll");
    
    if (!mWidget.isInsertionMode()) {
      if (mWidget.moveCursorToVisibleIndex()) {
        int index = mWidget.getCursorIndex();
        
        // cursor has been moved, synchronize field cursor
        mEditText.setCursorIndex(index);
      }
    }
  }


}
