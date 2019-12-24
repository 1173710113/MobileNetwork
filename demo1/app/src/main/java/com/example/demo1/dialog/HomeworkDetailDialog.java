package com.example.demo1.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.demo1.R;

public class HomeworkDetailDialog extends Dialog {
    private String title, content, deadline;

    public HomeworkDetailDialog(@NonNull Context context) {
        super(context);
    }

    public HomeworkDetailDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public HomeworkDetailDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public HomeworkDetailDialog setContent(String content) {
        this.content = content;
        return this;
    }

    public HomeworkDetailDialog setDeadline(String deadline) {
        this.deadline = deadline;
        return this;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework_detail_dialog);
        //设置宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x*0.9); //设置为90%
        getWindow().setAttributes(p);

        TextView titleView = (TextView)findViewById(R.id.homework_detail_dialog_title);
        TextView contentView = (TextView)findViewById(R.id.homework_detail_dialog_content);
        TextView deadlineView = (TextView)findViewById(R.id.homework_detail_dialog_deadline);
        titleView.setText(title);
        contentView.setText(content);
        deadlineView.setText(deadline);
    }
}
