package com.pictureperfect.activity;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.media.FaceDetector;
import android.util.Log;
/**
 * This is the central class of this project. It implements algorithms to detect faces, detect unwanted objects, and warp the changes to the background image.
 * It is used by all the views.
 * It acts as a storage for all the pictures taken in the burst. 
 * It maintains the latest version of processed image.
 * It maintains a collection of all the people detected in the burst and their corresponding faces.  
 * @author group13
 */
public class ImgData {

	private ArrayList<Bitmap> myPictures;

	private Bitmap myBackground;

	private ArrayList<ArrayList<Faces>> myFaces = null;

	private ArrayList<Person> myPeople;

	private ArrayList<UnwantedObjects> unwantedObjects;

	private Integer myBackgroundNum;
	
	private int numPictures = 0;
	private static final int MAX_FACES = 10;

	/**
	 * Given a person ID, it warps the current face of the person with the chosen best face.
	 * @param pId person ID
	 */
	private void warpFace(Integer pId) {
		Faces baseFace = myFaces.get(myBackgroundNum).get(pId);
		Faces bestFace = myPeople.get(pId).getBestFace();
		int dstWidth = baseFace.getFacePos().getWidth();
		int dstHeight = baseFace.getFacePos().getHeight();
		Bitmap faceTemp = Bitmap.createScaledBitmap(bestFace.getFaceImg(), dstWidth, dstHeight, false);
		int pixels [] =  null;
		faceTemp.getPixels(pixels, 0, 0, 0, 0, faceTemp.getWidth(), faceTemp.getHeight());
		myBackground.setPixels(pixels, 0, 0, baseFace.getFacePos().getX(),baseFace.getFacePos().getY() , baseFace.getFacePos().getWidth(), baseFace.getFacePos().getHeight());
	}

	/**
	 * Given a person ID, it returns all the faces that belong to this person.
	 * @param pId Person ID
	 * @return Faces of the person
	 */
	public ArrayList<Faces> getFaces(Integer pId) {
		return myPeople.get(pId).getFaces();
	}

	/**
	 * Given a person ID and best face, it updates the best face of that person.
	 * @param pId Person ID
	 * @param bestFace The best face chosen by user
	 */
	public void setBestFace(Integer pId, Faces bestFace) {
		myPeople.get(pId).setBestFace(bestFace);
		warpFace(pId);
	}

	/**
	 * Given an image, it finds all the faces and adds them to the myFaces array.
	 * @param myPicture Image
	 */
	private void findandAddFaces(int pictureIndex) {
	//calls a face detection code on this picture here and get back all
	//the required values.
		ArrayList<Faces> facesPic = null;
		FaceDetector fd;
		FaceDetector.Face [] faces = new FaceDetector.Face[MAX_FACES];
		PointF midpoint = new PointF();
		int [] fpx = null;
		int [] fpy = null;
		int count = 0;
		int mFaceWidth = myPictures.get(pictureIndex).getWidth();
		int mFaceHeight = myPictures.get(pictureIndex).getHeight();
		try {
			fd = new FaceDetector(mFaceWidth, mFaceHeight, MAX_FACES);        
			count = fd.findFaces(myPictures.get(pictureIndex), faces);
		} catch (Exception e) {
			/*Log.e(TAG, "setFace(): " + e.toString());*/
			return;
		}
		if (count > 0) {
			fpx = new int[count];
			fpy = new int[count];

			for (int i = 0; i < count; i++) { 
				try {                 
					faces[i].getMidPoint(midpoint);                  

					fpx[i] = (int)midpoint.x;
					fpy[i] = (int)midpoint.y;
					int mpx = fpx[i];
					int mpy = fpy[i];
					int eyedist = (int)faces[i].eyesDistance();
					int lcx = mpx - eyedist/2 - eyedist;
					int lcy = mpy - eyedist;
					int width = eyedist * 3;
					int height = eyedist * 3;
					int eplx = mpx - eyedist/2;
					int eply = mpy;
					int widthEye = eyedist;
					int heightEye = 1;
					RectRegion facePos = new RectRegion(lcx, lcy, width, height);
					RectRegion eyePos = new RectRegion(eplx,eply,widthEye,heightEye);
					Bitmap faceImg = Bitmap.createBitmap(myPictures.get(pictureIndex),lcx,lcy,width,height);
					Faces faceTemp = new Faces(facePos,faceImg,eyePos);
					facesPic.add(faceTemp);
				} catch (Exception e) { 
					/*Log.e(TAG, "setFace(): face " + i + ": " + e.toString());*/
				}            
			}      
		}
		myFaces.add(facesPic);

	}

	/**
	 * Adds a new picture to the myPicture array. Calls findandAddFaces() on the same.  
	 * @param data Byte stream of the captured image
	 */
	public void addPicture(byte[] data) {
		Bitmap myBitmap = BitmapFactory.decodeByteArray(data,0,data.length);
		myPictures.add(myBitmap.copy(Bitmap.Config.ARGB_8888, true));
		findandAddFaces(numPictures);
		numPictures ++;
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
	public ArrayList<UnwantedObjects> getUnwantedObjects() {
		return unwantedObjects;
	}

	/**
	 * Updates the myBackgroundNum with current selected Background image. Also calls findandAddUnwantedObjects on myBackground and populates myPeople.
	 * @param num The ID of the image
	 */
	public void setBackgroundNum(Integer num) {
		myBackgroundNum = num;
		myBackground = myPictures.get(myBackgroundNum);
		for (int i=0; i< myFaces.get(myBackgroundNum).size();i++)
		{
			Person personTemp = new Person(myFaces,myFaces.get(myBackgroundNum).get(i),i);
			myPeople.add(personTemp);
		}
	}

	/**
	 * Gets the replacement for the unwanted object from unwantedObjects and warps it to myBackground.
	 * @param pos Position of the unwanted object
	 */
	public void warpUnwanted(Integer []pos) {
	}
	
	/**
	 * Given face position, it returns the corresponding person ID.
	 * @param facePos Position of the face
	 * @return Person ID
	 */
	public Integer getPersonId(Integer facePos) {
		return 0; // return pid
	}

	/**
	 * @return the myPictures
	 */
	public ArrayList<Bitmap> getMyPictures() {
		return myPictures;
	}

	/**
	 * @return the myBackground
	 */
	public Bitmap getMyBackground() {
		return myBackground;
	}

	/**
	 * @return the myFaces
	 */
	public ArrayList<ArrayList<Faces>> getMyFaces() {
		return myFaces;
	}

	/**
	 * @return the myPeople
	 */
	public ArrayList<Person> getMyPeople() {
		return myPeople;
	}

	/**
	 * @return the myBackgroundNum
	 */
	public Integer getMyBackgroundNum() {
		return myBackgroundNum;
	}

	/**
	 * @return the numPictures
	 */
	public int getNumPictures() {
		return numPictures;
	}

	
}