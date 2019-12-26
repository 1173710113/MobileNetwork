package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.demo1.adapter.HomeworkRecyclerAdapter;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Homework;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.ValidateUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class StudentHomeworkActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private HomeworkRecyclerAdapter adapter;
    private List<Homework> homeworkList = new ArrayList<>();
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_homework);

        course = (Course)getIntent().getSerializableExtra("course");

        recyclerView = (RecyclerView)findViewById(R.id.student_homework_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomeworkRecyclerAdapter(homeworkList);
        recyclerView.setAdapter(adapter);

        queryHomework();
    }

    private void queryHomework(){
        String url = "http://10.0.2.2:8081/mobile/homework/init/" + course.getId();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                if(ValidateUtil.isEmpty(data)) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Homework> list = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(data);
                            for(int i=0; i <jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Homework homework = JSONUtil.JSONParseHomework(object);
                                list.add(homework);
                            }
                            homeworkList.clear();
                            homeworkList.addAll(list);
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
