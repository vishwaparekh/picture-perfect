package com.pictureperfect.removeunwantedtools;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.pictureperfect.common.ImageToGray;
import com.pictureperfect.common.RectRegion;

/**
 * @author group13
 * This class has an implementation of finding connected components in an image
 */
public class GetConnectedComponents {
	private static double thresholdValue = 0.75;
	private static double thresholdCount = 0.75;
	/**
	 * @param picture
	 * @return list of connected components
	 * This method generated connected components in an image i.e. generates components that are connected and have an intensity value greater than a certain threshold
	 */
	public static List<RectRegion> generate(Bitmap picture){
		
		ArrayList<RectRegion> listOfComponents = new ArrayList<RectRegion>();
		int width  = 5;
		int height = 5;
		
		for (int x = 0;x<picture.getWidth()-width;x++)
		{
			for (int y = 0;x<picture.getHeight()-height;y++){
				int[] pixels_extracted = new int[width*height];
				
				picture.getPixels(pixels_extracted, 0, width, x, y, width, height);
				int countMax=0;
				double maxVal = 0;
				double grayScaleValues[] = new double[width*height];
				for(int i =0;i<pixels_extracted.length;i++){
					grayScaleValues[i] = ImageToGray.getGrayScale(Color.red(pixels_extracted[i]), Color.green(pixels_extracted[i]), Color.blue(pixels_extracted[i]));
					if (grayScaleValues[i]>maxVal){
						maxVal = grayScaleValues[i];
					}
				}
				for(int i =0;i<pixels_extracted.length;i++){
					if(grayScaleValues[i] > thresholdValue * maxVal)
					{
						countMax ++;
					}
				}
				if(countMax>pixels_extracted.length*thresholdCount )
				{
					listOfComponents.add(new RectRegion(x, y, width, height));
				}
				 
			}
				
		}
		return listOfComponents;
		
	}
}
