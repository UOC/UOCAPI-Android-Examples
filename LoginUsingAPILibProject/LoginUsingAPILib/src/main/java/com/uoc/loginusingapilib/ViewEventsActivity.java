package com.uoc.loginusingapilib;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Event;
import com.uoc.openapilibrary.model.EventList;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class ViewEventsActivity extends Activity implements OnClickListener{
	ListView lvevents;
	Button bsend;
	Event e;
	List<Event> listE;
	ArrayList<Integer> positions = new ArrayList<Integer>();
	final ArrayList<String> list = new ArrayList<String>();
	StableArrayAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_events);
		final ListView listview = (ListView) findViewById(R.id.listView1);
		bsend = (Button)findViewById(R.id.savebutton);

		adapter = new StableArrayAdapter(this,
	        android.R.layout.simple_list_item_checked, list);
	    listview.setAdapter(adapter);
		bsend.setOnClickListener(this);
		String[] params = new String[2];
		params[0] = Utils.getToken();
		new LoadEventTask().execute(params);
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

		      public void onItemClick(AdapterView<?> parent, final View view,
		          int pos, long id) {
		    	  if (positions != null && positions.contains(pos)) {
		    		  positions.remove(Integer.valueOf(pos));
		    	  }else{
		    		  positions.add(pos);
		    	  }
		        
		      }
		    });
	}
	
	private class LoadEventTask extends AsyncTask<String, Void, EventList> {
		protected void onPostExecute(EventList result) {
			if (result != null) {
				ArrayList<Event> aux = result.getEvents();
				Log.v("RESULT", aux.get(0).getUrl());
				listE = aux;
			    for (int i = 0; i < aux.size(); ++i) {
				      list.add(aux.get(i).getSummary());
				}
			    adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(getApplicationContext(),
						"Hay problemas de conexi�n", Toast.LENGTH_LONG).show();
			}
		}
		@Override
		protected EventList doInBackground(String... param) {
			return EventList.getEventListWS(param[0]);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_events, menu);
		return true;
	}

	public void onClick(View arg0) {
		for (int i = 0; i < positions.size(); ++i) {
			e = listE.get(positions.get(i));
			
		    Calendar cal = Calendar.getInstance(); 
		    Date auxdateS = null;
		    Date auxdateE = null;
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
			try {
				auxdateS = formatoDelTexto.parse(e.getStart());
				auxdateE = formatoDelTexto.parse(e.getEnd());
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
			Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			cal.setTime(auxdateS);
			intent.putExtra("beginTime", cal.getTimeInMillis());
			intent.putExtra("allDay", false);
			intent.putExtra("rrule", "FREQ=DAILY;COUNT=1");
			//intent.putExtra("location", e.getUrl());
			cal.setTime(auxdateE);
			intent.putExtra("endTime", cal.getTimeInMillis());
			intent.putExtra("title", e.getSummary());
			startActivity(intent);
		}

	}
	
	private class StableArrayAdapter extends ArrayAdapter<String> {

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) {
	        mIdMap.put(objects.get(i), i);
	      }
	    }

	}

}
