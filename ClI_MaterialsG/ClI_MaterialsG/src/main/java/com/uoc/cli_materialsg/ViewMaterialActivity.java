package com.uoc.cli_materialsg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.TeachingMaterial;
/**
 * Created by Manuel on 11/12/13.
 */
public class ViewMaterialActivity extends Activity{
    TextView tvId;
    TextView tvTitle;
    TextView tvType;
    TextView tvURL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_material);
        Intent myIntent= getIntent();

        String[] params = new String[3];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("CID");
        params[2] = myIntent.getStringExtra("TMID");
        new LoadEventTask().execute(params);
    }

    private class LoadEventTask extends AsyncTask<String, Void, TeachingMaterial> {
        protected void onPostExecute(TeachingMaterial result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                tvTitle = (TextView)findViewById(R.id.tvTitle);
                tvTitle.setText(result.getTitle());
                tvType = (TextView)findViewById(R.id.tvType);
                tvType.setText(result.getType());
                tvURL = (TextView)findViewById(R.id.tvURL);
                tvURL.setText(result.getUrl());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected TeachingMaterial doInBackground(String... param) {
            return TeachingMaterial.getTeachingMaterialfromClassRoomWS(param[0],param[1],param[2]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_board, menu);
        return true;
    }

}