package com.pictureperfect.imagehandling;

import java.util.ArrayList;
import java.util.List;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.media.FaceDetector;

import com.pictureperfect.common.Helper;
import com.pictureperfect.common.RectRegion;
import com.pictureperfect.removeunwantedtools.AveragePictureGenerator;
import com.pictureperfect.removeunwantedtools.GetConnectedComponents;
import com.pictureperfect.removeunwantedtools.GetDifferencePicture;

/**
 * This is the central class of this project. It implements algorithms to detect
 * faces, detect unwanted objects, and warp the changes to the background image.
 * It is used by all the views. It acts as a storage for all the pictures taken
 * in the burst. It maintains the latest version of processed image. It
 * maintains a collection of all the people detected in the burst and their
 * corresponding faces.
 * 
 * @author group13
 */
public class ImgData extends Application {

	private ArrayList<Bitmap> myPictures = new ArrayList<Bitmap>();

	private Bitmap myBackground;

	private ArrayList<ArrayList<Faces>> myFaces = new ArrayList<ArrayList<Faces>>();

	private ArrayList<Person> myPeople = new ArrayList<Person>();;

	private ArrayList<UnwantedObjects> unwantedObjects = new ArrayList<UnwantedObjects>();

	private Integer myBackgroundNum;

	private int numPictures = 0;

	private static final int MAX_FACES = 10;
	
	private int multFactor = 3;

	/**
	 * Given a person ID, it warps the current face of the person with the
	 * chosen best face.
	 * 
	 * @param pId
	 *            person ID
	 */
	private void warpFace(Integer pId) {
		Faces baseFace = myPeople.get(pId).getBaseFace();
		Faces bestFace = myPeople.get(pId).getBestFace();
		int dstWidth = baseFace.getFaceImg().getWidth();
		int dstHeight = baseFace.getFaceImg().getHeight();
		int currWidth = bestFace.getFaceImg().getWidth();
		int currHeight = bestFace.getFaceImg().getHeight();
		Bitmap faceTemp = Bitmap.createScaledBitmap(bestFace.getFaceImg(),
				dstWidth, dstHeight, false);
		int tempWidth = faceTemp.getWidth();
		int tempHeight = faceTemp.getHeight();
		//faceTemp = ImageBlender.blend( baseFace.getFaceImg(),faceTemp);
		int pixels[] = new int[faceTemp.getWidth()*faceTemp.getHeight()];
		faceTemp.getPixels(pixels, 0, faceTemp.getWidth(), 0, 0, faceTemp.getWidth(),
				faceTemp.getHeight());
		myBackground.setPixels(pixels, 0, baseFace.getFaceImg().getWidth(), baseFace.getFacePos().getX(),
				baseFace.getFacePos().getY(), baseFace.getFaceImg().getWidth(),
				baseFace.getFaceImg().getHeight());
		
	}

	/**
	 * Given a person ID, it returns all the faces that belong to this person.
	 * 
	 * @param pId
	 *            Person ID
	 * @return Faces of the person
	 */
	public ArrayList<Faces> getFaces(Integer pId) {
		return myPeople.get(pId).getFaces();
	}

	/**
	 * Given a person ID and best face, it updates the best face of that person.
	 * 
	 * @param pId
	 *            Person ID
	 * @param bestFace
	 *            The best face chosen by user
	 */
	public void setBestFace(Integer pId, Faces bestFace) {
		myPeople.get(pId).setBestFace(bestFace);
		warpFace(pId);
	}

	/**
	 * Given an image, it finds all the faces and adds them to the myFaces
	 * array.
	 * 
	 * @param myPicture
	 *            Image
	 */
	private void findandAddFaces(Bitmap myPicture) {
		// calls a face detection code on this picture here and get back all
		// the required values.
		ArrayList<Faces> facesPic = new ArrayList<Faces>();
		FaceDetector faceDetector;
		FaceDetector.Face[] faces = new FaceDetector.Face[MAX_FACES];
		PointF midpoint = new PointF();
		int[] facePosX = null;
		int[] facePosY = null;
		int count = 0;
		int myFaceWidth = myPicture.getWidth();
		int myFaceHeight = myPicture.getHeight();
		try {
			faceDetector = new FaceDetector(myFaceWidth, myFaceHeight, MAX_FACES);
			count = faceDetector.findFaces(myPicture, faces);
		} catch (Exception e) {
			/* Log.e(TAG, "setFace(): " + e.toString()); */
			return;
		}
		if (count > 0) {
			facePosX = new int[count];
			facePosY = new int[count];

			for (int i = 0; i < count; i++) {
				try {
					faces[i].getMidPoint(midpoint);

					facePosX[i] = (int) midpoint.x;
					facePosY[i] = (int) midpoint.y;
					int midpointx = facePosX[i];
					int midppinty = facePosY[i];
					int eyedist = (int) faces[i].eyesDistance();
					int width = eyedist * multFactor;
					int height = eyedist * (int)(multFactor*1.5);
					int leftCornerX = Math.max(midpointx - Math.round(width/2),0);
					int leftCornerY = Math.max(midppinty - Math.round(height/2),0);
					
					int eyePosLeftX = midpointx - eyedist / 2;
					int eyePosLeftY = midppinty;
					int widthEye = eyedist;
					int heightEye = 1;
					RectRegion facePos = new RectRegion(leftCornerX, leftCornerY, width, height);
					RectRegion eyePos = new RectRegion(eyePosLeftX, eyePosLeftY, widthEye,
							heightEye);
					Bitmap faceImg = Bitmap.createBitmap(myPicture, leftCornerX, leftCornerY,
							width, height);
					Faces faceTemp = new Faces(facePos, faceImg, eyePos);
					facesPic.add(faceTemp);
					byte[] abc = Helper.bitmapToByteArray(faceImg);
					Helper.savePhoto(abc, i);
				} catch (Exception e) {
					/* Log.e(TAG, "setFace(): face " + i + ": " + e.toString()); */
				}
			}
		}
		//if(!facesPic.isEmpty())
		myFaces.add(facesPic);
	}

