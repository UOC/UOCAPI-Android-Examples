package com.uoc.loginusingapilib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//import com.uoc.loginuocusinglib.R;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Event;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.DialogFragment;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewEventActivity extends Activity implements OnClickListener{
	EditText etUrle;
	EditText etSummary;
	Button bstart;
	Button bend;
	Button bhstart;
	Button bhend;
	Button besend;
	Event e;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_event);
		etUrle = (EditText)findViewById(R.id.etUrle);
		etSummary = (EditText)findViewById(R.id.etSummary);
		bstart = (Button)findViewById(R.id.bstart);
		bend = (Button)findViewById(R.id.bend);
		bhstart = (Button)findViewById(R.id.bhstart);
		bhend = (Button)findViewById(R.id.bhend);
		besend = (Button)findViewById(R.id.besend);
		
		bstart.setOnClickListener(this);
		bend.setOnClickListener(this);
		bhstart.setOnClickListener(this);
		bhend.setOnClickListener(this);
		besend.setOnClickListener(this);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_event, menu);
		return true;
	}
	
	private class NewEventTask extends AsyncTask<String, Void, Event> {
		protected void onPostExecute(Event result) {
			Toast.makeText(getApplicationContext(),
					"Event sent", Toast.LENGTH_LONG).show();
			if (result != null) {
				Toast.makeText(getApplicationContext(),
						"Event created succesfully", Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(),
						"Encountered connection problems", Toast.LENGTH_LONG).show();
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Event doInBackground(String... token) {
			return Event.postEventWS(token[0], e);
		}
	}

	public void onClick(View v) {
		DialogFragment myfragment;
		switch (v.getId()) {
		
		case R.id.bstart:
			myfragment = DialogDate.newInstance((Button)findViewById(v.getId()));	
			myfragment.show(getFragmentManager(), "Date");
			break;
		case R.id.bend:
			myfragment = DialogDate.newInstance((Button)findViewById(v.getId()));
			myfragment.show(getFragmentManager(), "Date");
			break;
		case R.id.bhstart:
			myfragment = DialogTime.newInstance((Button)findViewById(v.getId()));
			myfragment.show(getFragmentManager(), "Time");
			break;
		case R.id.bhend:
			myfragment = DialogTime.newInstance((Button)findViewById(v.getId()));
			myfragment.show(getFragmentManager(), "Time");
			break;
		case R.id.besend:
			String texthstart = bhstart.getText().toString();
			String texthend = bhend.getText().toString();
			String textstart = bstart.getText().toString();
			String textend = bend.getText().toString();
			String textUrl = etUrle.getText().toString();
			String textSummary = etSummary.getText().toString();
			if (texthstart.equals("Time") || texthend.equals("Time") || 
					textstart.equals("Select date") || textend.equals("Select date")
					|| textUrl.equals("") || textSummary.equals("")){
				Toast.makeText(getApplicationContext(),
						"Debes rellenar todos los campos", Toast.LENGTH_LONG).show();
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
				Date datestart = new Date();
				Date dateend = new Date();
				Date datehstart = new Date();
				Date datehend = new Date();
				try {
					datestart = sdf.parse(textstart);
					dateend = sdf.parse(textend);
					sdf = new SimpleDateFormat("HH:mm");
					datehstart = sdf.parse(texthstart);
					datehend = sdf.parse(texthend);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				if (datestart.after(dateend)){
					Toast.makeText(getApplicationContext(),
							"Has creado una cita imposible: Fecha inicio es posterior a Fecha fin", Toast.LENGTH_LONG).show();
				}else if(datestart.equals(dateend) && (datehstart.after(datehend) || datehstart.equals(datehend)) ){
					Toast.makeText(getApplicationContext(),
							"Has creado una cita imposible: Hora inicio es posterior o igual a Hora fin", Toast.LENGTH_LONG).show();
				}else {
					String start = textstart+"T"+texthstart+":00Z";
					String end = textend+"T"+texthend+":00Z";
					e = new Event("does not matter", textUrl, textSummary, start, end);
					String[] params = new String[2];
					params[0] = Utils.getToken();
					params[1] = Utils.toJSON(e);
					new NewEventTask().execute(params);
					finish();
				}
			}
			
			break;
		default:
			break;
		}
	}
	


}
