package com.uoc.cal_events_ig;

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
import com.uoc.openapilibrary.model.Event;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ViewEventActivity extends Activity{
    TextView tvId;
    TextView tvURL;
    TextView tvSummary;
    TextView tvStart;
    TextView tvEnd;
    String id;
    @Override
    /*STARTUOCAPIEXAMPLE*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        Intent myIntent= getIntent();

        id = myIntent.getStringExtra("ID");
        tvId = (TextView)findViewById(R.id.tvId);
        tvId.setText(id);
        String[] params = new String[2];
        params[0] = Utils.getToken();
        params[1] =id;
        new GetEventTask().execute(params);
    }

    private class GetEventTask extends AsyncTask<String, Void, Event> {
        protected void onPostExecute(Event result) {
            if (result != null) {
                tvURL = (TextView)findViewById(R.id.tvURL);
                tvURL.setText(result.getUrl());
                tvSummary = (TextView)findViewById(R.id.tvSummary);
                tvSummary.setText(result.getSummary());
                tvStart = (TextView)findViewById(R.id.tvStart);
                tvStart.setText(result.getStart());
                tvEnd = (TextView)findViewById(R.id.tvEnd);
                tvEnd.setText(result.getEnd());

            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Event doInBackground(String... param) {
            /* UOCAPICALL /api/v1/calendar/events/{id} GET*/
            String event;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/calendar/events/"+ param[1]+"?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                event = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(event, Event.class);
            //OR return Event.getEventWS(param[0], param[1]); IF YOU USE OUR LIBRARY
        }
    }
    /*ENDUOCAPIEXAMPLE*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_event, menu);
        return true;
    }

}