package com.uoc.clibifimi_body;

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
import com.uoc.openapilibrary.model.MessageBody;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ViewBodyActivity extends Activity{
    /*STARTUOCAPIEXAMPLE*/
    TextView tvId;
    TextView tvBody;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_body);
        Intent myIntent= getIntent();

        String[] params = new String[5];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("CID");
        params[2] = myIntent.getStringExtra("BID");
        params[3] = myIntent.getStringExtra("FID");
        params[4] = myIntent.getStringExtra("MID");
        new LoadEventTask().execute(params);
    }

    private class LoadEventTask extends AsyncTask<String, Void, MessageBody> {
        protected void onPostExecute(MessageBody result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                tvBody = (TextView)findViewById(R.id.tvBody);
                tvBody.setText(result.getBody());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected MessageBody doInBackground(String... param) {
            String mb;
            HttpClient httpClient = new DefaultHttpClient();
            /* UOCAPICALL /api/v1/classrooms/{domain_id}/boards/{board_id}/folders/{folder_id}/messages/{id}/body GET*/
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/classrooms/"+param[1]+"/boards/"+param[2]+"/folders/"+param[3]+"/messages/"+param[4]+"/body"+"?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                mb = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(mb, MessageBody.class);
            //OR return MessageBody.getMessageBodyfromClassRoomBoardFolderMessageWS(param[0], param[1], param[2], param[3], param[4]); if you use our library
        }
    }
    /*ENDUOCAPIEXAMPLE*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_body, menu);
        return true;
    }

}