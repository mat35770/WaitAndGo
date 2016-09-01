package com.waitandgo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mathieu.waitandgo.R;

/**
 * Created by Mathieu on 29/08/2016.
 */

public class AddTaskActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        //Creation of the view to add a task
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_back) {
            Intent intent = new Intent(AddTaskActivity.this,MainActivity.class);
            startActivity(intent);
            return true;
        }
        else if (id == R.id.nav_valid){


            Intent intent = new Intent(AddTaskActivity.this,MainActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
