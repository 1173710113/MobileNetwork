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
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.demo1.adapter.TeacherCourseRecyclerAdapter;
import com.example.demo1.dialog.AddCourseDialog;
import com.example.demo1.dialog.CustomDialog;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.TeacherCourse;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.ValidateUtil;
import com.google.android.material.navigation.NavigationView;
import com.hjq.toast.ToastUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TeacherMainActivity extends BaseActivity {

    private DrawerLayout mDrawerLayout;
    private List<TeacherCourse> courseList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TeacherCourseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_main_drawer);
        recyclerView = (RecyclerView) findViewById(R.id.student_main_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TeacherCourseRecyclerAdapter(courseList);
        adapter.setDeleteListener(new TeacherCourseRecyclerAdapter.IOnDeleteListener() {
            @Override
            public void onDelete(final String courseId) {
                final CustomDialog dialog = new CustomDialog(TeacherMainActivity.this);
                dialog.setTitle("提示").setContent("确认删除课程吗？").setCancelListener(new CustomDialog.IOnCancelListener() {
                    @Override
                    public void onCancel() {
                        dialog.dismiss();
                    }
                }).setConfirmListener(new CustomDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        String url = "http://10.0.2.2:8081/mobile/course/delete/" +courseId;
                        HttpUtil.sendHttpRequest(url, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                ToastUtils.show("删除失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                ToastUtils.show("删除成功");
                                queryCourse();
                                dialog.dismiss();
                            }
                        });
                    }
                }).show();
            }
        });
        recyclerView.setAdapter(adapter);

        Toolbar toolbar = (Toolbar) findViewById(R.id.student_main_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.student_main_drawer);
        NavigationView navView = (NavigationView) findViewById(R.id.student_main_nav);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
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

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.main_toolbar_add:
                final AddCourseDialog dialog = new AddCourseDialog(TeacherMainActivity.this);
                dialog.setStartDateListener(new AddCourseDialog.IOnStartDateListener() {
                    @Override
                    public void onStartDate() {
                        //设置TimePicker
                        TimePickerView pvTime = new TimePickerBuilder(TeacherMainActivity.this, new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                dialog.setStartDate(date);
                            }
                        })
                                .setType(new boolean[]{true, true, true, true, true, true})
                                .setCancelText("取消")//取消按钮文字
                                .setSubmitText("确定")//确认按钮文字
                                .isDialog(true)
                                .build();
                        pvTime.show();
                    }
                }).setEndDateListener(new AddCourseDialog.IOnEndDateListener() {
                    @Override
                    public void onEndDate() {
                        //设置TimePicker
                        TimePickerView pvTime = new TimePickerBuilder(TeacherMainActivity.this, new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                dialog.setEndDate(date);
                            }
                        })
                                .setType(new boolean[]{true, true, true, true, true, true})
                                .setCancelText("取消")//取消按钮文字
                                .setSubmitText("确定")//确认按钮文字
                                .isDialog(true)
                                .build();
                        pvTime.show();
                    }
                }).setCancelListener(new AddCourseDialog.IOnCancelListener() {
                    @Override
                    public void onCancel() {
                        dialog.dismiss();
                    }
                }).setConfirmListener(new AddCourseDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        String name = dialog.getName();
                        String destination = dialog.getDestination();
                        int maxVol = dialog.getMax();
                        String startDate = dialog.getStartDate();
                        String endDate = dialog.getEndDate();
                        if (ValidateUtil.isEmptys(Arrays.asList(name, destination, startDate, endDate))) {
                            ToastUtils.show("请填完");
                            return;
                        }
                        String id = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", null);
                        Course course = new Course("", name, id, "", maxVol, destination, startDate, endDate, 0);
                        JSONObject object = JSONUtil.CourseParseJSON(course);
                        String url = "http://10.0.2.2:8081/mobile/course/add";
                        HttpUtil.sendHttpRequest(url, object, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        queryCourse();
                                    }
                                });
                                ToastUtils.show("添加成功");
                                dialog.dismiss();
                            }
                        });

                    }
                });
                dialog.show();
                break;
        }
        return true;
    }

    private void queryCourse() {
        String id = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", null);
        if (id == null) {
            return;
        }

        String url = "http://10.0.2.2:8081/mobile/course/query/teacher/" + id;
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

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
                       courseList.clear();
                       courseList.addAll(JSONUtil.JSONParseTeacherCourse(data));
                       adapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }
}
