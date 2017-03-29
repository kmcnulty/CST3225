package com.example.kathleenmcnulty.androidlabs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TestToolbar extends AppCompatActivity {
    protected Context ctx;
    String message;
    EditText editTextmessage;

    public void setMessage(String message){
        this.message=message;
    }
    public String getMessage(){
        return message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_toolbar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ctx=this;
//This is email button on the bottom
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "You selected the email icon", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        message="you selected item 1";
    }
    public boolean onCreateOptionsMenu (Menu m){

        getMenuInflater().inflate(R.menu.toolbar_menu, m );
        return true;


    }
    public boolean onOptionsItemSelected(MenuItem mi){

        switch( mi.getItemId()) {
            case R.id.action_one:
                //The snackbar is the message on the bottom
                Snackbar.make(findViewById(R.id.toolbar), getMessage(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Log.d("Toolbar", "Option 1 selected");
                break;
            case R.id.action_two:
                //Start an activity…
                Log.d("Toolbar", "Option 2 selected");
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(R.string.back);
// Add the buttons
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        // User clicked OK button
                    }
                });
                builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        // User cancelled the dialog
                    }
                });
// Create the AlertDialog
                AlertDialog dialog = builder.create();
                dialog.show();


                break;
            case R.id.action_three:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ctx);
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder1.setView(inflater.inflate(R.layout.dialogue_message, null))
                        // Add action buttons
                        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                Dialog dial =(Dialog)dialog;
                                editTextmessage=(EditText)dial.findViewById(R.id.newmessage);

                                setMessage(editTextmessage.getText().toString());
                            }
                        })
                        .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.d("Toolbar", "Option dialogue negative selected");
                            }
                        });
                builder1.show();
                Log.d("Toolbar", "Option 3 selected");
                break;
            case R.id.About:
                //The  little pop up from the ... menu part
Toast.makeText(this, "Version 1.0, by Kathleen", Toast.LENGTH_LONG).show();

                Log.d("Toolbar", "About selected");
                break;
        }


        return true;
    }
}

//when you create the forth activity the three dots appear because there isn't enough space
