package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button regist = findViewById(R.id.button_regist);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
            }
        });
    }

    public void Login(View view){
        final String id = ((EditText)findViewById(R.id.user_id)).getText().toString();
        final String password = ((EditText)findViewById(R.id.user_password)).getText().toString();
        final String url = "http://10.0.2.2:8081/mobile/user/login/"+id+"/"+password;
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if(response.code() == 200) {
                    String data = response.body().string();
                    writeUserInfo(data);
                    Log.d("LoginActivity", "LoginUser:" + data);
                    ToastUtil.showToast(LoginActivity.this, "登入成功");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else  {
                    ToastUtil.showToast(LoginActivity.this, "登入失败");
                }
            }
        });

    }

    /**
     * 将返回的User写入sharedPreferrence
     * @param data
     */
    private void writeUserInfo(String data) {
        try {
            JSONObject object = new JSONObject(data);
            String id = object.getString("id");
            String name = object.getString("name");
            String type = object.getString("type");
            String sex = object.getString("sex");
            SharedPreferences sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("id",id);
            editor.putString("name", name);
            editor.putString("type", type);
            editor.putString("sex",sex);
            editor.commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
