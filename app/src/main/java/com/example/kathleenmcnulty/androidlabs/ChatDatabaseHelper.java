package com.example.kathleenmcnulty.androidlabs;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Kathleen McNulty on 18/02/2017.
 */

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    private static final int VERSION_NUM = 4;
    public static final String DATABASE_NAME = "MyDatabase";
    public static final String KEY_ID = "_id";
    public static final String KEY_MESSAGE = "Key_Message";

    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION_NUM);
    }
    public void onCreate(SQLiteDatabase db) //only called if not yet created
    {
        db.execSQL("CREATE TABLE " + DATABASE_NAME+ "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_MESSAGE+ " VARCHAR(256));");
        Log.i("ChatDatabaseHelper", "Calling onCreate");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + "  newVersion=" + newVersion);
    }


}
