package com.uoc.loginusingapilib;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Profile;
import com.uoc.openapilibrary.model.ProfileList;
import com.uoc.openapilibrary.model.User;

public class ViewUserActivity extends Activity {
	TextView tvFullName;
	ProgressBar pbLoadingUser;
	ImageView ivPhoto;
	TextView tvCurrentProfile;
	Profile currentP;
	User u;
	ProfileList pfl;
	String token;

	/*
	 * En el siguiente ejemplo obtenemos nuestro usuario, para ello 1 -
	 * Recuperamos el Token 2 - En un AsynkTask cargamos el usuario 3 - Una vez
	 * tenemos los datos de usuario cargamos la imagen (descargar y mostrat)
	 * 
	 * Se usa AsyncTask para liberar el thread principal de la app, dado que si
	 * no Android 4 se queja, y hay el "peligro" de que se cuelgue la app si la
	 * conectividad es mala
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_login);
		tvFullName = (TextView) findViewById(R.id.tvFullname);
		pbLoadingUser = (ProgressBar) findViewById(R.id.pbLoadingUser);
		ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
		tvCurrentProfile = (TextView) findViewById(R.id.tvCurrentProfile);
		u = null;
		token = Utils.getToken();// Sabemos que si hemos entrado aqu� es porque
									// venimos de guardar el token
		if (!token.equals("")) {
			new LoadUserTask().execute(token);
		} else {
			Toast.makeText(this, "Error de login", Toast.LENGTH_LONG).show();
			// Nunca deberia entrar aqui
		}
		// Long aa = (long) 922222222;
		// Date a = new Date(aa);
		// Log.v("TAG",new Gson().toJson(a.getTime()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_post_login, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_profiles:
			startActivity(new Intent(this,ProfilesListActivity.class));
			return true;
		case R.id.menu_tutors:
			startActivity(new Intent(this,TutorsActivity.class));
			return true;
		case R.id.menu_settings:
			startActivity(new Intent(this, UserSettingsActivity.class));
			return true;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	/*
	 * Se hace aqu� con async task, para tener el control de cuando termina la
	 * tarea asyncrona
	 */
	private class LoadUserTask extends AsyncTask<String, Void, String> {
		protected void onPostExecute(String result) {
			if (result != null) {
				Log.v("USERUSER", result);
				u = User.JSONToUser(result);
				tvFullName.setText(u.getFullName());
				pbLoadingUser.setVisibility(View.GONE);
				new LoadPhotoTask().execute(u.getPhotoUrl());
				new LoadProfileTask().execute(token);
			} else {
				Toast.makeText(getApplicationContext(),
						"Hay problemas de conexi�n", Toast.LENGTH_LONG).show();
			}

		}

		@Override
		protected void onPreExecute() {
			// pbLoadingUser.seIn
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... token) {

			return User.getUserWS(token[0]);
		}
	}

	private class LoadProfileTask extends AsyncTask<String, Void, Profile> {

		protected void onPostExecute(Profile result) {
			if (result != null) {
				currentP = result;
				tvCurrentProfile.setText(result.getId());
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

	private class LoadPhotoTask extends AsyncTask<String, Void, Bitmap> {

		protected void onPostExecute(Bitmap result) {
			try {
				if (result != null) {
					ivPhoto.setImageBitmap(result);
				} else {
					// TODO Cargar imagen de no photo
					Log.e("USER", "Image load error");
				}
			} catch (Exception e) {
				// TODO: handle exception
				Log.e("ERROR", "Showing image Error", e);
			}

		}

		@Override
		protected Bitmap doInBackground(String... urls) {
			return Utils.loadImageFromNetwork(urls[0]);

		}

	}

}
