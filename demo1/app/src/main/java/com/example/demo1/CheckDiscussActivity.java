package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demo1.adapter.ReplyAdapter;
import com.example.demo1.domain.Discussion;
import com.example.demo1.domain.Reply;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CheckDiscussActivity extends AppCompatActivity implements View.OnClickListener{
    private Discussion discussion;
    private static final int UPDATE_LIST = 1;
    private List<Reply> replyList = new ArrayList<>();
    private Handler handler = new Handler() {
        public void handleMessage(Message msg){
            switch (msg.what) {
                case UPDATE_LIST:
                    ArrayAdapter<Reply> adapter = new ReplyAdapter(CheckDiscussActivity.this, R.layout.reply_item, replyList);
                    ListView listView = (ScrollListView)findViewById(R.id.list_comment);
                    listView.setAdapter(adapter);
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
        String data = intent.getStringExtra("discussion");
        try {
            this.discussion = JSONUtil.JSONParseDiscussion(new JSONObject((data)));
            ((TextView)findViewById(R.id.check_discussion_title)).setText(discussion.getTitle());
            ((TextView)findViewById(R.id.check_discussion_content)).setText(discussion.getContent());
            replyList.clear();
            initReplyList();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Button btn = (Button)findViewById(R.id.add_reply_button);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.add_reply_button:
                Intent intent = new Intent(CheckDiscussActivity.this, AddReplyActivity.class);
                intent.putExtra("discussion", discussion);
                startActivity(intent);
                break;
            default:
                break;
        }
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
                        replyList.add(JSONUtil.JSONParseReply(object));
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
