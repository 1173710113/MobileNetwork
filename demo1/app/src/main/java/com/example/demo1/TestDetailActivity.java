package com.example.demo1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.demo1.adapter.SingleChoiceRecyclerAdapter;
import com.example.demo1.dialog.CustomDialog;
import com.example.demo1.domain.Question;
import com.example.demo1.domain.SingleChoiceQuestion;
import com.example.demo1.domain.Test;
import com.example.demo1.util.HttpUtil;
import com.example.demo1.util.JSONUtil;
import com.example.demo1.util.MyNavView;
import com.example.demo1.domain.Score;
import com.example.demo1.util.ValidateUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.navigation.NavigationView;
import com.hjq.toast.ToastUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TestDetailActivity extends AppCompatActivity {

    private Test test;
    private String count;
    TextView titleText, startDateText, endDateText, countText;
    private List<SingleChoiceQuestion> questionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SingleChoiceRecyclerAdapter adapter;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_detail);
        test = (Test) getIntent().getSerializableExtra("test");
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.test_detail_collapse_toolbar);

        titleText = (TextView) findViewById(R.id.test_detail_title);
        startDateText = (TextView) findViewById(R.id.test_detail_start_date);
        endDateText = (TextView) findViewById(R.id.test_detail_end_date);
        countText = (TextView) findViewById(R.id.test_detail_count);

        titleText.setText(test.getName());
        startDateText.setText(test.getStartTime());
        endDateText.setText(test.getEndTime());
        countText.setText(count);

        Toolbar toolbar = (Toolbar) findViewById(R.id.test_detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_white_18dp);
        }

        mDrawerLayout = (DrawerLayout) findViewById(R.id.test_detail_drawer);

        NavigationView navView = (NavigationView) findViewById(R.id.test_detail_nav);
        MyNavView.initNavView(TestDetailActivity.this, TestDetailActivity.this, navView);

        recyclerView = (RecyclerView) findViewById(R.id.test_detail_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SingleChoiceRecyclerAdapter(questionList);
        recyclerView.setAdapter(adapter);

        queryQuestion();
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
                String type = getSharedPreferences("userInfo", MODE_PRIVATE).getString("type", null);
                switch (type){
                    case "学生":
                        final CustomDialog dialog = new CustomDialog(TestDetailActivity.this);
                        dialog.setTitle("提示").setContent("确认提交？").setCancelListener(new CustomDialog.IOnCancelListener() {
                            @Override
                            public void onCancel() {
                                dialog.dismiss();
                            }
                        }).setConfirmListener(new CustomDialog.IOnConfirmListener() {
                            @Override
                            public void onConfirm() {
                                String testId = test.getId();
                                String studentId = getSharedPreferences("userInfo", MODE_PRIVATE).getString("id", null);
                                String everyScore = adapter.getScore();
                                List<String> scoreList = Arrays.asList(everyScore.split("/"));
                                int scoreInt = 0;
                                for(String s:scoreList) {
                                    String c = s.substring(0,1);
                                    scoreInt += Integer.parseInt(c);
                                }
                                String scoreStr = Integer.toString(scoreInt);
                                Score score = new Score(null, testId, studentId, scoreStr, everyScore);
                                JSONObject object = JSONUtil.ScoreParseJSON(score);
                                String url = "http://10.0.2.2:8081/mobile/test/addscore";
                                HttpUtil.sendHttpRequest(url, object, new Callback() {
                                    @Override
                                    public void onFailure(Call call, IOException e) {

                                    }

                                    @Override
                                    public void onResponse(Call call, Response response) throws IOException {
                                        ToastUtils.show("提交成功");
                                        finish();
                                    }
                                });
                            }
                        }).show();
                        System.out.println(adapter.getScore());
                }
        }
        return true;
    }

    public void queryQuestion() {
        String url = "http://10.0.2.2:8081/mobile/test/getteststudent/" + test.getId();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String count = response.body().string();
                String url = "http://10.0.2.2:8081/mobile/test/getquestion/" + test.getId();
                HttpUtil.sendHttpRequest(url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String data = response.body().string();
                        if (!ValidateUtil.isEmpty(data)) {
                            List<Question> list = JSONUtil.JSOONParseQuestion(data);
                            final List<SingleChoiceQuestion> list2 = new ArrayList<>();
                            for (int i = 0; i < list.size(); i++) {
                                list2.add(new SingleChoiceQuestion(list.get(i)));
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    questionList.clear();
                                    questionList.addAll(list2);
                                    adapter.notifyDataSetChanged();
                                    countText.setText(count);
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
