package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FileActivity extends AppCompatActivity {

    private String[] data={"hkda.ppt", "hkjdka.ppt"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                FileActivity.this, android.R.layout.simple_list_item_1, data
        );
        ListView listView = (ListView) findViewById(R.id.list_view);
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
                Intent intent = new Intent(FileActivity.this, UplodaFileActivity.class);
                startActivity(intent);
                break;
                default:
                    break;
        }
        return true;
    }
}
