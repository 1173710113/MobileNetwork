package com.example.demo1.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.R;
import com.example.demo1.domain.Course;

import java.util.ArrayList;
import java.util.List;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> {
    private List<Course> mCourseList = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameText, teacherNameText, destinationText, realVolText, startDateText, endDateText;

        public ViewHolder(View view) {
            super(view);
            nameText = (TextView)view.findViewById(R.id.course_recycler_item_name);
            teacherNameText = (TextView)view.findViewById(R.id.course_recycler_item_teacher_name);
            destinationText = (TextView)view.findViewById(R.id.course_recycler_item_destination);
            realVolText = (TextView)view.findViewById(R.id.course_recycler_item_real_vol);
            startDateText = (TextView)view.findViewById(R.id.course_recycler_item_start_date);
            endDateText = (TextView)view.findViewById(R.id.course_recycler_item_end_date);
        }
    }

    public CourseRecyclerAdapter(List<Course> courseList) {
        mCourseList = courseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Course course = mCourseList.get(position);
        holder.nameText.setText(course.getName());
        holder.teacherNameText.setText(course.getTeacherName());
        holder.destinationText.setText(course.getDestination());
        holder.realVolText.setText(Integer.toString(course.getRealVol()));
        holder.startDateText.setText(course.getStartTime());
        holder.endDateText.setText(course.getEndTime());
    }

    @Override
    public int getItemCount(){
        return mCourseList.size();
    }
}
