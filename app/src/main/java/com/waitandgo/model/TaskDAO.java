package com.waitandgo.model;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;
import java.util.HashMap;

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
    public static final String UPDATE_STATUS = "updateStatus";
    public static final String KEY_EXTERN_DB = "idExternDB";


    public static final String TASK_TABLE_CREATE = "CREATE TABLE " + TASK_TABLE_NAME + "(" + KEY +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + TITLE + " TEXT, " + CATEGORY + " TEXT, " +
            SHARE_WITH + " TEXT, " + TASK_PREREQUISITE + " TEXT, " + DESCRIPTION + " TEXT, " +
            UPDATE_STATUS + " TEXT, " + KEY_EXTERN_DB + " INTEGER );";

    public static final String TASK_TABLE_DROP = "DROP TABLE IF EXISTS " + TASK_TABLE_NAME + ";";

    private String[] allColumns = {KEY,TITLE,CATEGORY,SHARE_WITH,TASK_PREREQUISITE,DESCRIPTION,
            UPDATE_STATUS,KEY_EXTERN_DB};

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
        values.put(UPDATE_STATUS,task.getUpdateStatus());
        values.put(KEY_EXTERN_DB,task.getId());
        long insertId = mDb.insert(TASK_TABLE_NAME,null,values);
        task.setId(insertId);
    }


    public void updateTask (Task task, String oldTitle) {
        ContentValues values = new ContentValues();
        values.put(TITLE, task.getTitle());
        values.put(CATEGORY, task.getCategory());
        values.put(SHARE_WITH,task.getShareWith());
        values.put(TASK_PREREQUISITE,task.getTaskPrerequisite());
        values.put(DESCRIPTION,task.getDescription());
        String[] whereArgs = new String[] {String.valueOf(oldTitle)};
        /* Me must use KEY instead TITLE for sql update query. In same way, do to
           deleteTask method
         */
        mDb.update(TASK_TABLE_NAME, values, TITLE + "=?", whereArgs);
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
                    cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
            task.setId(cursor.getInt(0));
            task.setIdExternDB(cursor.getLong(7));
            tasks.add(task);
            cursor.moveToNext();
        }
        cursor.close();
        return tasks;
    }


    /**
     * Compose JSON out of SQLite records
     *
     */
    public String composeTaskJSONfromSQLite(String mail, String name){
        ArrayList<HashMap<String, String>> wordList = new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM " +TASK_TABLE_NAME+" WHERE "+UPDATE_STATUS+" = '"+"no"+"'";
        this.open();
        //mDb = dBHelper.getWritableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("mail", mail);
                map.put("name", name);
                map.put(KEY, cursor.getString(0));
                map.put(TITLE, cursor.getString(1));
                if (cursor.getString(2) != null){
                    map.put(CATEGORY, cursor.getString(2));
                } else{
                    map.put(CATEGORY, "null");
                } if (cursor.getString(5) != null) {
                    map.put(DESCRIPTION, cursor.getString(5));
                } else{
                    map.put(DESCRIPTION, "null");
                }
                wordList.add(map);
            } while (cursor.moveToNext());
        }
        //this.close();
        cursor.close();
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }

    public String composeMailJSON(String mail){
        ArrayList<HashMap<String, String>> wordList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("mail", mail);
        wordList.add(map);
        Gson gson = new GsonBuilder().create();
        //Use GSON to serialize Array List to JSON
        return gson.toJson(wordList);
    }

    /**
     * Get Sync status of SQLite
     * @return
     */
    public String getSyncStatus(){
        String msg = null;
        if(this.dbSyncCount() == 0){
            msg = "SQLite and Remote MySQL DBs are in Sync!";
        }else{
            msg = "DB Sync needed\n";
        }
        return msg;
    }

    /**
     * Get SQLite records that are yet to be Synced
     * @return
     */
    public int dbSyncCount(){
        int count = 0;
        String selectQuery = "SELECT  * FROM "+TASK_TABLE_NAME+ " where " +UPDATE_STATUS+" = '"+"no"+"'";
        this.open();
        //SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = mDb.rawQuery(selectQuery, null);
        count = cursor.getCount();
        //this.close();
        return count;
    }

    /**
     * Update Sync status against each User ID
     * @param id
     * @param status
     */
    public void updateSyncStatus(String id, String status, String idExternDB){
        //SQLiteDatabase database = this.getWritableDatabase();
        this.open();
        String updateQuery = "Update " + TASK_TABLE_NAME+" set " +UPDATE_STATUS+" = '"+ status +"' where "+KEY+"="+"'"+ id +"'";
        String updateQuery2 = "Update " + TASK_TABLE_NAME+" set " +KEY_EXTERN_DB+" = '"+ idExternDB +"' where "+KEY+"="+"'"+ id +"'";
        Log.d("query update status : ",updateQuery);
        Log.d("query update id_ext : ",updateQuery2);
        mDb.execSQL(updateQuery);
        mDb.execSQL(updateQuery2);
        //this.close();
    }

}
