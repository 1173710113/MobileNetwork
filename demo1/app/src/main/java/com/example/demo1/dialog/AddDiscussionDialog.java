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

public class AddDiscussionDialog extends Dialog implements View.OnClickListener {
    private EditText title,content;
    private TextView cancel, confirm;
    private IOnCancelListener cancelListener;
    private IOnConfirmListener confirmListener;

    public AddDiscussionDialog(@NonNull Context context) {
        super(context);
    }

    public AddDiscussionDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public AddDiscussionDialog setCancelListener(IOnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        return this;
    }

    public AddDiscussionDialog setConfirmListener(IOnConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
        return this;
    }
    public String getTitle() {
        return title.getText().toString();
    }
    public String getContent() {
        return content.getText().toString();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_discussion_dialog);
        //设置宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x*0.9); //设置为90%
        getWindow().setAttributes(p);

        title = (EditText)findViewById(R.id.add_discussion_dialog_title);
        content = (EditText) findViewById(R.id.add_discussion_dialog_content);
        cancel = (TextView) findViewById(R.id.add_discussion_dialog_cancel);
        confirm = (TextView) findViewById(R.id.add_discussion_dialog_confirm);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_discussion_dialog_cancel:
                if(cancelListener != null) {
                    cancelListener.onCancel(this);
                }
                break;
            case R.id.add_discussion_dialog_confirm:
                if(confirmListener != null) {
                    confirmListener.onConfirm(this);
                }
                break;
        }
    }

    public interface IOnCancelListener{
        void onCancel(AddDiscussionDialog dialog);
    }

    public interface IOnConfirmListener{
        void onConfirm(AddDiscussionDialog dialog);
    }
}
