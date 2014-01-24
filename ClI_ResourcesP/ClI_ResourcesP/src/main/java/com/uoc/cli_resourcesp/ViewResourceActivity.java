package com.uoc.cli_resourcesp;

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
import com.uoc.openapilibrary.Constants;
import com.uoc.openapilibrary.RESTMethod;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Message;
import com.uoc.openapilibrary.model.Resource;
import com.uoc.openapilibrary.model.TeachingMaterial;
import com.uoc.openapilibrary.model.TeachingMaterialList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ViewResourceActivity extends Activity{
    TextView tvId;
    TextView tvType;
    TextView tvSubtype;
    TextView tvTitle;
    TextView tvCode;
    TextView tvDomain;
    Resource r = new Resource();
    String Cid;
    String[] params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_resource);

        params = new String[1];
        params[0] = Utils.getToken();
        Log.e("RESOURCE", params[0]);

        tvId = (TextView)findViewById(R.id.tvId);
        tvId.setText("545844");
        tvType = (TextView)findViewById(R.id.tvType);
        tvType.setText("Conversation");
        tvSubtype = (TextView)findViewById(R.id.tvSubtype);
        tvSubtype.setText("WKGRP_FO");
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText("F&amp;#242;rum");
        tvCode = (TextView)findViewById(R.id.tvCode);
        tvCode.setText("uoc_demo_011_01_f01");
        tvDomain = (TextView)findViewById(R.id.tvDomain);
        tvDomain.setText("308959");
    }
    /*STARTUOCAPIEXAMPLE*/
    private class NewResourceTask extends AsyncTask<String, Void, Resource> {
        protected void onPostExecute(Resource result) {
            Toast.makeText(getApplicationContext(),
                    "Resource sent", Toast.LENGTH_LONG).show();
            if (result != null) {
                Toast.makeText(getApplicationContext(),
                        "Resource created succesfully", Toast.LENGTH_LONG).show();
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
        protected Resource doInBackground(String... token) {
            /* UOCAPICALL /api/v1/classrooms/{id}/resources POST*/
            String raux;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/classrooms/"+Cid+"/resources" +"?access_token="+ params[0]);
            httpPost.setHeader("content-type", "application/json");
            try {
                StringEntity entity = new StringEntity(new Gson().toJson(r));
                httpPost.setEntity(entity);
                HttpResponse resp = httpClient.execute(httpPost);
                raux = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "POST Error!", ex);
                return null;
            }
            return new Gson().fromJson(raux, Resource.class);
            //OR return Resource.postResourceinClassRoomWS(params[0], Cid, r); if you use our library
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
                r.setId("545844");
                r.setType("Conversation");
                r.setSubtype("WKGRP_FO");
                r.setTitle("Forum");
                r.setCode("uoc_demo_011_01_f01");
                r.setDomainId("308959");
                Intent myIntent= getIntent();
                Cid = myIntent.getStringExtra("CID");
                Log.e("RESOURCE", Cid);
                new NewResourceTask().execute();
                //finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
/*ENDUOCAPIEXAMPLE*/
}