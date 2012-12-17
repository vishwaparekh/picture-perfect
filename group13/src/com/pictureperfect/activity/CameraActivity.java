package com.pictureperfect.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.pictureperfect.common.Helper;
import com.pictureperfect.imagehandling.ImgData;

/**
 * This screen helps the user to click a burst of images.
 * 
 * @author group13
 * 
 */

public class CameraActivity extends Activity {

	private static final int BURST_SIZE = 3;
	protected static final String FLASH_MODE_AUTO = "auto";
	private SurfaceView preview = null;
	private SurfaceHolder previewHolder = null;
	private Camera mCamera = null;
	private boolean cameraConfigured = false;
	private boolean inPreview = false;
	private int numPictureTaken = 1;

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
			int i = 0;
			if (inPreview) {
				takePicture();
				i++;
			}
		}
		return (super.onKeyDown(keyCode, event));
	}

	@Override
	public void onPause() {
		if (inPreview) {
			mCamera.stopPreview();
		}

		mCamera.release();
		mCamera = null;
		inPreview = false;
		super.onPause();
	}

	private Camera.Size getBestPreviewSize(int width, int height,
			Camera.Parameters parameters) {
		int rotation = this.getWindowManager().getDefaultDisplay()
				.getRotation();
		int degrees = 0;
		switch (rotation) {
		case Surface.ROTATION_0:
			degrees = 0;
			break;
		case Surface.ROTATION_90:
			degrees = 90;
			break;
		case Surface.ROTATION_180:
			degrees = 180;
			break;
		case Surface.ROTATION_270:
			degrees = 270;
			break;
		}

		mCamera.setDisplayOrientation((360 - degrees + 90) % 360);

		Camera.Size result = null;
		for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
			if (size.width <= width && size.height <= height) {
				if (result == null) {
					result = size;
				} else {
					int resultArea = result.width * result.height;
					int newArea = size.width * size.height;

					if (newArea > resultArea) {
						result = size;
					}
				}
			}
		}
		return (result);
	}

	private void initPreview(int width, int height) {
		if (mCamera != null && previewHolder.getSurface() != null) {
			try {
				mCamera.setPreviewDisplay(previewHolder);
			} catch (Throwable t) {
				Log.e("Preview-surfaceCallback",
						"Exception in setPreviewDisplay()", t);

				Toast.makeText(CameraActivity.this, t.getMessage(),
						Toast.LENGTH_LONG).show();
			}

			if (!cameraConfigured) {
				Camera.Parameters parameters = mCamera.getParameters();
				Camera.Size size = getBestPreviewSize(width, height, parameters);

				if (size != null) {
					parameters.setPreviewSize(size.width, size.height);
					mCamera.setParameters(parameters);
					cameraConfigured = true;
				}
			}
		}
	}

	private void startPreview() {
		if (cameraConfigured && mCamera != null) {
			mCamera.startPreview();
			inPreview = true;
		}
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
			// parameters.setFlashMode(FLASH_MODE_AUTO);
			mCamera.setParameters(parameters);
			initPreview(width, height);
			startPreview();

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
			((ImgData) getApplication()).addPicture(data);
			inPreview = true;
			Helper.savePhoto(data, 1);
			if (numPictureTaken < BURST_SIZE) {
				takePicture();
				numPictureTaken++;
			} else {
				System.out.println("Burst successfully taken");
				Intent intent = new Intent(CameraActivity.this,
						SelectBackgroundActivity.class);
				startActivity(intent);
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 1, R.string.app_help);
		menu.add(0, 1, 1, R.string.Exit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case 0:
			openOptionsDialog();
			break;
		case 1:
			exitOptionsDialog();
			break;
		}
		return true;
	}

	private void openOptionsDialog() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.app_help)
				.setMessage(R.string.app_about_message)
				.setPositiveButton(R.string.str_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
							}
						}).show();
	}

	private void exitOptionsDialog() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.Exit)
				.setMessage(R.string.ays)
				.setNegativeButton(R.string.str_no,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
							}
						})
				.setPositiveButton(R.string.str_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								finish();
							}
						}).show();
	}
}