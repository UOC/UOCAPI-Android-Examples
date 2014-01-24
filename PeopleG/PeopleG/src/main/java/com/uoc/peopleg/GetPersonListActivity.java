package com.uoc.peopleg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.PeopleSearchOptions;
import com.uoc.openapilibrary.model.Person;
import com.uoc.openapilibrary.model.PersonList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Manuel on 11/12/13.
 */
public class GetPersonListActivity extends Activity {
    /*STARTUOCAPIEXAMPLE*/
    Person c;
    List<Person> listC;
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
        new LoadClassroomTask().execute(params);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, final View view,
                                    int pos, long id) {
                c = listC.get(pos);
                Intent newintent = new Intent (getApplicationContext(), ViewPersonActivity.class);
                newintent.putExtra("ID", c.getId());
                GetPersonListActivity.this.startActivity(newintent);

            }
        });
    }

    private class LoadClassroomTask extends AsyncTask<String, Void, PersonList> {
        protected void onPostExecute(PersonList result) {
            if (result != null) {
                ArrayList<Person> aux = result.getPeople();
                listC = aux;
                for (int i = 0; i < aux.size(); ++i) {
                    list.add(aux.get(i).getUsername());
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected PersonList doInBackground(String... param) {
            PeopleSearchOptions pso = new PeopleSearchOptions();
            /* UOCAPICALL /api/v1/people GET*/
            String pl;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/people"+"?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                pl = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(pl, PersonList.class);
            //OR return PersonList.getPersonListWS(param[0], pso); if you use our library
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
