package com.pictureperfect.imagehandling;

import java.io.File;
import java.io.FileOutputStream;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

public class SavePhoto extends AsyncTask<byte[], String, String> {
	@Override
	protected String doInBackground(byte[]... jpeg) {
		File photo = new File(Environment.getExternalStorageDirectory(),
				"photo.jpg");

		if (photo.exists()) {
			photo.delete();
		}

		try {
			FileOutputStream fos = new FileOutputStream(photo.getPath());
			fos.write(jpeg[0]);
			fos.close();
		} catch (java.io.IOException e) {
			Log.e("PictureDemo", "Exception in photoCallback", e);
		}
		return (null);
	}
}