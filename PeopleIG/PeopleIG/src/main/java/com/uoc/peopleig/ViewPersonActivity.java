package com.uoc.peopleig;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;
import android.widget.Button;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.EventList;
import com.uoc.openapilibrary.model.Person;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Manuel on 12/12/13.
 */
public class ViewPersonActivity extends Activity implements View.OnClickListener {
    /*STARTUOCAPIEXAMPLE*/
    TextView tvId;
    TextView tvUsername;
    TextView tvName;
    TextView tvFSurname;
    TextView tvSSurname;
    TextView tvEmail;
    Button btProfiles;
    TextView tvUserNumber;
    TextView tvHobbies;
    TextView tvSkills;
    TextView tvAbout;
    TextView tvNGOes;
    TextView tvLanguages;
    TextView tvSecondaryEmail;
    TextView tvBlog;
    TextView tvPersonalSite;
    TextView tvLastUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_person);
        btProfiles = (Button)findViewById(R.id.Profilebutton);
        btProfiles.setOnClickListener(this);

        String[] params = new String[2];
        params[0] = Utils.getToken();
        params[1] = "100"; //This is a real ID on the system
        new LoadEventTask().execute(params);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.Profilebutton:
                Intent newintent = new Intent (getApplicationContext(), GetProfileListActivity.class);
                newintent.putExtra("PID", "100"); //This is a real ID on the system
                ViewPersonActivity.this.startActivity(newintent);

                break;

            default:
                break;
        }
    }

    private class LoadEventTask extends AsyncTask<String, Void, Person> {
        protected void onPostExecute(Person result) {
            if (result != null) {
                tvId = (TextView)findViewById(R.id.tvId);
                tvId.setText(result.getId());
                tvUsername = (TextView)findViewById(R.id.tvUsername);
                tvUsername.setText(result.getUsername());
                tvName = (TextView)findViewById(R.id.tvName);
                tvName.setText(result.getUsername());
                tvFSurname = (TextView)findViewById(R.id.tvFSurname);
                tvFSurname.setText(result.getSurname1());
                tvSSurname = (TextView)findViewById(R.id.tvSSurname);
                tvSSurname.setText(result.getSurname2());
                tvEmail = (TextView)findViewById(R.id.tvEmail);
                tvEmail.setText(result.getEmail());
                tvUserNumber = (TextView)findViewById(R.id.tvUserNumber);
                tvUserNumber.setText(result.getUserNumber());
                tvHobbies = (TextView)findViewById(R.id.tvHobbies);
                tvHobbies.setText(result.getHobbies());
                tvSkills = (TextView)findViewById(R.id.tvSkills);
                tvSkills.setText(result.getSkills());
                tvAbout = (TextView)findViewById(R.id.tvAbout);
                tvAbout.setText(result.getAbout());
                tvNGOes = (TextView)findViewById(R.id.tvNGOes);
                tvNGOes.setText(result.getNGOes());
                tvLanguages = (TextView)findViewById(R.id.tvLanguages);
                tvLanguages.setText(result.getLanguages());
                tvSecondaryEmail = (TextView)findViewById(R.id.tvSecondaryEmail);
                tvSecondaryEmail.setText(result.getSecondaryEmail());
                tvBlog = (TextView)findViewById(R.id.tvBlog);
                tvBlog.setText(result.getBlog());
                tvPersonalSite = (TextView)findViewById(R.id.tvPersonalSite);
                tvPersonalSite.setText(result.getPersonalSite());
                tvLastUpdate = (TextView)findViewById(R.id.tvLastUpdate);
                tvLastUpdate.setText(result.getLastUpdate().toString());
            } else {
                Toast.makeText(getApplicationContext(),
                        "Hay problemas de conexion", Toast.LENGTH_LONG).show();
            }
        }
        @Override
        protected Person doInBackground(String... param) {
            /* UOCAPICALL /api/v1/people/{id} GET*/
            String p;
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/people/"+param[1]+"?access_token="+param[0]);
            httpGet.setHeader("content-type", "application/json");
            try {
                HttpResponse resp = httpClient.execute(httpGet);
                p = EntityUtils.toString(resp.getEntity());
            } catch (Exception ex) {
                Log.e("REST", "GET Error!", ex);
                return null;
            }
            return new Gson().fromJson(p, Person.class);
            //OR return Person.getPersonWS(param[0], param[1]); if you use our library
        }
    }
    /*ENDUOCAPIEXAMPLE*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_classrooms, menu);
        return true;
    }

}