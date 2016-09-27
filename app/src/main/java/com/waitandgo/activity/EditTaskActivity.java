package com.waitandgo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mathieu.waitandgo.R;
import com.waitandgo.model.Task;
import com.waitandgo.model.TaskDAO;

/**
 * Created by aldo on 07-09-16.
 */
public class EditTaskActivity extends AppCompatActivity {

    TaskDAO taskDAO;

    // Get data from MainActivity when i press a task


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_edit_task);

        Bundle data = getIntent().getExtras();

        /*
        TextView textView = (TextView) findViewById(R.id.random_text);
        textView.setText(data.getString("TaskId"));
        */

        // Set title name to EditText label in content_main_edit_task.xml
        // The corresponding value is from task I pressed
        EditText title = (EditText) findViewById(R.id.input_text_title);
        title.setText(data.getString("TaskTitle"));

        // Set category name to EditText label in content_main_edit_task.xml
        // The corresponding value is from task I pressed
        EditText category = (EditText) findViewById(R.id.input_text_category);
        category.setText(data.getString("TaskCategory"));


        // Set ShareWith value to first spinner in content_main_edit_task.xml
        // The corresponding value is from task I pressed
        String shareWithItem = data.getString("TaskShareWith");
        Spinner shareWithSpinner = (Spinner) findViewById(R.id.spinner_compartir);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.shareWith, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        shareWithSpinner.setAdapter(adapter);
        if (!shareWithItem.equals(null)) {
            int spinnerPosition = adapter.getPosition(shareWithItem);
            shareWithSpinner.setSelection(spinnerPosition);
        }

        // Set ShareWith value to second spinner in content_main_edit_task.xml
        // The corresponding value is from task I pressed
        String prerequisiteItem = data.getString("TaskPrerequisite");
        Spinner prerequisiteSpinner = (Spinner) findViewById(R.id.spinner_prerrequisito);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.prerequisite, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prerequisiteSpinner.setAdapter(adapter2);
        if (!prerequisiteItem.equals(null)) {
            int spinnerPosition = adapter2.getPosition(prerequisiteItem);
            prerequisiteSpinner.setSelection(spinnerPosition);
        }

        // Set description value to EditText label in content_main_edit_task.xml
        // The corresponding value is from task I pressed
        EditText description = (EditText) findViewById(R.id.input_text_description);
        description.setText(data.getString("TaskDescription"));

        android.support.v7.widget.Toolbar toolbar_task = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_task);
        setSupportActionBar(toolbar_task);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Put in black the progress bars
        ProgressBar progressBar7 = (ProgressBar) findViewById(R.id.progressBar7);
        progressBar7.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);
        ProgressBar progressBar8 = (ProgressBar) findViewById(R.id.progressBar8);
        progressBar8.getProgressDrawable().setColorFilter(Color.BLACK, PorterDuff.Mode.SRC_IN);



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

                // Delete data and oldTitle when we solve id problem
                Bundle data = getIntent().getExtras();
                String oldTitle = data.getString("TaskTitle");
                taskDAO.updateTask(task, oldTitle); /* Remember take out second argument from
                                                       updateTask method when we solve id problem */
                Intent intent = new Intent(EditTaskActivity.this,MainActivity.class);
                startActivity(intent);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
