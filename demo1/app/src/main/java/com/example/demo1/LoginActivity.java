package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void Login(View view){
        final String id = ((EditText)findViewById(R.id.user_id)).getText().toString();
        final String password = ((EditText)findViewById(R.id.user_password)).getText().toString();
        final String path = "http://10.0.2.2:8081/mobile/user/login/11/11";
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
                    if(json == null){
                        json = "fail";
                    }
                    Log.d("json:", json);
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
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }

}
