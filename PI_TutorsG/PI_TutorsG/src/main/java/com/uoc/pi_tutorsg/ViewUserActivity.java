package com.uoc.pi_tutorsg;


import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;
import android.content.Intent;
/**
 * Created by Manuel on 11/12/13.
 */
public class ViewUserActivity extends Activity{
    TextView tvId;
    TextView tvUsername;
    TextView tvName;
    TextView tvNumber;
    TextView tvFull;
    TextView tvPhoto;
    TextView tvLang;
    TextView tvSession;
    TextView tvEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_people);
        Intent myIntent= getIntent();

        tvId = (TextView)findViewById(R.id.tvId);
        tvId.setText(myIntent.getStringExtra("UID"));
        tvUsername = (TextView)findViewById(R.id.tvUsername);
        tvUsername.setText(myIntent.getStringExtra("UUName"));
        tvName = (TextView)findViewById(R.id.tvName);
        tvName.setText(myIntent.getStringExtra("UName"));
        tvNumber = (TextView)findViewById(R.id.tvNumber);
        tvNumber.setText(myIntent.getStringExtra("UNumber"));
        tvFull = (TextView)findViewById(R.id.tvFull);
        tvFull.setText(myIntent.getStringExtra("UFull"));
        tvPhoto = (TextView)findViewById(R.id.tvPhoto);
        tvPhoto.setText(myIntent.getStringExtra("UPhoto"));
        tvLang = (TextView)findViewById(R.id.tvLang);
        tvLang.setText(myIntent.getStringExtra("ULanguage"));
        tvSession = (TextView)findViewById(R.id.tvSession);
        tvSession.setText(myIntent.getStringExtra("USession"));
        tvEmail = (TextView)findViewById(R.id.tvEmail);
        tvEmail.setText(myIntent.getStringExtra("UEmail"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_board, menu);
        return true;
    }

}