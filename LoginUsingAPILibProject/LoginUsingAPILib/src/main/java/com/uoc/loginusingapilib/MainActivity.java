package com.uoc.loginusingapilib;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.uoc.openapilibrary.LoginActivity;

public class MainActivity extends LoginActivity {
	Button Login;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Login = (Button) findViewById(R.id.btnLogin);
        Login.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				Login();
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

	@Override
	public Intent NextActivityIntent() {
		Log.v("INTENT", "Creando intent");
		return new Intent (this, TestActivity.class);
	}
	private void Login() {
		//Por el context
        IniciarLogin();
	}

    
}
