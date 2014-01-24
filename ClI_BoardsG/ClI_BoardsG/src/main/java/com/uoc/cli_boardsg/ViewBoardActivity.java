package com.uoc.cli_boardsg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Board;
import com.uoc.openapilibrary.model.Classroom;

public class ViewBoardActivity extends Activity{
    TextView tvId;
    TextView tvTitle;
    TextView tvSubtype;
    TextView tvDomainId;
    TextView tvUnread;
    TextView tvCode;
    TextView tvTotal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_board);
        Intent myIntent= getIntent();

        String[] params = new String[3];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("CID");
        params[2] = myIntent.getStringExtra("BID");
        new LoadEventTask().execute(params);
    }

    private class LoadEventTask extends AsyncTask<String, Void, Board> {
        protected void onPostExecute(Board result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                tvSubtype = (TextView)findViewById(R.id.tvSubtype);
                tvSubtype.setText(result.getSubtype());
                tvTitle = (TextView)findViewById(R.id.tvTitle);
                tvTitle.setText(result.getTitle());
                tvCode = (TextView)findViewById(R.id.tvCode);
                tvCode.setText(result.getCode());
                tvDomainId = (TextView)findViewById(R.id.tvDomainId);
                tvDomainId.setText(result.getDomainId());
                tvUnread = (TextView)findViewById(R.id.tvUnread);
                tvUnread.setText(result.getUnreadMessages());
                tvTotal = (TextView)findViewById(R.id.tvTotal);
                tvTotal.setText(result.getTotalMessages());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Board doInBackground(String... param) {
            return Board.getBoardfromClassRoomWS(param[0],param[1], param[2]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_board, menu);
        return true;
    }

}