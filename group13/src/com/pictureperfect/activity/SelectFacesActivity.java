package com.pictureperfect.activity;	

import android.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectFacesActivity extends Activity {
	
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
        setContentView(R.layout.selectfaces);
        
        Button Done = (Button) findViewById(R.id.facesDone); //Insert Button
        
        Done.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				finished = true;
				if(finished && RemoveUnwantedObjectsActivity.finished){
					Intent intent = new Intent(SelectFacesActivity.this,
						FinalResultActivity.class);
					startActivity(intent);
				}
				else{
					Intent intent = new Intent(SelectFacesActivity.this,
						ChooseOptionActivity.class);
					startActivity(intent);
				}
			}
		});
    }


}