package com.pictureperfect.activity;	

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.pictureperfect.imagehandling.ImgData;

/**
 * It is the main screen of the application It consists of two buttons. 
 * @author group13 
 *
 */
public class WelcomeActivity extends Activity {
	
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
        setContentView(R.layout.main);
        ((ImgData) getApplication()).reset();
        Button TakePictureButton = (Button) findViewById(R.id.button1);
        Button editExistingButton = (Button) findViewById(R.id.button2);
        TakePictureButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(WelcomeActivity.this,
						CameraActivity.class);
				startActivity(intent);
			}
		});
        
        editExistingButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
		/*		Intent intent = new Intent(WelcomeActivity.this,
						CustomGalleryActivity.class);
				startActivity(intent);*/
			}
		});
    }
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 1, R.string.About);
		menu.add(0, 1, 1, R.string.Exit);
		/*menu.add(0, 2, 1, R.string.Refresh);
		menu.add(0, 3, 1, R.string.Delete);*/
	/*	menu.add(0, 4, 1, R.string.app_about);
		menu.add(0, 5, 1, R.string.logout);*/
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);

		switch (item.getItemId()) {
		case 0:
			openOptionsDialog();
			break;
		case 1:
			exitOptionsDialog();
			break;
		}
		return true;
	}

	private void openOptionsDialog() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.app_about)
				.setMessage(R.string.app_about_messageW)
				.setPositiveButton(R.string.str_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
							}
						}).show();
	}

	private void exitOptionsDialog() {
		new AlertDialog.Builder(this)
				.setTitle(R.string.Exit)
				.setMessage(R.string.ays)
				.setNegativeButton(R.string.str_no,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
							}
						})
				.setPositiveButton(R.string.str_ok,
						new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface, int i) {
								finish();
							}
						}).show();
	}
}

