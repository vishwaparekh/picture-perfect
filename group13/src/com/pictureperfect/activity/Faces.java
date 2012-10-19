package com.pictureperfect.activity;

import android.graphics.Bitmap;

/**
 * This class acts as a storage for all the attributes like Image, Face position, Eye Position and Mouth position in a detected face.
 * @author group13
 *
 */
public class Faces {

	private Integer facePos[];

	private Bitmap faceImg;

	private Integer eyePos[];

	private Integer mouthPos[];

	public ImgData contains;

	/**
	 * This method initializes the face object with the given parameter values. 
	 * Position is an array of four integers
	 * 1. x-coordinate of top left corner
	 * 2. y-coordinate of top left corner
	 * 3. Width 
	 * 4. Height
	 * @param facePos  Position of face in the Image
	 * @param face 	   Image matrix that represents the face
	 * @param eyePos	Position of the eye in the detected face
	 * @param mouthPos Position of the mouth in the detected face
	 */
	private void init(int[] facePos, Bitmap face, int[] eyePos, int[] mouthPos) {
	}

	/**
	 * @return faceImg
	 */
	public Bitmap getFaceImg() {
		return faceImg;
	}


}