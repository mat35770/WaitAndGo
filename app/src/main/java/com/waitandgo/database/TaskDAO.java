package com.waitandgo.database;


import android.content.Context;

import com.waitandgo.database.DAOBase;

/**
 * Created by Mathieu on 30/08/2016.
 */

public class TaskDAO extends DAOBase {
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

    public TaskDAO(Context pContext) {
        super(pContext);
    }
}
