package com.mush.simplewebclient;

import android.app.ActionBar;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.MenuItem;

public class SettingsActivity  extends PreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.settings);
		ActionBar ab = getActionBar();
		ab.setDisplayHomeAsUpEnabled(true);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		boolean ret;

		switch (item.getItemId()) {
		case android.R.id.home:{
			this.finish();
			ret = true;
			break;
		}
		default: {
			ret = super.onOptionsItemSelected(item);
		}
		}

		return ret;
	}

}