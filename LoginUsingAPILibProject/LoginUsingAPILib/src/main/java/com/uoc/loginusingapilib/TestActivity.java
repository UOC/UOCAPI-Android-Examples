package com.uoc.loginusingapilib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TestActivity extends Activity implements OnClickListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		Button btnViewProfile = (Button)findViewById(R.id.btnViewProfile);
		btnViewProfile.setOnClickListener(this);
		Button btnViewUser = (Button)findViewById(R.id.btnViewUser);
		btnViewUser.setOnClickListener(this);
		Button btnEvents = (Button)findViewById(R.id.btnEvents);
		btnEvents.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_test, menu);
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnViewUser:
			startActivity(new Intent().setClass(this, ViewUserActivity.class));
			break;
		case R.id.btnViewProfile:
			startActivity(new Intent().setClass(this, ProfileActivity.class));
			break;
		case R.id.btnEvents:
			startActivity(new Intent().setClass(this, EventActivity.class));

			break;
		default:
			break;
		}
	}

}
