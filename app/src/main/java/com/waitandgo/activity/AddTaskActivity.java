package com.waitandgo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mathieu.waitandgo.R;
import com.waitandgo.model.Task;
import com.waitandgo.model.TaskDAO;

/**
 * Created by Mathieu on 29/08/2016.
 */

public class AddTaskActivity extends AppCompatActivity {

    private TaskDAO taskDAO;

    @Override
    public void onCreate(Bundle savedInstanceState){
        //Creation of the view to add a task
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        android.support.v7.widget.Toolbar toolbar_task = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_task);
        setSupportActionBar(toolbar_task);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Put in black the progress bars
        ProgressBar progressBar7 = (ProgressBar) findViewById(R.id.progressBar7);
        progressBar7.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        ProgressBar progressBar8 = (ProgressBar) findViewById(R.id.progressBar8);
        progressBar8.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);

        // Autocompletion for the category
        AutoCompleteTextView textViewCategory = (AutoCompleteTextView) findViewById(R.id.input_text_category);
        String[] category = getResources().getStringArray(R.array.category);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, category);
        textViewCategory.setAdapter(adapter);
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

        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        else if (id == R.id.nav_valid){
            // Save what the user has written
            EditText editTitle = (EditText) findViewById(R.id.input_text_title);
            String title = editTitle.getText().toString();
            EditText editCategory = (EditText) findViewById(R.id.input_text_category);
            String category = editCategory.getText().toString();
            Spinner spinnerShareWith = (Spinner) findViewById(R.id.spinner_compartir);
            String shareWith = spinnerShareWith.getSelectedItem().toString();
            Spinner spinnerPrerequisite = (Spinner) findViewById(R.id.spinner_prerrequisito);
            String prerequisite = spinnerPrerequisite.getSelectedItem().toString();
            EditText editDescription = (EditText) findViewById(R.id.input_text_description);
            String description = editDescription.getText().toString();

            // If the title is empty
            if (title.equals("")){
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, " entrar un titulo ", duration);
                toast.show();
            }
            else{
                taskDAO = new TaskDAO(this);
                taskDAO.open();
                Task task = new Task(title,category,shareWith,prerequisite,description,"no");
                taskDAO.createTask(task);
                Intent intent = new Intent(AddTaskActivity.this,MainActivity.class);
                startActivity(intent);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
