package com.example.demo1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.demo1.adapter.ReplyAdapter;
import com.example.demo1.dialog.AddReplyDialog;
import com.example.demo1.domain.Discussion;
import com.example.demo1.domain.Reply;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.TimeUtil;
import com.example.demo1.util.UIUpdateUtilImp;
import com.hjq.toast.ToastUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class CheckDiscussActivity extends AppCompatActivity implements View.OnClickListener{
    private Discussion discussion;
    private List<Reply> replyList = new ArrayList<>();
    private List<Reply> replyList2 = new ArrayList<>();
    private UIUpdateUtilImp uiUpdateList;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_discuss);
        //获得discussion
        discussion = (Discussion)getIntent().getSerializableExtra("discussion");
        listView = (ScrollListView)findViewById(R.id.list_comment);
        //显示标题和内容
        ((TextView)findViewById(R.id.check_discussion_title)).setText(discussion.getTitle());
        ((TextView)findViewById(R.id.check_discussion_content)).setText(discussion.getContent());

        final ArrayAdapter<Reply> adapter = new ReplyAdapter(CheckDiscussActivity.this, R.layout.reply_item, replyList2);
        listView.setAdapter(adapter);
        //初始化UI列表回调函数
        uiUpdateList = new UIUpdateUtilImp() {
            @Override
            public void onUIUpdate() {
                replyList2.clear();
                replyList2.addAll(replyList);
                adapter.notifyDataSetChanged();
            }
        };
        //初始化回复列表
        initReplyList();
        //发送回复
       TextView addReply = (TextView)findViewById(R.id.add_reply);
       addReply.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_reply:
                AddReplyDialog dialog = new AddReplyDialog(this);
                dialog.setCancelListener(new AddReplyDialog.IOnCancelListener() {
                    @Override
                    public void onCancel(AddReplyDialog dialog) {
                        dialog.cancel();
                    }
                }).setConfirmListener(new AddReplyDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm(final AddReplyDialog dialog) {
                        String content = dialog.getContent();
                        if(content == null || content.equals("")) {
                            ToastUtils.show("内容不能为空");
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
                                ToastUtils.show("添加失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                initReplyList();
                                ToastUtils.show("添加成功");
                            }
                        });
                        dialog.dismiss();
                    }
                }).show();
                break;
        }

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
                replyList.clear();
                replyList.addAll(DataSupport.where("replyDiscussion = ?", discussion.getId()).find(Reply.class));
                uiUpdateList.onUpdate();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                replyList.clear();
                DataSupport.deleteAll(Reply.class, "replyDiscussion = ?", discussion.getId());
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
                   uiUpdateList.onUpdate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
