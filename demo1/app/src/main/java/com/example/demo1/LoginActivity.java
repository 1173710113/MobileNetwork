package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import java.util.Iterator;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void Login(View view){
        String id = ((EditText)findViewById(R.id.user_id)).getText().toString();
        String password = ((EditText)findViewById(R.id.user_password)).getText().toString();
        final String path = "http://10.0.2.2:8081/mobile/user/login";

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
    private int init(String name,String pwd){
//  192.168.3.138 这个ip地址是电脑Ipv4 地址 /20170112 是服务端的项目名称  /login/toJsonMain 是@RequestMapping的地址
        String urlPath="http://10.0.2.2:8081/mobile/user/login";
        //    String urlPath="http://192.168.42.207:8080/20170112/login/toJsonMain.action"; 这个是实体机(手机)的端口
        URL url;
        int id=0;
        try {
            url=new URL(urlPath);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("username",name);  //参数put到json串里
            jsonObject.put("password",pwd);

            //JSONObject Authorization =new JSONObject();
            //   Authorization.put("po类名",jsonObject 即po的字段)

            String content=String.valueOf(jsonObject);  //json串转string类型

            HttpURLConnection conn=(HttpURLConnection) url.openConnection(); //开启连接
            conn.setConnectTimeout(5000);

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("ser-Agent", "Fiddler");
            conn.setRequestProperty("Content-Type","application/json");
            //写输出流，将要转的参数写入流里
            OutputStream os=conn.getOutputStream();
            os.write(content.getBytes()); //字符串写进二进流
            os.close();

            int code=conn.getResponseCode();
            if(code==200){   //与后台交互成功返回 200
                //读取返回的json数据
                InputStream inputStream=conn.getInputStream();
                // 调用自己写的NetUtils() 将流转成string类型
                String json= NetUtils.readString(inputStream);

                Gson gson=new Gson();  //引用谷歌的json包
                User user=gson.fromJson(json,User.class); //谷歌的解析json的方法

                id =user.getId();  //然后user.get你想要的值
                String username=user.getUsername();
                String password=user.getPassword();

            }else{
                Toast.makeText (getApplicationContext(),"数据提交失败", Toast.LENGTH_LONG ).show();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return  id;
    }
}
