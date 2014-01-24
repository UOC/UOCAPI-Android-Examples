package com.uoc.peopleg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Profile;
import com.uoc.openapilibrary.model.ProfileList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Manuel on 13/12/13.
 */
public class GetProfileListActivity extends Activity{
    Profile p;
    List<Profile> listP;
    final ArrayList<String> list = new ArrayList<String>();
    StableArrayAdapter adapter;
    String PID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classrooms);
        final ListView listview = (ListView) findViewById(R.id.listView1);

        adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listview.setAdapter(adapter);
        Intent myIntent= getIntent();
        PID = myIntent.getStringExtra("ID");

        String[] params = new String[2];
        params[0] = Utils.getToken();
        params[1] = PID; //TODO: Put real person id to search
        new LoadClassroomTask().execute(params);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, final View view,
                                    int pos, long id) {
                p = listP.get(pos);
                Intent newintent = new Intent (getApplicationContext(), ViewProfileActivity.class);
                newintent.putExtra("APPID", p.getAppId());
                newintent.putExtra("APP", p.getApp());
                newintent.putExtra("PID", p.getId());
                newintent.putExtra("USID", p.getUserSubtypeId());
                newintent.putExtra("UT", p.getUserType());
                newintent.putExtra("UTID", p.getUsertypeId());
                newintent.putExtra("US", p.getUserSubtype());
                newintent.putExtra("LANG", p.getLanguage());
                GetProfileListActivity.this.startActivity(newintent);

            }
        });
    }

    private class LoadClassroomTask extends AsyncTask<String, Void, ProfileList> {
        protected void onPostExecute(ProfileList result) {
            if (result != null) {
                ArrayList<Profile> aux = result.getProfiles();
                listP = aux;
                for (int i = 0; i < aux.size(); ++i) {
                    list.add(aux.get(i).getAppId());
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected ProfileList doInBackground(String... param) {
            return ProfileList.getPersonProfileWS(param[0],param[1]);
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
}