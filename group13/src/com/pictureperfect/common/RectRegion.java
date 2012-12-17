package com.pictureperfect.common;


/**
 * This class acts as a storage for a region that would indicate presence of a
 * image feature like a moving object, face, eyes and mouth.
 * 
 * @author group13
 * 
 */
public class RectRegion {
	private int x;

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private int y;
	private int width;
	private int height;

	public RectRegion(int x, int y, int width, int height) {
		// initialize the RectRegion
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}