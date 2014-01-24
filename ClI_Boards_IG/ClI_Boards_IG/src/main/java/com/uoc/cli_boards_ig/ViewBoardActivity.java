package com.uoc.cli_boards_ig;

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
import com.uoc.openapilibrary.model.Board;
import com.uoc.openapilibrary.model.Classroom;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/*STARTUOCAPIEXAMPLE*/
public class ViewBoardActivity extends Activity{
    TextView tvId;
    TextView tvTitle;
    TextView tvSubtype;
    TextView tvDomainId;
    TextView tvUnread;
    TextView tvCode;
    TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_board);
        Intent myIntent= getIntent();

        String[] params = new String[3];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("CID");
        params[2] = myIntent.getStringExtra("BID");
        new LoadEventTask().execute(params);
    }

    private class LoadEventTask extends AsyncTask<String, Void, Board> {
        protected void onPostExecute(Board result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                tvSubtype = (TextView)findViewById(R.id.tvSubtype);
                tvSubtype.setText(result.getSubtype());
                tvTitle = (TextView)findViewById(R.id.tvTitle);
                tvTitle.setText(result.getTitle());
                tvCode = (TextView)findViewById(R.id.tvCode);
                tvCode.setText(result.getCode());
                tvDomainId = (TextView)findViewById(R.id.tvDomainId);
                tvDomainId.setText(result.getDomainId());
                tvUnread = (TextView)findViewById(R.id.tvUnread);
                tvUnread.setText(result.getUnreadMessages());
                tvTotal = (TextView)findViewById(R.id.tvTotal);
                tvTotal.setText(result.getTotalMessages());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Board doInBackground(String... param) {
            /* UOCAPICALL /api/v1/classrooms/{domain_id}/boards/{id} GET*/
            String b;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/classrooms/"+ param[1]+ "/boards/" + param[2]+"?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                b = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(b, Board.class);
            //OR return Board.getBoardfromClassRoomWS(param[0],param[1], param[2]); IF YOU USE OUR LIBRARY
        }
    }
/*ENDUOCAPIEXAMPLE*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_board, menu);
        return true;
    }

}