package com.pictureperfect.tests;

import java.util.ArrayList;

import junit.framework.TestCase;
import android.graphics.Bitmap;

import com.pictureperfect.imagehandling.Faces;
import com.pictureperfect.imagehandling.ImgData;
import com.pictureperfect.imagehandling.Person;

public class ImgDataTest extends TestCase {
	
	/**
	 * Tests initial emptiness of the current set of faces. Then
	 *  ensures that faces are added successfully to the current
	 *  set of faces.
	 */  
	public void testGetFaces() {
		ImgData I = new ImgData();
		
		ArrayList<ArrayList<Faces>> myfaces = new ArrayList();
		ArrayList<Faces> testFace1 = new ArrayList();
		ArrayList<Faces> testFace2 = new ArrayList();
		
		Person P = new Person(myfaces, null);
		
		assertTrue(I.getMyPeople().get(1).getFaces().isEmpty());
		myfaces.add(testFace1);
		myfaces.add(testFace2);
		assertEquals(2, I.getFaces(1).size());
	}
	
	/**
	 * Tests for initial emptiness of current set of images. Then
	 * checks that an added picture was successfully added to current
	 * picture set.
	 */ 
	public void testAddPicture() {
		byte[] data = null;
		ImgData I = new ImgData();
		ArrayList<Bitmap> myPicturesTest = I.getMyPictures();
		int sizeBefore = myPicturesTest.size();
		assertEquals(sizeBefore, 0);
		I.addPicture(data);
		myPicturesTest = I.getMyPictures();
		int sizeAfter = myPicturesTest.size();
		assertEquals(sizeAfter, 1);
	}

	/**
	 * Verifies functionality of setMyBackgroundNum() by calling
	 * getMyBackgroundNum() to check current background index.	 
	 */
	public void testSetBackgroundNum() {
		ImgData I = new ImgData();		
		I.setBackgroundNum(1);
		int bgNum = I.getMyBackgroundNum();
		assertEquals(1, bgNum);
		I.setBackgroundNum(2);
		int bgNum2 = I.getMyBackgroundNum();
		assertEquals(2, bgNum2);
	}
}
