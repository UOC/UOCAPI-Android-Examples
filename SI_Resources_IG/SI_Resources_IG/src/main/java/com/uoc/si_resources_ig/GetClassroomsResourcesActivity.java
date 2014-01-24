package com.uoc.si_resources_ig;

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
import com.uoc.openapilibrary.model.Resource;
import com.uoc.openapilibrary.model.ResourceList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Manuel on 11/12/13.
 */
public class GetClassroomsResourcesActivity extends Activity {
    Resource r;
    String Cid;
    List<Resource> listR;
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
        Cid = myIntent.getStringExtra("ID");
        String[] params = new String[2];
        params[0] = Utils.getToken();
        params[1] = Cid;
        new LoadClassroomTask().execute(params);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, final View view,
                                    int pos, long id) {
                r = listR.get(pos);
                Intent newintent = new Intent (getApplicationContext(), ViewResourceslActivity.class);
                newintent.putExtra("RID", r.getId());
                newintent.putExtra("CID", Cid);
                GetClassroomsResourcesActivity.this.startActivity(newintent);

            }
        });
    }

    private class LoadClassroomTask extends AsyncTask<String, Void, ResourceList> {
        protected void onPostExecute(ResourceList result) {
            if (result != null) {
                ArrayList<Resource> aux = result.getResources();
                listR = aux;
                for (int i = 0; i < aux.size(); ++i) {
                    list.add(aux.get(i).getTitle());
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected ResourceList doInBackground(String... param) {
            return ResourceList.getResoucesListfromSubjectWS(param[0], param[1]);
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
