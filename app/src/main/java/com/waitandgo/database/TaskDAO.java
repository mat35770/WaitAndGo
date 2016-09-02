package com.waitandgo.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.icu.text.StringSearch;

import java.util.ArrayList;
import java.util.List;

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

    private String[] allColumns = {KEY,TITLE,CATEGORY,SHARE_WITH,TASK_PREREQUISITE,DESCRIPTION};

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

    public void createTask (Task task){
        ContentValues values = new ContentValues();
        values.put(TITLE, task.getTitle());
        values.put(CATEGORY, task.getCategory());
        values.put(SHARE_WITH,task.getShareWith());
        values.put(TASK_PREREQUISITE,task.getTaskPrerequisite());
        values.put(DESCRIPTION,task.getDescription());
        long insertId = mDb.insert(TASK_TABLE_NAME,null,values);
    }

    public void deleteTask (Task task){
        String[] selectionArgs = { task.getTitle()};
        this.mDb.delete(TASK_TABLE_NAME, TITLE + " = ?",selectionArgs);
    }

    public ArrayList<Task> getAllTasks(){
        ArrayList<Task> tasks = new ArrayList<Task>();
        Cursor cursor = mDb.query(TASK_TABLE_NAME,allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Task task = new Task(cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5));
            task.setId(cursor.getInt(0));
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }
}
