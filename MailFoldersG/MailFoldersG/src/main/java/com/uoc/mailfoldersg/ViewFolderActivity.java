package com.uoc.mailfoldersg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Folder;

public class ViewFolderActivity extends Activity{
    TextView tvId;
    TextView tvName;
    TextView tvUnread;
    TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_folder);
        Intent myIntent= getIntent();

        String[] params = new String[2];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("FID");
        new LoadEventTask().execute(params);
    }

    private class LoadEventTask extends AsyncTask<String, Void, Folder> {
        protected void onPostExecute(Folder result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                tvName = (TextView)findViewById(R.id.tvName);
                tvName.setText(result.getName());
                tvUnread = (TextView)findViewById(R.id.tvUnread);
                tvUnread.setText(Long.toString(result.getUnreadMessages()));
                tvTotal = (TextView)findViewById(R.id.tvTotal);
                tvTotal.setText(Long.toString(result.getTotalMessages()));
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Folder doInBackground(String... param) {
            return Folder.getMailFolderWS(param[0], param[1]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_folder, menu);
        return true;
    }

}