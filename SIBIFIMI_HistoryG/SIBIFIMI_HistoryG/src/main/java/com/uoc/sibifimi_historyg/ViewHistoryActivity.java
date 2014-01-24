package com.uoc.sibifimi_historyg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.widget.ListView;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.EventList;
import com.uoc.openapilibrary.model.MessageHistory;
import com.uoc.openapilibrary.model.MessageHistoryDetail;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
/**
 * Created by Manuel on 17/12/13.
 */
public class ViewHistoryActivity extends Activity{
    /*STARTUOCAPIEXAMPLE*/
    TextView tvId;
    ArrayList<MessageHistoryDetail> amhd = new ArrayList<MessageHistoryDetail>();
    MyAdapter adapter;
    ListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_history);
        Intent myIntent= getIntent();

        adapter = new MyAdapter(this);
        listview = (ListView)findViewById(R.id.list);
        listview.setAdapter(adapter);

        String[] params = new String[5];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("CID");
        params[2] = myIntent.getStringExtra("BID");
        params[3] = myIntent.getStringExtra("FID");
        params[4] = myIntent.getStringExtra("MID");
        new LoadEventTask().execute(params);
    }

    private class LoadEventTask extends AsyncTask<String, Void, MessageHistory> {
        protected void onPostExecute(MessageHistory result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                amhd = result.getDetails();
                adapter = new MyAdapter(ViewHistoryActivity.this);
                listview.setAdapter(adapter);
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected MessageHistory doInBackground(String... param) {
            /* UOCAPICALL /api/v1/subjects/{domain_id}/boards/{board_id}/folders/{folder_id}/messages/{id}/history GET*/
            String mh;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/subjects/"+param[1]+"/boards/"+param[2]+"/folders/"+param[3]+"/messages/"+param[4]+"/history"+"?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                mh = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(mh, MessageHistory.class);
            //OR return MessageHistory.getHistoryfromSubjectBoardFolderMessageWS(param[0], param[1], param[2], param[3], param[4]); if you use our library
        }
    }
    /*ENDUOCAPIEXAMPLE*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_message, menu);
        return true;
    }

    public class MyAdapter extends ArrayAdapter {

        Activity context;

        MyAdapter(Activity context) {
            super(context, R.layout.list_row, amhd);
            this.context = context;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View item = inflater.inflate(R.layout.list_row, null);

            TextView tvAction = (TextView)item.findViewById(R.id.tvAction);
            tvAction.setText(amhd.get(position).getAction());

            TextView tvDate = (TextView)item.findViewById(R.id.tvDate);
            tvDate.setText(amhd.get(position).getDate());

            TextView tvName = (TextView)item.findViewById(R.id.tvName);
            tvName.setText(amhd.get(position).getName());

            TextView tvTime = (TextView)item.findViewById(R.id.tvTime);
            tvTime.setText(amhd.get(position).getTime());

            return(item);
        }
    }

}