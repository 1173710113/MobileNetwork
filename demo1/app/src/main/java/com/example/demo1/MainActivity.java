package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.demo1.adapter.CourseAdapter;
import com.example.demo1.dialog.AddCourseDialog;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.User;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.UIUpdateUtilImp;
import com.example.demo1.util.ValidateUtil;
import com.hjq.toast.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private List<Course> courseList = new ArrayList<>();
    private List<Course> courseList2 = new ArrayList<>();
    private User user;
    private UIUpdateUtilImp uiUpdateList;
    private View userView;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_activity_toolbar);
        setSupportActionBar(toolbar);
        final ArrayAdapter<Course> adapter = new CourseAdapter(MainActivity.this, R.layout.course_item, courseList2);
        listView = (ListView) findViewById(R.id.list_course);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, ClassActivity.class);
                Course course = courseList2.get(position);
                intent.putExtra("course", course.toString());
                startActivity(intent);
            }
        });
        userView = (View) findViewById(R.id.user_info);
        userView.setOnClickListener(this);
        uiUpdateList = new UIUpdateUtilImp() {
            @Override
            public void onUIUpdate() {
                courseList2.clear();
                courseList2.addAll(courseList);
                adapter.notifyDataSetChanged();
            }
        };
        LitePal.getDatabase();
        initUser();
        ((TextView) this.findViewById(R.id.main_activity_user_id)).setText(user.getId());
        ((TextView) this.findViewById(R.id.main_activity_user_name)).setText(user.getName());
        initCourseList();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_toolbar_add:
                if (getSharedPreferences("userInfo", MODE_PRIVATE).getString("type", "ERROR").equals("教师")) {
                    final AddCourseDialog dialog = new AddCourseDialog(MainActivity.this);
                    dialog.setStartDateListener(new AddCourseDialog.IOnStartDateListener() {
                        @Override
                        public void onStartDate() {
                            //设置TimePicker
                            TimePickerView pvTime = new TimePickerBuilder(MainActivity.this, new OnTimeSelectListener() {
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
                            TimePickerView pvTime = new TimePickerBuilder(MainActivity.this, new OnTimeSelectListener() {
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
                            Course course = new Course("", name, user.getId(), "", maxVol, destination, startDate, endDate, 0);
                            JSONObject object = JSONUtil.CourseParseJSON(course);
                            String url = "http://10.0.2.2:8081/mobile/course/add";
                            HttpUtil.sendHttpRequest(url, object, new Callback() {
                                @Override
                                public void onFailure(Call call, IOException e) {

                                }

                                @Override
                                public void onResponse(Call call, Response response) throws IOException {
                                        initCourseList();
                                        ToastUtils.show("添加成功");
                                        dialog.dismiss();
                                }
                            });

                        }
                    });
                    dialog.show();
                }
                break;
        }
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_info:
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void initUser() {
        SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
        String id = pref.getString("id", "ERROR");
        String type = pref.getString("type", "ERROR");
        String name = pref.getString("name", "ERROR");
        String sex = pref.getString("sex", "ERROR");
        this.user = new User(id, "", type, name, sex, "");
    }

    private void initCourseList() {
        String type = user.getType();
        String url = null;
        if (type.equals("学生")) {
            url = "http://10.0.2.2:8081/mobile/course/query/student/" + user.getId();
        } else if (type.equals("教师")) {
            url = "http://10.0.2.2:8081/mobile/course/query/teacher/" + user.getId();
        }
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                courseList.clear();
                courseList.addAll(DataSupport.findAll(Course.class));
                uiUpdateList.onUpdate();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    courseList.clear();
                    DataSupport.deleteAll(Course.class);
                    String data = response.body().string();
                    try {
                        JSONArray jsonArray = new JSONArray(data);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            Course course = JSONUtil.JSONParseCourse(object);
                            course.save();
                            courseList.add(course);
                        }
                        uiUpdateList.onUpdate();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
