package com.pictureperfect.facerecognitiontools;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * This class implements a template matching algorithm. 
 * 
 * @author group13
 */
public class TemplateMatcher implements ImageMatcher {
	private double rConvert = 0.2989;
	private double gConvert = 0.5870;
	private double bConvert = 0.1140;
	public TemplateMatcher()
	{
		super();
	}

	/**
	 * It is used to match two images and returns a matching index.  
	 * Lower matching index implies higher match.
	 * Minimum value of matching index is 0
	 * @author group13
	 */
	public int getMatchIndex(Bitmap b1, Bitmap b2) {
		// TODO Auto-generated method stub
		int []pixelsB1 = null;
		int []pixelsB2 = null;
		b1.getPixels(pixelsB1,0,0,0,0,b1.getWidth(),b1.getHeight());
		b2.getPixels(pixelsB2,0,0,0,0,b2.getWidth(),b2.getHeight());
		double minValue = 0;
		for (int i=0;i<pixelsB1.length;i++)
		{
			double grayScaleB1 = getGrayScale(Color.red(pixelsB1[i]),Color.green(pixelsB1[i]),Color.blue(pixelsB1[i]));
			double grayScaleB2 = getGrayScale(Color.red(pixelsB2[i]),Color.green(pixelsB2[i]),Color.blue(pixelsB2[i]));
			minValue = minValue + Math.abs(grayScaleB1 - grayScaleB2);
		}
		
		return (int)minValue;
	}
	
	/**
	 * This method converts the a pixel from RGB to Grayscale.
	 * @param redValue Red component
	 * @param greenValue Green  component
	 * @param blueValue Blue  component
	 * @return GrayScale value
	 */
	private double getGrayScale(int redValue,int greenValue, int blueValue)
	{
		return redValue * rConvert + greenValue * gConvert + blueValue * bConvert; 
	}

}
