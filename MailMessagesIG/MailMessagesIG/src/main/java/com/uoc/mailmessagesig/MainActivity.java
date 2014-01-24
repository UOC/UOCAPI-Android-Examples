package com.uoc.mailmessagesig;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.uoc.openapilibrary.LoginActivity;

/**
 * Created by Manuel on 12/12/13.
 */
public class MainActivity extends LoginActivity {
    Button Login;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.uoc.mailmessagesig.R.layout.activity_main);
        Login = (Button) findViewById(com.uoc.mailmessagesig.R.id.btnLogin);
        Login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Login();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.uoc.mailmessagesig.R.menu.activity_main, menu);
        return true;
    }

    @Override
    public Intent NextActivityIntent() {
        Log.v("INTENT", "Creando intent");
        return new Intent (this, GetMailFoldersActivity.class);
    }
    private void Login() {
        //Por el context
        IniciarLogin();
    }


}
