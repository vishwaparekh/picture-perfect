package com.pictureperfect.imagehandling;

import com.pictureperfect.common.RectRegion;

import android.graphics.Bitmap;

/**
 * This class acts as a storage for position of each of the unwanted objects and
 * the replacement for the same.
 * 
 * @author group13
 */
public class UnwantedObjects {

	private RectRegion position;
	private Bitmap replaceImg;
	UnwantedObjects(RectRegion position,Bitmap replaceImg)
	{
		init(position,replaceImg);
	}
	/**
	 * Populates the object corresponding to an unwanted item with the position and its replacement. 
	 * @param replaceImg
	 */
	private void init(RectRegion position,Bitmap replaceImg) {
		this.position = position;
		this.replaceImg = replaceImg;
	}

}