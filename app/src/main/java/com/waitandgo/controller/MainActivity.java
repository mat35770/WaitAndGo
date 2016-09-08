package com.waitandgo.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mathieu.waitandgo.R;
import com.waitandgo.model.MyArrayAdapter;
import com.waitandgo.model.Task;
import com.waitandgo.model.TaskDAO;

import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener {

    private ListView listView;
    private ArrayList<Task> tasks;
    MyArrayAdapter adapter;
    TaskDAO taskDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddTaskActivity.class);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //display tasks stored in database and active listener
        taskDAO = new TaskDAO(this);
        taskDAO.open();
        tasks =  taskDAO.getAllTasks();
        adapter = new MyArrayAdapter(this,tasks);
        listView = (ListView) findViewById(R.id.listViewTask);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_list) {
            // Handle the camera action
        } else if (id == R.id.nav_group) {

        } else if (id == R.id.nav_group_add) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    /**
     * If an item is clicked it is deleted from the database
     * It is also removed from the ArrayList "tasks" and the view is refreshed
     */
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        for (Iterator<Task> it = tasks.iterator(); it.hasNext();){
            Task itg = (Task) it.next();
            if (itg == (Task) adapterView.getItemAtPosition(position)){
                TaskDAO taskDAO = new TaskDAO(this);
                // taskDAO.open();


                Intent intent = new Intent(MainActivity.this, EditTaskActivity.class);

                // Send task data values to EditTaskActivity
                intent.putExtra("TaskId", itg.getId());
                intent.putExtra("TaskTitle", itg.getTitle());
                intent.putExtra("TaskCategory", itg.getCategory());
                intent.putExtra("TaskShareWith", itg.getShareWith());
                intent.putExtra("TaskPrerequisite", itg.getTaskPrerequisite());
                intent.putExtra("TaskDescription", itg.getDescription());

                startActivity(intent);

                /*
                taskDAO.deleteTask(itg);
                tasks.remove(itg);
                */
                adapter.notifyDataSetChanged();
            }
        }
    }
}
