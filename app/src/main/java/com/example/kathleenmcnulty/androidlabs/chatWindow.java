package com.example.kathleenmcnulty.androidlabs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
    ChatDatabaseHelper dbHelper;

    protected static final String ACTIVITY_NAME = "chatWindow";

    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //this refers to the context which is the current activity
        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        setContentView(R.layout.activity_chat_window);

        button5=(Button) findViewById(R.id.button4);
        listView=(ListView) findViewById(R.id.listView);
        editText=(EditText) findViewById(R.id.editText4);
        final ChatAdapter messageAdapter =new ChatAdapter( this );
        listView.setAdapter(messageAdapter);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View view) {
                String msg = editText.getText().toString();
                //Carries to database and puts it in the right column
                ContentValues newValues = new ContentValues();
                //newVakyes is a LINK VARIABLE then matching the content values to the column
                newValues.put(ChatDatabaseHelper.KEY_MESSAGE, msg);
            //then im putting it in the database
            db.insert(ChatDatabaseHelper.DATABASE_NAME, "", newValues);
             arrayList.add(msg);
                messageAdapter.notifyDataSetChanged();
                editText.setText("");

            }
        });


        //
        Cursor cursor = db.query(false, ChatDatabaseHelper.DATABASE_NAME,
                //this means return all values for those fields
                new String[] { ChatDatabaseHelper.KEY_ID, ChatDatabaseHelper.KEY_MESSAGE},
                null,null, null, null, null, null);
        int rows = cursor.getCount() ; //number of rows returned
        cursor.moveToFirst(); //move to first result
        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount() );

        while(!cursor.isAfterLast() ){
//
            Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ) );
            //Adds to array
            arrayList.add(cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ));
            //Get's database cursor to move to next element
            cursor.moveToNext();
        }
        //GEt the column names for the log message???
        for(int i=0;i<cursor.getColumnCount();i++) {
            cursor.getColumnName(i);
            Log.i(ACTIVITY_NAME, "Column Name:" +  cursor.getColumnName(i));
        }



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
            //Just specifying the chat window is going to use what layout for each item????
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
    public void onDestroy(){
        super.onDestroy();
        if(db != null){
            db.close();
        }
        if(dbHelper != null){
            dbHelper.close();
        }

    }
}
