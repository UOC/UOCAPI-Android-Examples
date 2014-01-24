package com.uoc.userg;


import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Person;
import com.uoc.openapilibrary.model.User;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Manuel on 11/12/13.
 */
public class ViewUserActivity extends Activity{
    /*STARTUOCAPIEXAMPLE*/
    TextView tvId;
    TextView tvUsername;
    TextView tvName;
    TextView tvNumber;
    TextView tvFull;
    TextView tvPhoto;
    TextView tvLang;
    TextView tvSession;
    TextView tvEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_people);
        String[] params = new String[1];
        params[0] = Utils.getToken();
        new LoadEventTask().execute(params);
    }
    private class LoadEventTask extends AsyncTask<String, Void, User> {
        protected void onPostExecute(User result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                tvUsername = (TextView)findViewById(R.id.tvUsername);
                tvUsername.setText(result.getUsername());
                tvName = (TextView)findViewById(R.id.tvName);
                tvName.setText(result.getName());
                tvNumber = (TextView)findViewById(R.id.tvNumber);
                tvNumber.setText(result.getNumber());
                tvFull = (TextView)findViewById(R.id.tvFull);
                tvFull.setText(result.getFullName());
                tvPhoto = (TextView)findViewById(R.id.tvPhoto);
                tvPhoto.setText(result.getPhotoUrl());
                tvLang = (TextView)findViewById(R.id.tvLang);
                tvLang.setText(result.getLanguage());
                tvSession = (TextView)findViewById(R.id.tvSession);
                tvSession.setText(result.getSessionId());
                tvEmail = (TextView)findViewById(R.id.tvEmail);
                tvEmail.setText(result.getEmail());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected User doInBackground(String... param) {
            /* UOCAPICALL /api/v1/user GET*/
            String u;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/user?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                u = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(u, User.class);
            //OR return User.getUserWS(param[0]); if you use our library
        }
    }
    /*ENDUOCAPIEXAMPLE*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_classrooms, menu);
        return true;
    }

}