package com.example.demo1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.demo1.adapter.HomeworkRecyclerAdapter;
import com.example.demo1.dialog.AddHomeworkDialog;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Homework;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.MyNavView;
import com.example.demo1.util.TimeUtil;
import com.example.demo1.util.ValidateUtil;
import com.google.android.material.navigation.NavigationView;
import com.hjq.toast.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TeacherHomeworkActivity extends BaseActivity {

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
        course = (Course) getIntent().getSerializableExtra("course");

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.custom_layout_1_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryHomework();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.custom_layout_1_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HomeworkRecyclerAdapter(homeworkList);
        recyclerView.setAdapter(adapter);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.custom_layout_1_drawer);
        final NavigationView navView = (NavigationView) findViewById(R.id.custom_layout_1_nav);
        //MyNavView.initNavView(TeacherHomeworkActivity.this, TeacherHomeworkActivity.this, navView);
        //MyNavView.initNavView(AddTestActivity.this, AddTestActivity.this, navView);
        MyNavView myNavView = new MyNavView(TeacherHomeworkActivity.this, TeacherHomeworkActivity.this, navView).setNameChangeListener(new MyNavView.NameChangeListener() {
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

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_layout_1_toolbar);
        setSupportActionBar(toolbar);
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
            case R.id.main_toolbar_add:
                //初始化dialog
                AddHomeworkDialog dialog = new AddHomeworkDialog(TeacherHomeworkActivity.this);
                dialog.setPickerListener(new AddHomeworkDialog.IOnPickerListener() { //设置点击时间时间
                    @Override
                    public void onPicker(final AddHomeworkDialog dialog) {
                        //设置TimePicker
                        TimePickerView pvTime = new TimePickerBuilder(TeacherHomeworkActivity.this, new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                dialog.setDate(date);
                            }
                        })
                                .setType(new boolean[]{true, true, true, true, true, true})
                                .setCancelText("取消")//取消按钮文字
                                .setSubmitText("确定")//确认按钮文字
                                .isDialog(true)
                                .build();
                        pvTime.show();
                    }
                }).setCancelListener(new AddHomeworkDialog.IOnCancelListener() { //设置取消事件
                    @Override
                    public void onCancel(AddHomeworkDialog dialog) {
                        dialog.dismiss();
                    }
                }).setConfirmListener(new AddHomeworkDialog.IOnConfirmListener() { //设置确定事件
                    @Override
                    public void onConfirm(final AddHomeworkDialog dialog) {
                        String posterId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", "ERROR");
                        String title = dialog.getTitle();
                        String content = dialog.getContent();
                        String deadline = TimeUtil.parseTime(dialog.getDate());
                        if(ValidateUtil.isEmpty(title) || ValidateUtil.isEmpty(content) || ValidateUtil.isEmpty(deadline)) {
                            ToastUtils.show("请填完");
                            return;
                        }
                        String postTime = TimeUtil.getTime();
                        String courseId = course.getId();
                        Homework homework = new Homework("", posterId, "", title, content, deadline, postTime, courseId);
                        JSONObject object = JSONUtil.HomeworkParseJSON(homework);
                        Log.e("Homework", object.toString());
                        String url = "http://10.0.2.2:8081/mobile/homework/add";
                        HttpUtil.sendHttpRequest(url, object, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                queryHomework();
                                ToastUtils.show("发布成功");
                                dialog.dismiss();
                            }
                        });
                    }
                }).show();
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
                        List<Homework> list = new ArrayList<>();
                        try {
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Homework homework = JSONUtil.JSONParseHomework(object);
                                list.add(homework);
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

    public void onResume(){
        super.onResume();
        queryHomework();
        mDrawerLayout.closeDrawers();
    }

}
