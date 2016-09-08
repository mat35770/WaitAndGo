package com.waitandgo.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mathieu.waitandgo.R;

/**
 * Created by aldo on 07-09-16.
 */
public class EditTaskActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_edit_task);

        // Get data from MainActivity when i press a task
        Bundle data = getIntent().getExtras();

        TextView textView = (TextView) findViewById(R.id.random_text);
        textView.setText(data.getString("TaskTitle"));

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

    }
}
