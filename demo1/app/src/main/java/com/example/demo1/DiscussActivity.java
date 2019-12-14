package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
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
            data.add(new Discussion("120", "1173710113","滕文杰", "123456", "10.22", "1+1=2?", "4456", 15));
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
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}
