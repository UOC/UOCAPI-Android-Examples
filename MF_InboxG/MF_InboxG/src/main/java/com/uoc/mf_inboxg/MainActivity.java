package com.uoc.mf_inboxg;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.uoc.openapilibrary.LoginActivity;

public class MainActivity extends LoginActivity {
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.uoc.mf_inboxg.R.menu.activity_main, menu);
        return true;
    }

    @Override
    public Intent NextActivityIntent() {
        Log.v("INTENTMAIN", "Creando intent");
        return new Intent (this, ViewFolderActivity.class);
    }


}
