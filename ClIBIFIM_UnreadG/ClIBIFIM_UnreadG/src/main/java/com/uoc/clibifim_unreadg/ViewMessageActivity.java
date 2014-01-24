package com.uoc.clibifim_unreadg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Message;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);
        Intent myIntent= getIntent();

        String[] params = new String[5];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("CID");
        params[2] = myIntent.getStringExtra("BID");
        params[3] = myIntent.getStringExtra("FID");
        params[4] = myIntent.getStringExtra("MID");
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
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Message doInBackground(String... param) {
            return Message.getMessagefromClassRoomBoardFolderWS(param[0], param[1], param[2], param[3], param[4]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_message, menu);
        return true;
    }

}