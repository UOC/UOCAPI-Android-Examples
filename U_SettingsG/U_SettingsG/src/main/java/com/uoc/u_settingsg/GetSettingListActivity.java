package com.uoc.u_settingsg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.EventList;
import com.uoc.openapilibrary.model.Setting;
import com.uoc.openapilibrary.model.SettingList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Manuel on 18/12/13.
 */
public class GetSettingListActivity extends Activity{
    /*STARTUOCAPIEXAMPLE*/
    Setting s;
    List<Setting> listS;
    final ArrayList<String> list = new ArrayList<String>();
    StableArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classrooms);
        final ListView listview = (ListView) findViewById(R.id.listView1);

        adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);

        String[] params = new String[1];
        params[0] = Utils.getToken();
        new LoadClassroomTask().execute(params);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, final View view,
                                    int pos, long id) {
                s = listS.get(pos);
                Intent newintent = new Intent (getApplicationContext(), ViewSettingActivity.class);
                newintent.putExtra("ID", s.getId());
                newintent.putExtra("TITLE", s.getTitle());
                newintent.putExtra("DES", s.getDescription());
                newintent.putExtra("SECT", s.getSection());
                newintent.putExtra("URL", s.getUrl());
                newintent.putExtra("VAL", s.getValue());
                GetSettingListActivity.this.startActivity(newintent);

            }
        });
    }

    private class LoadClassroomTask extends AsyncTask<String, Void, SettingList> {
        protected void onPostExecute(SettingList result) {
            if (result != null) {
                ArrayList<Setting> aux = result.getSettings();
                listS = aux;
                for (int i = 0; i < aux.size(); ++i) {
                    list.add(aux.get(i).getDescription());
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected SettingList doInBackground(String... param) {
            /* UOCAPICALL /api/v1/user/settings GET*/
            String sl;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/user/settings"+"?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                sl = EntityUtils.toString(resp.getEntity());
                Log.i("SETTINGS", sl);
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(sl, SettingList.class);
            //OR return SettingList.getSettingListWS(param[0]); if you use our library
        }
    }
    /*ENDUOCAPIEXAMPLE*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.view_classrooms, menu);
        return true;
    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

    }
}