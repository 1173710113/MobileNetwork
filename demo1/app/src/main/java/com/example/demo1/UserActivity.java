package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.demo1.dialog.CustomDialog;
import com.example.demo1.util.ToastUtil;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView myDiscussionText, logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        myDiscussionText = (TextView)findViewById(R.id.my_discussion);
        logOut = (TextView)findViewById(R.id.log_out);
        myDiscussionText.setOnClickListener(this);
        logOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.log_out:
                final CustomDialog dialog = new CustomDialog(UserActivity.this);
                dialog.setTitle("提示").setContent("确认退出？").setCancelListener(new CustomDialog.IOnCancelListener() {
                    @Override
                    public void onCancel() {
                        dialog.dismiss();
                    }
                }).setConfirmListener(new CustomDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
                        pref.edit().clear().apply();
                        dialog.dismiss();
                        Intent intent = new Intent(UserActivity.this, LoginActivity.class);
                        startActivity(intent);
                        ToastUtil.showToast(UserActivity.this, "已退出");
                        UserActivity.this.finish();
                    }
                }).show();
                break;
            case R.id.my_discussion:
                Intent intent = new Intent(UserActivity.this, DiscussActivity.class);
                intent.putExtra("father", "user_call");
                startActivity(intent);
                break;
        }
    }
}
