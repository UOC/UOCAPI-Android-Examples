package com.uoc.loginusingapilib;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Event;

public class EventActivity extends Activity implements OnClickListener {
	Button btnLoadEvent;
	Button btnNewEvent;
	TextView tvSummary;
	Event e;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_event);
		btnLoadEvent = (Button)findViewById(R.id.btnLoadEvent);
		btnNewEvent = (Button)findViewById(R.id.btnNewEvent);
		tvSummary = (TextView)findViewById(R.id.tvSummary);
		btnLoadEvent.setOnClickListener(this);
		btnNewEvent.setOnClickListener(this);
		Toast.makeText(this, Utils.getToken(), Toast.LENGTH_LONG).show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_event, menu);
		return true;
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btnLoadEvent:
			startActivity(new Intent().setClass(this, ViewEventsActivity.class));
			break;
		case R.id.btnNewEvent:
			startActivity(new Intent().setClass(this, NewEventActivity.class));
			break;
		default:
			break;
		}
	}

}
