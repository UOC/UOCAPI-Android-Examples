package com.uoc.u_tutorsg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.uoc.openapilibrary.LoginActivity;
/**
 * Created by Manuel on 11/12/13.
 */
public class MainActivity extends LoginActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.uoc.u_tutorsg.R.menu.activity_main, menu);
        return true;
    }

    @Override
    public Intent NextActivityIntent() {
        Log.v("INTENTMAIN", "Creando intent");
        return new Intent (this, GetClassroomsPeopleActivity.class);
    }


}
