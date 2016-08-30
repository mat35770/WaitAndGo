package com.waitandgo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Mathieu on 30/08/2016.
 */

public abstract class DAOBase {
    protected final static int VERSION = 1;
    protected final static String NOM = "database.db";
    protected SQLiteDatabase mDb = null;
    protected DatabaseHandler mHandler = null;

    public DAOBase(Context pContext) {
        this.mHandler = new DatabaseHandler(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open() {
        mDb = mHandler.getWritableDatabase();
        return mDb;
    }

    public void close() {
        mDb.close();
    }

    public SQLiteDatabase getDb() {
        return mDb;
    }

}

