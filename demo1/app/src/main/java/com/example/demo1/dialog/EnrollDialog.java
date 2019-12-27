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

public class EnrollDialog extends Dialog implements View.OnClickListener{

    private EditText codeEdit;
    private TextView cancel, confirm;
    private IOnCancelListener onCancelListener;
    private IOnConfirmListener onConfirmListener;

    public EnrollDialog(@NonNull Context context) {
        super(context);
    }

    public EnrollDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enroll_dialog);

        //设置宽度
        WindowManager m = getWindow().getWindowManager();
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = getWindow().getAttributes();
        Point size = new Point();
        d.getSize(size);
        p.width = (int)(size.x*0.9); //设置为90%
        getWindow().setAttributes(p);

        codeEdit = (EditText)findViewById(R.id.enroll_dialog_code);
        cancel = (TextView)findViewById(R.id.enroll_dialog_cancel);
        confirm = (TextView)findViewById(R.id.enroll_dialog_confirm);
        cancel.setOnClickListener(this);
        confirm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enroll_dialog_cancel:
                if(onCancelListener != null) {
                    onCancelListener.onCancel();
                }
                break;
            case R.id.enroll_dialog_confirm:
                if(onConfirmListener != null) {
                    onConfirmListener.onConfirm();
                }
        }
    }

    public EnrollDialog setOnCancelListener(IOnCancelListener onCancelListener) {
        this.onCancelListener = onCancelListener;
        return this;
    }

    public EnrollDialog setOnConfirmListener(IOnConfirmListener onConfirmListener) {
        this.onConfirmListener = onConfirmListener;
        return this;
    }

    public String getCode() {
        return codeEdit.getText().toString();
    }

    public interface IOnCancelListener{
        void onCancel();
    }

    public interface IOnConfirmListener{
        void onConfirm();
    }
}
