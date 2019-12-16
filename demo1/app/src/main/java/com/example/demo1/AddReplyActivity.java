package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ButtonBarLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.demo1.domain.Discussion;
import com.example.demo1.domain.Reply;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.TimeUtil;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class AddReplyActivity extends AppCompatActivity implements View.OnClickListener{
    private Discussion discussion;
    public static final int SUCCESS = 1;
    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    Toast.makeText(AddReplyActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reply);
        Button btn = (Button)findViewById(R.id.add_reply_add_button);
        btn.setOnClickListener(this);
        Intent intent = getIntent();
        discussion = (Discussion)intent.getSerializableExtra("discussion");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_reply_add_button:
                String content = ((EditText)findViewById(R.id.add_reply_content)).getText().toString();
                String replyDiscussion = discussion.getId();
                String time = TimeUtil.getTime();
                SharedPreferences pref = getSharedPreferences("userInfo",MODE_PRIVATE);
                String posterId = pref.getString("id", "ERROR");
                Reply reply = new Reply("", replyDiscussion, posterId, "", time, content);
                JSONObject object = JSONUtil.replyParseJSON(reply);
                String url = "http://10.0.2.2:8081/mobile/discussion/addreply";
                HttpUtil.sendHttpRequest(url, object, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Message msg = new Message();
                        msg.what = SUCCESS;
                        handler.sendMessage(msg);
                    }
                });
                break;
            default:
                break;
        }
    }
}
