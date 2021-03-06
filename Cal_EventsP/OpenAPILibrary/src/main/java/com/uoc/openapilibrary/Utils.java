package com.uoc.openapilibrary;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;

public class Utils {
	public static Bitmap loadImageFromNetwork(String photoUrl) {
		Bitmap bm = null;
		try {
			InputStream in = new java.net.URL(photoUrl).openStream();
			bm = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return bm;
	}
	
	public static String getToken() {
		//SharedPreferences sh = LoginActivity.
		return LoginActivity.getToken();
	}

	public static String toJSON(Object obj) {
		return new Gson().toJson(obj);
	}


}
