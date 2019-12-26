package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.demo1.adapter.SingleChoiceAdapter;
import com.example.demo1.dialog.AddQuestionDialog;
import com.example.demo1.dialog.AddTestDialog;
import com.example.demo1.dialog.CustomDialog;
import com.example.demo1.domain.SingleChoiceQuestion;
import com.example.demo1.util.ValidateUtil;
import com.hjq.toast.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddTestActivity extends AppCompatActivity implements View.OnClickListener{
    private List<SingleChoiceQuestion> questionList = new ArrayList<>();
    private ListView listView;
    private ArrayAdapter<SingleChoiceQuestion> adapter;
    private TextView addText, postText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test2);
        listView = (ListView)findViewById(R.id.add_question_list);
        adapter = new SingleChoiceAdapter(AddTestActivity.this, R.layout.single_choice_item, questionList);
        listView.setAdapter(adapter);
        addText = (TextView)findViewById(R.id.add_question_add);
        postText = (TextView)findViewById(R.id.add_question_post);
        addText.setOnClickListener(this);
        postText.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()) {
            case R.id.add_question_add:
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
                        if(ValidateUtil.isEmptys(Arrays.asList(content, choiceA, choiceB,choiceC, choiceD))) {
                            ToastUtils.show("请填完");
                            return;
                        }
                        SingleChoiceQuestion question = new SingleChoiceQuestion("", content, choiceA, choiceB,choiceC,choiceD, answer, "", "");
                        questionList.add(question);
                        adapter.notifyDataSetChanged();
                        ToastUtils.show("添加成功");
                        dialog.dismiss();
                    }
                }).show();
                break;
            case R.id.add_question_post:
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
                                if(ValidateUtil.isEmptys(Arrays.asList(name, startDate, endDate))) {
                                    ToastUtils.show("请填完");
                                    return;
                                }
                                ToastUtils.show("发布成功");
                                dialog2.dismiss();
                            }
                        }).show();
                        dialog1.dismiss();
                    }
                }).show();
                break;
        }
    }
}
