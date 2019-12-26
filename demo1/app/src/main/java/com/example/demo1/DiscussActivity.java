package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.demo1.adapter.DiscussionAdapter;
import com.example.demo1.dialog.AddDiscussionDialog;
import com.example.demo1.domain.Course;
import com.example.demo1.domain.Discussion;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.TimeUtil;
import com.example.demo1.util.UIUpdateUtilImp;
import com.example.demo1.util.ValidateUtil;
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

public class DiscussActivity extends AppCompatActivity {

    private final List<Discussion> discussionList = new ArrayList<>();
    private final List<Discussion> discussionList2 = new ArrayList<>();
    private ListView listView;
    private Course course;
    private UIUpdateUtilImp uiUpdateList;
    private String father;
    private String TAG = "Discussion";
    private ArrayAdapter<Discussion> adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        Toolbar toolbar = (Toolbar) findViewById(R.id.discussion_activity_toolbar);
        setSupportActionBar(toolbar);
        course = (Course)getIntent().getSerializableExtra("course");
        father = getIntent().getStringExtra("father");
        listView = (ListView)findViewById(R.id.list_discuss);
        uiUpdateList = new UIUpdateUtilImp() {
            @Override
            public void onUIUpdate() {
                if(adapter == null) {
                    adapter = new DiscussionAdapter(DiscussActivity.this, R.layout.discussion_item, discussionList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(DiscussActivity.this, CheckDiscussActivity.class);
                            Discussion discussion = discussionList2.get(position);
                            intent.putExtra("discussion", discussion);
                            startActivity(intent);
                        }
                    });
                }
                Log.e(TAG, "通知修改" + isMainThread());
                discussionList2.clear();
                discussionList2.addAll(discussionList);
                adapter.notifyDataSetChanged();
            }
        };
        initDiscussionList();
    }

    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    private void initDiscussionList() {
        String url = null;
        if(father != null && father.equals("user_call")) {
            url = "http://10.0.2.2:8081/mobile/discussion/querydiscussionbyposter/" + getSharedPreferences("userInfo",MODE_PRIVATE).getString("id", "ERROR");
        } else {
            String courseId = course.getId();
            url = "http://10.0.2.2:8081/mobile/discussion/querydiscussion/" + courseId;
        }
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                discussionList.clear();
                if(father != null && father.equals("user_call")){
                    discussionList.addAll(DataSupport.where("posterId = ?", getSharedPreferences("userInfo",MODE_PRIVATE).getString("id", "ERROR")).find(Discussion.class));
                } else {
                    discussionList.addAll(DataSupport.where("courseId = ?", course.getId()).find(Discussion.class));
                }
                uiUpdateList.onUpdate();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                try {
                    discussionList.clear();
                    if(father != null && father.equals("user_call")){
                        DataSupport.deleteAll(Discussion.class, "posterId = ?", getSharedPreferences("userInfo",MODE_PRIVATE).getString("id", "ERROR"));
                    } else {
                        DataSupport.deleteAll(Discussion.class, "courseId = ?", course.getId());
                    }
                    JSONArray jsonArray = new JSONArray(data);
                    for(int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        Discussion discussion = JSONUtil.JSONParseDiscussion(object);
                        discussion.save();
                       discussionList.add(discussion);
                    }
                    Log.e(TAG, "获得数据"+ isMainThread());
                    uiUpdateList.onUpdate();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filemanage, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_toolbar_add:
                AddDiscussionDialog dialog = new AddDiscussionDialog(DiscussActivity.this);
                dialog.setCancelListener(new AddDiscussionDialog.IOnCancelListener() {
                    @Override
                    public void onCancel(AddDiscussionDialog dialog) {
                        dialog.dismiss();
                    }
                }).setConfirmListener(new AddDiscussionDialog.IOnConfirmListener() {
                    @Override
                    public void onConfirm(final AddDiscussionDialog dialog) {
                        String title = dialog.getTitle();
                        String content = dialog.getContent();
                        if(ValidateUtil.isEmpty(title)) {
                            ToastUtils.show("标题不能为空");
                            return;
                        }
                        String posterId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", "ERROR");
                        String posterName = getSharedPreferences("userInfo", MODE_PRIVATE).getString("name", "ERROR");
                        String courseId = course.getId();
                        String time = TimeUtil.getTime();
                        Discussion discussion = new Discussion(null, posterId, posterName, courseId, time, title, content, 0);
                        JSONObject object = JSONUtil.DiscussionParseJSON(discussion);
                        HttpUtil.sendHttpRequest("http://10.0.2.2:8081/mobile/discussion/add", object, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                ToastUtils.show("发送失败");
                                dialog.dismiss();
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                dialog.dismiss();
                                ToastUtils.show("添加成功");
                                initDiscussionList();
                            }
                        });
                    }
                }).show();
                break;
            default:
                break;
        }
        return true;
    }
}
