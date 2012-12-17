package com.pictureperfect.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.pictureperfect.imagehandling.ImgData;

/**
 * This screen helps the user to choose the background image.
 * 
 * @author group13
 * 
 */
public class SelectBackgroundActivity extends Activity {

	private ImageView myImageView;
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
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// mIV = new MyImageView(this);
		/*
		 * setContentView(mIV, new LayoutParams(LayoutParams.WRAP_CONTENT,
		 * LayoutParams.WRAP_CONTENT));
		 */
		setContentView(R.layout.selectbackground);
		myImageView = (ImageView) findViewById(R.id.imageView1);

		Button NextPictureButton = (Button) findViewById(R.id.button1);
		Button PreviousPictureButton = (Button) findViewById(R.id.button3);
		Button DonePictureButton = (Button) findViewById(R.id.button4);

		PreviousPictureButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int number = ((ImgData) getApplication()).getMyBackgroundNum();
				if (number > 0) {
					number--;
				}
				((ImgData) getApplication()).setBackgroundNum(number);
				myImageView.setImageBitmap(((ImgData) getApplication())
						.getBackground());
			}
		});

		NextPictureButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				int number = ((ImgData) getApplication()).getMyBackgroundNum();
				if (number < ((ImgData) getApplication()).getNumPictures() - 1) {
					number++;
				}
				((ImgData) getApplication()).setBackgroundNum(number);
				myImageView.setImageBitmap(((ImgData) getApplication())
						.getBackground());
			}
		});

		DonePictureButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Toast.makeText(SelectBackgroundActivity.this,
						"Base Image selected", Toast.LENGTH_SHORT).show();
				Intent intent = new Intent(SelectBackgroundActivity.this,
						ChooseOptionActivity.class);
				startActivity(intent);
			}
		});
		// perform face detection in setFace() in a background thread
		int isImageAvailable = init();
		if (isImageAvailable == 1) {
			Intent intent = new Intent(SelectBackgroundActivity.this,
					WelcomeActivity.class);
			startActivity(intent);
		}
	}

	protected Handler mHandler = new Handler() {
		// @Override
		public void handleMessage(Message msg) {
			myImageView.invalidate();
			super.handleMessage(msg);
		}
	};

	/**
	 * Initialize the screen
	 * 
	 * @return 1 OR 0
	 */
	private int init() {
		if (((ImgData) getApplication()).getMyPictures().size() == 0) {
			return 1;
		} else {
			((ImgData) getApplication()).setBackgroundNum(0);
			myImageView.setImageBitmap(((ImgData) getApplication())
					.getBackground());
			myImageView.invalidate();
			return 0;
		}
	}

	SurfaceHolder.Callback cameraSurfaceCallback = new SurfaceHolder.Callback() {
		public void surfaceCreated(SurfaceHolder holder) {

		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

		}

		public void surfaceDestroyed(SurfaceHolder holder) {

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
				.setMessage(R.string.app_about_messageB)
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
								Intent intent = new Intent(
										SelectBackgroundActivity.this,
										WelcomeActivity.class);
								startActivity(intent);
								finish();
							}
						}).show();
	}
}