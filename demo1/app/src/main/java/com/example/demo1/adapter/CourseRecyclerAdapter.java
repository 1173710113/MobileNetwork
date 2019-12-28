package com.example.demo1.adapter;

import android.content.Context;
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
import com.example.demo1.StudentHomeworkActivity;
import com.example.demo1.TestActivity;
import com.example.demo1.domain.Course;
import com.hjq.toast.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CourseRecyclerAdapter extends RecyclerView.Adapter<CourseRecyclerAdapter.ViewHolder> {
    private List<Course> mCourseList = new ArrayList<>();
    private IOnDeleteListener onDeleteListener;

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView nameText, teacherNameText, destinationText, realVolText, startDateText, endDateText;
        ImageView analysisImage, testImage, homeworkImage, discussionImage, fileImage, deleteImage;

        public ViewHolder(View view) {
            super(view);
            nameText = (TextView)view.findViewById(R.id.course_recycler_item_name);
            teacherNameText = (TextView)view.findViewById(R.id.course_recycler_item_teacher_name);
            destinationText = (TextView)view.findViewById(R.id.course_recycler_item_destination);
            realVolText = (TextView)view.findViewById(R.id.course_recycler_item_real_vol);
            startDateText = (TextView)view.findViewById(R.id.course_recycler_item_start_date);
            endDateText = (TextView)view.findViewById(R.id.course_recycler_item_end_date);
            analysisImage = (ImageView)view.findViewById(R.id.course_recycler_item_analysis);
            testImage = (ImageView)view.findViewById(R.id.course_recycler_item_test);
            homeworkImage = (ImageView)view.findViewById(R.id.course_recycler_item_homework);
            discussionImage = (ImageView)view.findViewById(R.id.course_recycler_item_discussion);
            fileImage = (ImageView)view.findViewById(R.id.course_recycler_item_file);
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
        holder.analysisImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String studentId = holder.itemView.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("id", null);
                if(studentId == null) {
                    ToastUtils.show("出错了");
                    return;
                }
                RequestBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("courseId", course.getId())
                        .addFormDataPart("studentId", studentId)
                        .build();
                String url = "http://10.0.2.2:8081/mobile/test/student/score";
                Request request = new Request.Builder().url(url).post(requestBody).build();
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(100, TimeUnit.MILLISECONDS)
                        .build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        try {
                            ArrayList<String> scores = new ArrayList<>();
                            JSONArray jsonArray = new JSONArray(data);
                            for(int i=0; i < jsonArray.length(); i++) {
                                String temp = jsonArray.getString(i);
                                scores.add(temp);
                            }
                            Intent intent = new Intent(v.getContext(), AnalysisActivity.class);
                            intent.putExtra("course", course);
                            Bundle bundle = new Bundle();
                            bundle.putStringArrayList("score", scores);
                            intent.putExtras(bundle);
                            v.getContext().startActivity(intent);
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
                Intent intent = new Intent(v.getContext(), StudentHomeworkActivity.class);
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
