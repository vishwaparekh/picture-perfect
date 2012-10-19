package com.pictureperfect.activity;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This screen helps the user to click a burst of images.
 * @author group13
 *
 */
public class CameraActivity extends Activity {
	
	private static final int CAMERA_PIC_REQUEST = 1337;
	private PictureCallback rawCallback;

    /** Called when the activity is first created. */
    @Override
    
    /**Called when the activity is first created. This is where you should do all 
     * of your normal static set up: create views, bind data to lists, etc. This method
     *  also provides you with a Bundle containing the activity's previously frozen 
     *  state, if there was one.
     * 
     * @param savedInstancesState f the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle) 
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera);
        Button CamButton = (Button) findViewById(R.id.cameraButton);
        
        CamButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(CameraActivity.this,
						SelectBackground.class);
				startActivity(intent);
			}
		});
        
    }
	public static int getCameraPicRequest() {
		return CAMERA_PIC_REQUEST;
	}
    
    
}