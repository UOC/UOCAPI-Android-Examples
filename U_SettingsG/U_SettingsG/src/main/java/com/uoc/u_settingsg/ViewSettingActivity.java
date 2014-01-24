package com.uoc.u_settingsg;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Profile;

/**
 * Created by Manuel on 13/12/13.
 */
public class ViewSettingActivity extends Activity {
    TextView tvId;
    TextView tvTitle;
    TextView tvDescription;
    TextView tvSection;
    TextView tvURL;
    TextView tvValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Intent myIntent= getIntent();

        tvId = (TextView)findViewById(R.id.tvId);
        tvId.setText(myIntent.getStringExtra("ID"));
        tvTitle = (TextView)findViewById(R.id.tvTitle);
        tvTitle.setText(myIntent.getStringExtra("TITLE"));
        tvDescription = (TextView)findViewById(R.id.tvDescription);
        tvDescription.setText(myIntent.getStringExtra("DES"));
        tvSection = (TextView)findViewById(R.id.tvSection);
        tvSection.setText(myIntent.getStringExtra("SECT"));
        tvURL = (TextView)findViewById(R.id.tvURL);
        tvURL.setText(myIntent.getStringExtra("URL"));
        tvValue = (TextView)findViewById(R.id.tvValue);
        tvValue.setText(myIntent.getStringExtra("VAL"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_classrooms, menu);
        return true;
    }

}