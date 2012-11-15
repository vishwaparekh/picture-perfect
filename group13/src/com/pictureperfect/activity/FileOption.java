package com.pictureperfect.activity;

/**
 * This is a helper class for listing the directory items.
 * @author Group 13
 *
 */
public class FileOption implements Comparable<FileOption>{
	private String name;
	private String data;
	private String path;
	
	public FileOption(String n,String d,String p)
	{
		name = n;
		data = d;
		path = p;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getData()
	{
		return data;
	}
	
	public String getPath()
	{
		return path;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(T)
	 */
	public int compareTo(FileOption o) {
		if(this.name != null)
			return this.name.toLowerCase().compareTo(o.getName().toLowerCase()); 
		else 
			throw new IllegalArgumentException();
	}
}
