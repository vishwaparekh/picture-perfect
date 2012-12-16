package com.pictureperfect.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.pictureperfect.common.Helper;
import com.pictureperfect.imagehandling.ImgData;

/**
 * This screen helps the user to click a burst of images.
 * 
 * @author group13
 * 
 */

public class EditExistingActivity extends Activity {

	boolean flag= true;
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
		uploadImagesFromGallery();
		}

	public void openGallery() {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, 0);
		
	}

	protected void onActivityResult(int requestCode, int resultCode,
			Intent imageReturnedIntent) {
		super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

		switch (requestCode) {
		case 0:
			if (resultCode == RESULT_OK) {
				Uri selectedImage = imageReturnedIntent.getData();
				String[] filePathC = { MediaStore.Images.Media.DATA };

				Cursor cursor = getContentResolver().query(selectedImage,
						filePathC, null, null, null);
				cursor.moveToFirst();

				int columnIndex = cursor.getColumnIndex(filePathC[0]);
				String filePath = cursor.getString(columnIndex);
				cursor.close();

				BitmapFactory.Options options = new BitmapFactory.Options();
				options.inPreferredConfig = Config.RGB_565;
				options.inSampleSize = 10;
				Bitmap myImage = BitmapFactory.decodeFile(filePath,options);
				((ImgData) getApplication()).addPicture(myImage);
			}
		}
		uploadImagesFromGallery();
	}
	
	private void uploadImagesFromGallery() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.Upload)
				.setMessage(R.string.ayss)
				.setNegativeButton(R.string.str_no,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								flag=false;
								Intent intent = new Intent(EditExistingActivity.this,
										 SelectBackgroundActivity.class); 
								 startActivity(intent);
							}
						})
				.setPositiveButton(R.string.str_yes,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								openGallery();
							}
						}).show();
	}
}