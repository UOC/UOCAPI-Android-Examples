package com.uoc.u_profilescurrentp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Profile;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ViewMessageActivity extends Activity{
    TextView tvAPPId;
    TextView tvAPP;
    TextView tvID;
    TextView tvUSID;
    TextView tvUT;
    TextView tvUTID;
    TextView tvUS;
    TextView tvLang;
    Profile p = new Profile();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        tvAPPId = (TextView)findViewById(R.id.tvAPPId);
        tvAPPId.setText("UOC");
        tvAPP = (TextView)findViewById(R.id.tvAPP);
        tvAPP.setText("UOC");
        tvID = (TextView)findViewById(R.id.tvID);
        tvID.setText("UOC-GESTIO-1-ca");
        tvUSID = (TextView)findViewById(R.id.tvUSID);
        tvUSID.setText("1");
        tvUT = (TextView)findViewById(R.id.tvUT);
        tvUT.setText("Gestió");
        tvUTID = (TextView)findViewById(R.id.tvUTID);
        tvUTID.setText("GESTIO");
        tvUS = (TextView)findViewById(R.id.tvUS);
        tvUS.setText("Gestió");
        tvLang = (TextView)findViewById(R.id.tvLang);
        tvLang.setText("ca");
    }
    /*STARTUOCAPIEXAMPLE*/
    private class NewProfileTask extends AsyncTask<String, Void, Profile> {
        protected void onPostExecute(Profile result) {
            Toast.makeText(getApplicationContext(),
                    "Profile sent", Toast.LENGTH_LONG).show();
            if (result != null) {
                Toast.makeText(getApplicationContext(),
                        "Profile updated succesfully", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Encountered connection problems", Toast.LENGTH_LONG).show();
            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Profile doInBackground(String... param) {
            /* UOCAPICALL /api/v1/user/profiles/current PUT*/
            String paux;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPut httpPut = new HttpPut("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/user/profiles/current" +"?access_token="+ Utils.getToken());
            httpPut.setHeader("content-type", "application/json");

            try {

                Log.v("PUT SEND",new Gson().toJson(p));
                StringEntity entity = new StringEntity(new Gson().toJson(p), "UTF-8");
                httpPut.setEntity(entity);
                HttpResponse resp = httpClient.execute(httpPut);

                Log.v("PUT Ent",resp.getStatusLine().toString());

                paux = EntityUtils.toString(resp.getEntity());
                Log.v("PUT RESPONSE", paux);

            } catch (Exception ex) {
                Log.e("REST", "PUT Error!", ex);
                return null;
            }
            return new Gson().fromJson(paux, Profile.class);
            //OR return Profile.getProfileWS(param[0]); if you use our library
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_send:
                p.setAppId("UOC");
                p.setApp("UOC");
                p.setId("UOC-GESTIO-1-ca");
                p.setUserSubtypeId("1");
                p.setUserType("Gestió");
                p.setUsertypeId("GESTIO");
                p.setUserSubtype("Gestió");
                p.setLanguage("ca");
                new NewProfileTask().execute();
                Intent newintent = new Intent (getApplicationContext(), ViewProfileActivity.class);
                ViewMessageActivity.this.startActivity(newintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
/*ENDUOCAPIEXAMPLE*/
}