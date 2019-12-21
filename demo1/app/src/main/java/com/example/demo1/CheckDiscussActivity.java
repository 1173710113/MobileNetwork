package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo1.adapter.ReplyAdapter;
import com.example.demo1.domain.Discussion;
import com.example.demo1.domain.Reply;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.TimeUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CheckDiscussActivity extends AppCompatActivity{
    private Discussion discussion;
    private static final int UPDATE_LIST = 1;
    private static final int SUCCESS = 2;
    private List<Reply> replyList = new ArrayList<>();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what) {
                case UPDATE_LIST:
                    ArrayAdapter<Reply> adapter = new ReplyAdapter(CheckDiscussActivity.this, R.layout.reply_item, replyList);
                    ListView listView = (ScrollListView)findViewById(R.id.list_comment);
                    listView.setAdapter(adapter);
                    break;
                case SUCCESS:
                    Toast.makeText(CheckDiscussActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_discuss);
        Intent intent = getIntent();
        //获得discussion
        discussion = (Discussion)getIntent().getSerializableExtra("discussion");
        //显示标题和内容
        ((TextView)findViewById(R.id.check_discussion_title)).setText(discussion.getTitle());
        ((TextView)findViewById(R.id.check_discussion_content)).setText(discussion.getContent());
        //初始化回复列表
        replyList.clear();
        initReplyList();
        //发送回复
        final EditText addReply = (EditText)findViewById(R.id.add_reply);
        addReply.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode() && KeyEvent.ACTION_DOWN == event.getAction())) {
                    addReply();
                    return true;
                    }
                return false;
            }
        });
    }

    private void addReply() {
        String content = ((EditText)findViewById(R.id.add_reply)).getText().toString();
        if(content == null || content.equals("")) {
            return;
        }
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
    }


    @Override
    public void onResume(){
        super.onResume();
        initReplyList();
    }


    /**
     * 向服务器请求回复数据
     */
    public void initReplyList() {
        if(discussion == null)
        {
            return;
        }
        //请求的url
        String url = "http://10.0.2.2:8081/mobile/discussion/queryreply/" + discussion.getId();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                replyList.clear();
                String data = response.body().string();
                System.out.println(data);
                try {
                    JSONArray jsonArray = new JSONArray(data);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Reply reply = JSONUtil.JSONParseReply(object);
                        reply.save();
                        replyList.add(reply);
                    }
                    Message message = new Message();
                    message.what = UPDATE_LIST;
                    handler.sendMessage(message);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
