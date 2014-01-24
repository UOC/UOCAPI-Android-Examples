package com.uoc.cal_eventsg;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Event;

public class ViewEventActivity extends Activity{
    TextView tvId;
    TextView tvURL;
    TextView tvSummary;
    TextView tvStart;
    TextView tvEnd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event);
        Intent myIntent= getIntent();

        String[] params = new String[2];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("ID");
        new LoadEventTask().execute(params);
    }

    private class LoadEventTask extends AsyncTask<String, Void, Event> {
        protected void onPostExecute(Event result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
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
            return Event.getEventWS(param[0],param[1]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_event, menu);
        return true;
    }

}