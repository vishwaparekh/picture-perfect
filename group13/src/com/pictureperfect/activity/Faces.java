package com.pictureperfect.activity;

import android.graphics.Bitmap;

/**
 * This class acts as a storage for all the attributes like Image, Face position, Eye Position and Mouth position in a detected face.
 * @author group13
 *
 */
public class Faces {

	private RectRegion facePos;
	private Bitmap faceImg;

	private RectRegion eyePos;

	private RectRegion mouthPos;

	public RectRegion getFacePos() {
		return facePos;
	}
	public void setFacePos(RectRegion facePos) {
		this.facePos = facePos;
	}
	public RectRegion getEyePos() {
		return eyePos;
	}
	public void setEyePos(RectRegion eyePos) {
		this.eyePos = eyePos;
	}
	public RectRegion getMouthPos() {
		return mouthPos;
	}
	public void setMouthPos(RectRegion mouthPos) {
		this.mouthPos = mouthPos;
	}
	public void setFaceImg(Bitmap faceImg) {
		this.faceImg = faceImg;
	}

	
	Faces(RectRegion facePos, Bitmap face, RectRegion eyePos) {
		init(facePos, face, eyePos);
	}
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
	private void init(RectRegion facePos, Bitmap face, RectRegion eyePos) {
		faceImg = face;
		this.facePos = facePos;
		this.eyePos = eyePos;
		
	}

	/**
	 * @return faceImg
	 */
	public Bitmap getFaceImg() {
		return faceImg;
	}


}