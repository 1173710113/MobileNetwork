package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class UserManageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_manage);
        List<String> data = new ArrayList<String>();
        for(int i=0; i<10; i++) {
            data.add("用户" + (i+1));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                UserManageActivity.this, android.R.layout.simple_list_item_1, data
        );
        ListView listView = (ListView) findViewById(R.id.list_user);
        listView.setAdapter(adapter);
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
                Intent intent = new Intent(UserManageActivity.this, AddUserActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}
