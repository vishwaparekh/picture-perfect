package com.pictureperfect.imagehandling;

import android.graphics.Bitmap;
import android.graphics.Color;

public class ImageBlender {
	public static Bitmap blend(Bitmap bmp1, Bitmap bmp2){
		int redVal = 0;
		int greenVal = 0;
		int blueVal =0;
		int pixel_bmp1;
		int pixel_bmp2;
		int width = bmp1.getWidth();
		int height = bmp1.getHeight();
		int constant1 = 10;
		int constant2  = 5;
		for (int y = 0;y < height ;y++){
			for (int x = 0;x < width ;x++){
				pixel_bmp1 = bmp1.getPixel(x, y);
				pixel_bmp2 = bmp2.getPixel(x, y);
				
				if(x < width/constant1 || x > width-width/constant1 || y<height/constant1 ||y > height-height/constant1)
				{
					pixel_bmp2 = pixel_bmp1;
					bmp2.setPixel(x, y, Color.rgb(Color.red(pixel_bmp1),Color.green(pixel_bmp1),Color.blue(pixel_bmp1)));
				}
				else if(x < width/constant2 || x > width-width/constant2 || y < height/constant2 || y > height-height/constant2 )
				{
					int multFactorLeft;
					int multFactorRight;
					if(x<width/constant2){
						multFactorLeft = (width/constant2 - x)/(width/constant2);
						multFactorRight = x/(width/constant2);
					}
					else if( x > width-width/constant2)
					{
						multFactorLeft = (width/constant2-(width - x))/(width/constant2);
						multFactorRight = (width - x)/(width/constant2);
					}
					else if(y < height/constant2)
					{
						multFactorLeft = (height/constant2 - y)/(height/constant2);
						multFactorRight = y/(height/constant2);	
					}
					else
					{
						multFactorLeft = (height/constant2-(height - x))/(height/constant2);
						multFactorRight = (height - x)/(height/constant2);
					}
						
						
				
					pixel_bmp2 = Math.round(multFactorLeft)*pixel_bmp1 + Math.round(multFactorRight)*pixel_bmp2;	
					/*redVal = (multFactorLeft*Color.red(pixel_bmp1))+(multFactorRight*Color.red(pixel_bmp2));
					greenVal = (multFactorLeft*Color.green(pixel_bmp1)+multFactorRight*Color.green(pixel_bmp2));
					blueVal = (multFactorLeft*Color.blue(pixel_bmp1)+multFactorRight*Color.blue(pixel_bmp2));
					pixel_bmp2 = Color.rgb(redVal, greenVal, blueVal);*/
					/*bmp2.setPixel(x, y, pixel_bmp2);*/
					bmp2.setPixel(x, y, Color.rgb(Color.red(pixel_bmp2),Color.green(pixel_bmp2),Color.blue(pixel_bmp2)));
				}
				
			}
		}
			
		
		return bmp2;
	}
}
