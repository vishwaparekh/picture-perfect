package com.pictureperfect.tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.pictureperfect.common.RectRegion;

import com.xtremelabs.robolectric.RobolectricTestRunner;

@RunWith(RobolectricTestRunner.class)
public class RectRegionTest {

	
	RectRegion r1 = new RectRegion(1, 2, 10, 20);


	@Test
	public final void testGetX() {				
		assertEquals(r1.getX(), 1);
	
	}

	@Test
	public final void testGetY() {
		assertEquals(r1.getY(), 2);

	}

	@Test
	public final void testGetWidth() {
		assertEquals(r1.getWidth(), 10);
		
	}

	@Test
	public final void testGetHeight() {
		assertEquals(r1.getHeight(), 20);
	}

}
