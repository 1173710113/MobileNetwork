package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo1.domain.Course;
import com.example.demo1.domain.Discussion;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.TimeUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddDiscussionActivity extends AppCompatActivity implements View.OnClickListener{
    private Course course;
    public static final int SUCCESS = 1;
    private Handler handler = new Handler() {
      public void handleMessage(Message msg) {
          switch (msg.what){
              case SUCCESS:
                  Toast.makeText(AddDiscussionActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                  finish();
                  break;
               default:
                   break;
          }
      }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussion);
        Intent intent = getIntent();
        String extraString = intent.getStringExtra("course");
        try {
            this.course = JSONUtil.JSONParseCourse(new JSONObject(extraString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Button addButton = (Button)this.findViewById(R.id.add_discussion_button);
        addButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        String title = ((TextView)findViewById(R.id.add_discussion_title)).getText().toString();
        String content = ((TextView)findViewById(R.id.add_discussion_content)).getText().toString();
        SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
        String posterId = pref.getString("id", "ERROR");
        String posterName = pref.getString("name", "ERROR");
        String courseId = course.getId();
        String time = TimeUtil.getTime();
        Discussion discussion = new Discussion(null, posterId, posterName, courseId, time, title, content, 0);
        JSONObject object = JSONUtil.DiscussionParseJSON(discussion);
        HttpUtil.sendHttpRequest("http://10.0.2.2:8081/mobile/discussion/add", object, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                        Message msg = new Message();
                        msg.what = SUCCESS;
                        handler.sendMessage(msg);
            }
        });
    }
}
