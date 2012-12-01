package com.pictureperfect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.pictureperfect.imagehandling.ImgData;

/**
 * This screen helps the user to choose the background image.
 * 
 * @author group13
 * 
 */
public class SelectBackgroundActivity extends Activity {

	private ImageView myImageView;
	private int mFaceWidth = 200;
	private int mFaceHeight = 200;
	private static final int MAX_FACES = 10;
	private static boolean DEBUG = false;

	protected static final int GUIUPDATE_SETFACE = 999;

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

	protected Handler mHandler = new Handler() {
		// @Override
		public void handleMessage(Message msg) {
			myImageView.invalidate();
			super.handleMessage(msg);
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//mIV = new MyImageView(this);
		/*setContentView(mIV, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));*/
		setContentView(R.layout.selectbackground);
		myImageView = (ImageView) findViewById(R.id.imageView1);
		
		 Button NextPictureButton = (Button) findViewById(R.id.button1);
		 Button PreviousPictureButton = (Button) findViewById(R.id.button3);
		 Button DonePictureButton = (Button) findViewById(R.id.button4);
		
		 PreviousPictureButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int number=((ImgData) getApplication()).getMyBackgroundNum();
				if(number>0){
					number--;
				}
				((ImgData) getApplication()).setBackgroundNum(number);
				myImageView.setImageBitmap(((ImgData) getApplication()).getBackground());
			}
		});
		
		NextPictureButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int number=((ImgData) getApplication()).getMyBackgroundNum();
				if(number<((ImgData) getApplication()).getNumPictures()-1){
					number++;
				}
				((ImgData) getApplication()).setBackgroundNum(number);
				myImageView.setImageBitmap(((ImgData) getApplication()).getBackground());
			}
		});
		
		DonePictureButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(SelectBackgroundActivity.this,
						ChooseOptionActivity.class);
				startActivity(intent);
			}
		});
		// perform face detection in setFace() in a background thread
		init();
		
	}

	private void init() {
		((ImgData) getApplication()).setBackgroundNum(0);
		myImageView.setImageBitmap(((ImgData) getApplication()).getBackground());
		myImageView.invalidate();
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
	 * This method updates the background number in both the view and the
	 * ImgData object.
	 */
	private void changeBackground() {

	}

}