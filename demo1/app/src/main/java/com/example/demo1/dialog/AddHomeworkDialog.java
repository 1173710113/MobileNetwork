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

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.demo1.R;
import com.example.demo1.util.TimeUtil;

import java.util.Date;

public class AddHomeworkDialog extends Dialog implements View.OnClickListener {
    private EditText title,content;
    private TextView timePicker, cancel, confirm;
    private Date date;
    private IOnPickerListener pickerListener;
    private IOnCancelListener cancelListener;
    private IOnConfirmListener confirmListener;

    public AddHomeworkDialog(@NonNull Context context) {
        super(context);
    }

    public AddHomeworkDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public AddHomeworkDialog setPickerListener(IOnPickerListener pickerListener) {
        this.pickerListener = pickerListener;
        return this;
    }

    public AddHomeworkDialog setCancelListener(IOnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        return this;
    }

    public AddHomeworkDialog setConfirmListener(IOnConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
        return this;
    }
    public String getTitle() {
        return title.getText().toString();
    }

    public String getContent() {
        return content.getText().toString();
    }

    public void setDate(Date date) {
        this.date = date;
        timePicker.setText("截止时间：" + TimeUtil.parseTime(date));
    }

    public Date getDate() {
        return date;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_homework_dialog);
        //设置宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x*0.9); //设置为90%
        getWindow().setAttributes(p);

        title = (EditText)findViewById(R.id.add_homework_dialog_title);
        content = (EditText) findViewById(R.id.add_homework_dialog_content);
        timePicker = (TextView)findViewById(R.id.add_homework_dialog_picker);
        cancel = (TextView) findViewById(R.id.add_homework_dialog_cancel);
        confirm = (TextView) findViewById(R.id.add_homework_dialog_confirm);
        timePicker.setOnClickListener(this);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_homework_dialog_picker:
                if(pickerListener != null) {
                    pickerListener.onPicker(this);
                }
                break;
            case R.id.add_homework_dialog_cancel:
                if(cancelListener != null) {
                    cancelListener.onCancel(this);
                }
                break;
            case R.id.add_homework_dialog_confirm:
                if(confirmListener != null) {
                    confirmListener.onConfirm(this);
                }
                break;
        }
    }

    public interface IOnPickerListener{
        void onPicker(AddHomeworkDialog dialog);
    }

    public interface IOnCancelListener{
        void onCancel(AddHomeworkDialog dialog);
    }

    public interface IOnConfirmListener{
        void onConfirm(AddHomeworkDialog dialog);
    }

}
