package com.pictureperfect.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.R;
import android.app.Activity;
import android.widget.Button;

import com.pictureperfect.activity.CameraActivity;
import com.pictureperfect.activity.EditExistingActivity;
import com.pictureperfect.activity.WelcomeActivity;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class EditExistingActivityTest {

	private Activity A1;
	
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public final void test() {
		A1 = new EditExistingActivity();
		
		
	}

}
