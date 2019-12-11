package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Xfermode;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.demo1.adapter.FileItemAdapter;
import com.example.demo1.domain.User;
import com.example.demo1.domain.XFile;

import java.util.ArrayList;
import java.util.List;

public class FileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        final List<XFile> data = new ArrayList<>();
        for(int i=0; i<10; i++){
            data.add(new XFile("计算机导论1.ppt", "计算机导论1.ppt", new User("1173710113", null, null, "滕文杰", null, null), "11.20", "45.0kb"));
        }
        FileItemAdapter adapter = new FileItemAdapter(
                FileActivity.this, R.layout.file_item, data
        );
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                XFile file = data.get(position);
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
                Intent intent = new Intent(FileActivity.this, UplodaFileActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }
}
