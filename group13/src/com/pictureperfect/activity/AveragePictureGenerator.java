package com.pictureperfect.activity;

import java.util.ArrayList;

import android.graphics.Bitmap;

/**
 * @author group13
 *	This class contains implementation of the code to calculate an average background picture given N pictures. 
 */
public class AveragePictureGenerator {
	
	/**
	 * @param myPictures
	 * @return average picture calculated
	 */
	@SuppressWarnings("null")
	public static Bitmap generate(ArrayList<Bitmap> myPictures) {
		// TODO Auto-generated method stub
		Bitmap b = myPictures.get(0);
		int []pixelsAverage=null;
		for (int i = 0; i<myPictures.size();i++)
		{
			Bitmap b1 = myPictures.get(i);
			int []pixelsB1 = null;
			b1.getPixels(pixelsB1,0,0,0,0,b1.getWidth(),b1.getHeight());
			if(i==0)
				pixelsAverage = pixelsB1;
			else
			{
				for (int j =0; j<pixelsB1.length;j++)
					pixelsAverage[j] = pixelsB1[j] + pixelsAverage[j];
			}
		}
		b.setPixels(pixelsAverage, 0, 0, 0, 0, b.getWidth(), b.getHeight());
		return null;
	}

}
