package com.ibm.eti.eteacher;

import com.ibm.eti.eteacher.util.SimpleResourceHelper;
import com.visionobjects.math.MathWidgetApi;
import com.visionobjects.myscript.certificate.MyCertificate;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MathRecognitionFragment extends Fragment{
	
	private TestActivity  activity = null;
	private MathWidgetApi mWidget;
	
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
	    
	    // Configure equation recognition engine
	    configure();
	    return view;
	}
	
	 @Override
	  public void onAttach(Activity activity) {
	    super.onAttach(activity);
	    this.activity = (TestActivity)activity;
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
	    boolean res = SimpleResourceHelper.copyResourcesFromAssets(this.activity.getAssets(), subfolder /* from */, resourcePath /* to */, resources /* resource names */);
		System.err.println("academic result: ******************** " + res + " path: " + resourcePath + " cert: ");
	    // Configure math widget
	    mWidget.setResourcesPath(resourcePath);
	    mWidget.configure(this.activity, resources, MyCertificate.getBytes());
	  }
}
