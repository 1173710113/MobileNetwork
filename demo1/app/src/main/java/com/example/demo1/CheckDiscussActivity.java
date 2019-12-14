package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demo1.domain.Discussion;
import com.example.demo1.util.JSONUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class CheckDiscussActivity extends AppCompatActivity {
    private Discussion discussion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_discuss);
        Intent intent = getIntent();
        String data = intent.getStringExtra("discussion");
        try {
            this.discussion = JSONUtil.JSONParseDiscussion(new JSONObject((data)));
            ((TextView)findViewById(R.id.check_discussion_title)).setText(discussion.getTitle());
            ((TextView)findViewById(R.id.check_discussion_content)).setText(discussion.getContent());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
