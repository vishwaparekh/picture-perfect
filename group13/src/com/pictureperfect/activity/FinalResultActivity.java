package com.pictureperfect.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.pictureperfect.common.Helper;
import com.pictureperfect.common.ImageToGray;
import com.pictureperfect.imagehandling.ImgData;

/**
 * This screen shows the final processed image to the user. The user can choose
 * to save or discard it.
 * 
 * @author group13
 * 
 */
public class FinalResultActivity extends Activity {
	private Canvas myCanvas;
	private Bitmap myBitmap;
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	ImageView myImageView;

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
		setContentView(R.layout.finalresult);
		Button Done = (Button) findViewById(R.id.button4);
		myImageView = (ImageView) findViewById(R.id.imageView1);
		init();

		Done.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Helper.savePhoto(Helper
						.bitmapToByteArray(((ImgData) getApplication())
								.getBackground()), 3);
				Intent intent = new Intent(FinalResultActivity.this,
						WelcomeActivity.class);
				startActivity(intent);
			}
		});
	}

	public void init() {
		Bitmap myBitmapImm = ((ImgData) getApplication()).getBackground();
		myBitmap = Bitmap.createScaledBitmap(myBitmapImm,
				myBitmapImm.getWidth(), myBitmapImm.getHeight(), false);
		myCanvas = new Canvas(myBitmap);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setStrokeWidth(0);
		mPaint.setColor(0x80ff0000);

		myCanvas.drawBitmap(myBitmap, 0, 0, null);
		myImageView.setImageBitmap(myBitmap);
	}

	public Bitmap processBW() {
		int r = 0, g = 0, b = 0, color;

		for (int x = 0; x < myBitmap.getWidth(); x++) {
			for (int y = 0; y < myBitmap.getHeight(); y++) {
				color = myBitmap.getPixel(x, y);
				r = Color.red(color);
				g = Color.green(color);
				b = Color.blue(color);
				double grayScale = ((r+ g+ b)/3);
				if (grayScale > 130) {
					mPaint.setColor(Color.argb(100, 200, 200, 200));
				} else {
					mPaint.setColor(Color.argb(100,10,10,10));
				}
				myCanvas.drawPoint(x, y, mPaint);
				
			}
		}
		return myBitmap;
	}

	public Bitmap processSepia() {
		int r = 0, g = 0, b = 0, color, outputRed, outputGreen,outputBlue;

		for (int x = 0; x < myBitmap.getWidth(); x++) {
			for (int y = 0; y < myBitmap.getHeight(); y++) {
				color = myBitmap.getPixel(x, y);
				r = Color.red(color);
				g = Color.green(color);
				b = Color.blue(color);
				outputRed = (int)((r * .393) + (g * .769) + (b * .189));
				if (outputRed > 255)
					outputRed=255;
				outputGreen = (int)((r * .349) + (g * .686) + (b * .168));
				if (outputGreen>255)
					outputGreen = 255;
				outputBlue = (int)((r * .272) + (g * .534) + (b * .131));
				if(outputBlue>255)
					outputBlue = 255;
				mPaint.setColor(Color
						.argb(80, outputRed, outputGreen, outputBlue));
				myCanvas.drawPoint(x, y, mPaint);
			}
		}
		return myBitmap;
	}

	public Bitmap processGrayscale() {
		int r = 0, g = 0, b = 0, color;

		for (int x = 0; x < myBitmap.getWidth(); x++) {
			for (int y = 0; y < myBitmap.getHeight(); y++) {
				color = myBitmap.getPixel(x, y);
				r = Color.red(color);
				g = Color.green(color);
				b = Color.blue(color);
				int grayScale = (int) ((r+ g+ b)/3);
				mPaint.setColor(Color
						.argb(100, grayScale, grayScale, grayScale));
				myCanvas.drawPoint(x, y, mPaint);
			}
		}
		return myBitmap;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 1, R.string.Sepia);
		menu.add(0, 1, 1, R.string.BW);
		menu.add(0, 2, 1, R.string.GrayScale);
		menu.add(0, 3, 1, R.string.Normal);
		menu.add(0, 4, 1, R.string.app_help);
		menu.add(0, 5, 1, R.string.Exit);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case 0:
			init();
			Helper.savePhoto(
					Helper.bitmapToByteArray(processSepia()), 0);
			break;

		case 1:
			init();
			Helper.savePhoto(Helper.bitmapToByteArray(processBW()), 1);
			break;

		case 2:
			init();
			Helper.savePhoto(Helper.bitmapToByteArray(processGrayscale()), 2);
			break;

		case 3:
			init();
			Helper.savePhoto(Helper
					.bitmapToByteArray(((ImgData) getApplication())
							.getBackground()), 3);
			break;

		case 4:
			openOptionsDialog();
			break;

		case 5:
			exitOptionsDialog();
			break;
		}
		return true;
	}

	private void openOptionsDialog() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.app_help)
				.setMessage(R.string.app_about_messageR)
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
										FinalResultActivity.this,
										WelcomeActivity.class);
								startActivity(intent);
								finish();
							}
						}).show();
	}
}