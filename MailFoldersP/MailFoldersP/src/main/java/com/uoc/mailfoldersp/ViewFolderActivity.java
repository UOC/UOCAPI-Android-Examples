package com.uoc.mailfoldersp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Folder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.helpers.Util;

public class ViewFolderActivity extends Activity{
    TextView tvId;
    TextView tvName;
    TextView tvUnread;
    TextView tvTotal;
    Folder f = new Folder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_folder);

        tvId = (TextView)findViewById(R.id.tvId);
        tvId.setText("602574");
        tvName = (TextView)findViewById(R.id.tvName);
        tvName.setText("Random Name");
        tvUnread = (TextView)findViewById(R.id.tvUnread);
        tvUnread.setText("0");
        tvTotal = (TextView)findViewById(R.id.tvTotal);
        tvTotal.setText("0");
    }
    /*STARTUOCAPIEXAMPLE*/
    private class NewMailFolderTask extends AsyncTask<String, Void, Folder> {
        protected void onPostExecute(Folder result) {
            Toast.makeText(getApplicationContext(),
                    "Folder sent", Toast.LENGTH_LONG).show();
            if (result != null) {
                Toast.makeText(getApplicationContext(),
                        "Folder created succesfully", Toast.LENGTH_LONG).show();
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
            /* UOCAPICALL /api/v1/mail/folders POST*/
            String faux;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/mail/folders" +"?access_token="+ Utils.getToken());
            httpPost.setHeader("content-type", "application/json");
            try {
                StringEntity entity = new StringEntity(new Gson().toJson(f));
                httpPost.setEntity(entity);
                HttpResponse resp = httpClient.execute(httpPost);
                faux = EntityUtils.toString(resp.getEntity());

            } catch (Exception ex) {
                Log.e("REST", "POST Error!", ex);
                return null;
            }
            return new Gson().fromJson(faux, Folder.class);
            //OR return Folder.postNewMailFolderWS(token[0], f); if you use our library
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_send:
                f.setId("602574");
                f.setName("Random Name");
                f.setTotalMessages(0);
                f.setUnreadMessages(0);
                new NewMailFolderTask().execute();
                Intent newintent = new Intent (getApplicationContext(), GetMailFoldersActivity.class);
                ViewFolderActivity.this.startActivity(newintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
/*ENDUOCAPIEXAMPLE*/
}