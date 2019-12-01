package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CourseManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_manage);
        List<String> data = new ArrayList<String>();
        for(int i=0; i<10; i++) {
            data.add("用户" + (i+1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                CourseManageActivity.this, android.R.layout.simple_list_item_1, data
        );
        ListView listView = (ListView) findViewById(R.id.list_course);
        listView.setAdapter(adapter);
    }
}
