package com.uoc.cli_materials_ig;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Constants;
import com.uoc.openapilibrary.RESTMethod;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.TeachingMaterial;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Manuel on 11/12/13.
 */
public class ViewMaterialActivity extends Activity{
    TextView tvId;
    TextView tvTitle;
    TextView tvType;
    TextView tvURL;
    @Override
/*STARTUOCAPIEXAMPLE*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_material);
        Intent myIntent= getIntent();

        String[] params = new String[3];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("CID");
        params[2] = myIntent.getStringExtra("TMID");
        new LoadEventTask().execute(params);
    }

    private class LoadEventTask extends AsyncTask<String, Void, TeachingMaterial> {
        protected void onPostExecute(TeachingMaterial result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                tvTitle = (TextView)findViewById(R.id.tvTitle);
                tvTitle.setText(result.getTitle());
                tvType = (TextView)findViewById(R.id.tvType);
                tvType.setText(result.getType());
                tvURL = (TextView)findViewById(R.id.tvURL);
                tvURL.setText(result.getUrl());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected TeachingMaterial doInBackground(String... param) {
            /* UOCAPICALL /api/v1/classrooms/{domain_id}/materials/{id} GET*/
            String tm;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/classrooms/"+ param[1] + "/materials/" + param[2]+"?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                tm = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(tm, TeachingMaterial.class);
            // OR return TeachingMaterial.getTeachingMaterialfromClassRoomWS(param[0],param[1],param[2]); IF YOU ARE USING OUR LIBRARY
        }
    }
/*ENDUOCAPIEXAMPLE*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_board, menu);
        return true;
    }

}