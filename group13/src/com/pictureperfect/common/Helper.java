package com.pictureperfect.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.util.Log;

public class Helper {

	/**
	 * Convert the Bitmap of an image into Byte array
	 * 
	 * @param bitmap
	 * @return Byte array
	 */
	public static byte[] bitmapToByteArray(Bitmap bitmap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0 /* ignored for PNG */, bos);
		byte[] bitmapdata = bos.toByteArray();
		return bitmapdata;
	}

	public static void savePhoto(byte[] data, int type) {
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/Picture Perfect/");
		File folderAnimated = new File(Environment.getExternalStorageDirectory()
				+ "/Picture Perfect/Animated/");
		boolean success = true;
		if (!folder.exists()) {
			success = folder.mkdir();
		}
		if (!folderAnimated.exists()) {
			success = folderAnimated.mkdir();
		}
		
		if (success) {
			File photo = null;
			long numPictureTaken = System.currentTimeMillis();
			if (type == 1) {
				photo = new File(Environment.getExternalStorageDirectory(),
						"/Picture Perfect/" + "Burstphoto" + numPictureTaken
								+ ".jpg");
			} else if (type == 2) {
				photo = new File(Environment.getExternalStorageDirectory(),
						"/Picture Perfect/" + "Finalphoto" + numPictureTaken
								+ ".jpg");
			} else if (type == 3) {
				photo = new File(Environment.getExternalStorageDirectory(),
						"/Picture Perfect/Animated/" + "Animatedphoto"
								+ numPictureTaken + ".jpg");
			}

			if (photo.exists()) {
				photo.delete();
			}

			try {
				FileOutputStream fos = new FileOutputStream(photo.getPath());
				fos.write(data);
				fos.close();
			} catch (java.io.IOException e) {
				Log.e("PictureDemo", "Exception in photoCallback", e);
			}
		}
	}
}
