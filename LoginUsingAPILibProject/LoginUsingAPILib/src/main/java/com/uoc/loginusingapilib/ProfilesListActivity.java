package com.uoc.loginusingapilib;

import java.util.ArrayList;

import android.app.Activity;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.widget.ListView;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Profile;
import com.uoc.openapilibrary.model.ProfileList;

import android.app.LoaderManager;
import android.database.Cursor;
import android.content.CursorLoader;
import android.content.Loader;


public class ProfilesListActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{
	ProfileList pfl;
	String token;
	ListView lv;
	ArrayList<Profile> prfList;
	MatrixCursor myCursor;
	String[] Cols;
	String[] Cols2;
	int[] to;
	private static final int LOADER_ID = 0x02;
    private SimpleCursorAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_list);
		getLoaderManager().initLoader(LOADER_ID, null, this);
		token = Utils.getToken();
		new LoadProfileListTask().execute(token);
		lv = (ListView) findViewById(R.id.listView1);
		Cols = new String[] { "_id", "app", "appid", "type", "typeid", "subtype",
		"subtypeid" };		
		Cols2 = new String[] {"app", "appid", "type", "typeid", "subtype",
		"subtypeid" };
		to = new int[] { R.id.tvApp, R.id.tvAppId, R.id.tvType, R.id.tvTypeId,
				R.id.tvSubtype, R.id.tvSubtypeId };

		//myCursor = new MatrixCursor(Cols);
		//startManagingCursor(myCursor);
	}

	private void initList() {

		SimpleCursorAdapter arrayAdapter = new SimpleCursorAdapter(this,
				R.layout.row_profile, myCursor, Cols2, to, 0);
		lv.setAdapter(arrayAdapter);
	}

	private class LoadProfileListTask extends
			AsyncTask<String, Void, ProfileList> {

		protected void onPostExecute(ProfileList result) {
			if (result != null) {
				pfl = result;
				Log.v("PFLUser", Utils.toJSON(pfl));
				prfList = pfl.getProfiles();
				for (int i = 0; i < prfList.size(); ++i) {
					Profile f = prfList.get(i);
					myCursor.addRow(new Object[] {i, f.getApp(), f.getAppId()+":",
							f.getUserType(), f.getUsertypeId()+":",
							f.getUserSubtype(), f.getUserSubtypeId()+":" });
				}
				initList();
			}

		}

		@Override
		protected ProfileList doInBackground(String... token) {
			return ProfileList.getOwnProfileListWS(token[0]);

		}

	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(this, null, new String[]{"col1"}, null, null, null);
	}

	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		adapter.swapCursor(arg1);
		
	}

	public void onLoaderReset(Loader<Cursor> arg0) {
		adapter.swapCursor(null);
		
	}
}
