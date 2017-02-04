package com.example.kathleenmcnulty.androidlabs;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;



public class ChatWindow extends AppCompatActivity {
Button button5;
ListView listView;
    EditText editText;
    ArrayList<String> arrayList =new ArrayList<>();
    String position="null";
    protected static final String ACTIVITY_NAME = "chatWindow";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        button5=(Button) findViewById(R.id.button4);
        listView=(ListView) findViewById(R.id.listView);
        editText=(EditText) findViewById(R.id.editText4);
        final ChatAdapter messageAdapter =new ChatAdapter( this );
        listView.setAdapter (messageAdapter);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {

             arrayList.add(editText.getText().toString());
                messageAdapter.notifyDataSetChanged();
                editText.setText("");

            }
        });


    }
    private class ChatAdapter extends ArrayAdapter<String>{

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
        public	int getCount(){return arrayList.size();
        }
        public String getItem(int position){
            return arrayList.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                TextView message = (TextView) result.findViewById(R.id.textView3);
                message.setText(getItem(position)); // get the string at position
                return result;

            }else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = (TextView)result.findViewById(R.id.textView2);
            message.setText(   getItem(position)  ); // get the string at position
            return result;
        }



    }

}
