package com.uoc.classroomsidboardsidfoldersidget;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Constants;
import com.uoc.openapilibrary.RESTMethod;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Folder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ViewFolderActivity extends Activity{
    TextView tvId;
    TextView tvName;
    TextView tvUnread;
    TextView tvTotal;
    @Override
/*STARTUOCAPIEXAMPLE*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_folder);
        Intent myIntent= getIntent();

        String[] params = new String[4];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("CID");
        params[2] = myIntent.getStringExtra("BID");
        params[3] = myIntent.getStringExtra("FID");
        new LoadEventTask().execute(params);
    }

    private class LoadEventTask extends AsyncTask<String, Void, Folder> {
        protected void onPostExecute(Folder result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                tvName = (TextView)findViewById(R.id.tvName);
                tvName.setText(result.getName());
                tvUnread = (TextView)findViewById(R.id.tvUnread);
                tvUnread.setText(Long.toString(result.getUnreadMessages()));
                tvTotal = (TextView)findViewById(R.id.tvTotal);
                tvTotal.setText(Long.toString(result.getTotalMessages()));
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Folder doInBackground(String... param) {
            /* UOCAPICALL /api/v1/classrooms/{domain_id}/boards/{board_id}/folders/{id} GET*/
            String f;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/classrooms/"+ param[1] + "/boards/" + param[2] + "/folders/" + param[3]+"?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                f = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(f, Folder.class);
            // OR return Folder.getFolderfromClassRoomBoardWS(param[0], param[1], param[2], param[3]); IF YOU USE OUR LIBRARY
        }
    }
/*ENDUOCAPIEXAMPLE*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_folder, menu);
        return true;
    }

}