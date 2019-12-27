package com.example.demo1.adapter;

import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.DiscussionByCourseActivity;
import com.example.demo1.R;
import com.example.demo1.StudentHomeworkActivity;
import com.example.demo1.TeacherTestActivity;
import com.example.demo1.TestActivity;
import com.example.demo1.domain.Course;
import java.util.ArrayList;
import java.util.List;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> {
    private List<Course> mCourseList = new ArrayList<>();
    private IOnDeleteListener onDeleteListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameText, teacherNameText, destinationText, realVolText, startDateText, endDateText;
        ImageView testImage, homeworkImage, discussionImage, deleteImage;

        public ViewHolder(View view) {
            super(view);
            nameText = (TextView)view.findViewById(R.id.course_recycler_item_name);
            teacherNameText = (TextView)view.findViewById(R.id.course_recycler_item_teacher_name);
            destinationText = (TextView)view.findViewById(R.id.course_recycler_item_destination);
            realVolText = (TextView)view.findViewById(R.id.course_recycler_item_real_vol);
            startDateText = (TextView)view.findViewById(R.id.course_recycler_item_start_date);
            endDateText = (TextView)view.findViewById(R.id.course_recycler_item_end_date);
            testImage = (ImageView)view.findViewById(R.id.course_recycler_item_test);
            homeworkImage = (ImageView)view.findViewById(R.id.course_recycler_item_homework);
            discussionImage = (ImageView)view.findViewById(R.id.course_recycler_item_discussion);
            deleteImage = (ImageView)view.findViewById(R.id.course_recycler_item_delete);
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
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Course course = mCourseList.get(position);
        holder.nameText.setText(course.getName());
        holder.teacherNameText.setText(course.getTeacherName());
        holder.destinationText.setText(course.getDestination());
        holder.realVolText.setText(Integer.toString(course.getRealVol()));
        holder.startDateText.setText(course.getStartTime());
        holder.endDateText.setText(course.getEndTime());
        holder.testImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeacherTestActivity.class);
                intent.putExtra("course", course);
                v.getContext().startActivity(intent);
            }
        });
        holder.homeworkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), StudentHomeworkActivity.class);
                intent.putExtra("course", course);
                v.getContext().startActivity(intent);
            }
        });
        holder.discussionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DiscussionByCourseActivity.class);
                intent.putExtra("course", course);
                v.getContext().startActivity(intent);
            }
        });
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDeleteListener != null) {
                    onDeleteListener.onDelete(course.getId());
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return mCourseList.size();
    }

    public void setDeleteListener(IOnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface IOnDeleteListener{
        void onDelete(final String courseId);
    }

}
