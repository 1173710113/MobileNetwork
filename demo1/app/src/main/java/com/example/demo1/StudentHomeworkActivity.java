package com.example.demo1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.demo1.adapter.HomeworkRecyclerAdapter;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Homework;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.MyNavView;
import com.example.demo1.util.ValidateUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.hjq.toast.ToastUtils;

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

public class StudentHomeworkActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private HomeworkRecyclerAdapter adapter;
    private List<Homework> homeworkList = new ArrayList<>();
    private Course course;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_1);

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.custom_layout_1_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryHomework();
            }
        });

        course = (Course) getIntent().getSerializableExtra("course");

        //设置fba
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.custom_layout_1_fab);
        fab.setImageResource(R.drawable.ic_refresh_white_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryHomework();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.custom_layout_1_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomeworkRecyclerAdapter(homeworkList);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_layout_1_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.custom_layout_1_drawer);
        final NavigationView navView = (NavigationView) findViewById(R.id.custom_layout_1_nav);
        //MyNavView.initNavView(StudentHomeworkActivity.this, StudentHomeworkActivity.this, navView);
        //MyNavView.initNavView(AddTestActivity.this, AddTestActivity.this, navView);
        MyNavView myNavView = new MyNavView(StudentHomeworkActivity.this, StudentHomeworkActivity.this, navView).setNameChangeListener(new MyNavView.NameChangeListener() {
            @Override
            public void onNameChange(final String name) {
                View view = navView.getHeaderView(0);
                final TextView nameText = view.findViewById(R.id.main_nav_header_name);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nameText.setText(name);
                    }
                });
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_18dp);
        }
        queryHomework();
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
        }
        return true;
    }

    private void queryHomework() {
        String url = "http://10.0.2.2:8081/mobile/homework/init/" + course.getId();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.show("刷新失败");
                final List<Homework> cache = DataSupport.where("courseId = ?", course.getId()).find(Homework.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        homeworkList.clear();
                        homeworkList.addAll(cache);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                if (ValidateUtil.isEmpty(data)) {
                    final List<Homework> cache = DataSupport.where("courseId = ?", course.getId()).find(Homework.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            homeworkList.clear();
                            homeworkList.addAll(cache);
                            adapter.notifyDataSetChanged();
                        }
                    });
                    return;
                }
                runOnUiThread(
                        new Runnable() {
                            @Override
                            public void run() {
                                List<Homework> list = new ArrayList<>();
                                try {
                                    DataSupport.deleteAll(Homework.class, "courseId = ?", course.getId());
                                    JSONArray jsonArray = new JSONArray(data);
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject object = jsonArray.getJSONObject(i);
                                        Homework homework = JSONUtil.JSONParseHomework(object);
                                        list.add(homework);
                                        homework.save();
                                    }
                                    homeworkList.clear();
                                    homeworkList.addAll(list);
                                    adapter.notifyDataSetChanged();
                                    swipeRefreshLayout.setRefreshing(false);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        });
    }

    public void onResume() {
        super.onResume();
        queryHomework();
        mDrawerLayout.closeDrawers();
    }
}
