package com.uoc.si_resourcesg;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Resource;
/**
 * Created by Manuel on 11/12/13.
 */
public class ViewResourceslActivity extends Activity{
    TextView tvId;
    TextView tvType;
    TextView tvSubtype;
    TextView tvTitle;
    TextView tvCode;
    TextView tvDomain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_resource);
        Intent myIntent= getIntent();

        String[] params = new String[3];
        params[0] = Utils.getToken();
        params[1] = myIntent.getStringExtra("CID");
        params[2] = myIntent.getStringExtra("RID");
        new LoadEventTask().execute(params);
    }

    private class LoadEventTask extends AsyncTask<String, Void, Resource> {
        protected void onPostExecute(Resource result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                tvType = (TextView)findViewById(R.id.tvType);
                tvType.setText(result.getTitle());
                tvSubtype = (TextView)findViewById(R.id.tvSubtype);
                tvSubtype.setText(result.getTitle());
                tvTitle = (TextView)findViewById(R.id.tvTitle);
                tvTitle.setText(result.getTitle());
                tvCode = (TextView)findViewById(R.id.tvCode);
                tvCode.setText(result.getType());
                tvDomain = (TextView)findViewById(R.id.tvDomain);
                tvDomain.setText(result.getDomainId());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Resource doInBackground(String... param) {
            return Resource.getResourcefromSubjectWS(param[0], param[1], param[2]);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_board, menu);
        return true;
    }

}