package com.pictureperfect.tests;

import java.util.ArrayList;

import com.pictureperfect.activity.Faces;
import com.pictureperfect.activity.ImgData;
import com.pictureperfect.activity.Person;

import junit.framework.TestCase;

public class ImgDataTest extends TestCase {

	public void testGetFaces() {
		ImgData I = new ImgData();
		
		ArrayList<ArrayList<Faces>> myfaces = new ArrayList();
		ArrayList<Faces> testFace1 = new ArrayList();
		ArrayList<Faces> testFace2 = new ArrayList();
		
		Person P = new Person(myfaces, null, 1);
		
		assertTrue(I.getMyPeople().get(1).getFaces().isEmpty());
		myfaces.add(testFace1);
		
		assertEquals(2, I.getFaces(1).size());
	}

	public void testSetBestFace() {
		ArrayList<ArrayList<Faces>> myfaces = new ArrayList();
		Faces bestFace = new Faces(null, null, null);
		Person P = new Person(myfaces, null, 1);
		P.setBestFace(bestFace);

		assertEquals(P.bestFace, bestFace);
	}

	public void testAddPicture() {
		
	}

	public void testGetBackground() {
		fail("Not yet implemented");
	}

	public void testGetUnwantedObjects() {
		fail("Not yet implemented");
	}

	public void testSetBackgroundNum() {
		fail("Not yet implemented");
	}

	public void testWarpUnwanted() {
		fail("Not yet implemented");
	}

	public void testGetPersonId() {
		fail("Not yet implemented");
	}

}
