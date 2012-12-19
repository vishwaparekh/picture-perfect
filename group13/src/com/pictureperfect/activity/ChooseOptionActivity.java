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

import com.pictureperfect.common.Helper;
import com.pictureperfect.imagehandling.ImgData;

/**
 * This screen helps the user to chose between selecting faces and removing
 * unwanted objects.
 * 
 * @author group13
 * 
 */
public class ChooseOptionActivity extends Activity {

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
	@Override
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
						CreateAnimationActivity.class);
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
				byte[] myBitmapFinal = Helper
						.bitmapToByteArray(((ImgData) getApplication())
								.getBackground());
			//	new SavePhoto().execute(myBitmapFinal);
				Intent intent = new Intent(ChooseOptionActivity.this,
						FinalResultActivity.class);
				startActivity(intent);
				finish();

			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 0, 1, R.string.app_help);
		menu.add(0, 1, 1, R.string.Exit);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
				.setTitle(R.string.app_help)
				.setMessage(R.string.app_about_messageH)
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