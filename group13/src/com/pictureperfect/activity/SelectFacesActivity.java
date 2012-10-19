package com.pictureperfect.activity;	

import pp.iteration2.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SelectFacesActivity extends Activity {
	
	static boolean finished = false;
	
	/** Called when the activity is first created. */
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