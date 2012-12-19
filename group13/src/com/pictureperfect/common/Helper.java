package com.pictureperfect.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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

	/**
	 * Save the image data to file
	 * 
	 * @param data
	 *            Byte array of image to be saved
	 * @param type
	 *            type of image. Burst/Animated
	 */
	public static void savePhoto(byte[] data, int type) {
		File folder = new File(Environment.getExternalStorageDirectory()
				+ "/Picture Perfect/");
		File folderAnimated = new File(
				Environment.getExternalStorageDirectory()
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
				Log.e("SavePhoto", "Exception in photoCallback", e);
			}
		}
	}

	/**
	 * Apply the cartoon effect to the image.
	 * 
	 * @return Processed bitmap
	 */
	public static Bitmap processCartoon(Bitmap myBitmap, Canvas myCanvas,
			Paint mPaint) {
		int r = 0, g = 0, b = 0, color;

		for (int x = 0; x < myBitmap.getWidth(); x++) {
			for (int y = 0; y < myBitmap.getHeight(); y++) {
				color = myBitmap.getPixel(x, y);
				r = Color.red(color);
				g = Color.green(color);
				b = Color.blue(color);
				double grayScale = ((r + g + b) / 3);
				if (grayScale > 130) {
					mPaint.setColor(Color.argb(255, 200, 200, 200));
				} else {
					mPaint.setColor(Color.argb(255, 10, 10, 10));
				}
				myCanvas.drawPoint(x, y, mPaint);

			}
		}
		return myBitmap;
	}

	/**
	 * Apply the Sepia effect to the image.
	 * 
	 * @return Processed bitmap
	 */
	public static Bitmap processSepia(Bitmap myBitmap, Canvas myCanvas,
			Paint mPaint) {
		int r = 0, g = 0, b = 0, color, outputRed, outputGreen, outputBlue;

		for (int x = 0; x < myBitmap.getWidth(); x++) {
			for (int y = 0; y < myBitmap.getHeight(); y++) {
				color = myBitmap.getPixel(x, y);
				r = Color.red(color);
				g = Color.green(color);
				b = Color.blue(color);
				outputRed = (int) ((r * .393) + (g * .769) + (b * .189));
				if (outputRed > 255)
					outputRed = 255;
				outputGreen = (int) ((r * .349) + (g * .686) + (b * .168));
				if (outputGreen > 255)
					outputGreen = 255;
				outputBlue = (int) ((r * .272) + (g * .534) + (b * .131));
				if (outputBlue > 255)
					outputBlue = 255;
				mPaint.setColor(Color.argb(255, outputRed, outputGreen,
						outputBlue));
				myCanvas.drawPoint(x, y, mPaint);
			}
		}
		return myBitmap;
	}

	/**
	 * Apply the Grayscale effect to the image.
	 * 
	 * @return Processed bitmap
	 */
	public static Bitmap processGrayscale(Bitmap myBitmap, Canvas myCanvas,
			Paint mPaint) {
		int r = 0, g = 0, b = 0, color;

		for (int x = 0; x < myBitmap.getWidth(); x++) {
			for (int y = 0; y < myBitmap.getHeight(); y++) {
				color = myBitmap.getPixel(x, y);
				r = Color.red(color);
				g = Color.green(color);
				b = Color.blue(color);
				int grayScale = (int) ((r + g + b) / 3);
				mPaint.setColor(Color
						.argb(255, grayScale, grayScale, grayScale));
				myCanvas.drawPoint(x, y, mPaint);
			}
		}
		return myBitmap;
	}

}