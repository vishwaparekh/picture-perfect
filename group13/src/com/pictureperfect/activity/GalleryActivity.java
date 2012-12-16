package com.pictureperfect.activity;


import java.io.File;
import java.io.FileOutputStream;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.*;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;


public class GalleryActivity extends Activity {

	private int count;
	private Bitmap[] thumbs;
	private boolean[] chosenThumbs;
	private String[] paths;
	private ImageAdapter imageAdapter;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery);

		/*  Here we establish values for querying image metadata*/
		
		final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID };
		final String orderBy = MediaStore.Images.Media.DATE_ADDED;
		Cursor imageCursor = managedQuery(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
				null, orderBy);
		int image_column_index = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
		
		count = imageCursor.getCount();
		thumbs = new Bitmap[count];
		chosenThumbs = new boolean[count];
		paths = new String[count];
		
		for (int i = 0; i < count; i++) {
			imageCursor.moveToPosition(i);
			int id = imageCursor.getInt(image_column_index);
			int dataColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
			thumbs[i] = MediaStore.Images.Thumbnails.getThumbnail(
					getApplicationContext().getContentResolver(), id,
					MediaStore.Images.Thumbnails.MICRO_KIND, null);
			paths[i]= imageCursor.getString(dataColumnIndex);
		}
		
		GridView imagegrid = (GridView) findViewById(R.id.imageLayout);
		imageAdapter = new ImageAdapter();
		imagegrid.setAdapter(imageAdapter);
		imageCursor.close();

		
		/*When done selecting...*/
		final Button backBtn = (Button) findViewById(R.id.backBtn);
		final Button doneBtn = (Button) findViewById(R.id.doneBtn);
		
		doneBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
				final int chosenLength = chosenThumbs.length;
				int count = 0;
				String selectImages = "";
				for (int i = 0; i < chosenLength; i++)
				{
					if (chosenThumbs[i]){
						count++;
						selectImages = selectImages + paths[i] + "|";
					}
				}
				if (count == 0){
					Toast.makeText(getApplicationContext(),
							"At least one image must be selected.",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(getApplicationContext(),
							count + " image(s) are selected.",
							Toast.LENGTH_LONG).show();
					Log.d("SelectedImages", selectImages);
				}
			}
			
			
			public void savePhoto(byte[] data) {
				File photo = new File(Environment.getExternalStorageDirectory(),
						"/Picture Perfect/" + "photo" + count + ".jpg");

				if (photo.exists()) {
					photo.delete();
				}

				try {
					FileOutputStream fos = new FileOutputStream(photo.getPath());
					fos.write(data);
					fos.close();
				} catch (java.io.IOException e) {
					Log.e("PictureDemo", "Exception in photoCallback", e);
				}
			}
		});
		
		backBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(GalleryActivity.this, WelcomeActivity.class);
				startActivity(intent);
				
			}
			
		});
			
		
	}

	class ViewHolder {
		ImageView imView;
		CheckBox checkbox;
		int id;
	}
	
	class ImageAdapter extends BaseAdapter {
		private LayoutInflater inflater;

		public ImageAdapter() {
			inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public int getCount() {
			return count;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder Viewholder;
			if (convertView == null) {
				Viewholder = new ViewHolder();
				convertView = inflater.inflate(R.layout.galleryitem, null);
				Viewholder.imView = (ImageView) convertView.findViewById(R.id.thumbImage);
				Viewholder.checkbox = (CheckBox) convertView.findViewById(R.id.itemCheckBox);

				convertView.setTag(Viewholder);
			}
			else {
				Viewholder = (ViewHolder) convertView.getTag();
			}
			Viewholder.checkbox.setId(position);
			Viewholder.imView.setId(position);
			
			/*Check the chosen images*/
			
			Viewholder.checkbox.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					CheckBox cb = (CheckBox) v;
					int id = cb.getId();
					if (chosenThumbs[id]){
						cb.setChecked(false);
						chosenThumbs[id] = false;
					} 
					else {
						cb.setChecked(true);
						chosenThumbs[id] = true;
					}
				}
			});
			
			Viewholder.imView.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					int id = v.getId();
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_VIEW);
					intent.setDataAndType(Uri.parse("file://" + paths[id]), "image/*");
					startActivity(intent);
				}
			});
			Viewholder.imView.setImageBitmap(thumbs[position]);
			Viewholder.checkbox.setChecked(chosenThumbs[position]);
			Viewholder.id = position;
			return convertView;
		}
	}
}