package com.pictureperfect.activity;	


import pp.iteration2.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ChooseOptionActivity extends Activity {
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chooseoption);
        
        
        
        Button SelectFaces = (Button) findViewById(R.id.choosebutton1); 
        Button RemoveObjects = (Button) findViewById(R.id.choosebutton2); 
        
        
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
        
        
        
    }


}