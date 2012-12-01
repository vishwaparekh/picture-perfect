package com.pictureperfect.activity;	



import java.io.ByteArrayOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.pictureperfect.imagehandling.ImgData;
import com.pictureperfect.imagehandling.SavePhoto;

/**
 * This screen helps the user to chose between selecting faces and removing unwanted objects.
 * @author group13
 *
 */
public class ChooseOptionActivity extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    /**Called when the activity is first created. This is where you should do all 
     * of your normal static set up: create views, bind data to lists, etc. This method
     *  also provides you with a Bundle containing the activity's previously frozen 
     *  state, if there was one.
     * 
     * @param savedInstancesState f the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle) 
     *
     */
     
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseoption);
        
        
        
        Button SelectFaces = (Button) findViewById(R.id.choosebutton1); 
        Button RemoveObjects = (Button) findViewById(R.id.choosebutton2); 
        Button Cancel = (Button) findViewById(R.id.button3);
        Button Done = (Button) findViewById(R.id.button4);
       
        SelectFaces.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ChooseOptionActivity.this,
						SelectFacesActivity.class);
				startActivity(intent);
			}
		});
        
        RemoveObjects.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ChooseOptionActivity.this,
						RemoveUnwantedObjectsActivity.class);
				startActivity(intent);
				
			}
		});
        
        Cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(ChooseOptionActivity.this,
						SelectFacesActivity.class);
				startActivity(intent);
			}
		});
        
        Done.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
			/*	Intent intent = new Intent(ChooseOptionActivity.this,
						FinalResultActivity.class);
				startActivity(intent);*/
				byte[] myBitmapFinal= bitmapToByteArray(((ImgData) getApplication()).getMyBackground());
				new SavePhoto().execute(myBitmapFinal);
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
			}
		});
        
    }
    /**
	 * Convert the Bitmap of an image into Byte array
	 * @param bitmap
	 * @return Byte array
	 */
	public static byte[] bitmapToByteArray(Bitmap bitmap) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0 /* ignored for PNG */, bos);
		byte[] bitmapdata = bos.toByteArray();
		return bitmapdata;
	}

}