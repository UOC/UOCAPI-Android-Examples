package com.uoc.classroomsg;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Classroom;

public class ViewClassroomsActivity extends Activity{
    TextView tvId;
    TextView tvTitle;
    TextView tvColor;
    TextView tvFatherId;
    TextView tvAssignments;
    TextView tvCode;
    TextView tvShortTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_classroom);
        Intent myIntent= getIntent();

        String[] params = new String[2];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("ID");
        new LoadEventTask().execute(params);
    }

    private class LoadEventTask extends AsyncTask<String, Void, Classroom> {
        protected void onPostExecute(Classroom result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                tvTitle = (TextView)findViewById(R.id.tvTitle);
                tvTitle.setText(result.getTitle());
                tvColor = (TextView)findViewById(R.id.tvColor);
                tvColor.setText(result.getColor());
                tvFatherId = (TextView)findViewById(R.id.tvFatherId);
                tvFatherId.setText(result.getFatherId());

                tvAssignments = (TextView)findViewById(R.id.tvAssignments);
                String[] work = result.getAssignments();
                int listsize = work.length;
                String aux = work[0];
                for (int i = 1; i < listsize; ++i) {
                    aux = aux+"\n"+work[i];
                }
                tvAssignments.setText(aux);

                tvCode = (TextView)findViewById(R.id.tvCode);
                tvCode.setText(result.getCode());
                tvShortTitle = (TextView)findViewById(R.id.tvShortTitle);
                tvShortTitle.setText(result.getShorttitle());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Classroom doInBackground(String... param) {
            return Classroom.getClassroomWS(param[0],param[1]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_classroom, menu);
        return true;
    }

}