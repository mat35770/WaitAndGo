package com.waitandgo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.waitandgo.database.TaskDAO.TASK_TABLE_CREATE;
import static com.waitandgo.database.TaskDAO.TASK_TABLE_DROP;

/**
 * Created by Mathieu on 30/08/2016.
 */

public class DatabaseHandler extends SQLiteOpenHelper {

    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASK_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TASK_TABLE_DROP);
        onCreate(db);
    }
}
