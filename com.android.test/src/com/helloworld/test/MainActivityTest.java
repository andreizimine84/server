package com.helloworld.test;

import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import com.helloworld.MainActivity;
import android.test.UiThreadTest;
import android.test.ServiceTestCase;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
	private MainActivity mActivity;

	protected void setUp() throws Exception {
	    super.setUp();
	    
	    setActivityInitialTouchMode(false);
	    
	    mActivity = getActivity();
	}   
	
	@UiThreadTest
	public void testStatePause() {
		Instrumentation mInstr = this.getInstrumentation();

		mInstr.callActivityOnPause(mActivity);

		mInstr.callActivityOnResume(mActivity);
	}
	
	public MainActivityTest() {
		super(MainActivity.class);
	}
}
