package com.example.demo1;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.demo1.adapter.ReplyRecyclerAdapter;
import com.example.demo1.dialog.AddReplyDialog;
import com.example.demo1.domain.Discussion;
import com.example.demo1.domain.Reply;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.MyNavView;
import com.example.demo1.util.TimeUtil;
import com.example.demo1.util.ValidateUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
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

public class DiscussionDetailActivity extends BaseActivity {

    private Discussion discussion;
    TextView titleText, posterNameText, contentText, postDateText, countText;
    private List<Reply> replyList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ReplyRecyclerAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discussion_detail);

        //设置fba
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.discussion_detail_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryReply();
            }
        });

        //下拉刷新
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.discussion_detail_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.primary));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                queryReply();
            }
        });

        discussion = (Discussion) getIntent().getSerializableExtra("discussion");
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.discussion_detail_collapse_toolbar);

        titleText = (TextView) findViewById(R.id.discussion_detail_title);
        posterNameText = (TextView) findViewById(R.id.discussion_detail_poster_name);
        contentText = (TextView) findViewById(R.id.discussion_detail_content);
        postDateText = (TextView) findViewById(R.id.discussion_detail_post_date);
        countText = (TextView) findViewById(R.id.discussion_detail_count);

        titleText.setText(discussion.getTitle());
        posterNameText.setText(discussion.getPosterName());
        String content = discussion.getContent();
        if (ValidateUtil.isEmpty(content)) {
            contentText.setVisibility(View.GONE);
        } else {
            contentText.setText(discussion.getContent());
        }
        postDateText.setText(discussion.getPostTime());
        countText.setText(Integer.toString(discussion.getReplyCount()));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.discussion_detail_drawer);
        final NavigationView navView = (NavigationView) findViewById(R.id.discussion_detail_nav);
        // MyNavView.initNavView(DiscussionActivity.this, DiscussionActivity.this, navView);
        //MyNavView.initNavView(AddTestActivity.this, AddTestActivity.this, navView);
        MyNavView myNavView = new MyNavView(DiscussionDetailActivity.this, DiscussionDetailActivity.this, navView).setNameChangeListener(new MyNavView.NameChangeListener() {
            @Override
            public void onNameChange(final String name) {
                View view = navView.getHeaderView(0);
                final TextView nameText = view.findViewById(R.id.main_nav_header_name);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        nameText.setText(name);
                    }
                });
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.discussion_detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_18dp);
        }


        recyclerView = (RecyclerView) findViewById(R.id.discussion_detail_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReplyRecyclerAdapter(replyList);
        recyclerView.setAdapter(adapter);
        queryReply();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.main_toolbar_add:
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
                        if (content == null || content.equals("")) {
                            ToastUtils.show("内容不能为空");
                            return;
                        }
                        String replyDiscussion = discussion.getId();
                        String time = TimeUtil.getTime();
                        SharedPreferences pref = getSharedPreferences("userInfo", MODE_PRIVATE);
                        String posterId = pref.getString("id", "ERROR");
                        Reply reply = new Reply("", replyDiscussion, posterId, "", time, content);
                        final JSONObject object = JSONUtil.replyParseJSON(reply);
                        String url = "http://10.0.2.2:8081/mobile/discussion/addreply";
                        HttpUtil.sendHttpRequest(url, object, new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                ToastUtils.show("添加失败");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                queryReply();
                                ToastUtils.show("添加成功");
                            }
                        });
                        dialog.dismiss();
                    }
                }).show();
                break;
        }
        return true;
    }

    private void queryReply() {
        String url = "http://10.0.2.2:8081/mobile/discussion/queryreply/" + discussion.getId();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        ToastUtils.show("刷新失败");
                        replyList.clear();
                        replyList.addAll(DataSupport.where("replyDiscussion = ?", discussion.getId()).find(Reply.class));
                        adapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String data = response.body().string();
                if (ValidateUtil.isEmpty(data)) {
                    return;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            DataSupport.deleteAll(Reply.class, "replyDiscussion = ?", discussion.getId());
                            JSONArray jsonArray = new JSONArray(data);
                            List<Reply> list = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject object = jsonArray.getJSONObject(i);
                                Reply reply = JSONUtil.JSONParseReply(object);
                                reply.save();
                                list.add(reply);
                            }
                            replyList.clear();
                            replyList.addAll(list);
                            adapter.notifyDataSetChanged();
                            swipeRefreshLayout.setRefreshing(false);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        String url1 = "http://10.0.2.2:8081/mobile/discussion/query/id/" + discussion.getId();
        HttpUtil.sendHttpRequest(url1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                swipeRefreshLayout.setRefreshing(false);
                ToastUtils.show("刷新失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                if (!ValidateUtil.isEmpty(data)) {
                    try {
                        JSONObject object1 = new JSONObject(data);
                        final Discussion newDiscussion = JSONUtil.JSONParseDiscussion(object1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                titleText.setText(newDiscussion.getTitle());
                                posterNameText.setText(newDiscussion.getPosterName());
                                String content = newDiscussion.getContent();
                                if (ValidateUtil.isEmpty(content)) {
                                    contentText.setVisibility(View.GONE);
                                } else {
                                    contentText.setText(newDiscussion.getContent());
                                }
                                postDateText.setText(newDiscussion.getPostTime());
                                countText.setText(Integer.toString(newDiscussion.getReplyCount()));
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void onResume(){
        super.onResume();
        mDrawerLayout.closeDrawers();
        queryReply();
    }
}