	/**
	 * Adds a new picture to the myPicture array. Calls findandAddFaces() on the
	 * same.
	 * 
	 * @param data
	 *            Byte stream of the captured image
	 */
	public void addPicture(byte[] data) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		/* options.inJustDecodeBounds= true; */
		options.inPreferredConfig = Config.RGB_565;
		options.inSampleSize = 10;
		Bitmap myBitmap = BitmapFactory.decodeByteArray(data, 0, data.length,
				options);
		myBitmap.getHeight();
		myBitmap.getWidth();
		myPictures.add(myBitmap.copy(Bitmap.Config.RGB_565, true));
		findandAddFaces(myPictures.get(numPictures));
		numPictures++;
	}

	public void addPicture(Bitmap bitmap) {
		bitmap.getHeight();
		bitmap.getWidth();
		myPictures.add(bitmap.copy(Bitmap.Config.RGB_565, true));
		findandAddFaces(myPictures.get(numPictures));
		numPictures++;
	}
	
	/**
	 * Given an image, it finds all the unwanted objects and adds them to the
	 * unwantedObjects array.
	 * 
	 * @param myPicture
	 *            Image
	 */
	private void findandAddUnwantedObjects(Bitmap myPicture) {
		Bitmap avgPicture = AveragePictureGenerator.generate(myPictures);
		Bitmap differencePicture = GetDifferencePicture.generate(myPicture,
				avgPicture);
		List<RectRegion> regions = GetConnectedComponents
				.generate(differencePicture);
		for (int i = 0; i < regions.size(); i++) {
			unwantedObjects.add(new UnwantedObjects(regions.get(i), Bitmap
					.createBitmap(avgPicture, regions.get(i).getX(), regions
							.get(i).getY(), regions.get(i).getWidth(), regions
							.get(i).getHeight())));
		}
	}

	/**
	 * Returns the latest processed image.
	 * 
	 * @return myBackground
	 */
	public Bitmap getBackground() {
		return myBackground;
	}

	/**
	 * Returns all the unwanted objects that belong to the background image
	 * 
	 * @return
	 */
	public ArrayList<UnwantedObjects> getUnwantedObjects() {
		return unwantedObjects;
	}

	/**
	 * Updates the myBackgroundNum with current selected Background image. Also
	 * calls findandAddUnwantedObjects on myBackground and populates myPeople.
	 * 
	 * @param num
	 *            The ID of the image
	 */
	public void setBackgroundNum(Integer num) {
		myBackgroundNum = num;
		this.myPeople = new ArrayList<Person>();
		myBackground = myPictures.get(myBackgroundNum);
		if (myFaces.get(myBackgroundNum).size()==0){
			System.out.println("hello");
			return;
		}
		for (int i = 0; i < myFaces.get(myBackgroundNum).size(); i++) {
			Person personTemp = new Person(myFaces, myFaces
					.get(myBackgroundNum).get(i));
			myPeople.add(personTemp);
		}
		//findandAddUnwantedObjects(myBackground);
	}

	/**
	 * Gets the replacement for the unwanted object from unwantedObjects and
	 * warps it to myBackground.
	 * 
	 * @param pos
	 *            Position of the unwanted object
	 */
	public void warpUnwanted(UnwantedObjects unwantedObj) {
		Bitmap replaceImg = unwantedObj.getReplaceImg();
		int[] pixels = null;
		replaceImg.getPixels(pixels, 0, 0, 0, 0, replaceImg.getWidth(),
				replaceImg.getHeight());
		RectRegion regionToreplace = unwantedObj.getPosition();
		this.getBackground().setPixels(pixels, 0, 0, regionToreplace.getX(),
				regionToreplace.getY(), regionToreplace.getWidth(),
				regionToreplace.getHeight());
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

	public void reset(){
		this.myPictures = new ArrayList<Bitmap>();
		this.myFaces = new ArrayList<ArrayList<Faces>>();
		this.myPeople = new ArrayList<Person>();;
		this.unwantedObjects = new ArrayList<UnwantedObjects>();
		this.numPictures = 0;
	}
}