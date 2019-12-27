package com.example.demo1;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import com.example.demo1.adapter.ReplyRecyclerAdapter;
import com.example.demo1.adapter.SingleChoiceRecyclerAdapter;
import com.example.demo1.domain.Reply;
import com.example.demo1.domain.SingleChoiceQuestion;
import com.example.demo1.domain.Test;
import com.example.demo1.util.TimeUtil;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

public class TestDetailActivity extends AppCompatActivity {

    private Test test;
    private String count;
    TextView titleText, startDateText, endDateText, countText;
    private List<SingleChoiceQuestion> questionList = new ArrayList<>();
    private RecyclerView recyclerView;
    private SingleChoiceRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_detail);
        test = (Test)getIntent().getSerializableExtra("test");
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.test_detail_collapse_toolbar);

        titleText = (TextView)findViewById(R.id.test_detail_title);
        startDateText = (TextView)findViewById(R.id.test_detail_start_date);
        endDateText = (TextView) findViewById(R.id.test_detail_end_date);
        countText = (TextView)findViewById(R.id.test_detail_count);

        titleText.setText(test.getName());
        startDateText.setText(test.getStartTime());
        endDateText.setText(test.getEndTime());
        countText.setText(count);

        Toolbar toolbar = (Toolbar)findViewById(R.id.test_detail_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = (RecyclerView)findViewById(R.id.test_detail_recycler_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new SingleChoiceRecyclerAdapter(questionList);
        recyclerView.setAdapter(adapter);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_toolbar, menu);
        return true;
    }

    public void queryQuestion() {
        
    }
}
