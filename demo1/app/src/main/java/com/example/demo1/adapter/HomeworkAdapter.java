package com.example.demo1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.demo1.R;
import com.example.demo1.domain.Homework;
import com.example.demo1.domain.Reply;

import java.util.List;

public class HomeworkAdapter extends ArrayAdapter<Homework> {
    private int resourceId;

    public HomeworkAdapter(Context context, int textViewResourceId, List<Homework> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Homework homework = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent,false);
        TextView homeworkTitle = (TextView)view.findViewById(R.id.homework_title);
        TextView homeworkPostTime = (TextView)view.findViewById(R.id.homework_post_time);
        TextView homeworkPosterId = (TextView)view.findViewById(R.id.homework_poster_id);
        TextView homeworkPosterName = (TextView)view.findViewById(R.id.homework_poster_name);
        homeworkTitle.setText(homework.getTitle());
        homeworkPostTime.setText(homework.getPostTime());
        homeworkPosterId.setText(homework.getPosterId());
        homeworkPosterName.setText(homework.getPosterName());
        return view;
    }

}
