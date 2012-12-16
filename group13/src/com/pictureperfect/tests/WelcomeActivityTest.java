package com.pictureperfect.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.R;
import android.app.Activity;
import android.widget.Button;

import com.pictureperfect.activity.CameraActivity;
import com.pictureperfect.activity.GalleryActivity;
import com.pictureperfect.activity.WelcomeActivity;
import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class WelcomeActivityTest {
	
	private Activity activity;
	private Activity activity2;
	private Activity activity3;
	private Button b1;
	private Button b2;
	
	@Before
	public void setUp()throws Exception{
		activity = new WelcomeActivity();
		b1 = (Button) activity.findViewById(R.id.button1);
		b2 = (Button) activity.findViewById(R.id.button2);
	}
	
	@Test
	public void shouldOpenCameraActivityWhenPressed() throws Exception{
		b1.performClick();
		activity2 = new CameraActivity();
		assertEquals(activity2, activity);
		
	}
	
	@Test
	public void shouldOpenGalleryActivityWhenPressed() throws Exception{
		b2.performClick();
		activity3 = new CameraActivity();
		assertEquals(activity3, activity);
	}
}