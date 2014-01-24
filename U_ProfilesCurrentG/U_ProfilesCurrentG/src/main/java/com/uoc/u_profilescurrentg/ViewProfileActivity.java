package com.uoc.u_profilescurrentg;

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
import com.uoc.openapilibrary.model.EventList;
import com.uoc.openapilibrary.model.Profile;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Manuel on 13/12/13.
 */
public class ViewProfileActivity extends Activity {
    /*STARTUOCAPIEXAMPLE*/
    TextView tvAPPId;
    TextView tvAPP;
    TextView tvID;
    TextView tvUSID;
    TextView tvUT;
    TextView tvUTID;
    TextView tvUS;
    TextView tvLang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        String[] params = new String[1];
        params[0] = Utils.getToken();
        new LoadEventTask().execute(params);

    }

    private class LoadEventTask extends AsyncTask<String, Void, Profile> {
        protected void onPostExecute(Profile result) {
            if (result != null) {
                tvAPPId = (TextView)findViewById(R.id.tvAPPId);
                tvAPPId.setText(result.getAppId());
                tvAPP = (TextView)findViewById(R.id.tvAPP);
                tvAPP.setText(result.getApp());
                tvID = (TextView)findViewById(R.id.tvID);
                tvID.setText(result.getId());
                tvUSID = (TextView)findViewById(R.id.tvUSID);
                tvUSID.setText(result.getUserSubtypeId());
                tvUT = (TextView)findViewById(R.id.tvUT);
                tvUT.setText(result.getUserType());
                tvUTID = (TextView)findViewById(R.id.tvUTID);
                tvUTID.setText(result.getUsertypeId());
                tvUS = (TextView)findViewById(R.id.tvUS);
                tvUS.setText(result.getUserSubtype());
                tvLang = (TextView)findViewById(R.id.tvLang);
                tvLang.setText(result.getLanguage());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Profile doInBackground(String... param) {
            /* UOCAPICALL /api/v1/user/profiles/current GET*/
            String p;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/user/profiles/current"+"?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                p = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(p, Profile.class);
            //OR return Profile.getProfileWS(param[0]); if you use our library
        }
    }
/*ENDUOCAPIEXAMPLE*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_classrooms, menu);
        return true;
    }

}