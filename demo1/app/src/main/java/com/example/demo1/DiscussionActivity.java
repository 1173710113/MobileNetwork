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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.demo1.adapter.DiscussionRecyclerAdapter;
import com.example.demo1.dialog.AddDiscussionDialog;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Discussion;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.MyNavView;
import com.example.demo1.util.TimeUtil;
import com.example.demo1.util.ValidateUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.hjq.toast.ToastUtils;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DiscussionActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private List<Discussion> discussionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DiscussionRecyclerAdapter adapter;
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
                queryDiscussion();
            }
        });

        course = (Course) getIntent().getSerializableExtra("course");

        recyclerView = (RecyclerView) findViewById(R.id.custom_layout_1_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DiscussionRecyclerAdapter(discussionList);
        recyclerView.setAdapter(adapter);

        //设置fba
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.custom_layout_1_fab);
        fab.setImageResource(R.drawable.ic_refresh_white_24dp);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryDiscussion();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_layout_1_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.custom_layout_1_drawer);
        final NavigationView navView = (NavigationView) findViewById(R.id.custom_layout_1_nav);
       // MyNavView.initNavView(DiscussionActivity.this, DiscussionActivity.this, navView);
        //MyNavView.initNavView(AddTestActivity.this, AddTestActivity.this, navView);
        MyNavView myNavView = new MyNavView(DiscussionActivity.this, DiscussionActivity.this, navView).setNameChangeListener(new MyNavView.NameChangeListener() {
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
        queryDiscussion();
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
                if(course == null) {
                    return true;
                }
                AddDiscussionDialog dialog = new AddDiscussionDialog(DiscussionActivity.this);
                dialog.setCancelListener(new AddDiscussionDialog.IOnCancelListener() {
                    @Override
                    public void onCancel(AddDiscussionDialog dialog) {
                        dialog.dismiss();
                    }
                }).setConfirmListener(new AddDiscussionDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm(final AddDiscussionDialog dialog) {
                        String title = dialog.getTitle();
                        String content = dialog.getContent();
                        if (ValidateUtil.isEmpty(title)) {
                            ToastUtils.show("标题不能为空");
                            return;
                        }
                        String posterId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", "ERROR");
                        String posterName = getSharedPreferences("userInfo", MODE_PRIVATE).getString("name", "ERROR");
                        String courseId = course.getCourseId();
                        String time = TimeUtil.getTime();
                        Discussion discussion = new Discussion(null, posterId, posterName, courseId, new Date(time), title, content, 0);
                        JSONObject object = JSONUtil.DiscussionParseJSON(discussion);
                        HttpUtil.sendHttpRequest("http://10.0.2.2:8081/mobile/discussion/add", object, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                ToastUtils.show("发送失败");
                                dialog.dismiss();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                dialog.dismiss();
                                ToastUtils.show("添加成功");
                                queryDiscussion();
                            }
                        });
                    }
                }).show();
                break;
        }
        return true;
    }

    public void onResume() {
        super.onResume();
        mDrawerLayout.closeDrawers();
        queryDiscussion();
    }

    private void queryDiscussion() {

        if (course != null) {
            final String courseId = course.getCourseId();
            String url = "http://10.0.2.2:8081/mobile/discussion/querydiscussion/" + courseId;
            HttpUtil.sendHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    swipeRefreshLayout.setRefreshing(false);
                    ToastUtils.show("刷新失败");
                    final List<Discussion> cache = DataSupport.where("courseId = ?", courseId).find(Discussion.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            discussionList.clear();
                            discussionList.addAll(cache);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String data = response.body().string();
                    if (ValidateUtil.isEmpty(data)) {
                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<Discussion> list = new ArrayList<>();
                                DataSupport.deleteAll(Discussion.class, "courseId = ?", course.getCourseId());
                                JSONArray jsonArray = JSONArray.parseArray(data);
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Discussion discussion = JSONUtil.JSONParseDiscussion(object);
                                    discussion.save();
                                    list.add(discussion);
                                }
                                discussionList.clear();
                                discussionList.addAll(list);
                                adapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            });
        } else {
            final String id =getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", null);
            String url = "http://10.0.2.2:8081/mobile/discussion/querydiscussionbyposter/" + id;
            HttpUtil.sendHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    swipeRefreshLayout.setRefreshing(false);
                    ToastUtils.show("刷新失败");
                    final List<Discussion> cache = DataSupport.where("courseId = ?", course.getCourseId()).find(Discussion.class);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            discussionList.clear();
                            discussionList.addAll(cache);
                            adapter.notifyDataSetChanged();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String data = response.body().string();
                    if (ValidateUtil.isEmpty(data)) {
                        return;
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            List<Discussion> list = new ArrayList<>();

                                DataSupport.deleteAll(Discussion.class, "posterId = ?", id);
                                JSONArray jsonArray = JSONArray.parseArray(data);
                                for (int i = 0; i < jsonArray.size(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    Discussion discussion = JSONUtil.JSONParseDiscussion(object);
                                    discussion.save();
                                    list.add(discussion);
                                }
                                discussionList.clear();
                                discussionList.addAll(list);
                                adapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);

                        }
                    });
                }
            });
        }
    }
}
