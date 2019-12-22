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

import com.example.demo1.adapter.HomeworkAdapter;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Homework;

import java.util.ArrayList;
import java.util.List;

public class HomeWorkActivity extends AppCompatActivity {
    List<Homework> homeworkList = new ArrayList<>();
    Course course;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        course = (Course)getIntent().getSerializableExtra("course");
        HomeworkAdapter adapter = new HomeworkAdapter(
                HomeWorkActivity.this, R.layout.homework_item, homeworkList
        );
        ListView listView = (ListView) findViewById(R.id.list_homework);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(HomeWorkActivity.this, HomeworkDetailActivity.class);
                startActivity(intent);
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
        switch(item.getItemId()) {
            case R.id.add_item:
                Intent intent = new Intent(HomeWorkActivity.this, AddHomeworkActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}
