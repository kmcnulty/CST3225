package com.example.kathleenmcnulty.androidlabs;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class TestToolbar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public boolean onCreateOptionsMenu (Menu m){

        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;


    }
    public boolean onOptionsItemSelected(MenuItem mi){

        switch( mi.getItemId()) {
            case R.id.action_one:
                Log.d("Toolbar", "Option 1 selected");
                break;
            case R.id.action_two:
                //Start an activity…
                Log.d("Toolbar", "Option 2 selected");
                break;
            case R.id.action_three:
                //Start an activity…
                Log.d("Toolbar", "Option 3 selected");
                break;
        }


        return true;
    }
}
