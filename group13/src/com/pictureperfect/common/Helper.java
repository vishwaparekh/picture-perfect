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
	 * @param bitmap
	 * @return Byte array
	 */
	public static byte[] bitmapToByteArray(Bitmap bitmap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0 /* ignored for PNG */, bos);
		byte[] bitmapdata = bos.toByteArray();
		return bitmapdata;
	}
	
	public static void savePhoto(byte[] data, int numPictureTaken) {
		File photo = new File(Environment.getExternalStorageDirectory(),
				"/Picture Perfect/" + "photo" + numPictureTaken + ".jpg");

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
