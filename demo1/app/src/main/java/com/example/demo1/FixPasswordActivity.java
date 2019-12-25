package com.example.demo1;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo1.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class FixPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fix_password);
        Button define = (Button)findViewById(R.id.define);
        define.setOnClickListener(this);
        ((TextView)findViewById(R.id.fix_password_user_id)).setText(getSharedPreferences("userInfo", MODE_PRIVATE).getString("password", "ERROR"));
        ((TextView)findViewById(R.id.fix_password_user_name)).setText(getSharedPreferences("userInfo", MODE_PRIVATE).getString("name", "ERROR"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.define:
                String oldPassword = ((TextView)findViewById(R.id.old_pass)).getText().toString();
                if (CheckOldPassword(oldPassword)) {
                    String new1 = ((TextView)findViewById(R.id.new_pass)).getText().toString();
                    String new2 = ((TextView)findViewById(R.id.new2_pass)).getText().toString();
                    if (CheckNew(new1, new2)) {
                        String url = "http://10.0.2.2:8081/mobile/user/updatepassword/" + new1;
                        HttpUtil.sendHttpRequest(url, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });
                    }
                }
                break;
            case R.id.open_nav:
                finish();
                break;
        }
    }

    private boolean CheckNew(String new1, String new2) {

        if (new1.equals(new2) && new1.length() >= 6) {
            return true;
        } else {
            if (new1.length() < 6) {
                Toast.makeText(this, "新密码应大于等于6位数", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "你填写的新密码两次不一致", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
    }

    private boolean CheckOldPassword(String password) {
        //原来的密码
        String oldPassword = getSharedPreferences("userInfo", MODE_PRIVATE).getString("password", "ERROR");
        if (password.equals(oldPassword)) {
            return true;
        } else {
            Toast.makeText(this, "你填写的旧密码错误", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
}
