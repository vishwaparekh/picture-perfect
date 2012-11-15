package com.pictureperfect.activity;	

import com.pictureperfect.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * This screen helps the user to remove unwanted objects from the image. 
 * @author group13 
 *
 */
public class RemoveUnwantedObjectsActivity extends Activity {
	
	static boolean finished = false;
	
	/**Called when the activity is first created. This is where you should do all 
     * of your normal static set up: create views, bind data to lists, etc. This method
     *  also provides you with a Bundle containing the activity's previously frozen 
     *  state, if there was one.
     * 
     * @param savedInstancesState f the activity is being re-initialized after 
     * previously being shut down then this Bundle contains the data it most 
     * recently supplied in onSaveInstanceState(Bundle) 
     */
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.removeunwantedobjects);
        
        Button Done = (Button) findViewById(R.id.removeDone); //Insert Button
        
        Done.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finished = true;
				
				if(finished && SelectFacesActivity.finished){
					Intent intent = new Intent(RemoveUnwantedObjectsActivity.this,
						FinalResultActivity.class);
					startActivity(intent);
				}
				else{
					Intent intent = new Intent(RemoveUnwantedObjectsActivity.this,
						ChooseOptionActivity.class);
					startActivity(intent);
				}
			}
		});
        
    }

    /**
     * Given the position on the image where the user clicked, calls the ImgData object to replace that portion of the image with the replacement.
     */
    private void replaceSelection() {
    }

    /**
     * It is called by replaceSelection to update the modified image on the screen
     */
    private void refreshImage() {
    }



}
