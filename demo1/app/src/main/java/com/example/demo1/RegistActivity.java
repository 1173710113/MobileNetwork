package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.demo1.util.AESUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
    }

    public void Regist(View view) {
        final String id = AESUtil.encryptAES(((EditText) findViewById(R.id.user_id)).getText().toString());
        final String password = AESUtil.encryptAES(((EditText) findViewById(R.id.user_password)).getText().toString());
        final String name = AESUtil.encryptAES(((EditText) findViewById(R.id.user_name)).getText().toString());
        final String type = ((Spinner) findViewById(R.id.user_type)).getSelectedItem().toString();
        final String sex = ((Spinner) findViewById(R.id.user_sex)).getSelectedItem().toString();
        try {
            JSONObject obj = new JSONObject();
            obj.put("id", id);
            obj.put("password", password);
            obj.put("type", type);
            obj.put("name", name);
            obj.put("sex", sex);
            obj.put("iconpath", "123457");
            OkHttpClient client = new OkHttpClient();
            MediaType mtype = MediaType.parse("application/json;charset=utf-8");
            RequestBody requestBody = RequestBody.create(mtype, "" + obj.toString());
            System.out.println(obj.toString());
            Request request = new Request.Builder()
                    .url("http://10.0.2.2:8081/mobile/user/add")
                    .post(requestBody)
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
            startActivity(intent);
        }


    }
}
