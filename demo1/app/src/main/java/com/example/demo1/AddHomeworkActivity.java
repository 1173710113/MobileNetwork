package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddHomeworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);
    }
    public void addHomework(View view){
        String id = getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("account","");
        String content = findViewById(R.id.edit_homework_content).toString();
        String title =  findViewById(R.id.edit_homework_name).toString();
        try{
            JSONObject obj = new JSONObject();
            obj.put("id",id);
            obj.put("title",title);
            obj.put("content",content);
            HttpUtil.sendHttpRequest("", obj, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
