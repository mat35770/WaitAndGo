package com.waitandgo.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.StringSearch;

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
    public static final String DESCRIPTION = "description";

    public static final String TASK_TABLE_CREATE = "CREATE TABLE " + TASK_TABLE_NAME + "(" + KEY +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT, " + CATEGORY + " TEXT, " +
            SHARE_WITH + " TEXT, " + TASK_PREREQUISITE + " TEXT, " + DESCRIPTION + " TEXT);";

    public static final String TASK_TABLE_DROP = "DROP TABLE IF EXISTS " + TASK_TABLE_NAME + ";";

    protected DBHelper dBHelper = null;
    protected SQLiteDatabase mDb = null;

    public TaskDAO(Context context) {
        this.dBHelper = new DBHelper(context);
    }

    public void open() {
        this.mDb = dBHelper.getWritableDatabase();
    }

    public void close() {
        dBHelper.close();
    }

    public SQLiteDatabase getDb() {
        return this.mDb;
    }

    public void createTask (String title, String category, String shareWith, String taskPrerequisite,
                            String description){
        ContentValues values = new ContentValues();
        values.put(TITLE, title);
        values.put(CATEGORY, category);
        values.put(SHARE_WITH,shareWith);
        values.put(TASK_PREREQUISITE,taskPrerequisite);
        values.put(DESCRIPTION,description);
        long insertId = mDb.insert(TASK_TABLE_NAME,null,values);

       /* this.mDb.execSQL("INSERT INTO " + TASK_TABLE_NAME + "(" + TITLE + ", " + CATEGORY + ", " +
                SHARE_WITH + ", " + TASK_PREREQUISITE + ", " + DESCRIPTION + ") VALUES (" + title +
                ", " + category +", " + shareWith + ", " + taskPrerequisite + ", " + description + ");");*/
    }

    public void deleteTask (Task task){
        String[] selectionArgs = { task.getTitle()};
        this.mDb.delete(TASK_TABLE_NAME, TITLE + " = ?",selectionArgs);
    }
}
