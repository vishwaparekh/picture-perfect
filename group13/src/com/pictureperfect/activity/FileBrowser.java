package com.pictureperfect.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

/**
 * THis class implements the file browsing functionality for choosing the existing images for "Edit Existing Burst"
 * @author Group13
 *
 */
public class FileBrowser extends ListActivity {
	
    private File currentDir;
    private FileArrayAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        currentDir = new File("/sdcard/");
        fill(currentDir);
    }
    
    /**
     * Takes path as an argument and lists all the files. 
     * @param f Path
     */
    private void fill(File f)
    {
    	File[]dirs = f.listFiles();
		 this.setTitle("Current Dir: "+f.getName());
		 List<FileOption>dir = new ArrayList<FileOption>();
		 List<FileOption>fls = new ArrayList<FileOption>();
		 try{
			 for(File ff: dirs)
			 {
				if(ff.isDirectory())
					dir.add(new FileOption(ff.getName(),"Folder",ff.getAbsolutePath()));
				else
				{
					fls.add(new FileOption(ff.getName(),"File Size: "+ff.length(),ff.getAbsolutePath()));
				}
			 }
		 }catch(Exception e)
		 {
			 
		 }
		 Collections.sort(dir);
		 Collections.sort(fls);
		 dir.addAll(fls);
		 if(!f.getName().equalsIgnoreCase("sdcard"))
			 dir.add(0,new FileOption("..","Parent Directory",f.getParent()));
		 adapter = new FileArrayAdapter(FileBrowser.this,R.layout.file_view,dir);
		 this.setListAdapter(adapter);
    }
    
    /* (non-Javadoc)
     * @see android.app.ListActivity#onListItemClick(android.widget.ListView, android.view.View, int, long)
     */
    @Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		FileOption o = adapter.getItem(position);
		if(o.getData().equalsIgnoreCase("folder")||o.getData().equalsIgnoreCase("parent directory")){
				currentDir = new File(o.getPath());
				fill(currentDir);
		}
		else
		{
			onFileClick(o);
		}
	}
    
    private void onFileClick(FileOption o)
    {
    	Toast.makeText(this, "File Clicked: "+o.getName(), Toast.LENGTH_SHORT).show();
    }
}