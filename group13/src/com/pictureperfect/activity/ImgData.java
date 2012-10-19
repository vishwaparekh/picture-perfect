package com.pictureperfect.activity;

import android.graphics.Bitmap;

/**
 * This is the central class of this project. It implements algorithms to detect faces, detect unwanted objects, and warp the changes to the background image.
 * It is used by all the views.
 * It acts as a storage for all the pictures taken in the burst. 
 * It maintains the latest version of processed image.
 * It maintains a collection of all the people detected in the burst and their corresponding faces.  
 * @author group13
 */
public class ImgData {

	private Bitmap[] myPictures;

	private Bitmap myBackground;

	private Faces[] myFaces;

	private Person[] myPeople;

	private UnwantedObjects[] unwantedObjects;

	private Integer myBackgroundNum;

	/**
	 * Given a person ID, it warps the current face of the person with the chosen best face.
	 * @param pId person ID
	 */
	private void warpFace(Integer pId) {
		
	}

	/**
	 * Given a person ID, it returns all the faces that belong to this person.
	 * @param pId Person ID
	 * @return Faces of the person
	 */
	public Faces []getFaces(Integer pId) {
		return myPeople[pId].getFaces();
	}

	/**
	 * Given a person ID and best face, it updates the best face of that person.
	 * @param pId Person ID
	 * @param bestFace The best face chosen by user
	 */
	public void setBestFace(Integer pId, Faces bestFace) {
	}

	/**
	 * Given an image, it finds all the faces and adds them to the myFaces array.
	 * @param myPicture Image
	 */
	private void findandAddFaces(Bitmap myPicture) {
	}

	/**
	 * Adds a new picture to the myPicture array. Calls findandAddFaces() on the same.  
	 * @param data Byte stream of the captured image
	 */
	public void addPicture(Byte data) {
	}

	/**
	 * Given an image, it finds all the unwanted objects and adds them to the unwantedObjects array.
	 * @param myPicture Image
	 */
	private void findandAddUnwantedObjects(Bitmap myPicture) {
	}

	/**
	 * Returns the latest processed image.
	 * @return myBackground
	 */
	public Bitmap getBackground() {
		return myBackground;
	}

	/**
	 * Returns all the unwanted objects that belong to the background image
	 * @return
	 */
	public UnwantedObjects []getUnwantedObjects() {
		return unwantedObjects;
	}

	/**
	 * Updates the myBackgroundNum with current selected Background image. Also calls findandAddUnwantedObjects on myBackground and populates myPeople.
	 * @param num The ID of the image
	 */
	public void setBackgroundNum(Integer num) {
	}

	/**
	 * Gets the replacement for the unwanted object from unwantedObjects and warps it to myBackground.
	 * @param pos Position of the unwanted object
	 */
	public void warpUnwanted(Integer []pos) {
	}
	
	/**
	 * Given face position, it returns the corresponding person ID.
	 * @param facePos Positon of the face
	 * @return Person ID
	 */
	public Integer getPersonId(Integer []facePos) {
		return 0; // return pid
	}

	
}