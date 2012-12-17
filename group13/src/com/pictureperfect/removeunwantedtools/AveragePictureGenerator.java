package com.pictureperfect.removeunwantedtools;

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
	public static Bitmap generate(ArrayList<Bitmap> myPictures) {
		Bitmap b = myPictures.get(0);
		int []pixelsAverage= new int[b.getWidth()*b.getHeight()];
		for (int i = 0; i<myPictures.size();i++)
		{
			Bitmap b1 = myPictures.get(i);
			int []pixelsB1 = new int[b1.getWidth()*b1.getHeight()];
			b1.getPixels(pixelsB1,0,b1.getWidth(),0,0,b1.getWidth(),b1.getHeight());
			if(i==0)
				pixelsAverage = pixelsB1;
			else
			{
				for (int j =0; j<pixelsB1.length;j++)
					pixelsAverage[j] = pixelsB1[j] + pixelsAverage[j];
			}
		}
		b.setPixels(pixelsAverage, 0, b.getWidth(), 0, 0, b.getWidth(), b.getHeight());
		return b;
	}

}
