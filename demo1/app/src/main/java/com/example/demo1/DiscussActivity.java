package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.demo1.adapter.DiscussionAdapter;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Discussion;
import com.example.demo1.listener.UIHttpResponseListListener;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DiscussActivity extends AppCompatActivity {

    private final List<Discussion> discussionList = new ArrayList<>();
    private static final int UPDATE_LIST = 1;
    private Course course;
    private UIHttpResponseListListener uiHttpResponseListListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        course = (Course)getIntent().getSerializableExtra("course");
        uiHttpResponseListListener = new UIHttpResponseListListener() {
            @Override
            public void onUIHttpResponseList() {
                ArrayAdapter<Discussion> adapter = new DiscussionAdapter(DiscussActivity.this, R.layout.discussion_item, discussionList);
                ListView listView = (ListView) findViewById(R.id.list_discuss);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(DiscussActivity.this, CheckDiscussActivity.class);
                        Discussion discussion = discussionList.get(position);
                        intent.putExtra("discussion", discussion);
                        startActivity(intent);
                    }
                });
            }
        };
        initDiscussionList();
    }
    @Override
    protected void onResume() {
        super.onResume();
        initDiscussionList();
    }

    private void initDiscussionList() {
        String courseId = course.getId();
        String url = "http://10.0.2.2:8081/mobile/discussion/querydiscussion/" + courseId;
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    discussionList.clear();
                    JSONArray jsonArray = new JSONArray(data);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Discussion discussion = JSONUtil.JSONParseDiscussion(object);
                        discussion.save();
                       discussionList.add(discussion);
                    }
                    uiHttpResponseListListener.onHttpResponseList();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filemanage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_item:
                Intent intent = new Intent(DiscussActivity.this, AddDiscussionActivity.class);
                intent.putExtra("course", course);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}
