package com.uoc.clibif_inboxg;

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
import com.uoc.openapilibrary.Constants;
import com.uoc.openapilibrary.RESTMethod;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Folder;
import com.uoc.openapilibrary.model.FolderList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Manuel on 3/12/13.
 */
/*STARTUOCAPIEXAMPLE*/
public class GetClassroomsBoardsFoldersActivity extends Activity{
    Folder f;
    String Cid;
    String Bid;
    List<Folder> listF;
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
        Intent myIntent= getIntent();
        Cid = myIntent.getStringExtra("CID");
        Bid = myIntent.getStringExtra("BID");
        String[] params = new String[3];
        params[0] = Utils.getToken();
        params[1] = Cid;
        params[2] = Bid;
        new LoadClassroomTask().execute(params);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, final View view,
                                    int pos, long id) {
                f = listF.get(pos);
                Intent newintent = new Intent (getApplicationContext(), GetClassroomsBoardsFoldersMessagesActivity.class);
                newintent.putExtra("FID", f.getId());
                newintent.putExtra("CID", Cid);
                newintent.putExtra("BID", Bid);
                GetClassroomsBoardsFoldersActivity.this.startActivity(newintent);

            }
        });
    }

    private class LoadClassroomTask extends AsyncTask<String, Void, Folder> {
        protected void onPostExecute(Folder result) {
            if (result != null) {
                list.add(result.getName());
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Folder doInBackground(String... param) {
            /* UOCAPICALL /api/v1/classrooms/{domain_id}/boards/{board_id}/folders/inbox GET*/
            String f;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/classrooms/"+ param[1] + "/boards/" + param[2] + "/folders/inbox"+"?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                f = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(f, Folder.class);
            //OR return Folder.getInboxFolderfromClassRoomBoardWS(param[0], param[1], param[2]); if you use our library
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
