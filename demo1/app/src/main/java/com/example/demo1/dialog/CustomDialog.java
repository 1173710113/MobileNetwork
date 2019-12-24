package com.example.demo1.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.demo1.R;
import com.example.demo1.util.ValidateUtil;

public class CustomDialog extends Dialog implements View.OnClickListener {
    private TextView titleText, contentText, cancelText, confirmText;
    private CustomDialog.IOnCancelListener cancelListener;
    private CustomDialog.IOnConfirmListener confirmListener;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);
        //设置宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x*0.9); //设置为90%
        getWindow().setAttributes(p);

        titleText = (TextView) findViewById(R.id.custom_dialog_title);
        contentText = (TextView) findViewById(R.id.custom_dialog_content);
        cancelText = (TextView) findViewById(R.id.custom_dialog_cancel);
        confirmText = (TextView) findViewById(R.id.custom_dialog_confirm);
        cancelText.setOnClickListener(this);
        confirmText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.custom_dialog_cancel:
                if (cancelListener != null) {
                    cancelListener.onCancel();
                }
                break;
            case R.id.custom_dialog_confirm:
                if (confirmListener != null) {
                    confirmListener.onConfirm();
                }
        }
    }

    public CustomDialog setTitle(String title) {
        if (ValidateUtil.isEmpty(title)) {
            titleText.setText(title);
        }
        return this;
    }

    public CustomDialog setContent(String content) {
        if (ValidateUtil.isEmpty(content)) {
            contentText.setText(content);
        }
        return this;
    }

    public CustomDialog setCancelListener(IOnCancelListener cancelListener) {
        this.cancelListener = cancelListener;
        return this;
    }

    public CustomDialog setConfirmListener(IOnConfirmListener confirmListener) {
        this.confirmListener = confirmListener;
        return this;
    }

    public interface IOnCancelListener {
        void onCancel();
    }

    ;

    public interface IOnConfirmListener {
        void onConfirm();
    }

    ;
}
