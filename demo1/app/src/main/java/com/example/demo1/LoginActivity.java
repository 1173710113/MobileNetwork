package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.demo1.util.AES;
import com.example.demo1.util.HttpUtil;
import com.hjq.toast.ToastUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
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
        final String id = idText.getText().toString();
        final String password = passwordText.getText().toString();
        //final String url = "http://10.0.2.2:8081/mobile/user/login/" + AES.Encrypt(id,"abcdefghabcdefgh") + "/" + AES.Encrypt(password,"abcdefghabcdefgh");
        final String url = "http://10.0.2.2:8081/mobile/user/login/" + id + "/" + password;
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException  {
                if (response.code() == 200) {
                    String data = response.body().string();
                    writeUserInfo(data);
                    Log.d("LoginActivity", "LoginUser:" + data);
                    ToastUtils.show("登入成功");
                    try {
                        System.out.println(AES.Encrypt(id,"abcdefghabcdefgh"));
                    }catch (Exception e){
                        System.out.println("11");
                    }
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
