package com.example.demo1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.demo1.adapter.TestRecyclerAdapter;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Test;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.MyNavView;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TeacherTestActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private List<Test> testList = new ArrayList<>();
    private Map<Test, Integer> mMap = new HashMap<>();
    private RecyclerView recyclerView;
    private TestRecyclerAdapter adapter;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_1);
        course = (Course)getIntent().getSerializableExtra("course");
        recyclerView = (RecyclerView) findViewById(R.id.custom_layout_1_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TestRecyclerAdapter(mMap, testList);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_layout_1_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.custom_layout_1_drawer);
        NavigationView navView = (NavigationView) findViewById(R.id.custom_layout_1_nav);
        MyNavView.initNavView(TeacherTestActivity.this, TeacherTestActivity.this, navView);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_18dp);
        }
        queryTest();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.main_toolbar_add:
                Intent intent = new Intent(TeacherTestActivity.this, AddTestActivity.class);
                intent.putExtra("course", course);
                startActivity(intent);
        }
        return true;
    }

    private void queryTest(){
        String url = "http://10.0.2.2:8081/mobile/test/gettest/" + course.getId();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Test> list = JSONUtil.JSONParseTest(data);
                        Map<Test, Integer> map = new HashMap<>();
                        for(int i = 0; i < list.size(); i++) {
                            map.put(list.get(i), 1);
                        }
                        testList.clear();
                        mMap.clear();
                        testList.addAll(list);
                        mMap.putAll(map);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
