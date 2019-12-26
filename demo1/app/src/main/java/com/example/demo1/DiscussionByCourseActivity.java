package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.demo1.adapter.DiscussionRecyclerAdapter;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Discussion;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.ValidateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DiscussionByCourseActivity extends AppCompatActivity {

    private List<Discussion> discussionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DiscussionRecyclerAdapter adapter;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_by_course);
        recyclerView = (RecyclerView)findViewById(R.id.discussion_by_course_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DiscussionRecyclerAdapter(discussionList);
        recyclerView.setAdapter(adapter);
    }

    private void queryDiscussion() {
        String courseId = course.getId();
        String url = "http://10.0.2.2:8081/mobile/discussion/querydiscussion/" + courseId;
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                if (ValidateUtil.isEmpty(data))
                {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Discussion> list = new ArrayList<>();
                        try {
                            DataSupport.deleteAll(Discussion.class, "courseId = ?", course.getId());
                            JSONArray jsonArray = new JSONArray(data);
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Discussion discussion = JSONUtil.JSONParseDiscussion(object);
                                discussion.save();
                                list.add(discussion);
                            }
                            discussionList.clear();
                            discussionList.addAll(list);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
