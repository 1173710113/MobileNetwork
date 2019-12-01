package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class DrawActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        Button button = (Button) findViewById(R.id.button_start);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                int student_number = 10;  //从数据库读取的学生数量
                Random r = new Random();
                int number = r.nextInt(student_number);
                String name = "xie";  //根据随机的学号所查到的学生姓名

                TextView tv_name = (TextView) findViewById(R.id.textview_name);
                TextView tv_number = (TextView) findViewById(R.id.textview_number);
                tv_name.setText(name);
                tv_number.setText(Integer.toString(number));
            }
        });



    }
}