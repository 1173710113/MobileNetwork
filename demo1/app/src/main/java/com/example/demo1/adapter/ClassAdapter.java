package com.example.demo1.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.demo1.R;
import com.example.demo1.domain.CourseClass;

import java.util.List;

public class ClassAdapter extends ArrayAdapter<CourseClass> {
    private int resourceId;

    public ClassAdapter(Context context, int textViewResourceId, List<CourseClass> objects){
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        CourseClass courseClass = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        TextView courseName = (TextView)view.findViewById(R.id.course_name);
        TextView courseTeacher = (TextView)view.findViewById(R.id.course_teacher);
        TextView coursePeople = (TextView)view.findViewById(R.id.course_people);
        courseName.setText(courseClass.getCourseName());
        courseTeacher.setText(courseClass.getCourseTeacher());
        coursePeople.setText(Integer.toString(courseClass.getClassPeople()) + "人");
        return view;
    }
}
