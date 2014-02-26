package com.uoc.clibimi_move_ip;

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
import com.uoc.openapilibrary.model.Folder;
import com.uoc.openapilibrary.model.FolderList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Manuel on 14/01/14.
 */
public class GetFoldersActivity extends Activity {
    Folder f;
    String Cid;
    String Bid;
    String Mid;
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
        Mid = myIntent.getStringExtra("MID");
        String[] params = new String[3];
        params[0] = Utils.getToken();
        params[1] = Cid;
        params[2] = Bid;
        new LoadClassroomTask().execute(params);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, final View view,
                                    int pos, long id) {
                f = listF.get(pos);
                new MoveToFolderTask().execute();
                Intent newintent = new Intent (getApplicationContext(), GetClassroomsActivity.class);
                GetFoldersActivity.this.startActivity(newintent);

            }
        });
    }

    private class LoadClassroomTask extends AsyncTask<String, Void, FolderList> {
        protected void onPostExecute(FolderList result) {
            if (result != null) {
                ArrayList<Folder> aux = result.getFolders();
                listF = aux;
                for (int i = 0; i < aux.size(); ++i) {
                    list.add(aux.get(i).getName());
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected FolderList doInBackground(String... param) {
            return FolderList.getFolderListfromClassRoomBoardWS(param[0], param[1], param[2]);
        }
    }

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

    /*STARTUOCAPIEXAMPLE*/
    private class MoveToFolderTask extends AsyncTask<String, Void, Folder> {
        protected void onPostExecute(Folder result) {
            Toast.makeText(getApplicationContext(),
                    "Moving message", Toast.LENGTH_LONG).show();
            if (result != null) {
                Toast.makeText(getApplicationContext(),
                        "Message moved successfully", Toast.LENGTH_LONG).show();
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
        protected Folder doInBackground(String... token) {
            /* UOCAPICALL /api/v1/classrooms/{domain_id}/boards/{board_id}/messages/{id}/move/{folder_id} POST*/
            String faux;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/classrooms/"+Cid+"/boards/"+Bid+"/messages/"+Mid+"/move/"+f.getId() +"?access_token="+ Utils.getToken());
            httpPost.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpPost);
                faux = EntityUtils.toString(resp.getEntity());

            } catch (Exception ex) {
                Log.e("REST", "POST Error!", ex);
                return null;
            }
            return new Gson().fromJson(faux, Folder.class);
            //OR return Folder.postMoveMessageinClassRoomBoartoFolderWS(token[0], Cid, Bid, Mid, f.getId()); if you use our library
        }
    }
    /*ENDUOCAPIEXAMPLE*/
}