package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.demo1.adapter.CourseRecyclerAdapter;
import com.example.demo1.domain.Course;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.ValidateUtil;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class StudentMainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private List<Course> courseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CourseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main_drawer);

        recyclerView = (RecyclerView)findViewById(R.id.student_main_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CourseRecyclerAdapter(courseList);
        recyclerView.setAdapter(adapter);
        Toolbar toolbar = (Toolbar)findViewById(R.id.student_main_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.student_main_drawer);
        NavigationView navView = (NavigationView)findViewById(R.id.student_main_nav);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_18dp);
        }
        navView.setCheckedItem(R.id.student_main_nav_info);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                return true;
            }
        });
        queryCourse();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return true;
    }
    private void queryCourse(){
        String id = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", null);
        if(id == null) {
            return;
        }
        String url = "http://10.0.2.2:8081/mobile/course/query/student/" + id;
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        courseList.clear();
                        courseList.addAll(DataSupport.findAll(Course.class));
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                if (ValidateUtil.isEmpty(data))
                {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<Course> list = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Course course = JSONUtil.JSONParseCourse(object);
                                course.save();
                                list.add(course);
                            }
                            courseList.clear();
                            DataSupport.deleteAll(Course.class);
                            courseList.addAll(list);
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
