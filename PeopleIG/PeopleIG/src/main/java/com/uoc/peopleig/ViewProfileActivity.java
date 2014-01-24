package com.uoc.peopleig;

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
public class ViewProfileActivity extends Activity {
    TextView tvAPPId;
    TextView tvAPP;
    TextView tvID;
    TextView tvUSID;
    TextView tvUT;
    TextView tvUTID;
    TextView tvUS;
    TextView tvLang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        Intent myIntent= getIntent();

        tvAPPId = (TextView)findViewById(R.id.tvAPPId);
        tvAPPId.setText(myIntent.getStringExtra("APPID"));
        tvAPP = (TextView)findViewById(R.id.tvAPP);
        tvAPP.setText(myIntent.getStringExtra("APP"));
        tvID = (TextView)findViewById(R.id.tvID);
        tvID.setText(myIntent.getStringExtra("PID"));
        tvUSID = (TextView)findViewById(R.id.tvUSID);
        tvUSID.setText(myIntent.getStringExtra("USID"));
        tvUT = (TextView)findViewById(R.id.tvUT);
        tvUT.setText(myIntent.getStringExtra("UT"));
        tvUTID = (TextView)findViewById(R.id.tvUTID);
        tvUTID.setText(myIntent.getStringExtra("UTID"));
        tvUS = (TextView)findViewById(R.id.tvUS);
        tvUS.setText(myIntent.getStringExtra("US"));
        tvLang = (TextView)findViewById(R.id.tvLang);
        tvLang.setText(myIntent.getStringExtra("LANG"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_classrooms, menu);
        return true;
    }

}