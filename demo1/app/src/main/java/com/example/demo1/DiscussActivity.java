package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.demo1.adapter.DiscussionAdapter;
import com.example.demo1.domain.Discussion;
import com.example.demo1.domain.User;

import java.util.ArrayList;
import java.util.List;

public class DiscussActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        List<Discussion> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            data.add(new Discussion("120", new User("1173710113", "123", null, "滕文杰 ", null, null), "10.22", "1+1=2?", "4456", 15));
        }
        ArrayAdapter<Discussion> adapter = new DiscussionAdapter(DiscussActivity.this, R.layout.discussion_item, data);
        ListView listView = (ListView) findViewById(R.id.list_discuss);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DiscussActivity.this, CheckDiscussActivity.class);
                startActivity(intent);
            }
        });
    }
}
