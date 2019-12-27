package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.demo1.adapter.DiscussionRecyclerAdapter;
import com.example.demo1.dialog.AddDiscussionDialog;
import com.example.demo1.dialog.CustomDialog;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Discussion;
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
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DiscussionByCourseActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private List<Discussion> discussionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private DiscussionRecyclerAdapter adapter;
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_1);

        course = (Course)getIntent().getSerializableExtra("course");

        recyclerView = (RecyclerView)findViewById(R.id.custom_layout_1_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DiscussionRecyclerAdapter(discussionList);
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar)findViewById(R.id.custom_layout_1_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.custom_layout_1_drawer);
        NavigationView navView = (NavigationView)findViewById(R.id.custom_layout_1_nav);
        MyNavView.initNavView(DiscussionByCourseActivity.this, DiscussionByCourseActivity.this, navView);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_18dp);
        }
        queryDiscussion();
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
            case R.id.main_toolbar_add:
                AddDiscussionDialog dialog = new AddDiscussionDialog(DiscussionByCourseActivity.this);
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
                        if(ValidateUtil.isEmpty(title)) {
                            ToastUtils.show("标题不能为空");
                            return;
                        }
                        String posterId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", "ERROR");
                        String posterName = getSharedPreferences("userInfo", MODE_PRIVATE).getString("name", "ERROR");
                        String courseId = course.getId();
                        String time = TimeUtil.getTime();
                        Discussion discussion = new Discussion(null, posterId, posterName, courseId, time, title, content, 0);
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

    private void queryDiscussion() {
        String courseId = course.getId();
        String url = "http://10.0.2.2:8081/mobile/discussion/querydiscussion/" + courseId;
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

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
                        List<Discussion> list = new ArrayList<>();
                        try {
                            DataSupport.deleteAll(Discussion.class, "courseId = ?", course.getId());
                            JSONArray jsonArray = new JSONArray(data);
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Discussion discussion = JSONUtil.JSONParseDiscussion(object);
                                discussion.save();
                                list.add(discussion);
                            }
                            discussionList.clear();
                            discussionList.addAll(list);
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
