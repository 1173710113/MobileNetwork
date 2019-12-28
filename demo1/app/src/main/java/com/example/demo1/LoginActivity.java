package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.demo1.domain.Course;
import com.example.demo1.domain.Discussion;
import com.example.demo1.domain.LocalFile;
import com.example.demo1.domain.Reply;
import com.example.demo1.domain.Score;
import com.example.demo1.domain.SingleChoiceQuestion;
import com.example.demo1.domain.Test;
import com.example.demo1.domain.User;
import com.example.demo1.util.AES;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.hjq.toast.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //private static String myip = "172.20.10.2";
    private static String myip="10.0.2.2";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText idText, passwordText;
    private CheckBox rememberPass, autoLogin;
    private Button loginBtn, registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ToastUtils.init(getApplication());
        idText = (EditText) findViewById(R.id.user_id);
        passwordText = (EditText) findViewById(R.id.user_password);
        rememberPass = (CheckBox) findViewById(R.id.remember_pass);
        autoLogin = (CheckBox) findViewById(R.id.auto_login);
        loginBtn = (Button) findViewById(R.id.button_login);
        registerBtn = (Button) findViewById(R.id.button_register);
        pref = getSharedPreferences("userInfo", MODE_PRIVATE);
        editor = pref.edit();
        boolean isRemember = pref.getBoolean("remember_password", false);
        if (isRemember) {
            idText.setText(pref.getString("id", ""));
            passwordText.setText(pref.getString("password", ""));
            rememberPass.setChecked(true);
        }
        boolean isAutoLogin = pref.getBoolean("auto_login", false);
        if (isAutoLogin) {
            autoLogin.setChecked(true);
            //Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            ToastUtils.show("登入成功");
            //startActivity(intent);
            LoginActivity.this.finish();
        }
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
        autoLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_login:
                if (rememberPass.isChecked()) {
                    editor.putBoolean("remember_password", true);
                }
                if (autoLogin.isChecked()) {
                    editor.putBoolean("remember_password", true);
                    editor.putBoolean("auto_login", true);
                }
                editor.apply();
                try {
                    login();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.button_register:
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                break;
            case R.id.auto_login:
                if (autoLogin.isChecked()) {
                    rememberPass.setChecked(true);
                } else {
                    rememberPass.setChecked(false);
                }
        }
    }

    public void login() throws Exception {
        //String id = AES.Encrypt(idText.getText().toString(),AES.sKey);
        //String password = AES.Encrypt(passwordText.getText().toString(), AES.sKey);
        String id = idText.getText().toString();
        String password = passwordText.getText().toString();
        System.out.println(id);
        System.out.println(password);
        System.out.println("456");
        User user = new User(id, password, null, null, null, null);
        JSONObject object = JSONUtil.UserParseJSON(user);
        String url = "http://" + myip + ":8081/mobile/user/login";
        System.out.println(url);
        HttpUtil.sendHttpRequest(url, object, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    String data = response.body().string();
                    System.out.println(data);
                    writeUserInfo(data);
                    Log.d("LoginActivity", "LoginUser:" + data);
                    ToastUtils.show("登入成功");
                    String type = getSharedPreferences("userInfo", MODE_PRIVATE).getString("type", null);
                    if(type.equals("教师")) {
                        Intent intent = new Intent(LoginActivity.this, TeacherMainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    } else {
                        Intent intent = new Intent(LoginActivity.this, StudentMainActivity.class);
                        startActivity(intent);
                        LoginActivity.this.finish();
                    }
                } else {
                    ToastUtils.show("登入失败");
                }
            }
        });
    }

    /**
     * 将返回的User写入sharedPreferrence
     *
     * @param data
     */
    private void writeUserInfo(String data) {
        try {
            JSONObject object = new JSONObject(data);
            String id = object.getString("id");
            String pre = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", null);
            if(pre == null || !id.equals(pre)) {
                DataSupport.deleteAll(Course.class);
                DataSupport.deleteAll(Discussion.class);
                DataSupport.deleteAll(Reply.class);
                DataSupport.deleteAll(SingleChoiceQuestion.class);
                DataSupport.deleteAll(Test.class);
                DataSupport.deleteAll(Score.class);
                DataSupport.deleteAll(LocalFile.class);
                Log.e("User", "新用户");
            }
            String password = object.getString("password");
            String name = object.getString("name");
            String type = object.getString("type");
            String sex = object.getString("sex");
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id", id);
            editor.putString("password", password);
            editor.putString("name", name);
            editor.putString("type", type);
            editor.putString("sex", sex);
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
