package com.pictureperfect.activity;

import com.pictureperfect.imagehandling.ImgData;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * This screen helps the user to choose the background image.
 * @author group13
 *
 */
public class SelectBackgroundActivity extends Activity {

	ImgData imgData;

	private SurfaceView m_previewSurfaceView = null;
	private SurfaceHolder m_previewSurfaceHolder = null;
	
	private Canvas myCanvas;
	
	private Paint myPaint= new Paint(Paint.ANTI_ALIAS_FLAG);

	private Bitmap[] myPictures;

	private Integer myBackgroundNum;

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
		setContentView(R.layout.selectbackground);
		
		m_previewSurfaceView = (SurfaceView) findViewById(R.id.preview);
		m_previewSurfaceHolder = m_previewSurfaceView.getHolder();
		m_previewSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		m_previewSurfaceHolder.addCallback(cameraSurfaceCallback);
		
		Intent i = getIntent();
		imgData = (ImgData)i.getSerializableExtra("imgData");
		init();
		/*Button doneButton = (Button) findViewById(R.id.backgroundDone); // INSERT
																		// BUTTON
		doneButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SelectBackground.this,
						ChooseOptionActivity.class);
				startActivity(intent);
			}
		});*/
	}

	
	private void init() {
		imgData.setBackgroundNum(0);
		try {
			myCanvas= new Canvas(imgData.getBackground());	
		} catch (NullPointerException e) {
			
		}
		
		myPaint.setStyle(Paint.Style.STROKE);
		myPaint.setStrokeCap(Paint.Cap.ROUND);
		myPaint.setColor(0x80ff0000);
		myPaint.setStrokeWidth(3);
	}


	SurfaceHolder.Callback cameraSurfaceCallback = new SurfaceHolder.Callback() {
		public void surfaceCreated(SurfaceHolder holder) {
			
		}
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
	
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			// remove the holder
		}
	};

	/**
	 * This method updates the background number in both the view and the ImgData object.
	 */
	private void changeBackground() {
		
	}

}