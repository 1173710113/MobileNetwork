package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.demo1.domain.Course;
import com.example.demo1.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class ClassActivity extends AppCompatActivity {
    private String[] data={"文件管理", "课堂测试", "作业管理", "抽签提问", "课堂点名",
            "数据统计", "答疑讨论", "用户管理"};
    private Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        Intent intent = getIntent();
        String extraString = intent.getStringExtra("course");
        try {
            this.course = JSONUtil.JSONParseCourse(new JSONObject(extraString));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ClassActivity.this , android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView) findViewById(R.id.list_function);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                switch (position){
                    case 0:
                        intent = new Intent(ClassActivity.this, FileActivity.class);
                        break;
                    case 2:
                        intent = new Intent(ClassActivity.this, HomeWorkActivity.class);
                        break;
                    case 3:
                        intent = new Intent(ClassActivity.this, DrawActivity.class);
                        break;
                    case 4:
                        intent = new Intent(ClassActivity.this, CallActivity.class);
                        break;
                    case 5:
                        intent = new Intent(ClassActivity.this, AnalysisActivity.class);
                        break;
                    case 6:
                        intent = new Intent(ClassActivity.this, DiscussActivity.class);
                        break;
                    case 7:
                        intent = new Intent(ClassActivity.this, UserManageActivity.class);
                        break;
                }
                if(intent != null) {
                    intent.putExtra("course", course);
                    startActivity(intent);
                }
            }
        });
    }
}
