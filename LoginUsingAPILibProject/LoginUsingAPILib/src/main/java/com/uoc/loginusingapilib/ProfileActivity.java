package com.uoc.loginusingapilib;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Profile;

public class ProfileActivity extends Activity implements OnClickListener {
	EditText etLangId;
	TextView tvLangId;
	Button btnApply;
	Profile p;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		etLangId = (EditText)findViewById(R.id.etLangID);
		tvLangId = (TextView)findViewById(R.id.tvLangId);
		btnApply = (Button)findViewById(R.id.btnApply);
		btnApply.setOnClickListener(this);
		new LoadProfileTask().execute(Utils.getToken());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_profile, menu);
		return true;
	}

	private class LoadProfileTask extends AsyncTask<String, Void, Profile> {

	
		protected void onPostExecute(Profile result) {
			if (result != null) {
				p = result;
				etLangId.setText(result.getLanguage());
			} else {
				Toast.makeText(getApplicationContext(),
						"Hay problemas de conexi�n", Toast.LENGTH_LONG).show();
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Profile doInBackground(String... token) {
			return Profile.getProfileWS(token[0]);
		}
	}


	private class ChangeProfileTask extends AsyncTask<String, Void, Profile> {

	
		protected void onPostExecute(Profile result) {
			Toast.makeText(getApplicationContext(),
					"PUT HECHO", Toast.LENGTH_LONG).show();
			if (result != null) {
				etLangId.setText(result.getLanguage());
			} else {
				Toast.makeText(getApplicationContext(),
						"Hay problemas de conexi�n", Toast.LENGTH_LONG).show();
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Profile doInBackground(String... token) {
			return Profile.putProfileWS(token[0], p);
		}
	}
	
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnApply:
			p.setLanguage("ES");
			tvLangId.setText(p.getLanguage());
			new ChangeProfileTask().execute(Utils.getToken());
			
			break;

		default:
			break;
		}
	}

}
