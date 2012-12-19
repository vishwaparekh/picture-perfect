package com.pictureperfect.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.pictureperfect.common.Helper;
import com.pictureperfect.imagehandling.ImgData;

/**
 * This screen helps to animate the object in the images.
 * 
 * @author group13
 * 
 */
public class CreateAnimationActivity extends Activity {
	private Canvas myCanvas;
	private Bitmap myBitmap;
	private List<Canvas> myCanvasImages = new ArrayList<Canvas>();
	private List<Bitmap> myBitmapImages = new ArrayList<Bitmap>();
	private ImageView myImageView;
	private List<ImageView> pictureView = new ArrayList<ImageView>();
	static boolean finished = false;
	private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
	protected static final int GUIUPDATE_SETFACE = 999;
	private int alphaComponent = 255;
	private int currentPictureId = 0;

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
		setContentView(R.layout.removeunwantedobjects);
		myImageView = (ImageView) findViewById(R.id.imageView1);
		pictureView.add((ImageView) findViewById(R.id.imageView2));
		pictureView.add((ImageView) findViewById(R.id.imageView4));
		pictureView.add((ImageView) findViewById(R.id.imageView3));
		Button NextPerson = (Button) findViewById(R.id.button1);
		Button NextFace = (Button) findViewById(R.id.button2);
		Button Done = (Button) findViewById(R.id.facesDone);
		initializeScreen();

		Done.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Helper.savePhoto(Helper.bitmapToByteArray(myBitmap), 3);
				finished = true;
				if (finished && SelectFacesActivity.finished) {
					Intent intent = new Intent(CreateAnimationActivity.this,
							FinalResultActivity.class);
					startActivity(intent);
				} else {
					Toast.makeText(CreateAnimationActivity.this,
							"Animation saved", Toast.LENGTH_LONG).show();
					Intent intent = new Intent(CreateAnimationActivity.this,
							ChooseOptionActivity.class);
					startActivity(intent);
				}
			}
		});

		NextPerson.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				addImageSelection();
			}

			
		});

		NextFace.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				changeImageSelection();
			}

			
		});
	}

	/**
	 * This method initializes the screen with all the images.
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
		myImageView.setImageBitmap(myBitmap);
		for (int j = 0; j < 3; j++) {
			int i = j % ((ImgData) getApplication()).getMyPictures().size();
			myBitmapImages.add(i, ((ImgData) getApplication()).getMyPictures()
					.get(i));
			myCanvasImages.add(i, new Canvas(myBitmapImages.get(i)));
			myCanvasImages.get(i).drawBitmap(myBitmapImages.get(i), 0, 0, null);
			pictureView.get(i).setImageBitmap(myBitmapImages.get(i));
		}
	}

	/**
     * This method add the object into animation.
     */
    private void addImageSelection() {
            Bitmap superImpose = ((ImgData) getApplication()).getMyPictures().get(
                            currentPictureId);
            alphaComponent = alphaComponent * 1 / 2;
            mPaint.setAlpha(alphaComponent);
            myCanvas.drawBitmap(superImpose, 0, 0, mPaint);
    }
	
	/**
     * This method switches the current selection of face.
     */
    private void changeImageSelection() {
            currentPictureId = (currentPictureId + 1)
                            % ((ImgData) getApplication()).getMyPictures().size();
            for (int j = currentPictureId; j < currentPictureId + 3; j++) {
                    int i = j % ((ImgData) getApplication()).getMyPictures().size();
                    myBitmapImages.add(i, ((ImgData) getApplication()).getMyPictures()
                                    .get(i));
                    myCanvasImages.add(i, new Canvas(myBitmapImages.get(i)));
                    myCanvasImages.get(i).drawBitmap(myBitmapImages.get(i), 0, 0, null);
                    pictureView.get(j - currentPictureId).setImageBitmap(
                                    myBitmapImages.get(i));
            }

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
				.setMessage(R.string.app_about_messageA)
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
										CreateAnimationActivity.this,
										WelcomeActivity.class);
								startActivity(intent);
								finish();
							}
						}).show();
	}
}