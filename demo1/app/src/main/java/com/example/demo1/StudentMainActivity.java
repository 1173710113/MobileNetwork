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

import com.example.demo1.adapter.CourseRecyclerAdapter;
import com.example.demo1.dialog.CustomDialog;
import com.example.demo1.dialog.EnrollDialog;
import com.example.demo1.domain.Course;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.MyNavView;
import com.example.demo1.util.ValidateUtil;
import com.google.android.material.navigation.NavigationView;
import com.hjq.toast.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class StudentMainActivity extends BaseActivity {
    private DrawerLayout mDrawerLayout;
    //private static String myip = "172.20.10.2";
    private static String myip="10.0.2.2";
    private List<Course> courseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private CourseRecyclerAdapter adapter;
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
                queryCourse();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.custom_layout_1_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new CourseRecyclerAdapter(courseList);
        adapter.setDeleteListener(new CourseRecyclerAdapter.IOnDeleteListener() {
            @Override
            public void onDelete(final String courseId) {
                final CustomDialog dialog = new CustomDialog(StudentMainActivity.this);
                dialog.setTitle("提示").setContent("确认退选课程吗？").setCancelListener(new CustomDialog.IOnCancelListener() {
                    @Override
                    public void onCancel() {
                        dialog.dismiss();
                    }
                }).setConfirmListener(new CustomDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        String studentId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", null);
                        if (ValidateUtil.isEmptys(Arrays.asList(studentId, courseId))) {
                            ToastUtils.show("退选失败");
                            return;
                        }
                        String url = "http://"+ myip+ ":8081/mobile/course/drop/" + studentId + "/" + courseId;
                        HttpUtil.sendHttpRequest(url, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                ToastUtils.show("退选失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String data = response.body().string();
                                if (!data.equals("success")) {
                                    ToastUtils.show("退选失败");
                                } else {
                                    ToastUtils.show("退选成功");
                                    dialog.dismiss();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            queryCourse();
                                        }
                                    });
                                }
                            }
                        });
                    }
                }).show();
            }
        });
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_layout_1_toolbar);
        setSupportActionBar(toolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.custom_layout_1_drawer);

       final NavigationView navView = (NavigationView) findViewById(R.id.custom_layout_1_nav);
       //MyNavView.initNavView(StudentMainActivity.this, StudentMainActivity.this, navView);
        //MyNavView.initNavView(AddTestActivity.this, AddTestActivity.this, navView);
        MyNavView myNavView = new MyNavView(StudentMainActivity.this, StudentMainActivity.this, navView).setNameChangeListener(new MyNavView.NameChangeListener() {
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
        queryCourse();
    }

    public void onResume(){
        super.onResume();
        mDrawerLayout.closeDrawers();
        queryCourse();
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
                final EnrollDialog dialog = new EnrollDialog(StudentMainActivity.this);
                dialog.setOnCancelListener(new EnrollDialog.IOnCancelListener() {
                    @Override
                    public void onCancel() {
                        dialog.dismiss();
                    }
                }).setOnConfirmListener(new EnrollDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        String code = dialog.getCode();
                        if (ValidateUtil.isEmpty(code) || code.length() != 6) {
                            ToastUtils.show("请输入6位选课码");
                            return;
                        }
                        String studentId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", "ERROR");
                        String url = "http://" + myip+":8081/mobile/course/enroll/" + code + "/" + studentId;
                        HttpUtil.sendHttpRequest(url, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                ToastUtils.show("选课失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String data = response.body().string();
                                if (!data.equals("success")) {
                                    ToastUtils.show("选课失败");
                                    return;
                                }
                                ToastUtils.show("选课成功");
                                dialog.dismiss();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        queryCourse();
                                    }
                                });
                            }
                        });
                    }
                }).show();
                break;
        }
        return true;
    }

    private void queryCourse() {
        String id = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", null);
        if (id == null) {
            return;
        }
        String url = "http://" + myip +":8081/mobile/course/query/student/" + id;
        OkHttpClient client = new OkHttpClient();

        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ToastUtils.show("刷新失败");
                        courseList.clear();
                        courseList.addAll(DataSupport.findAll(Course.class));
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                if (ValidateUtil.isEmpty(data)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            courseList.clear();
                            courseList.addAll(DataSupport.findAll(Course.class));
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        }
                    });
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
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
