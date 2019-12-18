package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demo1.adapter.CourseAdapter;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.User;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private List<Course> courseList = new ArrayList<>();
    private User user;
    private static final int UPDATE_LIST = 1;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what) {
                case UPDATE_LIST:
                    ArrayAdapter<Course> adapter = new CourseAdapter(MainActivity.this, R.layout.course_item, courseList);
                    ListView listView = (ListView) findViewById(R.id.list_course);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(MainActivity.this, ClassActivity.class);
                            Course course = courseList.get(position);
                            intent.putExtra("course", course.toString());
                            startActivity(intent);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LitePal.getDatabase();
        initUser();
        ((TextView)this.findViewById(R.id.main_activity_user_id)).setText(user.getId());
        ((TextView)this.findViewById(R.id.main_activity_user_name)).setText(user.getName());
        initCourseList();
    }

    private void initUser() {
        SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
        String id = pref.getString("id","ERROR");
        String type = pref.getString("type", "ERROR");
        String name = pref.getString("name", "ERROR");
        String sex = pref.getString("sex","ERROR");
        this.user = new User(id, "", type, name, sex, "");
    }

    private void initCourseList(){
        String type = user.getType();
        String url = null;
        if(type.equals("学生")){
            url="http://10.0.2.2:8081/mobile/course/query/student/" + user.getId();
        } else if(type.equals("教师")) {
            url = "http://10.0.2.2:8081/mobile/course/query/teacher/" + user.getId();
        }
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200) {
                    courseList.clear();
                    String data = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(data);
                        for(int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Course course = JSONUtil.JSONParseCourse(object);
                            course.save();
                            courseList.add(course);
                        }
                        Message message = new Message();
                        message.what = UPDATE_LIST;
                        handler.sendMessage(message);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
