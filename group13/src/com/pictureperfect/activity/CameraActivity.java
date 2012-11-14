package com.pictureperfect.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

/**
 * This screen helps the user to click a burst of images.
 * 
 * @author group13
 * 
 */

public class CameraActivity extends Activity {

	private static final int CAMERA_PIC_REQUEST = 1337;
	private SurfaceView preview = null;
	private SurfaceHolder previewHolder = null;
	private Camera mCamera = null;

	/**
	 * Called when the activity is first created. This is where you should do
	 * all of your normal static set up: create views, bind data to lists, etc.
	 * This method also provides you with a Bundle containing the activity's
	 * previously frozen state, if there was one.
	 * 
	 * @param savedInstancesState
	 *            f the activity is being re-initialized after previously being
	 *            shut down then this Bundle contains the data it most recently
	 *            supplied in onSaveInstanceState(Bundle)
	 */

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);

		preview = (SurfaceView) findViewById(R.id.preview);
		previewHolder = preview.getHolder();
		previewHolder.addCallback(surfaceCallback);
		previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		/* When we touch the screen, it will auto-focus again */
		if (mCamera != null) {
			mCamera.cancelAutoFocus(); // release the previous auto-focus
			mCamera.autoFocus(new Camera.AutoFocusCallback() {

				public void onAutoFocus(boolean success, Camera camera) {
					Log.d("HOME", "isAutofoucs " + Boolean.toString(success));
				}
			});

		}
		return super.dispatchTouchEvent(ev);
	}

	/* Define which keys will "take the picture" */

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_CAMERA
				|| keyCode == KeyEvent.KEYCODE_SEARCH) {
			takePicture();

			return (true);
		}

		return (super.onKeyDown(keyCode, event));
	}

	private void takePicture() {
		mCamera.takePicture(null, null, photoCallback);
	}

	SurfaceHolder.Callback surfaceCallback = new SurfaceHolder.Callback() {

		public void surfaceCreated(SurfaceHolder holder) {
			mCamera = Camera.open();

			try {
				mCamera.setPreviewDisplay(previewHolder);
			} catch (Throwable t) {
				Log.e("surfaceCallback", "Exception in setPreviewDisplay()", t);
				Toast.makeText(CameraActivity.this, t.getMessage(),
						Toast.LENGTH_LONG).show();
			}
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			Camera.Parameters parameters = mCamera.getParameters();

			/*
			 * Customize width/height here - otherwise defaults to screen
			 * width/height
			 */
			parameters.setPreviewSize(width, height);
			parameters.setPictureFormat(PixelFormat.JPEG);
			parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
			parameters.setJpegQuality(100);

			mCamera.setParameters(parameters);
			mCamera.startPreview();

			/* Must add the following callback to allow the camera to autofocus. */
			mCamera.autoFocus(new Camera.AutoFocusCallback() {
				public void onAutoFocus(boolean success, Camera camera) {
					Log.d("HOME", "isAutofoucs " + Boolean.toString(success));
				}
			});
		}

		public void surfaceDestroyed(SurfaceHolder holder) {

		}
	};

	Camera.PictureCallback photoCallback = new Camera.PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Intent mIntent = new Intent();
			mIntent.putExtra("picture", data);
			setResult(1, mIntent); // 1 for result code = ok
			/*
			 * This segment moved from surfaceDestroyed - otherwise the Camera
			 * is not properly released
			 */
			if (mCamera != null) {
				mCamera.stopPreview();
				mCamera.release();
				mCamera = null;
			}
			finish();
		}
	};
}