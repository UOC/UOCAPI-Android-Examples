package com.uoc.pi_tutorsg;

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
import com.uoc.openapilibrary.model.User;
import com.uoc.openapilibrary.model.UserList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Manuel on 11/12/13.
 */
public class GetClassroomsPeopleActivity extends Activity {
    /*STARTUOCAPIEXAMPLE*/
    User u;
    String Cid;
    List<User> listU;
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

        String[] params = new String[2];
        params[0] = Utils.getToken();
        params[1] = "156161616161"; //TODO: Put real id
        new LoadClassroomTask().execute(params);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, final View view,
                                    int pos, long id) {
                u = listU.get(pos);
                Intent newintent = new Intent (getApplicationContext(), ViewUserActivity.class);
                newintent.putExtra("UID", u.getId());
                newintent.putExtra("UUName", u.getUsername());
                newintent.putExtra("UName", u.getName());
                newintent.putExtra("UNumber", u.getNumber());
                newintent.putExtra("UFull", u.getFullName());
                newintent.putExtra("UPhoto", u.getPhotoUrl());
                newintent.putExtra("ULanguage", u.getLanguage());
                newintent.putExtra("USession", u.getSessionId());
                newintent.putExtra("UEmail", u.getEmail());
                GetClassroomsPeopleActivity.this.startActivity(newintent);

            }
        });
    }

    private class LoadClassroomTask extends AsyncTask<String, Void, UserList> {
        protected void onPostExecute(UserList result) {
            if (result != null) {
                ArrayList<User> aux = result.getUsers();
                listU = aux;
                for (int i = 0; i < aux.size(); ++i) {
                    list.add(aux.get(i).getFullName());
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected UserList doInBackground(String... param) {
            /* UOCAPICALL /api/v1/people/{id}/tutors GET*/
            String ul;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/people/"+param[1]+"/tutors"+"?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                ul = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(ul, UserList.class);
            //OR return UserList.getTutorsWS(param[0], param[1]); if you use our library
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
