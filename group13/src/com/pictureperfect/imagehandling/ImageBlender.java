package com.pictureperfect.imagehandling;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ImageBlender {
	/**
	 * Method to blend the images: This method is based on linear interpolation.
	 * We interpolate the delta pixels on the side to linearly vary to match the starting
	 * intensity of each of the 4 sides of the face
	 */
	public static void blendImage(Bitmap myBitmapIm, Paint mPaint, List<Person> myPeople) {
		Canvas myCanvasIm = new Canvas(myBitmapIm);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		mPaint.setColor(0x80ff0000);
		mPaint.setStrokeWidth(0);
		myCanvasIm.drawBitmap(myBitmapIm, 0, 0, null);
		for (int tempPersonCtr = 0; tempPersonCtr < myPeople.size(); tempPersonCtr++) {
			Bitmap faceBase = myPeople.get(tempPersonCtr).getBaseFace()
					.getFaceImg();

			int startX = myPeople.get(tempPersonCtr).getBaseFace().getFacePos()
					.getX();
			int startY = myPeople.get(tempPersonCtr).getBaseFace().getFacePos()
					.getY();

			blendSides(myBitmapIm, myCanvasIm, startX, startY, faceBase, mPaint);
		}
	}

	/**
	 * Method to blend the sides. This method gets called by the blendImage.
	 * This actually calculates the delta interpolate and calls the interpolation method
	 * on each of the 4 sides
	 * @param myBitmapIm
	 * @param myCanvasIm
	 * @param startX
	 * @param startY
	 * @param faceBase
	 */
	private static void blendSides(Bitmap myBitmapIm, Canvas myCanvasIm, int startX,
			int startY, Bitmap faceBase, Paint mPaint) {
		double outerBoundary = 0.1;
		int deltaX = (int) (outerBoundary * faceBase.getWidth());
		int deltaY = (int) (outerBoundary * faceBase.getHeight());
		int tempstartX = Math.max(startX - deltaX, 0);
		int tempstartY = Math.max(startY, 0);
		int tempendX = Math.min(startX, myBitmapIm.getWidth() - 1);
		int tempendY = Math.min(startY + faceBase.getHeight(),
				myBitmapIm.getHeight() - 1);
		loopandInterpolate(tempstartX, tempstartY, tempendX, tempendY,
				myBitmapIm, myCanvasIm, true, mPaint);
		tempstartX = Math.max(startX + faceBase.getWidth(), 0);
		tempendX = Math.min(startX + faceBase.getWidth() + deltaX,
				myBitmapIm.getWidth() - 1);
		loopandInterpolate(tempstartX, tempstartY, tempendX, tempendY,
				myBitmapIm, myCanvasIm, true, mPaint);
		tempstartX = Math.max(startX, 0);
		tempendX = Math.min(startX + faceBase.getWidth(),
				myBitmapIm.getWidth() - 1);
		tempstartY = Math.max(startY - deltaY, 0);
		tempendY = Math.min(startY, myBitmapIm.getHeight() - 1);
		loopandInterpolate(tempstartX, tempstartY, tempendX, tempendY,
				myBitmapIm, myCanvasIm, false, mPaint);
		tempstartY = Math.max(startY + faceBase.getHeight(), 0);
		tempendY = Math.min(startY + faceBase.getHeight() + deltaY,
				myBitmapIm.getHeight() - 1);
		loopandInterpolate(tempstartX, tempstartY, tempendX, tempendY,
				myBitmapIm, myCanvasIm, false, mPaint);

	}

	/**
	 * This method interpolates the bitmaps based on information provided by blendSides
	 * It calls the interpolate method which calculates the final intensity to be produced
	 * for each point and superimposes that on the original image
	 * @param tempstartX
	 * @param tempstartY
	 * @param tempendX
	 * @param tempendY
	 * @param myBitmapIm
	 * @param myCanvasIm
	 * @param isX
	 */
	private static void loopandInterpolate(int tempstartX, int tempstartY,
			int tempendX, int tempendY, Bitmap myBitmapIm, Canvas myCanvasIm,
			boolean isX, Paint mPaint) {
		for (int x = tempstartX; x <= tempendX; x++) {
			for (int y = tempstartY; y <= tempendY; y++) {
				if (x > myBitmapIm.getWidth() || x < 0 || y < 0
						|| y > myBitmapIm.getHeight()) {
					continue;
				}
				if (isX)
					interpolateColor(myBitmapIm, tempstartX, tempendX, y, y, y,
							x, myCanvasIm, mPaint);
				else
					interpolateColor(myBitmapIm, x, x, tempstartY, tempendY, y,
							x, myCanvasIm, mPaint);
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
	private static int interpolateColor(Bitmap myBitmapIm, int startX, int endX,
			int startY, int endY, int y, int x, Canvas myCanvasIm, Paint mPaint) {
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
