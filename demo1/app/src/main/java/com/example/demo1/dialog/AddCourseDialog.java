package com.example.demo1.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.demo1.R;
import com.example.demo1.util.TimeUtil;

import java.util.Date;

public class AddCourseDialog extends Dialog implements View.OnClickListener{
    private EditText name, destination, max;
    private TextView start, end, cancel, confirm;
    private Date startDate, endDate;
    private IOnStartDateListener onStartDateListener;
    private IOnEndDateListener onEndDateListener;
    private IOnCancelListener onCancelListener;
    private IOnConfirmListener onConfirmListener;

    public AddCourseDialog(@NonNull Context context) {
        super(context);
    }

    public AddCourseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public AddCourseDialog setStartDateListener(IOnStartDateListener onStartDateListener) {
        this.onStartDateListener = onStartDateListener;
        return this;
    }

    public AddCourseDialog setEndDateListener(IOnEndDateListener onEndDateListener){
        this.onEndDateListener = onEndDateListener;
        return this;
    }

    public AddCourseDialog setCancelListener(IOnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
        return this;
    }

    public AddCourseDialog setConfirmListener(IOnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
        return this;
    }

    public void setStartDate(Date date) {
        this.startDate = date;
        start.setText("开始时间："+ TimeUtil.parseTime(date));
    }

    public void setEndDate(Date date) {
        this.endDate = date;
        end.setText("截止时间："+ TimeUtil.parseTime(date));
    }

    public String getName() {
        return name.getText().toString();
    }

    public String getDestination(){
        return destination.getText().toString();
    }

    public int getMax(){
        return Integer.parseInt(max.getText().toString());
    }

    public String getStartDate() {
        return TimeUtil.parseTime(startDate);
    }

    public String getEndDate() {
        return TimeUtil.parseTime(endDate);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_course_dialog);
        //设置宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x*0.9); //设置为90%
        getWindow().setAttributes(p);

        name = (EditText)findViewById(R.id.add_course_dialog_name);
        destination = (EditText)findViewById(R.id.add_course_dialog_destination);
        max = (EditText)findViewById(R.id.add_course_dialog_max);
        start = (TextView)findViewById(R.id.add_course_dialog_start);
        end = (TextView)findViewById(R.id.add_course_dialog_end);
        cancel = (TextView)findViewById(R.id.add_course_dialog_cancel);
        confirm = (TextView)findViewById(R.id.add_course_dialog_confirm);
        start.setOnClickListener(this);
        end.setOnClickListener(this);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_course_dialog_start:
                if(onStartDateListener != null) {
                    onStartDateListener.onStartDate();
                }
                break;
            case R.id.add_course_dialog_end:
                if(onEndDateListener != null) {
                    onEndDateListener.onEndDate();
                }
                break;
            case R.id.add_course_dialog_cancel:
                if(onCancelListener != null){
                    onCancelListener.onCancel();
                }
                break;
            case R.id.add_course_dialog_confirm:
                if(onConfirmListener != null) {
                    onConfirmListener.onConfirm();
                }
                break;
        }
    }

    public interface IOnStartDateListener{
        void onStartDate();
    }
    public interface IOnEndDateListener{
        void onEndDate();
    }

    public interface IOnCancelListener{
        void onCancel();
    }

    public interface IOnConfirmListener{
        void onConfirm();
    }
}
