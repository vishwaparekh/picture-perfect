package com.pictureperfect.activity;

import android.graphics.Bitmap;

/**
 * Its an interface that different image matching algorithms will implement.
 * 
 * @author group13
 *
 */
public interface ImageMatcher {
	public int getMatchIndex(Bitmap b1,Bitmap b2);
}
