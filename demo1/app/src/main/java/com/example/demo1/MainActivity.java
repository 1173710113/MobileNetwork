package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.demo1.adapter.ClassAdapter;
import com.example.demo1.domain.CourseClass;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<CourseClass> data = new ArrayList<>();
        for(int i=0; i<10; i++) {
            data.add(new CourseClass("计算机导论","杨忠杰", 175 ));
        }
        ClassAdapter adapter = new ClassAdapter(
                MainActivity.this, R.layout.course_item, data
        );
        ListView listView = (ListView) findViewById(R.id.class_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ClassActivity.class);
                startActivity(intent);
            }
        });
    }
}
