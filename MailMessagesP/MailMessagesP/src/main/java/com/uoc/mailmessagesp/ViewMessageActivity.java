package com.uoc.mailmessagesp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import com.google.gson.Gson;
import com.uoc.openapilibrary.Constants;
import com.uoc.openapilibrary.RESTMethod;
import com.uoc.openapilibrary.Utils;
import com.uoc.openapilibrary.model.Message;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class ViewMessageActivity extends Activity{
    TextView tvId;
    TextView tvSubject;
    TextView tvSnippet;
    TextView tvDate;
    TextView tvColor;
    TextView tvStatus;
    TextView tvFrom;
    TextView tvTo;
    TextView tvCC;
    TextView tvBody;
    Message m = new Message();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);

        tvId = (TextView)findViewById(R.id.tvId);
        tvId.setText("39806937699");
        tvSubject = (TextView)findViewById(R.id.tvSubject);
        tvSubject.setText("Test mail");
        tvSnippet = (TextView)findViewById(R.id.tvSnippet);
        tvSnippet.setText("Test mail");
        tvDate = (TextView)findViewById(R.id.tvDate);
        tvDate.setText("30.06.2012 01:57");
        tvColor = (TextView)findViewById(R.id.tvColor);
        tvColor.setText("0");
        tvStatus = (TextView)findViewById(R.id.tvStatus);
        tvStatus.setText("0");
        tvFrom = (TextView)findViewById(R.id.tvFrom);
        tvFrom.setText("Manuel Perera Paquico");
        tvTo = (TextView)findViewById(R.id.tvTo);
        tvTo.setText("Manuel Perera Paquico");
        tvCC = (TextView)findViewById(R.id.tvCC);
        tvCC.setText("Manuel Perera Paquico");
        tvBody = (TextView)findViewById(R.id.tvBody);
        tvBody.setText("Lorem ipsum ...");
    }
    /*STARTUOCAPIEXAMPLE*/
    private class NewMessageTask extends AsyncTask<String, Void, Message> {
        protected void onPostExecute(Message result) {
            Toast.makeText(getApplicationContext(),
                    "Message sent", Toast.LENGTH_LONG).show();
            if (result != null) {
                Toast.makeText(getApplicationContext(),
                        "Message created succesfully", Toast.LENGTH_LONG).show();
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
        protected Message doInBackground(String... token) {
            /* UOCAPICALL /api/v1/mail/messages POST*/
            String maux;
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://oslo.uoc.es:8080/webapps/uocapi/api/v1/mail/messages" +"?access_token="+ Utils.getToken());
            httpPost.setHeader("content-type", "application/json");
            try {
                StringEntity entity = new StringEntity(new Gson().toJson(m));
                httpPost.setEntity(entity);
                HttpResponse resp = httpClient.execute(httpPost);
                maux = EntityUtils.toString(resp.getEntity());

            } catch (Exception ex) {
                Log.e("REST", "POST Error!", ex);
                return null;
            }
            return new Gson().fromJson(maux, Message.class);
            //OR return Message.postSendMessagetoMailWS(token[0], m); if you use our library
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_send:
                m.setId("39806937699");
                m.setSubject("Test mail");
                m.setSnippet("Test mail");
                m.setDate("30.06.2012 01:57");
                m.setColor(0);
                m.setStatus(0);
                m.setFrom("Manuel Perera Paquico");
                m.setTo("Manuel Perera Paquico");
                m.setCc("Manuel Perera Paquico");
                m.setBody("Lorem ipsum ...");
                new NewMessageTask().execute();
                Intent newintent = new Intent (getApplicationContext(), GetMessagesActivity.class);
                ViewMessageActivity.this.startActivity(newintent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
/*ENDUOCAPIEXAMPLE*/
}