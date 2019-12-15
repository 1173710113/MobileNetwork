package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.demo1.util.HttpUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddHomeworkActivity extends AppCompatActivity {
    private TimePicker timePicker;
    private DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_homework);
    }
    public void addHomework(View view){
        String id = getSharedPreferences("userInfo", Context.MODE_PRIVATE).getString("account","");
        String content = ((TextView)findViewById(R.id.edit_homework_content)).toString();
        String title =  ((TextView)findViewById(R.id.edit_homework_name)).toString();
        datePicker = findViewById(R.id.deadline_time);
        String deadline = datePicker.getYear()+"-"+datePicker.getMonth()+"-"+datePicker.getDayOfMonth();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        String date = df.format(new Date());
        //尚未完成，等course部分写完再补充
        String courseId = "1";
        System.out.println(id+"123");
        try{
            JSONObject obj = new JSONObject();
            obj.put("id","");
            obj.put("poster_id",id);
            obj.put("title",title);
            obj.put("content",content);
            obj.put("deadline",deadline);
            obj.put("postTime",date);
            obj.put("course_id",courseId);
            HttpUtil.sendHttpRequest("http://10.0.2.2:8081/mobile/homework/add", obj, new Callback() {
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
