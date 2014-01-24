package com.uoc.loginusingapilib;

import java.util.ArrayList;

import android.app.Activity;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.Menu;
import android.widget.ListView;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.User;
import com.uoc.openapilibrary.model.UserList;

public class TutorsActivity extends Activity {
	UserList usrlist;
	String token;
	ListView lv;
	ArrayList<User> usrArray;
	MatrixCursor myCursor;
	String[] Cols;
	String[] Cols2;
	int[] to;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutors);
		
		token = Utils.getToken(); 
		new LoadTutorsTask().execute(token);
		lv = (ListView) findViewById(R.id.lvTutors);
		Cols = new String[] { "_id", "fullname", "username"};		
		Cols2 = new String[] {"fullname", "username"};
		to = new int[] { R.id.tvRowFullName, R.id.tvRowUsername};
		myCursor = new MatrixCursor(Cols);
	}

	private void initList() {

		SimpleCursorAdapter arrayAdapter = new SimpleCursorAdapter(this,
				R.layout.row_tutor, myCursor, Cols2, to, 0);
		lv.setAdapter(arrayAdapter);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_tutors, menu);
		return true;
	}

	private class LoadTutorsTask extends AsyncTask<String, Void, UserList> {

		protected void onPostExecute(UserList result) {

			if (result != null) {
				usrlist = result;
				usrArray = usrlist.getUsers();
				for (int i = 0; i < usrArray.size(); ++i) {
					User u = usrArray.get(i);
					// TODO: MEjorable
					myCursor.addRow(new Object[] { i, u.getFullName(), u.getUsername()});
				}
				initList();
			}

		}

		@Override
		protected UserList doInBackground(String... token) {
			return UserList.getUserListWS(token[0]);

		}

	}
}
