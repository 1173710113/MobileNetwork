package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ClassActivity extends AppCompatActivity {
    private String[] data={"文件管理", "课堂测试", "作业管理", "抽签提问", "课堂点名",
            "数据统计", "答疑讨论", "用户管理"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ClassActivity.this , android.R.layout.simple_list_item_1, data);
        ListView listView = (ListView) findViewById(R.id.list_function);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position){
                    case 0:
                        intent = new Intent(ClassActivity.this, FileActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(ClassActivity.this, HomeWorkActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(ClassActivity.this, DrawActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(ClassActivity.this, CallActivity.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(ClassActivity.this, AnalysisActivity.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(ClassActivity.this, DiscussActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(ClassActivity.this, UserManageActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });
    }
}
