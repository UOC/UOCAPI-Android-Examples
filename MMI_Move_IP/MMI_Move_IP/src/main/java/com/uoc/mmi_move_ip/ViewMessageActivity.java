package com.uoc.mmi_move_ip;

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
import com.uoc.openapilibrary.model.EventList;
import com.uoc.openapilibrary.model.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Manuel on 12/12/13.
 */
public class ViewMessageActivity extends Activity{

    TextView tvId;
    TextView tvSubject;
    TextView tvSnippet;
    TextView tvDate;
    TextView tvColor;
    TextView tvStatus;
    TextView tvFrom;
    TextView tvTo;
    TextView tvCC;
    TextView tvBody;
    Message m = new Message();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);
        Intent myIntent= getIntent();

        String[] params = new String[2];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("MID");
        new LoadEventTask().execute(params);
    }

    private class LoadEventTask extends AsyncTask<String, Void, Message> {
        protected void onPostExecute(Message result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                tvSubject = (TextView)findViewById(R.id.tvSubject);
                tvSubject.setText(result.getSubject());
                tvSnippet = (TextView)findViewById(R.id.tvSnippet);
                tvSnippet.setText(result.getSnippet());
                tvDate = (TextView)findViewById(R.id.tvDate);
                tvDate.setText(result.getDate());
                tvColor = (TextView)findViewById(R.id.tvColor);
                tvColor.setText(Long.toString(result.getColor()));
                tvStatus = (TextView)findViewById(R.id.tvStatus);
                tvStatus.setText(Long.toString(result.getStatus()));
                tvFrom = (TextView)findViewById(R.id.tvFrom);
                tvFrom.setText(result.getFrom());
                tvTo = (TextView)findViewById(R.id.tvTo);
                tvTo.setText(result.getTo());
                tvCC = (TextView)findViewById(R.id.tvCC);
                tvCC.setText(result.getCc());
                tvBody = (TextView)findViewById(R.id.tvBody);
                tvBody.setText(result.getBody());
                m = result;
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Message doInBackground(String... param) {
            return Message.getMessagefromMailWS(param[0], param[1]);
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
                Intent newintent = new Intent (getApplicationContext(), GetFoldersActivity.class);
                newintent.putExtra("Message", m);
                ViewMessageActivity.this.startActivity(newintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}