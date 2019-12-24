package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.demo1.adapter.HomeworkAdapter;
import com.example.demo1.dialog.AddHomeworkDialog;
import com.example.demo1.dialog.HomeworkDetailDialog;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Homework;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.TimeUtil;
import com.example.demo1.util.ToastUtil;
import com.example.demo1.util.UIUpdateUtilImp;
import com.example.demo1.util.ValidateUtil;

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

public class HomeWorkActivity extends AppCompatActivity {
    private List<Homework> homeworkList = new ArrayList<>();
    private Course course;
    private UIUpdateUtilImp uiUpdateList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);
        course = (Course)getIntent().getSerializableExtra("course");
        uiUpdateList = new UIUpdateUtilImp() {
            @Override
            public void onUIUpdate() {
                HomeworkAdapter adapter = new HomeworkAdapter(
                        HomeWorkActivity.this, R.layout.homework_item, homeworkList
                );
                ListView listView = (ListView) findViewById(R.id.list_homework);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Homework homework = homeworkList.get(position);
                        HomeworkDetailDialog dialog = new HomeworkDetailDialog(HomeWorkActivity.this);
                        dialog.setTitle(homework.getTitle()).setContent(homework.getContent()).setDeadline(homework.getDeadline()).show();
                    }
                });
            }
        };
        initHomeworkList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filemanage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.add_item:
                //初始化dialog
                AddHomeworkDialog dialog = new AddHomeworkDialog(HomeWorkActivity.this);
                dialog.setPickerListener(new AddHomeworkDialog.IOnPickerListener() { //设置点击时间时间
                    @Override
                    public void onPicker(final AddHomeworkDialog dialog) {
                        //设置TimePicker
                        TimePickerView pvTime = new TimePickerBuilder(HomeWorkActivity.this, new OnTimeSelectListener() {
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
                            ToastUtil.showToast(HomeWorkActivity.this, "请填完");
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
                                initHomeworkList();
                                ToastUtil.showToast(HomeWorkActivity.this, "发布成功");
                                dialog.dismiss();
                            }
                        });
                    }
                }).show();
                break;
            default:
                break;
        }
        return true;
    }

    private void initHomeworkList() {
        String url = "http://10.0.2.2:8081/mobile/homework/init/" + course.getId();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                homeworkList.clear();
                String data = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for(int i=0; i <jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Homework homework = JSONUtil.JSONParseHomework(object);
                        homeworkList.add(homework);
                    }
                    uiUpdateList.onUpdate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
