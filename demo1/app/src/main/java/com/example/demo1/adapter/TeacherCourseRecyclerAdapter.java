package com.example.demo1.adapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.demo1.AnalysisActivity;
import com.example.demo1.DiscussionActivity;
import com.example.demo1.FileActivity;
import com.example.demo1.R;
import com.example.demo1.TeacherHomeworkActivity;
import com.example.demo1.TestActivity;
import com.example.demo1.domain.Course;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.TimeUtil;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TeacherCourseRecyclerAdapter extends RecyclerView.Adapter<TeacherCourseRecyclerAdapter.ViewHolder> {
    private List<Course> mTeacherCourseList = new ArrayList<>();
    private IOnDeleteListener onDeleteListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameText, codeText, teacherNameText, destinationText, realVolText, startDateText, endDateText;
        ImageView analysisImage, testImage, homeworkImage, discussionImage, fileImage, deleteImage;

        public ViewHolder(View view) {
            super(view);
            nameText = (TextView)view.findViewById(R.id.teacher_course_recycler_item_name);
            codeText = (TextView)view.findViewById(R.id.teacher_course_recycler_item_code);
            teacherNameText = (TextView)view.findViewById(R.id.teacher_course_recycler_item_teacher_name);
            destinationText = (TextView)view.findViewById(R.id.teacher_course_recycler_item_destination);
            realVolText = (TextView)view.findViewById(R.id.teacher_course_recycler_item_real_vol);
            startDateText = (TextView)view.findViewById(R.id.teacher_course_recycler_item_start_date);
            analysisImage = (ImageView)view.findViewById(R.id.teacher_course_recycler_item_analysis);
            endDateText = (TextView)view.findViewById(R.id.teacher_course_recycler_item_end_date);
            testImage = (ImageView)view.findViewById(R.id.teacher_course_recycler_item_test);
            homeworkImage = (ImageView)view.findViewById(R.id.teacher_course_recycler_item_homework);
            discussionImage = (ImageView)view.findViewById(R.id.teacher_course_recycler_item_discussion);
            fileImage = (ImageView)view.findViewById(R.id.teacher_course_recycler_item_file);
            deleteImage = (ImageView)view.findViewById(R.id.teacher_course_recycler_item_delete);
        }
    }

    public TeacherCourseRecyclerAdapter(List<Course> courseList) {
        mTeacherCourseList = courseList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.teacher_course_recycler_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Course course = mTeacherCourseList.get(position);
        holder.nameText.setText(course.getName());
        holder.codeText.setText(course.getCode());
        holder.teacherNameText.setText(course.getTeacherName());
        holder.destinationText.setText(course.getDestination());
        holder.realVolText.setText(Integer.toString(course.getRealVol()));
        holder.startDateText.setText(TimeUtil.parseTime(course.getStartTime()));
        holder.endDateText.setText(TimeUtil.parseTime(course.getEndTime()));
        holder.analysisImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String courseId = course.getCourseId();
                String url = "http://10.0.2.2:8081/mobile/test/teacher/score/" + courseId;
                HttpUtil.sendHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        System.out.println(data);
                        try {
                            ArrayList<String> scores = new ArrayList<>();
                            JSONArray jsonArray = new JSONArray(data);
                            for(int i=0; i < jsonArray.length(); i++) {
                                String temp = jsonArray.getString(i);
                                scores.add(temp);
                            }
                            Intent intent = new Intent(holder.itemView.getContext(), AnalysisActivity.class);
                            intent.putExtra("course", course);
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("score", scores);
                            intent.putExtras(bundle);
                            holder.itemView.getContext().startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        holder.testImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TestActivity.class);
                intent.putExtra("course", course);
                v.getContext().startActivity(intent);
            }
        });
        holder.homeworkImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), TeacherHomeworkActivity.class);
                intent.putExtra("course", course);
                v.getContext().startActivity(intent);
            }
        });
        holder.discussionImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DiscussionActivity.class);
                intent.putExtra("course", course);
                v.getContext().startActivity(intent);
            }
        });
        holder.fileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), FileActivity.class);
                intent.putExtra("course", course);
                v.getContext().startActivity(intent);
            }
        });
        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDeleteListener != null) {
                    onDeleteListener.onDelete(course.getCourseId());
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return mTeacherCourseList.size();
    }

    public void setDeleteListener(IOnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    public interface IOnDeleteListener{
        void onDelete(final String courseId);
    }

}
