package com.pictureperfect.activity;	

import pp.iteration2.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FinalResultActivity extends Activity {
	
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
     */
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finalresult);
        
        
        
    }


}