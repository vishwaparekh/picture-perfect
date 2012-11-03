package com.pictureperfect.activity;

import java.util.ArrayList;

import android.graphics.Bitmap;

/**
 * This class acts as a storage for all the attributes like Person ID, different
 * faces of this person , best face.
 * 
 * @author group13
 */
public class Person {

	private Integer pId;

	private ArrayList<Faces> myfaces;

	private Faces bestFace;

	public Person(ArrayList<ArrayList<Faces>> faces, Faces baseFace, int pId) {
		super();
		this.pId = pId;
		this.bestFace = baseFace;
		init(faces, baseFace);
	}

	/**
	 * Given all the faces of all the people in the burst and one face that
	 * belongs to this person ID, this method finds out all the faces that
	 * belong to this person ID.
	 * 
	 * @param faces
	 *            All the faces in this burst(that belong to everyone)
	 * @param baseFace
	 *            The face that belongs to this person ID in the background
	 *            image to which the corresponding faces in all the images need
	 *            to be found
	 */
	private void init(ArrayList<ArrayList<Faces>> faces, Faces baseFace) {

		for (int i = 0; i < faces.size(); i++) {
			myfaces.add(this.getBestMatch(faces.get(i), baseFace));
		}
	}

	/**
	 * Contains all the faces of this person
	 * 
	 * @return An array of all the faces of this person in the burst
	 */
	public ArrayList<Faces> getFaces() {
		return myfaces;
	}

	/**
	 * Sets the best face of the person
	 * 
	 * @param face
	 */
	public void setBestFace(Faces face) {
		bestFace = face;
	}

	/**
	 * Gets the best face of this person
	 * 
	 * @return The best face of this person
	 */
	public Faces getBestFace() {
		return bestFace;
	}

	/**
	 * Finds the corresponding face of a given face in a picture.
	 * 
	 * @param faces Array of faces of a specific picture
	 * @param baseFace The face to be compared from the Background picture 
	 * @return the matched face of the person
	 */
	private Faces getBestMatch(ArrayList<Faces> faces, Faces baseFace) {
		// logic to add the best matching face from every picture
		Faces matchedFace = faces.get(0);
		int dstWidth = baseFace.getFacePos().getWidth();
		int dstHeight = baseFace.getFacePos().getHeight();
		int minIndex = -1;
		for (int i = 0; i < faces.size(); i++) {
			Bitmap faceTemp = Bitmap.createScaledBitmap(faces.get(i)
					.getFaceImg(), dstWidth, dstHeight, false);
			ImageMatcher imgMatcher = new TemplateMatcher();
			int matchIndex = imgMatcher.getMatchIndex(faceTemp,
					baseFace.getFaceImg());
			if (i == 0) {
				minIndex = matchIndex;
			} else {
				if (matchIndex < minIndex) {
					minIndex = matchIndex;
					matchedFace = faces.get(i);
				}
			}
		}
		return matchedFace;
	}

}