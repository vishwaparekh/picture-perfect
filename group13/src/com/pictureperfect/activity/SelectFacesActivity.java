package com.pictureperfect.activity;

import java.util.ArrayList;
import java.util.List;

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
import android.widget.Toast;

import com.pictureperfect.common.RectRegion;
import com.pictureperfect.imagehandling.Faces;
import com.pictureperfect.imagehandling.ImgData;
import com.pictureperfect.imagehandling.Person;

/**
 * This screen helps the user to select the best face of all the persons.
 * 
 * @author group13
 * 
 */
public class SelectFacesActivity extends Activity {
	private Canvas myCanvas;
	private Bitmap myBitmap;
	private List<Canvas> myCanvasFaces = new ArrayList<Canvas>();
	private List<Bitmap> myBitmapFaces = new ArrayList<Bitmap>();
	private ImageView mIV;
	private List<ImageView> faceView = new ArrayList<ImageView>();
	static boolean finished = false;
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected static final int GUIUPDATE_SETFACE = 999;
	private int totalFaces = 0;
	private int currentpId = 0;
	private int currentfaceId = 0;
	private int totalPeople = 0;
	private List<Person> myPeople = new ArrayList<Person>();
	private List<Faces> myFaces = new ArrayList<Faces>();

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
		setContentView(R.layout.selectfaces);
		mIV = (ImageView) findViewById(R.id.imageView1);
		faceView.add((ImageView) findViewById(R.id.imageView2));
		faceView.add((ImageView) findViewById(R.id.imageView4));
		faceView.add((ImageView) findViewById(R.id.imageView3));
		Button NextPerson = (Button) findViewById(R.id.button1);
		Button NextFace = (Button) findViewById(R.id.button2);
		Button Done = (Button) findViewById(R.id.facesDone);
		initializeScreen();

