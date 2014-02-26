package com.uoc.openapilibrary.model;

import android.os.AsyncTask;
import android.util.Log;
import com.uoc.openapilibrary.Constants;
import com.google.gson.Gson;
import com.uoc.openapilibrary.RESTMethod;

public class User {
	private String fullName;
	private String id;
	private String language;
	private String name;
	private String photoUrl;
	private String username;
    private String number;
    private String sessionId;
    private String email;
	private static String userJson;

	public User(){
		//Default constructor
	}
	public String newUser(String authToken) {
		new getUserJSONTask().execute(authToken);
		return userJson;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getId() {
		return id;
	}

	public String getLanguage() {
		return language;
	}

	public void setLenguage(String language) {
		this.language = language;
	}

	public String getName() {
		return name;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getUsername() {
		return username;
	}

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	public static User JSONToUser(String userJSON) {
		Gson gson = new Gson();
		User obj = gson.fromJson(userJSON, User.class);
		return obj;
	}

	public static String UserToJSON(User u) {
		return new Gson().toJson(u);
	}

	public static User getUser() {
		if (userJson == null) {
			Log.v("ERROR", "User aun no cargado");
		} else {
			return new Gson().fromJson(userJson, User.class);
		}
		return null;
	}

	public String getUserJson() {
		return userJson;
	}

	//
	public User getUser(String token) {
		return JSONToUser(userJson);
	}

	public static String getUserWS(String token) {
		return RESTMethod.Get(
				Constants.BASEURI +"user",
				token);
	}


    public class getUserJSONTask extends AsyncTask<String, Void, String> {
		protected void onPostExecute(String result) {
			userJson = result;
			if (userJson == null) {
				Log.v("USER", "User retrive Error");
			}
			Log.v("ASYNC_USER", "Token obtenido en asyncTask: " + result);
		}

		@Override
		protected String doInBackground(String... token) {
			Log.v("Token DIB", token[0]);
			return RESTMethod.Get(
					Constants.BASEURI +"user",
					token[0]);
		}
	}

}
