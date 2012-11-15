package com.pictureperfect.activity;

import android.graphics.Bitmap;

/**
 * @author group13
 * This method contains implementation of calculating difference between a foreground and a background image
 */
public class GetDifferencePicture {
	/**
	 * @param foreground
	 * @param background
	 * @return difference image
	 */
	static Bitmap generate(Bitmap foreground,Bitmap background){
		int[] pixelsForeground = null;
		int[] pixelsBackground = null;
		int[] subPixels = null;
		foreground.getPixels(pixelsForeground,0,0,0,0,foreground.getWidth(),foreground.getHeight());
		
		background.getPixels(pixelsBackground,0,0,0,0,background.getWidth(),background.getHeight());
		
		
		for (int i=0;i<pixelsForeground.length;i++)
		{
			subPixels[i]=Math.abs(pixelsForeground[i]-pixelsBackground[i]);
		}
		foreground.setPixels(subPixels, 0, 0, 0, 0, foreground.getWidth(), foreground.getHeight());
		return foreground;
	}
}
