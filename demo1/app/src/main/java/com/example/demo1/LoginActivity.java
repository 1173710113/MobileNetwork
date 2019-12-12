package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void Login(View view){
        final String id = ((EditText)findViewById(R.id.user_id)).getText().toString();
        final String password = ((EditText)findViewById(R.id.user_password)).getText().toString();
        final String path = "http://10.0.2.2:8081/mobile/user/login/"+id+"/"+password;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection conn = null;
                BufferedReader reader = null;
                try{

                    URL url = new URL(path);
                    conn = (HttpURLConnection)url.openConnection();
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    InputStream in = conn.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    String json = reader.readLine();
                    System.out.println(json);
                    JSONObject obj = new JSONObject();
                    obj.put("id","user1");
                    obj.put("password","123457");
                    obj.put("type","管理员");
                    obj.put("name","123457");
                    obj.put("sex","男");
                    obj.put("iconpath","123457");
                    OkHttpClient client = new OkHttpClient();

                    MediaType type = MediaType.parse("application/json;charset=utf-8");
                    RequestBody requestBody = RequestBody.create(type,""+obj.toString());
                    System.out.println(obj.toString());
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2:8081/mobile/user/add")
                            .post(requestBody)
                            .build();
                    client.newCall(request).execute();
                    if(json.equals("success")){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_LONG).show();
                    }
                    reader.close();
                } catch(Exception e)
                {
                    e.printStackTrace();
                }
                finally {
                    if(conn!=null){
                        conn.disconnect();
                    }
                }
            }
        }).start();

    }

}
