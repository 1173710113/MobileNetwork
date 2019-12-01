package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class UplodaFileActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploda_file);
        final List<DataHolder> data = new ArrayList<DataHolder>();
        for (int i = 0; i < 10; i++) {
            data.add(new DataHolder("harvic的blog------" + i,false));}
        final FileListAdapter adapter = new FileListAdapter(UplodaFileActivity.this, data);
        ListView listView = (ListView)findViewById(R.id.list_file);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                data.get(position).checked = !data.get(position).checked;
                adapter.notifyDataSetChanged();
            }
        });
    }
}

