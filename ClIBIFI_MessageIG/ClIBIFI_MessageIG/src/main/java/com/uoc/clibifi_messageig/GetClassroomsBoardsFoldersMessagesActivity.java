package com.uoc.clibifi_messageig;

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
import com.uoc.openapilibrary.model.Message;
import com.uoc.openapilibrary.model.MessageList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Manuel on 5/12/13.
 */
public class GetClassroomsBoardsFoldersMessagesActivity extends Activity{
    Message m;
    String Cid;
    String Bid;
    String Fid;
    List<Message> listF;
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
        Fid = myIntent.getStringExtra("FID");
        String[] params = new String[4];
        params[0] = Utils.getToken();
        params[1] = Cid;
        params[2] = Bid;
        params[3] = Fid;
        new LoadClassroomTask().execute(params);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, final View view,
                                    int pos, long id) {
                m = listF.get(pos);
                Intent newintent = new Intent (getApplicationContext(), ViewMessageActivity.class);
                newintent.putExtra("MID", m.getId());
                newintent.putExtra("CID", Cid);
                newintent.putExtra("BID", Bid);
                newintent.putExtra("FID", Fid);
                GetClassroomsBoardsFoldersMessagesActivity.this.startActivity(newintent);

            }
        });
    }

    private class LoadClassroomTask extends AsyncTask<String, Void, MessageList> {
        protected void onPostExecute(MessageList result) {
            if (result != null) {
                ArrayList<Message> aux = result.getMessages();
                listF = aux;
                for (int i = 0; i < aux.size(); ++i) {
                    list.add(aux.get(i).getSubject());
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected MessageList doInBackground(String... param) {
            return MessageList.getMessagesfromClassRoomBoardFolderWS(param[0], param[1], param[2], param[3], "0", "1000");
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
