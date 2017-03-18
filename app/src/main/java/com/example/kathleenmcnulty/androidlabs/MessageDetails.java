package com.example.kathleenmcnulty.androidlabs;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MessageDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_details);
        Bundle saved = getIntent().getExtras();
        MessageFragment msg = new MessageFragment();
        msg.setArguments(saved);
        getFragmentManager().beginTransaction().add(R.id.frame2, msg).commit();

    }
}
