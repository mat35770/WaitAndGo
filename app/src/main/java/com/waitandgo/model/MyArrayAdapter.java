package com.waitandgo.model;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mathieu.waitandgo.R;

import java.util.ArrayList;

/**
 * Created by Mathieu on 07/09/2016.
 * Adapter to custom the display of the ListView
 */

public class MyArrayAdapter extends ArrayAdapter {
    private ArrayList<Task> tasks;

    public MyArrayAdapter(Context context, ArrayList<Task> tasks){
        super(context, R.layout.task_list, tasks);
        this.tasks = tasks;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater)
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.task_list, parent, false);
        TextView textViewTitle = (TextView) rowView.findViewById(R.id.labelTaskTitle);
        TextView textViewDescription = (TextView) rowView.findViewById(R.id.labelTaskDescription);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageTask);

        //Write the title and the description in the textViews
        textViewTitle.setText(tasks.get(position).getTitle());
        textViewDescription.setText(tasks.get(position).getDescription());

        //if the task is not shared, the icon changes
        if (tasks.get(position).getShareWith().equals("Nadie")){
            imageView.setImageResource(R.drawable.ic_person);
        }

        //Color change with the category
        switch (tasks.get(position).getCategory()){
            case "perso" :
                rowView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorGreenTaskAlpha));
                break;
            case "trabajo" :
                rowView.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.colorGreenTask2Alpha));
                break;
            }

        return rowView;
    }
}
