package com.pictureperfect.activity;	

import pp.iteration2.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class RemoveUnwantedObjectsActivity extends Activity {
	
	static boolean finished = false;
	
	/** Called when the activity is first created. */
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


}