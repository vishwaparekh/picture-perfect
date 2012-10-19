package com.pictureperfect.activity;

/**
 * This class acts as a storage for all the attributes like Person ID, different
 * faces of this person , best face.

 * @author group13
 */
public class Person {

	private Integer pId;

	private Faces[] myfaces;

	public Person(Faces[] faces, Faces baseFace) {
		super();
		this.bestFace = baseFace;
		init(faces, baseFace);
	}

	private Faces bestFace;

	/**
	 * Given all the faces of all the people in the burst and one face that belongs to this person ID, this method finds out all the faces that belong to this person ID.
	 * @param faces		All the faces in this burst(that belong to everyone)
	 * @param baseFace	The face that belongs to this person ID in the background image to which the corresponding faces in all the images need to be found
	 */
	private void init(Faces[] faces, Faces baseFace) {
	}

	
	/**
	 * Contains all the faces of this person
	 * @return An array of all the faces of this person in the burst
	 */
	public Faces []getFaces() {
		return myfaces;
	}

	/**
	 * Sets the best face of the person 
	 * @param face
	 */
	public void setBestFace(Faces face) {
	}
	
	/**
	 * Gets the best face of this person
	 * @return The best face of this person
	 */
	public Faces getBestFace() {
		return bestFace;
	}

}