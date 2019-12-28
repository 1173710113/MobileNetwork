package com.example.demo1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.demo1.adapter.SingleChoiceRecyclerAdapter;
import com.example.demo1.dialog.AddQuestionDialog;
import com.example.demo1.dialog.AddTestDialog;
import com.example.demo1.dialog.CustomDialog;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Question;
import com.example.demo1.domain.QuestionContentSingleChoice;
import com.example.demo1.domain.SingleChoiceQuestion;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.MyNavView;
import com.example.demo1.util.ValidateUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.hjq.toast.ToastUtils;

import org.json.JSONArray;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddTestActivity extends AppCompatActivity {
    private List<SingleChoiceQuestion> questionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SingleChoiceRecyclerAdapter adapter;
    private Course course;
    private DrawerLayout mDrawerLayout;

    @SuppressLint("RestrictedApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_layout_1);

        course = (Course) getIntent().getSerializableExtra("course");
        recyclerView = (RecyclerView) findViewById(R.id.custom_layout_1_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SingleChoiceRecyclerAdapter(questionList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        Toolbar toolbar = (Toolbar) findViewById(R.id.custom_layout_1_toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.custom_layout_1_drawer);
        final NavigationView navView = (NavigationView) findViewById(R.id.custom_layout_1_nav);
        //MyNavView.initNavView(AddTestActivity.this, AddTestActivity.this, navView);
        MyNavView myNavView = new MyNavView(AddTestActivity.this, AddTestActivity.this, navView).setNameChangeListener(new MyNavView.NameChangeListener() {
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

        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.custom_layout_1_fab);
        fab.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CustomDialog dialog1 = new CustomDialog(AddTestActivity.this);
                dialog1.setTitle("提示").setContent("确定完成小测创建?").setCancelListener(new CustomDialog.IOnCancelListener() {
                    @Override
                    public void onCancel() {
                        dialog1.dismiss();
                    }
                }).setConfirmListener(new CustomDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        final AddTestDialog dialog2 = new AddTestDialog(AddTestActivity.this);
                        dialog2.setStartDateListener(new AddTestDialog.IOnStartDateListener() {
                            @Override
                            public void onStartDate() {
                                //设置TimePicker
                                TimePickerView pvTime = new TimePickerBuilder(AddTestActivity.this, new OnTimeSelectListener() {
                                    @Override
                                    public void onTimeSelect(Date date, View v) {
                                        dialog2.setStartDate(date);
                                    }
                                })
                                        .setType(new boolean[]{true, true, true, true, true, true})
                                        .setCancelText("取消")//取消按钮文字
                                        .setSubmitText("确定")//确认按钮文字
                                        .isDialog(true)
                                        .build();
                                pvTime.show();
                            }
                        }).setEndDateListener(new AddTestDialog.IOnEndDateListener() {
                            @Override
                            public void onEndDate() {
                                //设置TimePicker
                                TimePickerView pvTime = new TimePickerBuilder(AddTestActivity.this, new OnTimeSelectListener() {
                                    @Override
                                    public void onTimeSelect(Date date, View v) {
                                        dialog2.setEndDate(date);
                                    }
                                })
                                        .setType(new boolean[]{true, true, true, true, true, true})
                                        .setCancelText("取消")//取消按钮文字
                                        .setSubmitText("确定")//确认按钮文字
                                        .isDialog(true)
                                        .build();
                                pvTime.show();
                            }
                        }).setCancelListener(new AddTestDialog.IOnCancelListener() {
                            @Override
                            public void onCancel() {
                                dialog2.dismiss();
                            }
                        }).setConfirmListener(new AddTestDialog.IOnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                String name = dialog2.getName();
                                String startDate = dialog2.getStartDate();
                                String endDate = dialog2.getEndDate();
                                if (ValidateUtil.isEmptys(Arrays.asList(name, startDate, endDate))) {
                                    ToastUtils.show("请填完");
                                    return;
                                }
                                JSONArray jsonArray = new JSONArray();
                                for (int i = 0; i < questionList.size(); i++) {
                                    QuestionContentSingleChoice content = new QuestionContentSingleChoice(questionList.get(i));
                                    String contentStr = content.toString();
                                    Question question = new Question(null, contentStr, questionList.get(i).getAnswer(), null, "4");
                                    jsonArray.put(JSONUtil.QuestionParseJSON(question));
                                }
                                String url = "http://10.0.2.2:8081/mobile/test/addtest/" + name + "/" + startDate + "/" + endDate + "/" + course.getId();

                                HttpUtil.sendHttpRequest(url, jsonArray, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {
                                        ToastUtils.show("发布失败");
                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        ToastUtils.show("发布成功");
                                        dialog2.dismiss();
                                        AddTestActivity.this.finish();
                                    }
                                });
                            }
                        }).show();
                        dialog1.dismiss();
                    }
                }).show();
            }
        });

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
                final AddQuestionDialog dialog = new AddQuestionDialog(AddTestActivity.this);
                dialog.setOnCancelListener(new AddQuestionDialog.IOnCancelListener() {
                    @Override
                    public void onCancel() {
                        dialog.dismiss();
                    }
                }).setOnConfirmListener(new AddQuestionDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        String content = dialog.getContent();
                        String choiceA = dialog.getChoiceA();
                        String choiceB = dialog.getChoiceB();
                        String choiceC = dialog.getChoiceC();
                        String choiceD = dialog.getChoiceD();
                        String answer = dialog.getAnswer();
                        if (ValidateUtil.isEmptys(Arrays.asList(content, choiceA, choiceB, choiceC, choiceD))) {
                            ToastUtils.show("请填完");
                            return;
                        }
                        SingleChoiceQuestion question = new SingleChoiceQuestion("", content, choiceA, choiceB, choiceC, choiceD, answer, "", "1");
                        questionList.add(question);
                        adapter.notifyDataSetChanged();
                        ToastUtils.show("添加成功");
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
        return true;
    }

    public void onResume(){
        super.onResume();
        mDrawerLayout.closeDrawers();
    }
}