		Done.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				blendImage();
				finished = true;
				if (finished && CreateAnimationActivity.finished) {
					Intent intent = new Intent(SelectFacesActivity.this,
							FinalResultActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(SelectFacesActivity.this, "Face selected",
							Toast.LENGTH_LONG).show();
					Intent intent = new Intent(SelectFacesActivity.this,
							ChooseOptionActivity.class);
					startActivity(intent);
				}
			}
		});

		NextPerson.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				changePersonSelection();
			}
		});

		NextFace.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				changeFaceSelection();
			}
		});
	}

	/**
	 * This method initializes the screen with all the faces corresponding to
	 * each person
	 */
	private void initializeScreen() {
		Bitmap myBitmapImm = ((ImgData) getApplication()).getBackground();
		myBitmap = Bitmap.createScaledBitmap(myBitmapImm,
				myBitmapImm.getWidth(), myBitmapImm.getHeight(), false);
		myCanvas = new Canvas(myBitmap);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setColor(0x80ff0000);
		mPaint.setStrokeWidth(3);
		myCanvas.drawBitmap(myBitmap, 0, 0, null);
		mIV.setImageBitmap(myBitmap);
		totalPeople = ((ImgData) getApplication()).getMyPeople().size();
		if (totalPeople == 0) {
			return;
		}
		totalFaces = ((ImgData) getApplication()).getMyPeople().get(0)
				.getFaces().size();
		if (totalFaces == 0) {
			return;
		}
		myFaces = ((ImgData) getApplication()).getMyPeople().get(0).getFaces();
		myPeople = ((ImgData) getApplication()).getMyPeople();
		for (int j = 0; j < 3; j++) {
			int i = j % totalFaces;
			myBitmapFaces.add(i, myFaces.get(i).getFaceImg());
			myCanvasFaces.add(i, new Canvas(myBitmapFaces.get(i)));
			myCanvasFaces.get(i).drawBitmap(myBitmapFaces.get(i), 0, 0, null);
			faceView.get(i).setImageBitmap(myBitmapFaces.get(i));
		}
		RectRegion facP = myPeople.get(currentpId).getBaseFace().getFacePos();
		float left = facP.getX();
		float bottom = facP.getY();
		float right = facP.getWidth() + left;
		float top = facP.getHeight() + bottom;
		mPaint.setColor(Color.GREEN);
		myCanvas.drawRect(left, top, right, bottom, mPaint);

	}

	/**
	 * This method switches the current selection of person.
	 */
	private void changePersonSelection() {
		if (totalPeople == 0)
			return;
		currentpId = (currentpId + 1) % myPeople.size();
		Bitmap myBitmapImm = ((ImgData) getApplication()).getBackground();
		myBitmap = Bitmap.createScaledBitmap(myBitmapImm,
				myBitmapImm.getWidth(), myBitmapImm.getHeight(), false);

		myCanvas = new Canvas(myBitmap);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setColor(0x80ff0000);
		mPaint.setStrokeWidth(3);
		myCanvas.drawBitmap(myBitmap, 0, 0, null);
		mIV.setImageBitmap(myBitmap);
		RectRegion facP = myPeople.get(currentpId).getBaseFace().getFacePos();
		float left = facP.getX();
		float bottom = facP.getY();
		float right = facP.getWidth() + left;
		float top = facP.getHeight() + bottom;
		mPaint.setColor(Color.GREEN);
		myCanvas.drawRect(left, top, right, bottom, mPaint);
	}

	/**
	 * This method switches the current selection of face.
	 */
	private void changeFaceSelection() {
		if (totalPeople == 0)
			return;
		currentfaceId = (currentfaceId + 1) % myFaces.size();
		Faces bestFace = myPeople.get(currentpId).getFaces().get(currentfaceId);
		((ImgData) getApplication()).setBestFace(currentpId, bestFace);
		Bitmap myBitmapImm = ((ImgData) getApplication()).getBackground();
		myBitmap = Bitmap.createScaledBitmap(myBitmapImm,
				myBitmapImm.getWidth(), myBitmapImm.getHeight(), false);
		myCanvas = new Canvas(myBitmap);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setColor(0x80ff0000);
		mPaint.setStrokeWidth(3);
		myCanvas.drawBitmap(myBitmap, 0, 0, null);
		mIV.setImageBitmap(myBitmap);
		for (int j = currentfaceId; j < currentfaceId + 3; j++) {
			int i = j % totalFaces;
			myBitmapFaces.add(i, myFaces.get(i).getFaceImg());
			myCanvasFaces.add(i, new Canvas(myBitmapFaces.get(i)));
			myCanvasFaces.get(i).drawBitmap(myBitmapFaces.get(i), 0, 0, null);
			faceView.get(j - currentfaceId)
					.setImageBitmap(myBitmapFaces.get(i));
		}
		RectRegion facP = myPeople.get(currentpId).getBaseFace().getFacePos();
		float left = facP.getX();
		float bottom = facP.getY();
		float right = facP.getWidth() + left;
		float top = facP.getHeight() + bottom;
		mPaint.setColor(Color.GREEN);
		myCanvas.drawRect(left, top, right, bottom, mPaint);
	}

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
				.setMessage(R.string.app_about_messageF)
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
										SelectFacesActivity.this,
										WelcomeActivity.class);
								startActivity(intent);
								finish();
							}
						}).show();
	}

	/**
	 * Alternate Blend option.
	 */
	private void blendImage_old() {
		Bitmap myBitmapIm = ((ImgData) getApplication()).getBackground();
		Canvas myCanvasIm = new Canvas(myBitmapIm);
		int r = 0, g = 0, b = 0, color;
		int r1 = 0, g1 = 0, b1 = 0, color1;
		double outerBoundary = 0.2;

		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setColor(0x80ff0000);
		mPaint.setStrokeWidth(0);
		myCanvasIm.drawBitmap(myBitmapIm, 0, 0, null);
		for (currentpId = 0; currentpId < myPeople.size(); currentpId++) {
			Bitmap faceBase = myPeople.get(currentpId).getBaseFace()
					.getFaceImg();
			int startX = myPeople.get(currentpId).getBaseFace().getFacePos()
					.getX();
			int startY = myPeople.get(currentpId).getBaseFace().getFacePos()
					.getY();
			Bitmap faceBestTemp = myPeople.get(currentpId).getBestFace()
					.getFaceImg();
			Bitmap faceBest = Bitmap.createScaledBitmap(faceBestTemp,
					faceBase.getWidth(), faceBase.getHeight(), false);

			for (int x = 0; x < faceBest.getWidth(); x++) {
				for (int y = 0; y < faceBest.getHeight(); y++) {
					int alphaVal = 255;
					color = faceBest.getPixel(x, y);
					r = Color.red(color);
					g = Color.green(color);
					b = Color.blue(color);
					color1 = faceBase.getPixel(x, y);
					r1 = Color.red(color1);
					g1 = Color.green(color1);
					b1 = Color.blue(color1);

					if (x < outerBoundary * faceBest.getWidth()
							|| y < outerBoundary * faceBest.getHeight()
							|| x > (1 - outerBoundary) * faceBest.getWidth()
							|| y > (1 - outerBoundary) * faceBest.getHeight()) {

						double baseContr = 0;
						double bestContr = 0;
						if (x < faceBest.getWidth() * outerBoundary) {
							bestContr = Math.max(bestContr,
									x / (faceBest.getWidth() * outerBoundary));
							baseContr = 1 - bestContr;
						}
						if (x > faceBest.getWidth() * (1 - outerBoundary)) {
							bestContr = Math
									.max(bestContr,
											(faceBase.getWidth() - x)
													/ (faceBest.getWidth() * outerBoundary));
							baseContr = 1 - bestContr;
						}
						if (y < faceBest.getHeight() * outerBoundary) {
							bestContr = Math.max(bestContr,
									y / (faceBest.getHeight() * outerBoundary));
							baseContr = 1 - bestContr;
						}
						if (y > faceBest.getHeight() * (1 - outerBoundary)) {
							bestContr = Math
									.max(bestContr,
											(faceBase.getHeight() - y)
													/ (faceBest.getHeight() * outerBoundary));
							baseContr = 1 - bestContr;
						}

						r = (int) ((double) r1 * baseContr + (double) r
								* bestContr);
						g = (int) ((double) g1 * baseContr + (double) g
								* bestContr);
						b = (int) ((double) b1 * baseContr + (double) b
								* bestContr);
					}

					mPaint.setColor(Color.argb(alphaVal, r, g, b));
					myCanvasIm.drawPoint(startX + x, startY + y, mPaint);
				}
			}
		}
	}

	/**
	 * Method to blend the images
	 */
	private void blendImage() {
		Bitmap myBitmapIm = ((ImgData) getApplication()).getBackground();
		Canvas myCanvasIm = new Canvas(myBitmapIm);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setColor(0x80ff0000);
		mPaint.setStrokeWidth(0);
		myCanvasIm.drawBitmap(myBitmapIm, 0, 0, null);
		for (currentpId = 0; currentpId < myPeople.size(); currentpId++) {
			Bitmap faceBase = myPeople.get(currentpId).getBaseFace()
					.getFaceImg();

			int startX = myPeople.get(currentpId).getBaseFace().getFacePos()
					.getX();
			int startY = myPeople.get(currentpId).getBaseFace().getFacePos()
					.getY();

			blendSides(myBitmapIm, myCanvasIm, startX, startY, faceBase);
		}
	}

	/**
	 * Method to blend the sides
	 * @param myBitmapIm
	 * @param myCanvasIm
	 * @param startX
	 * @param startY
	 * @param faceBase
	 */
	private void blendSides(Bitmap myBitmapIm, Canvas myCanvasIm, int startX,
			int startY, Bitmap faceBase) {
		double outerBoundary = 0.1;
		int deltaX = (int) (outerBoundary * faceBase.getWidth());
		int deltaY = (int) (outerBoundary * faceBase.getHeight());
		int tempstartX = Math.max(startX - deltaX, 0);
		int tempstartY = Math.max(startY, 0);
		int tempendX = Math.min(startX, myBitmapIm.getWidth() - 1);
		int tempendY = Math.min(startY + faceBase.getHeight(),
				myBitmapIm.getHeight() - 1);
		loopandInterpolate(tempstartX, tempstartY, tempendX, tempendY,
				myBitmapIm, myCanvasIm, true);
		tempstartX = Math.max(startX + faceBase.getWidth(), 0);
		tempendX = Math.min(startX + faceBase.getWidth() + deltaX,
				myBitmapIm.getWidth() - 1);
		loopandInterpolate(tempstartX, tempstartY, tempendX, tempendY,
				myBitmapIm, myCanvasIm, true);
		tempstartX = Math.max(startX, 0);
		tempendX = Math.min(startX + faceBase.getWidth(),
				myBitmapIm.getWidth() - 1);
		tempstartY = Math.max(startY - deltaY, 0);
		tempendY = Math.min(startY, myBitmapIm.getHeight() - 1);
		loopandInterpolate(tempstartX, tempstartY, tempendX, tempendY,
				myBitmapIm, myCanvasIm, false);
		tempstartY = Math.max(startY + faceBase.getHeight(), 0);
		tempendY = Math.min(startY + faceBase.getHeight() + deltaY,
				myBitmapIm.getHeight() - 1);
		loopandInterpolate(tempstartX, tempstartY, tempendX, tempendY,
				myBitmapIm, myCanvasIm, false);

	}

	/**
	 * This method interpolates the bitmaps
	 * 
	 * @param tempstartX
	 * @param tempstartY
	 * @param tempendX
	 * @param tempendY
	 * @param myBitmapIm
	 * @param myCanvasIm
	 * @param isX
	 */
	private void loopandInterpolate(int tempstartX, int tempstartY,
			int tempendX, int tempendY, Bitmap myBitmapIm, Canvas myCanvasIm,
			boolean isX) {
		for (int x = tempstartX; x <= tempendX; x++) {
			for (int y = tempstartY; y <= tempendY; y++) {
				if (x > myBitmapIm.getWidth() || x < 0 || y < 0
						|| y > myBitmapIm.getHeight()) {
					continue;
				}
				if (isX)
					interpolateColor(myBitmapIm, tempstartX, tempendX, y, y, y,
							x, myCanvasIm);
				else
					interpolateColor(myBitmapIm, x, x, tempstartY, tempendY, y,
							x, myCanvasIm);
			}
		}
	}

	/**
	 * This method interpolates the color of the Bitmap
	 * @param myBitmapIm
	 * @param startX
	 * @param endX
	 * @param startY
	 * @param endY
	 * @param y
	 * @param x
	 * @param myCanvasIm
	 * @return
	 */
	private int interpolateColor(Bitmap myBitmapIm, int startX, int endX,
			int startY, int endY, int y, int x, Canvas myCanvasIm) {
		int delta;

		delta = Math.max(endX - startX, endY - startY);
		if (delta == 0) {
			return 0;
		}
		int color_left = myBitmapIm.getPixel(startX, startY);
		int rLeft = Color.red(color_left);
		int gLeft = Color.green(color_left);
		int bLeft = Color.blue(color_left);
		int color_right = myBitmapIm.getPixel(endX, endY);
		int rRight = Color.red(color_right);
		int gRight = Color.green(color_right);
		int bRight = Color.blue(color_right);

		int distLeft = Math.min(x - (startX), (endX - startX))
				+ Math.min(y - (startY), (endY - startY));
		int distRight = Math.min(endX - (x), (endX - startX))
				+ Math.min(endY - y, (endY - startY));

		int r = (rLeft * distRight + rRight * distLeft) / (delta);
		int g = (gLeft * distRight + gRight * distLeft) / (delta);
		int b = (bLeft * distRight + bRight * distLeft) / (delta);
		mPaint.setColor(Color.argb(255, r, g, b));
		myCanvasIm.drawPoint(x, y, mPaint);
		return 0;
	}
}