package com.uoc.cli_materialsp;

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
import com.uoc.openapilibrary.model.TeachingMaterial;
import com.uoc.openapilibrary.model.TeachingMaterialList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

public class ViewTeachingMaterialActivity extends Activity{
    TextView tvId;
    TextView tvTitle;
    TextView tvType;
    TextView tvURL;
    TeachingMaterial tm = new TeachingMaterial();
    String Cid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_material);

        tvId = (TextView)findViewById(R.id.tvId);
        tvId.setText("123456789999");
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText("Random title");
        tvType = (TextView)findViewById(R.id.tvType);
        tvType.setText("Random type");
        tvURL = (TextView)findViewById(R.id.tvURL);
        tvURL.setText("Random url");
    }
    /*STARTUOCAPIEXAMPLE*/
    private class NewMaterialTask extends AsyncTask<String, Void, TeachingMaterial> {
        protected void onPostExecute(TeachingMaterial result) {
            Toast.makeText(getApplicationContext(),
                    "TeachingMaterial sent", Toast.LENGTH_LONG).show();
            if (result != null) {
                Toast.makeText(getApplicationContext(),
                        "TeachingMaterial created succesfully", Toast.LENGTH_LONG).show();
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
        protected TeachingMaterial doInBackground(String... token) {
            /* UOCAPICALL /api/v1/classrooms/{id}/materials POST*/
            String tmaux;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/classrooms/"+Cid+"/materials" +"?access_token="+ Utils.getToken());
            httpPost.setHeader("content-type", "application/json");
            try {
                StringEntity entity = new StringEntity(new Gson().toJson(tm));
                httpPost.setEntity(entity);
                HttpResponse resp = httpClient.execute(httpPost);
                tmaux = EntityUtils.toString(resp.getEntity());
                Log.i("SEND", tmaux);
            } catch (Exception ex) {
                Log.e("REST", "POST Error!", ex);
                return null;
            }
            return new Gson().fromJson(tmaux, TeachingMaterial.class);
            //OR return TeachingMaterial.postTeachingMaterialinClassRoomWS(token[0], Cid, tm); if you use our library
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
                tm.setId("545844");
                tm.setTitle("WKGRP_FO");
                tm.setType("Conversation");
                tm.setUrl("www.google.es");
                Intent myIntent= getIntent();
                Cid = myIntent.getStringExtra("CID");
                new NewMaterialTask().execute();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
/*ENDUOCAPIEXAMPLE*/
}