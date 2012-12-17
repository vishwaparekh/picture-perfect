package com.pictureperfect.common;

public class ImageToGray {
	private static double rConvert = 0.2989;
	private static double gConvert = 0.5870;
	private static double bConvert = 0.1140;

	/**
	 * Converts the image into grayscale for comparison
	 * @param redValue Red component
	 * @param greenValue Green component
	 * @param blueValue Blue component
	 * @return Grayscale 
	 */
	public static double getGrayScale(int redValue,int greenValue, int blueValue)
	{
		return redValue * rConvert + greenValue * gConvert + blueValue * bConvert; 
	}

}