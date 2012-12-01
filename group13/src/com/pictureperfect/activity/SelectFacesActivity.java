package com.pictureperfect.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.provider.Contacts.People;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

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
	
	private int currentpId = 0;
	private int currentfaceId = 0;
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
		Button Next = (Button) findViewById(R.id.button1);
		Button Done = (Button) findViewById(R.id.facesDone); // Insert Button
		initializeScreen();
		
		Done.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finished = true;
				if (finished && RemoveUnwantedObjectsActivity.finished) {
					Intent intent = new Intent(SelectFacesActivity.this,
							FinalResultActivity.class);
					startActivity(intent);
				} else {
					Intent intent = new Intent(SelectFacesActivity.this,
							ChooseOptionActivity.class);
					startActivity(intent);
				}
			}
		});
		
		Next.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				currentfaceId = currentfaceId+1;
				if(currentfaceId > ((ImgData) getApplication()).getNumPictures()-1){
					currentfaceId = 0;
				}
				Faces bestFace = ((ImgData) getApplication()).getMyPeople().get(currentpId).getFaces().get(currentfaceId);
				((ImgData) getApplication()).setBestFace(currentpId, bestFace);
			}
		});
	}
	
	/**
	 * This method initializes the screen with all the faces corresponding to each person
	 */
	private void initializeScreen() {
		myBitmap = ((ImgData) getApplication()).getBackground();
		currentfaceId=((ImgData) getApplication()).getMyBackgroundNum();
		myCanvas = new Canvas(myBitmap);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setColor(0x80ff0000);
		mPaint.setStrokeWidth(3);
		myCanvas.drawBitmap(myBitmap, 0, 0, null);
		mIV.setImageBitmap(myBitmap);
		int bgImg =  ((ImgData) getApplication()).getMyBackgroundNum();
		for (int i=0;i<((ImgData) getApplication()).getNumPictures();i++)
		{
			myBitmapFaces.add(((ImgData) getApplication()).getFaces(bgImg).get(i).getFaceImg());
			myCanvasFaces.add(new Canvas(myBitmapFaces.get(i)));
			myCanvasFaces.get(i).drawBitmap(myBitmapFaces.get(i),0,0,null);
			faceView.get(i).setImageBitmap(myBitmapFaces.get(i));
		}
		
	}

	/**
	 * This method switches the current selection of person.  
	 */
	private void changePersonSelection() {
	}

	/**
	 * This method updates the processed image to the view.
	 */
	private void refreshImage() {
	}

	/**
	 * This method switches the current selection of face.
	 */
	private void changeFaceSelection() {
	}

}