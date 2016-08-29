package com.example.mathieu.waitandgo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

/**
 * Created by Mathieu on 29/08/2016.
 */

public class AddTaskActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        android.support.v7.widget.Toolbar toolbar_task = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_task);
        setSupportActionBar(toolbar_task);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_add_task_drawer, menu);
        return true;
    }
}
