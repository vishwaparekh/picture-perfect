package com.pictureperfect.removeunwantedtools;

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
	public static Bitmap generate(Bitmap foreground,Bitmap background){
		int[] pixelsForeground = new int[foreground.getWidth()*foreground.getHeight()];
		int[] pixelsBackground = new int[background.getWidth()*background.getHeight()];
		int[] subPixels = new int[foreground.getWidth()*foreground.getHeight()];
		foreground.getPixels(pixelsForeground,0,foreground.getWidth(),0,0,foreground.getWidth(),foreground.getHeight());
		
		background.getPixels(pixelsBackground,0,background.getWidth(),0,0,background.getWidth(),background.getHeight());
		
		
		for (int i=0;i<pixelsForeground.length;i++)
		{
			subPixels[i]=Math.abs(pixelsForeground[i]-pixelsBackground[i]);
		}
		foreground.setPixels(subPixels, 0, foreground.getWidth(), 0, 0, foreground.getWidth(), foreground.getHeight());
		return foreground;
	}
}
