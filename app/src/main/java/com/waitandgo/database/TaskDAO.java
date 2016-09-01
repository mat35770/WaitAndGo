package com.waitandgo.database;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import static com.waitandgo.database.DBHelper.DATABASE_NAME;
import static com.waitandgo.database.DBHelper.DATABASE_VERSION;

/**
 * Created by Mathieu on 30/08/2016.
 */

public class TaskDAO {
    public static final String TASK_TABLE_NAME = "task";
    public static final String KEY = "id";
    public static final String TITLE = "title";
    public static final String CATEGORY = "category";
    public static final String SHARE_WITH = "shareWith";
    public static final String TASK_PREREQUISITE = "taskPrerequisite";

    public static final String TASK_TABLE_CREATE = "CREATE TABLE" + TASK_TABLE_NAME + "(" + KEY +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT, " + CATEGORY + " TEXT, " +
            SHARE_WITH + " TEXT, " + TASK_PREREQUISITE + " TEXT);";

    public static final String TASK_TABLE_DROP = "DROP TABLE IF EXISTS " + TASK_TABLE_NAME + ";";

    protected DBHelper dBHelper = null;
    protected SQLiteDatabase mDb = null;

    public TaskDAO(Context context) {
        this.dBHelper = new DBHelper(context);
    }

    public SQLiteDatabase open() {
        this.mDb = dBHelper.getWritableDatabase();
        return this.mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }

    public void createTask (String title, String category, String shareWith, Task taskPrerequisite){

    }
}
