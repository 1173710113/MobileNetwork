package com.example.demo1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.demo1.adapter.TestRecyclerAdapter;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Score;
import com.example.demo1.domain.Test;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.MyNavView;
import com.example.demo1.util.ValidateUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.hjq.toast.ToastUtils;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TestActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private List<Test> testList = new ArrayList<>();
    private Map<Test, Integer> mMap = new HashMap<>();
    private RecyclerView recyclerView;
    private TestRecyclerAdapter adapter;
    private Course course;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_1);

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.custom_layout_1_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryTest();
            }
        });

        course = (Course) getIntent().getSerializableExtra("course");
        recyclerView = (RecyclerView) findViewById(R.id.custom_layout_1_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TestRecyclerAdapter(mMap, testList);
        recyclerView.setAdapter(adapter);

        //设置fba
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.custom_layout_1_fab);
        fab.setImageResource(R.drawable.ic_refresh_white_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryTest();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_layout_1_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.custom_layout_1_drawer);
        final NavigationView navView = (NavigationView) findViewById(R.id.custom_layout_1_nav);
        //MyNavView.initNavView(TestActivity.this, TestActivity.this, navView);
        //MyNavView.initNavView(AddTestActivity.this, AddTestActivity.this, navView);
        MyNavView myNavView = new MyNavView(TestActivity.this, TestActivity.this, navView).setNameChangeListener(new MyNavView.NameChangeListener() {
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
                String type = getSharedPreferences("userInfo", MODE_PRIVATE).getString("type", null);
                if(type != null && type.equals("教师")){
                    Intent intent = new Intent(TestActivity.this, AddTestActivity.class);
                    intent.putExtra("course", course);
                    startActivity(intent);
                }
        }
        return true;
    }

    private void queryTest() {
        String url = "http://10.0.2.2:8081/mobile/test/gettest/" + course.getCourseId();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.show("刷新失败");
                final List<Test> cache = DataSupport.where("courseId = ?", course.getCourseId()).find(Test.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        testList.clear();
                        testList.addAll(cache);
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                DataSupport.deleteAll(Test.class);
                final List<Test> list = JSONUtil.JSONParseTest(data);
                for (Test test : list) {
                    test.save();
                }
                String type = getSharedPreferences("userInfo", MODE_PRIVATE).getString("type",null);
                switch (type) {
                    case "学生":
                        String id = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", null);
                        if(id == null){
                            return;
                        }
                        for(Test test: list){
                            String url = "http://10.0.2.2:8081/mobile/test/queryscore/" + test.getTestId() + "/" + id;
                            HttpUtil.sendHttpRequest(url, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    swipeRefreshLayout.setRefreshing(false);
                                    ToastUtils.show("刷新失败");
                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                        if(response.code() == 200) {
                                            final String data = response.body().string();
                                            if(!ValidateUtil.isEmpty(data)){
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
                                                        Score score = JSONUtil.JSONParseScore(data);
                                                        DataSupport.deleteAll(Score.class, "scoreId = ?", score.getScoreId());
                                                        score.save();
                                                        adapter.notifyDataSetChanged();
                                                        swipeRefreshLayout.setRefreshing(false);
                                                    }
                                                });
                                            }
                                        }
                                }
                            });
                        }
                        break;
                }
                final Map<Test, Integer> map = new HashMap<>();
                for (int i = 0; i < list.size(); i++) {
                    String url = "http://10.0.2.2:8081/mobile/test/getteststudent/" + list.get(i).getTestId();
                    final int finalI = i;
                    HttpUtil.sendHttpRequest(url, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            swipeRefreshLayout.setRefreshing(false);
                            ToastUtils.show("刷新失败");
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            Integer count = Integer.parseInt(response.body().string());
                            map.put(list.get(finalI), count);
                        }
                    });
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        testList.clear();
                        mMap.clear();
                        testList.addAll(list);
                        mMap.putAll(map);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    public void onResume(){
        super.onResume();
        queryTest();
        mDrawerLayout.closeDrawers();
    }
}
