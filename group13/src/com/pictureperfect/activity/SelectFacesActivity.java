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
	private int mFaceWidth = 200;
	private int mFaceHeight = 200;
	private static final int MAX_FACES = 10;
	private static boolean DEBUG = false;
	static boolean finished = false;
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	private int mDisplayStyle = 0;
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
				if (finished && RemoveUnwantedObjectsActivity.finished) {
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
		// TODO Auto-generated method stub
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

	private void blendImage() {
		Bitmap myBitmapIm = ((ImgData) getApplication()).getBackground();
		Canvas myCanvasIm = new Canvas(myBitmapIm);
		int r = 0, g = 0, b = 0, color;
		int r1 = 0, g1 = 0, b1 = 0, color1;
		double outerBoundary = 0.3;
		double innerBoundary = 0;

		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setColor(0x80ff0000);
		mPaint.setStrokeWidth(0);
		myCanvasIm.drawBitmap(myBitmapIm, 0, 0, null);
		for (currentpId = 0; currentpId < myPeople.size(); currentpId++) {
			Bitmap faceBase = myPeople.get(currentpId).getBaseFace()
					.getFaceImg();
			int startX =  myPeople.get(currentpId).getBaseFace().getFacePos().getX();
			int startY = myPeople.get(currentpId).getBaseFace().getFacePos().getY();
			Bitmap faceBestTemp = myPeople.get(currentpId).getBestFace()
					.getFaceImg();
			Bitmap faceBest = Bitmap.createScaledBitmap(faceBestTemp, faceBase.getWidth(), faceBase.getHeight(), false);
			
			for (int x = 0; x < faceBest.getWidth(); x++) {
				for (int y = 0; y < faceBest.getHeight(); y++) {
					int alphaVal = 1;
					color = faceBest.getPixel(x, y);
					r = Color.red(color);
					g = Color.green(color);
					b = Color.blue(color);
					color1 = faceBase.getPixel(x, y);
					r1 = Color.red(color1);
					g1 = Color.green(color1);
					b1 = Color.blue(color1);
					if (x < innerBoundary * faceBest.getWidth()
							|| y < innerBoundary * faceBest.getHeight()
							|| x > (1 - innerBoundary) * faceBest.getWidth()
							|| y > (1 - innerBoundary) * faceBest.getHeight()) {
						r = r1;
						b = b1;
						g = g1;

					}
					else if (x < outerBoundary * faceBest.getWidth()
							|| y < outerBoundary * faceBest.getHeight()
							|| x > (1 - outerBoundary) * faceBest.getWidth()
							|| y > (1 - outerBoundary) * faceBest.getHeight()) {
						
						
						double baseContr=0;
						double bestContr=0;
						if(x< faceBest.getWidth() * outerBoundary){
							bestContr = Math.max(bestContr,x/(faceBest.getWidth() * outerBoundary));
							baseContr = 1-bestContr;
						}
						if(x > faceBest.getWidth() * (1-outerBoundary)){
							bestContr = Math.max(bestContr,(faceBase.getWidth()-x)/(faceBest.getWidth() * outerBoundary));
							baseContr = 1-bestContr;
						}
						if(y< faceBest.getHeight()* outerBoundary){
							bestContr = Math.max(bestContr,y/(faceBest.getHeight() * outerBoundary));
							baseContr = 1-bestContr;
						}
						if(y > faceBest.getHeight()* (1-outerBoundary)){
							bestContr = Math.max(bestContr,(faceBase.getHeight()-y)/(faceBest.getHeight()* outerBoundary));
							baseContr = 1-bestContr;
						}
							
						/*r = (int)((double)r1*baseContr + (double)r*bestContr);
						g = (int)((double)g1*baseContr + (double)g*bestContr);
						b = (int)((double)b1*baseContr + (double)b*bestContr);*/
						r = r1;
						g = g1;
						b = b1;
						alphaVal = (int)(baseContr*100);
					}
					
					mPaint.setColor(Color
							.argb(alphaVal, r, g, b));
					myCanvas.drawPoint(startX+x, startY+y, mPaint);
				}
			}
		}
	}
}
