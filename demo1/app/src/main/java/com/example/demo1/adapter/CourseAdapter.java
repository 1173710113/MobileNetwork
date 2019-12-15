package com.example.demo1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.demo1.R;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.CourseClass;

import java.util.List;

public class CourseAdapter extends ArrayAdapter<Course> {
    private int resourceId;

    public CourseAdapter(Context context, int textViewResourceId, List<Course> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Course course = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView courseName = (TextView)view.findViewById(R.id.course_name);
        TextView courseTeacher = (TextView)view.findViewById(R.id.course_teacher);
        TextView coursePeople = (TextView)view.findViewById(R.id.course_people);
        courseName.setText(course.getName());
        courseTeacher.setText(course.getTeacherName());
        coursePeople.setText(Integer.toString(course.getRealVol()) + "人");
        return view;
    }
}
