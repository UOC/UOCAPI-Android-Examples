package com.uoc.loginusingapilib;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.ListView;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Setting;
import com.uoc.openapilibrary.model.SettingList;

public class UserSettingsActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>{
	String token;
	String[] ColsSetVisibles;
	String[] ColsSetting;
	int[] to;
	MatrixCursor myCursor;
	ListView lv;
	SettingList settings;
    private SimpleCursorAdapter arrayAdapter;
    private static final int LOADER_ID = 0x03;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_settings);
		getLoaderManager().initLoader(LOADER_ID, null, this);
		token = Utils.getToken();
		new LoadSettingListTask().execute(token);
		ColsSetting = new String[] { "_id", "id", "title", "description" };
		ColsSetVisibles = new String[] { "id", "title", "description" };

		// Para values se podria repetir el proceso
		// Y tratarlo con dos adapters distintos, por ejemplo poner
		// el adapter de values en un spinner
		// , o crear un SettingAdapter propio para todo,
		to = new int[] { R.id.tvSettingId, R.id.tvSettingTitle,
				R.id.tvSettingDescription };
		//myCursor = new MatrixCursor(ColsSetting);
		//startManagingCursor(myCursor);

	}

	private void initList() {
		SimpleCursorAdapter arrayAdapter = new SimpleCursorAdapter(this,
				R.layout.row_user_setting, myCursor, ColsSetVisibles, to, 0);
		lv.setAdapter(arrayAdapter);
	}

	private class LoadSettingListTask extends
			AsyncTask<String, Void, SettingList> {

		protected void onPostExecute(SettingList result) {

			if (result != null) {
				settings = result;
				for (int i = 0; i < settings.getSettings().size(); ++i) {
					Setting f = settings.getSettings().get(i);
					// TODO: Mejorar
					myCursor.addRow(new Object[] { i, f.getId(),
							f.getTitle(), f.getDescription()});
				}
				initList();
			}

		}

		@Override
		protected SettingList doInBackground(String... token) {
			return SettingList.getSettingListWS(token[0]);

		}

	}

	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		return new CursorLoader(this,null
                , new String[]{"col2"}, null, null, null);
	}

	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		arrayAdapter.swapCursor(arg1);
		
	}

	public void onLoaderReset(Loader<Cursor> arg0) {
		arrayAdapter.swapCursor(null);
		
	}
}
