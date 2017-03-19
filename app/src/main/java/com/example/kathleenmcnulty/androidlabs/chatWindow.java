package com.example.kathleenmcnulty.androidlabs;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
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
    ArrayList<Message> arrayList =new ArrayList<>();
    String position="null";
    ChatDatabaseHelper dbHelper;
    boolean isTablet;
    ChatAdapter messageAdapter;

    protected static final String ACTIVITY_NAME = "chatWindow";

    SQLiteDatabase db;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //this refers to the context which is the current activity
        dbHelper = new ChatDatabaseHelper(this);
        db = dbHelper.getWritableDatabase();
        setContentView(R.layout.activity_chat_window);
        messageAdapter =new ChatAdapter( this );
        button5=(Button) findViewById(R.id.button4);
        listView=(ListView) findViewById(R.id.listView);
        editText=(EditText) findViewById(R.id.editText4);

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
                long newId = db.insert(ChatDatabaseHelper.DATABASE_NAME, "", newValues);
                Message msgTemp = new Message(newId, msg);
                Log.d("MessaGEBELIKE:", msgTemp.toString());
                arrayList.add(msgTemp);
                messageAdapter.notifyDataSetChanged();
                editText.setText("");

            }
        });


        //
        final Cursor cursor = db.query(false, ChatDatabaseHelper.DATABASE_NAME,
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
            Message myNewMsg = new Message(cursor.getLong(cursor.getColumnIndex( ChatDatabaseHelper.KEY_ID)), cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_MESSAGE) ));
            Log.d("WHATILOOKLIKE", myNewMsg.toString());
            arrayList.add(myNewMsg);
            //arrayListDbID.add(cursor.getString( cursor.getColumnIndex( ChatDatabaseHelper.KEY_ID) ));
            //Get's database cursor to move to next element
            cursor.moveToNext();
        }
        //GEt the column names for the log message???
        for(int i=0;i<cursor.getColumnCount();i++) {
            cursor.getColumnName(i);
            Log.i(ACTIVITY_NAME, "Column Name:" +  cursor.getColumnName(i));


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int j, long l) {



                    Bundle bun = new Bundle();
                    bun.putInt("ID", j);//l is the database ID of selected item
                    //bun.putLong("DBID", Long.parseLong(arrayListDbID.get(j)));
                    //Log.d("LONDID", l+"");
                    String msg = arrayList.get(j).getMessage();
                    bun.putString("MESSAGE", msg);
                    bun.putLong("DBID", arrayList.get(j).getId());

                    //step 2, if a tablet, insert fragment into FrameLayout, pass data
                    if(isTablet) {
                        MessageFragment frag = new MessageFragment();

                        frag.setArguments(bun);

                        getFragmentManager().beginTransaction().add(R.id.frame, frag).commit();
                    }
                    //step 3 if a phone, transition to empty Activity that has FrameLayout
                    else //isPhone
                    {
                        Intent intnt = new Intent(ChatWindow.this, MessageDetails.class);
                        intnt.putExtra("ID", j); //pass the Database ID to next activity
                        intnt.putExtra("DBID", arrayList.get(j).getId());
                        intnt.putExtra("MESSAGE", msg);
                        startActivityForResult(intnt,1);
                    }
                }
            });

            //step 1, find out if you are on a phone or tablet.
            isTablet = (findViewById(R.id.frame) != null); //find out if this is a phone or tablet


        }


    }
    private class Message{
        String msg;
        long id;
        public Message(long idi, String mess){
            id = idi;
            msg = mess;
        }
        public void setId(long newId){
            id = newId;
        }
        public void setMessage(String newMsg){
            msg = newMsg;
        }
        public long getId(){
            return id;
        }
        public String getMessage(){
            return msg;
        }
        public String toString(){
            return "Message: "+msg+"id "+id;
        }
    }
    private class ChatAdapter extends ArrayAdapter<Message>{

        public ChatAdapter(Context ctx) {
            super(ctx, 0);
        }
        public	int getCount(){return arrayList.size();
        }
        public Message getItem(int position){
            return arrayList.get(position);
        }
        public View getView(int position, View convertView, ViewGroup parent){
            //Just specifying the chat window is going to use what layout for each item????
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result = null ;
            if(position%2 == 0) {
                result = inflater.inflate(R.layout.chat_row_incoming, null);
                TextView message = (TextView) result.findViewById(R.id.textView3);
                message.setText(getItem(position).getMessage()); // get the string at position
                return result;

            }else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);
            TextView message = (TextView)result.findViewById(R.id.textView2);
            message.setText(   getItem(position).getMessage()  ); // get the string at position
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                Bundle bun = data.getExtras();
                int result = bun.getInt("ID");
                Long dbid = bun.getLong("DBID");
                Log.d("DELETEFROMDBBEFORE", dbid+"");
                deleteFromDb(result, dbid);
            }
        }
    }

    public void deleteFromDb(int index, long dbid){
        Log.d("INDEXLOOKS", index+"");
        arrayList.remove(index);//remove from the arraylist
        Log.d("DBIDLOOKS", dbid+"");
        db.delete(ChatDatabaseHelper.DATABASE_NAME, ChatDatabaseHelper.KEY_ID + "="+ dbid, null);
        messageAdapter.notifyDataSetChanged();
    }
}
